package com.ale.starblog.admin.system.service;

import com.ale.starblog.framework.core.service.ICrudService;
import com.ale.starblog.admin.system.domain.entity.Notice;
import com.ale.starblog.admin.system.domain.pojo.notice.NoticeBO;

/**
 * 系统公告服务接口
 *
 * @author Ale
 * @version 1.0.0 2025-12-23 15:17:56
 */
public interface INoticeService extends ICrudService<Notice, NoticeBO> {
}
