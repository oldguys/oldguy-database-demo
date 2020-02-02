package com.example.oldguy.datasource.services;

import com.example.oldguy.datasource.constants.DbType;
import com.example.oldguy.datasource.models.DbQueryProperty;
import com.example.oldguy.datasource.services.api.DataQueryApi;

import javax.sql.DataSource;

/**
 * @ClassName: DataSourceTemplateFactory
 * @Author: ren
 * @Description:
 * @CreateTIme: 2020/1/31 0031 下午 4:12
 **/
public interface DataSourceTemplateFactory extends DataSourceFactory {

    /**
     * 配置实体及映射关系
     * @param dataSource
     * @param dbType
     * @return
     */
    DataQueryApi createTemplateFromMap(DataSource dataSource, DbType dbType);

    /**
     * 创建数据源
     * @param property
     * @return
     */
    DataSource createDataSource(DbQueryProperty property);
}
