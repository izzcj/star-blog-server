package com.ale.starblog.admin.system.service;

import cn.hutool.core.lang.Pair;
import cn.hutool.core.util.StrUtil;
import com.ale.starblog.admin.common.constants.SystemConstants;
import com.ale.starblog.admin.common.constants.TranslationConstants;
import com.ale.starblog.admin.system.domain.entity.User;
import com.ale.starblog.admin.system.domain.pojo.user.UserBO;
import com.ale.starblog.admin.system.domain.pojo.user.UserQuery;
import com.ale.starblog.admin.system.mapper.UserMapper;
import com.ale.starblog.framework.common.enumeration.SwitchStatus;
import com.ale.starblog.framework.common.exception.ServiceException;
import com.ale.starblog.framework.common.support.PatchData;
import com.ale.starblog.framework.core.service.AbstractCrudService;
import com.ale.starblog.framework.core.service.hook.HookContext;
import com.ale.starblog.framework.core.translation.GenericTranslationSupport;
import com.ale.starblog.framework.core.translation.TranslationPatchType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * 用户服务实现类
 *
 * @author Ale
 * @version 1.0.0
 * @since 2025/3/13
 */
@Service
@RequiredArgsConstructor
public class UserService extends AbstractCrudService<UserMapper, User, UserBO, UserQuery> {

    /**
     * 密码编码器
     */
    private final PasswordEncoder passwordEncoder;

    @Override
    public void beforeCreate(User entity, HookContext context) {
        entity.setStatus(SwitchStatus.ENABLE);
        if (StrUtil.isBlank(entity.getPassword())) {
            entity.setPassword(this.passwordEncoder.encode(SystemConstants.DEFAULT_PASSWORD));
        } else {
            entity.setPassword(this.passwordEncoder.encode(entity.getPassword()));
        }
    }

    @Override
    public void afterCreate(User entity, HookContext context) {
        this.publishUserTranslationPatch(entity, TranslationPatchType.ADD);
    }

    @Override
    public void afterModify(User entity, HookContext context) {
        this.publishUserTranslationPatch(entity, TranslationPatchType.CHANGE);
    }

    @Override
    public void afterDelete(User entity, HookContext context) {
        this.publishUserTranslationPatch(entity, TranslationPatchType.REMOVE);
    }

    /**
     * 修改用户密码
     *
     * @param id          用户ID
     * @param newPassword 新密码
     * @param oldPassword 旧密码
     */
    public void changePassword(Long id, String newPassword, String oldPassword) {
        User user = this.getOptById(id)
            .orElseThrow(() -> new ServiceException("修改密码失败！用户[{}]不存在！", id));
        if (!this.passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new ServiceException("修改密码失败！原密码错误！");
        }
        this.lambdaUpdate()
            .eq(User::getId, id)
            .set(User::getPassword, this.passwordEncoder.encode(newPassword))
            .update();
    }

    /**
     * 重置用户密码
     *
     * @param id 用户ID
     */
    public void resetPassword(Long id) {
        User user = this.getOptById(id)
            .orElseThrow(() -> new ServiceException("重置密码失败！用户[{}]不存在！", id));
        user.setPassword(this.passwordEncoder.encode(SystemConstants.DEFAULT_PASSWORD));
        this.updateById(user);
    }

    /**
     * 发布用户翻译补丁事件
     *
     * @param entity    用户实体
     * @param patchType 补丁类型
     */
    private void publishUserTranslationPatch(User entity, TranslationPatchType patchType) {
        String userId = entity.getId().toString();

        PatchData<Pair<String, String>> nicknamePatch = GenericTranslationSupport.buildPatchData(
            Pair.of(userId, entity.getNickname()),
            patchType
        );

        PatchData<Pair<String, String>> avatarPatch = GenericTranslationSupport.buildPatchData(
            Pair.of(userId, entity.getAvatar()),
            patchType
        );

        GenericTranslationSupport.publishUpdateEvent(
            TranslationConstants.TRANSLATION_USER_NICKNAME,
            nicknamePatch
        );

        GenericTranslationSupport.publishUpdateEvent(
            TranslationConstants.TRANSLATION_USER_AVATAR,
            avatarPatch
        );
    }
}
