package com.example.oldguy.datasource.services;

import com.example.oldguy.common.dto.PageResult;
import com.example.oldguy.datasource.exceptions.DataQueryException;
import com.example.oldguy.datasource.models.DbColumn;
import com.example.oldguy.datasource.models.DbQueryProperty;
import com.example.oldguy.datasource.services.api.DataQueryApi;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: AbstractDataQueryTemplate
 * @Author: ren
 * @Description:
 * @CreateTIme: 2020/1/30 0030 下午 8:24
 **/
public abstract class AbstractDataQueryTemplate implements DataQueryApi {

    protected DataSource dataSource;

    protected JdbcTemplate jdbcTemplate;

    protected DbDialect dbDialect;

    public AbstractDataQueryTemplate(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void close() {

        if (dataSource instanceof HikariDataSource) {
            ((HikariDataSource) dataSource).close();
        } else {
            throw new DataQueryException("不合法数据源类型");
        }
    }


    @Override
    public void useDatabase(String dbName) {

        String sql = dbDialect.useDatabase(dbName);
        jdbcTemplate.execute(sql);
    }

    @Override
    public List<Map<String, Object>> queryList(String sql) {
        return jdbcTemplate.queryForList(sql);
    }

    @Override
    public String getVersion() {
        return jdbcTemplate.queryForObject(dbDialect.version(), String.class);
    }

    @Override
    public List<DbColumn> getTableColumns(String dbName, String tableName) {

        String sql = dbDialect.columns(dbName, tableName);
        return jdbcTemplate.query(sql, dbDialect.rowMapper());
    }

    @Override
    public List<String> getTables(String dbName) {

        useDatabase(dbName);
        return jdbcTemplate.queryForList(dbDialect.tables(dbName), String.class);
    }

    @Override
    public List<String> getDatabases() {
        return jdbcTemplate.queryForList(dbDialect.database(), String.class);
    }

    @Override
    public int count(String sql) {
        return jdbcTemplate.queryForObject(dbDialect.count(sql), Integer.class);
    }

    @Override
    public PageResult<Map<String, Object>> queryByPage(String sql, long offset, long size) {

        int total = count(sql);

        String pageSql = dbDialect.buildPaginationSql(sql, offset, size);
        List<Map<String, Object>> records = jdbcTemplate.queryForList(pageSql);

        return new PageResult<>(total, records);
    }
}
