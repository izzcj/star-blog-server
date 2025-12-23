package com.ale.starblog.admin.system.controller;

import com.ale.starblog.admin.system.domain.entity.DictData;
import com.ale.starblog.admin.system.domain.pojo.dict.data.*;
import com.ale.starblog.admin.system.service.IDictDataService;
import com.ale.starblog.framework.common.domain.JsonResult;
import com.ale.starblog.framework.common.support.Option;
import com.ale.starblog.framework.core.convert.OptionConvertible;
import com.ale.starblog.framework.core.controller.BaseController;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 字典数据接口
 *
 * @author Ale
 * @version 1.0.0 2025/5/30 14:45
 */
@RestController
@RequestMapping("/system/dict-data")
public class DictDataController extends BaseController<DictData, IDictDataService, DictDataVO, DictDataBO, DictDataQuery, CreateDictDataDTO, ModifyDictDataDTO> {

    /**
     * 获取字典数据选项
     *
     * @param query 查询条件
     * @return 字典数据选项
     */
    @GetMapping("/options")
    public JsonResult<List<Option>> fetchOptions(DictDataQuery query) {
        List<DictDataBO> result = this.service.queryList(query);
        return JsonResult.success(
            result.stream()
                .map(OptionConvertible::convert)
                .collect(Collectors.toList())
        );
    }
}
