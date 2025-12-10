package com.ale.starblog.admin.common.translation;

import cn.hutool.core.lang.Pair;
import com.ale.starblog.admin.common.constants.TranslationConstants;
import com.ale.starblog.admin.system.domain.entity.User;
import com.ale.starblog.admin.system.service.IUserService;
import com.ale.starblog.framework.common.enumeration.SwitchStatus;
import com.ale.starblog.framework.core.translation.GenericTranslationMappingData;
import com.ale.starblog.framework.core.translation.GenericTranslationMappingDataLoader;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.google.common.collect.Maps;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 用户翻译映射数据加载器
 *
 * @author Ale
 * @version 1.0.0 2025/6/3 11:49
 */
@Component
@RequiredArgsConstructor
public class UserTranslationMappingDataLoader implements GenericTranslationMappingDataLoader {

    /**
     * 用户服务
     */
    private final IUserService userService;

    @Override
    public boolean supports(String s) {
        return TranslationConstants.TRANSLATION_USER_NICKNAME.equals(s) || TranslationConstants.TRANSLATION_USER_AVATAR.equals(s);
    }

    @Override
    public Map<String, String> load(String type, Map<String, Object> params) {
        List<User> users = this.userService.lambdaQuery()
            .select(User::getId, User::getNickname, User::getAvatar)
            .eq(User::getStatus, SwitchStatus.ENABLE.getValue())
            .list();
        if (CollectionUtils.isEmpty(users)) {
            return Collections.emptyMap();
        }

        if (TranslationConstants.TRANSLATION_USER_NICKNAME.equals(type)) {
            return users.stream()
                .map(user -> Pair.of(user.getId().toString(), user.getNickname()))
                .collect(Collectors.toMap(Pair::getKey, Pair::getValue));
        }

        return users.stream()
            .map(user -> Pair.of(user.getId().toString(), user.getAvatar()))
            .collect(Collectors.toMap(Pair::getKey, Pair::getValue));
    }

    @Override
    public List<GenericTranslationMappingData> loadAll() {
        List<User> users = this.userService.lambdaQuery()
            .select(User::getId, User::getNickname, User::getAvatar)
            .eq(User::getStatus, SwitchStatus.ENABLE.getValue())
            .list();
        if (CollectionUtils.isEmpty(users)) {
            return Collections.emptyList();
        }

        Map<String, String> userNicknameMappingData = Maps.newHashMapWithExpectedSize(users.size());
        Map<String, String> userAvatarMappingData = Maps.newHashMapWithExpectedSize(users.size());
        users.forEach(user -> {
            userNicknameMappingData.put(user.getId().toString(), user.getNickname());
            userAvatarMappingData.put(user.getId().toString(), user.getAvatar());
        });

        return List.of(
            GenericTranslationMappingData.builder()
                .type(TranslationConstants.TRANSLATION_USER_NICKNAME)
                .mappingData(userNicknameMappingData)
                .build(),
            GenericTranslationMappingData.builder()
                .type(TranslationConstants.TRANSLATION_USER_AVATAR)
                .mappingData(userAvatarMappingData)
                .build()
        );
    }
}
