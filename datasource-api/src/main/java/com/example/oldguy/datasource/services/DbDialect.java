package com.example.oldguy.datasource.services;

import com.example.oldguy.datasource.models.DbColumn;
import org.springframework.jdbc.core.RowMapper;

/**
 * @ClassName: DbDialect
 * @Author: ren
 * @Description:
 * @CreateTIme: 2020/1/30 0030 下午 8:22
 **/
public interface DbDialect {

    RowMapper<DbColumn> rowMapper();

    /**
     * 使用数据库
     *
     * @param dbName
     * @return
     */
    String useDatabase(String dbName);

    /**
     * 获取指定表的所有列
     *
     * @param tableName
     * @return
     */
    String columns(String dbName, String tableName);

    /**
     * 获取数据库下的 所有表
     *
     * @param dbName
     * @return
     */
    String tables(String dbName);

    /**
     * 获取数据库版本
     *
     * @return
     */
    String version();

    /**
     * 获取指定数据源具有的数据库
     *
     * @return
     */
    String database();

    /**
     * 构建 分页 sql
     *
     * @param sql
     * @param offset
     * @param count
     * @return
     */
    String buildPaginationSql(String sql, long offset, long count);

    /**
     * 包装 count sql
     *
     * @param sql
     * @return
     */
    String count(String sql);
}
