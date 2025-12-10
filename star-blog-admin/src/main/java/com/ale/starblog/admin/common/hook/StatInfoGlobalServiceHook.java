package com.ale.starblog.admin.common.hook;

import cn.hutool.core.annotation.AnnotationUtil;
import com.ale.starblog.admin.common.annotations.StatInfo;
import com.ale.starblog.admin.common.cache.CacheableHotspotDataManager;
import com.ale.starblog.admin.common.support.StatInfoSupport;
import com.ale.starblog.framework.common.domain.entity.BaseEntity;
import com.ale.starblog.framework.core.service.hook.GlobalServiceHook;
import com.ale.starblog.framework.core.service.hook.HookContext;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 统计信息全局服务钩子
 *
 * @author Ale
 * @version 1.0.0 2025/12/10 10:22
 */
@Component
public class StatInfoGlobalServiceHook extends StatInfoSupport implements GlobalServiceHook {

    public StatInfoGlobalServiceHook(CacheableHotspotDataManager cacheableHotspotDataManager) {
        super(cacheableHotspotDataManager);
    }

    @Override
    public void afterCreate(BaseEntity entity, HookContext context) {
        StatInfo statInfo = AnnotationUtil.getAnnotation(entity.getClass(), StatInfo.class);
        if (statInfo != null) {
            this.updateStatInfo(statInfo.type(), 1, true);
        }
    }

    @Override
    public void afterBatchCreate(List<BaseEntity> entityList, HookContext context) {
        StatInfo statInfo = AnnotationUtil.getAnnotation(entityList.getFirst().getClass(), StatInfo.class);
        if (statInfo != null) {
            this.updateStatInfo(statInfo.type(), entityList.size(), true);
        }
    }

    @Override
    public void afterDelete(BaseEntity entity, HookContext context) {
        StatInfo statInfo = AnnotationUtil.getAnnotation(entity.getClass(), StatInfo.class);
        if (statInfo != null) {
            this.updateStatInfo(statInfo.type(), 1, false);
        }
    }

    @Override
    public void afterBatchDelete(List<BaseEntity> entityList, HookContext context) {
        StatInfo statInfo = AnnotationUtil.getAnnotation(entityList.getFirst().getClass(), StatInfo.class);
        if (statInfo != null) {
            this.updateStatInfo(statInfo.type(), entityList.size(), false);
        }
    }
}
