package com.ale.starblog.admin.system.service.impl;

import com.ale.starblog.framework.core.service.AbstractCrudServiceImpl;
import org.springframework.stereotype.Service;
import com.ale.starblog.admin.system.domain.entity.Notice;
import com.ale.starblog.admin.system.domain.pojo.notice.NoticeBO;
import com.ale.starblog.admin.system.mapper.NoticeMapper;
import com.ale.starblog.admin.system.service.INoticeService;

/**
 * 系统公告Service实现类
 *
 * @author Ale
 * @version 1.0.0 2025-12-23 15:19:51
 */
@Service
public class NoticeServiceImpl extends AbstractCrudServiceImpl<NoticeMapper, Notice, NoticeBO> implements INoticeService {
}
