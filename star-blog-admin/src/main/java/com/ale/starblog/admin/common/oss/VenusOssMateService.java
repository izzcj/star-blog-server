package com.ale.starblog.admin.common.oss;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.IdUtil;
import com.ale.starblog.framework.core.oss.OssMate;
import com.ale.starblog.framework.core.oss.OssMateService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * venus oss元信息服务
 *
 * @author Ale
 * @version 1.0.0 2026/2/2 16:52
 */
@Component
@RequiredArgsConstructor
public class VenusOssMateService implements OssMateService {

    /**
     * Oss元信息Mapper
     */
    private final OssMateMapper ossMateMapper;

    @Override
    public void save(OssMate ossMate) {
        ossMate.setId(IdUtil.fastUUID());
        this.ossMateMapper.insert(ossMate);
    }

    @Override
    public void batchSave(Collection<OssMate> ossMates) {
        ossMates.forEach(ossMate -> ossMate.setId(IdUtil.fastUUID()));
        this.ossMateMapper.insert(ossMates);
    }

    @Override
    public OssMate load(String id) {
        return this.ossMateMapper.selectById(id);
    }

    @Override
    public List<OssMate> load(Collection<String> ids) {
        if (CollectionUtil.isEmpty(ids)) {
            return Collections.emptyList();
        }
        return this.ossMateMapper.selectBatchIds(ids);
    }

    @Override
    public OssMate loadByUrl(String url) {
        return this.ossMateMapper.selectOne(
            Wrappers.<OssMate>lambdaQuery().eq(OssMate::getUrl, url)
        );
    }

    @Override
    public List<OssMate> loadByUrls(Collection<String> urls) {
        if (CollectionUtil.isEmpty(urls)) {
            return Collections.emptyList();
        }
        return this.ossMateMapper.selectList(
                Wrappers.<OssMate>lambdaQuery().in(OssMate::getUrl, urls)
        );
    }

    @Override
    public void update(OssMate ossMate) {
        this.ossMateMapper.updateById(ossMate);
    }

    @Override
    public void batchUpdate(Collection<OssMate> ossMates) {
        this.ossMateMapper.updateById(ossMates);
    }

    @Override
    public void remove(String id) {
        this.ossMateMapper.deleteById(id);
    }

    @Override
    public void batchRemove(Collection<String> ids) {
        if (CollectionUtil.isEmpty(ids)) {
            return;
        }
        this.ossMateMapper.deleteByIds(ids);
    }
}
