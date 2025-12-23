package com.ale.starblog.admin.system.controller;

import com.ale.starblog.admin.system.domain.entity.DictType;
import com.ale.starblog.admin.system.domain.pojo.dict.type.*;
import com.ale.starblog.admin.system.service.IDictTypeService;
import com.ale.starblog.framework.common.domain.JsonResult;
import com.ale.starblog.framework.core.controller.BaseController;
import org.springframework.web.bind.annotation.*;

/**
 * 字典类型接口
 *
 * @author Ale
 * @version 1.0.0 2025/5/29 11:13
 */
@RestController
@RequestMapping("/system/dict-type")
public class DictTypeController extends BaseController<DictType, IDictTypeService, DictTypeVO, DictTypeBO, DictTypeQuery, CreateDictTypeDTO, ModifyDictTypeDTO> {

    /**
     * 刷新字典缓存
     *
     * @return Void
     */
    @PostMapping("/refresh-cache")
    public JsonResult<Void> refreshCache() {
        this.service.refreshCache();
        return JsonResult.success();
    }

}
