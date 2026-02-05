package com.ale.starblog.admin.system.controller;

import com.ale.starblog.admin.system.domain.entity.Notice;
import com.ale.starblog.admin.system.domain.pojo.notice.*;
import com.ale.starblog.admin.system.service.INoticeService;
import com.ale.starblog.framework.common.domain.JsonResult;
import com.ale.starblog.framework.core.controller.BaseController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统通知接口
 *
 * @author Ale
 * @version 1.0.0 2025-12-23 15:21:37
 */
@RestController
@RequestMapping(value = "/system/notice")
public class NoticeController extends BaseController<Notice, INoticeService, NoticeVO, NoticeBO, NoticeQuery, CreateNoticeDTO, ModifyNoticeDTO> {

    /**
     * 切换发布状态
     *
     * @param id 通知ID
     * @return voi
     */
    @PutMapping("/{id}/toggle-published")
    public JsonResult<Void> togglePublished(@PathVariable Long id) {
        this.service.togglePublished(id);
        return JsonResult.success();
    }

}
