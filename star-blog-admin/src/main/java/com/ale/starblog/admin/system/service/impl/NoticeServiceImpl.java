package com.ale.starblog.admin.system.service.impl;

import cn.hutool.core.util.BooleanUtil;
import com.ale.starblog.admin.system.domain.pojo.notice.NoticeQuery;
import com.ale.starblog.framework.common.exception.ServiceException;
import com.ale.starblog.framework.common.utils.SecurityUtils;
import com.ale.starblog.framework.core.im.InstantMessage;
import com.ale.starblog.framework.core.im.InstantMessageSender;
import com.ale.starblog.framework.core.im.InstantMessageType;
import com.ale.starblog.framework.core.service.AbstractCrudServiceImpl;
import com.ale.starblog.framework.core.service.hook.HookContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.ale.starblog.admin.system.domain.entity.Notice;
import com.ale.starblog.admin.system.domain.pojo.notice.NoticeBO;
import com.ale.starblog.admin.system.mapper.NoticeMapper;
import com.ale.starblog.admin.system.service.INoticeService;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;

/**
 * 系统通知Service实现类
 *
 * @author Ale
 * @version 1.0.0 2025-12-23 15:19:51
 */
@Service
@RequiredArgsConstructor
public class NoticeServiceImpl extends AbstractCrudServiceImpl<NoticeMapper, Notice, NoticeBO, NoticeQuery> implements INoticeService {

    /**
     * 即时消息发送器
     */
    private final InstantMessageSender instantMessageSender;

    @Override
    public void beforeSave(Notice entity, HookContext context) {
        if (BooleanUtil.isTrue(entity.getPublished())) {
            entity.setPublishedTime(LocalDateTime.now());
            this.sendInstantMessage(entity);
        }
    }

    @Override
    public void togglePublished(Long id) {
        Notice notice = this.getOptById(id)
            .orElseThrow(() -> new ServiceException("切换通知发布状态失败！通知[{}]不存在！", id));
        notice.setPublished(!notice.getPublished());
        if (BooleanUtil.isTrue(notice.getPublished())) {
            notice.setPublishedTime(LocalDateTime.now());
            this.sendInstantMessage(notice);
        }
        this.updateById(notice);
    }

    /**
     * 发送即时消息
     *
     * @param notice 通知
     */
    private void sendInstantMessage(Notice notice) {
        this.instantMessageSender.send(
            InstantMessage.builder()
                .from(Objects.requireNonNull(SecurityUtils.getLoginUserId()).toString())
                .type(InstantMessageType.PUSH)
                .content(notice.getContent())
                .extra(Map.of("title", notice.getTitle()))
                .build()
        );
    }
}
