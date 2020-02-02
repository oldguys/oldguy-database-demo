package com.example.oldguy.datasource.services;

import com.example.oldguy.datasource.constants.DbType;
import com.example.oldguy.datasource.models.DbQueryProperty;
import com.example.oldguy.datasource.services.api.DataQueryApi;

import javax.sql.DataSource;

/**
 * @ClassName: DataSourceFactory
 * @Author: ren
 * @Description:
 * @CreateTIme: 2020/1/30 0030 下午 8:22
 **/
public interface DataSourceFactory {

    /**
     * 创建数据源实例
     *
     * @param property
     * @return
     */
    DataQueryApi createDataQueryTemplate(DbQueryProperty property);


}
