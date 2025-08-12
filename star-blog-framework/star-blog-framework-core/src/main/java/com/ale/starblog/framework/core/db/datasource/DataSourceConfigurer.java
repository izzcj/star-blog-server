package com.ale.starblog.framework.core.db.datasource;

import com.ale.starblog.framework.common.support.Supportable;
import com.ale.starblog.framework.core.db.VenusDataBaseProperties;
import org.springframework.core.Ordered;

import javax.sql.DataSource;

/**
 * 数据源配置器
 *
 * @author Ale
 * @version 1.0.0
 * @since 2025/3/10
 */
public interface DataSourceConfigurer extends Supportable<VenusDataBaseProperties.PoolType>, Ordered {

    /**
     * 配置数据源
     *
     * @param venusDataBaseProperties 数据库配置
     * @return {@link DataSource}
     */
    DataSource config(VenusDataBaseProperties venusDataBaseProperties);
}
