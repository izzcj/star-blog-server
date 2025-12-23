package com.ale.starblog.admin.system.controller;

import com.ale.starblog.admin.system.domain.entity.Notice;
import com.ale.starblog.admin.system.domain.pojo.notice.*;
import com.ale.starblog.admin.system.service.INoticeService;
import com.ale.starblog.framework.core.controller.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统公告接口
 *
 * @author Ale
 * @version 1.0.0 2025-12-23 15:21:37
 */
@RestController
@RequestMapping(value = "/system/notice")
public class NoticeController extends BaseController<Notice, INoticeService, NoticeVO, NoticeBO, NoticeQuery, CreateNoticeDTO, ModifyNoticeDTO> {
}
