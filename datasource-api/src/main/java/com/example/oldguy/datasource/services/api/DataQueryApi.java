package com.example.oldguy.datasource.services.api;

import com.example.oldguy.common.dto.PageResult;
import com.example.oldguy.datasource.models.DbColumn;

import java.util.List;
import java.util.Map;

/**
 * @ClassName: DataQueryApi
 * @Author: ren
 * @Description:
 * @CreateTIme: 2020/1/30 0030 下午 8:21
 **/
public interface DataQueryApi {

    /**
     * 使用其他数据库
     *
     * @param dbName
     * @return
     */
    void useDatabase(String dbName);

    /**
     * 关闭数据源
     */
    void close();

    /**
     * 获取版本
     *
     * @return
     */
    String getVersion();

    /**
     *  获取指定表 具有的所有字段列表
     * @param dbName
     * @param tableName
     * @return
     */
    List<DbColumn> getTableColumns(String dbName, String tableName);

    /**
     * 获取指定数据库下 所有的表信息
     *
     * @param dbName
     * @return
     */
    List<String> getTables(String dbName);

    /**
     * 获取数据库列表
     *
     * @return
     */
    List<String> getDatabases();

    /**
     * 获取总数
     *
     * @param sql
     * @return
     */
    int count(String sql);

    /**
     * 查询结果列表
     *
     * @param sql
     * @return
     */
    List<Map<String, Object>> queryList(String sql);

    /**
     * 查询结果分页
     *
     * @param sql
     * @param offset
     * @param size
     * @return
     */
    PageResult<Map<String, Object>> queryByPage(String sql, long offset, long size);
}
