package com.example.oldguy.datasource.services;

/**
 * @ClassName: AbstractDialect
 * @Author: ren
 * @Description:
 * @CreateTIme: 2020/1/30 0030 下午 10:27
 **/
public abstract class AbstractDialect implements DbDialect {

    @Override
    public String count(String sql) {
        return "SELECT COUNT(*) FROM ( " + sql + " ) temp";
    }

    @Override
    public String useDatabase(String dbName) {
        return "use `" + dbName + "`";
    }

    @Override
    public String columns(String dbName,String tableName) {
        return "show columns from `" + tableName + "`;";
    }

    @Override
    public String tables(String dbName) {
        return "show tables";
    }

    @Override
    public String version() {
        return "SELECT VERSION()";
    }

    @Override
    public String database() {
        return "show databases";
    }

    @Override
    public String buildPaginationSql(String sql, long offset, long count) {

        // 获取 分页实际条数
        StringBuilder builder = new StringBuilder(sql);
        builder.append(" LIMIT ").append(offset).append(" , ").append(count);

        return builder.toString();
    }
}
