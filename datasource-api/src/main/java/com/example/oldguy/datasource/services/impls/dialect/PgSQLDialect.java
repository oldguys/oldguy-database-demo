package com.example.oldguy.datasource.services.impls.dialect;

import com.example.oldguy.datasource.models.DbColumn;
import com.example.oldguy.datasource.services.AbstractDialect;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;

/**
 * @ClassName: PgSQLDialect
 * @Author: ren
 * @Description:
 * @CreateTIme: 2020/1/31 0031 下午 8:36
 **/
public class PgSQLDialect extends AbstractDialect {

    @Override
    public String tables(String dbName) {
        return "select tablename from pg_tables WHERE  schemaname = 'public' AND tableowner = '" + dbName + "'";
    }

    @Override
    public String database() {
        return "SELECT distinct schemaname FROM pg_tables";
    }

    @Override
    public String columns(String dbName, String tableName) {
        String sql = "SELECT a.attnum,\n" +
                "a.attname AS field,\n" +
                "t.typname AS type,\n" +
                "a.attlen AS length,\n" +
                "a.atttypmod AS lengthvar,\n" +
                "a.attnotnull AS notnull,\n" +
                "b.description AS comment\n" +
                "FROM pg_class c,\n" +
                "pg_attribute a\n" +
                "LEFT OUTER JOIN pg_description b ON a.attrelid=b.objoid AND a.attnum = b.objsubid,\n" +
                "pg_type t\n" +
                "WHERE c.relname = 'teble_a'\n" +
                "and a.attnum > 0\n" +
                "and a.attrelid = c.oid\n" +
                "and a.atttypid = t.oid\n" +
                "ORDER BY a.attnum;";

        return sql;
    }

    @Override
    public RowMapper<DbColumn> rowMapper() {
        return (ResultSet rs, int rowNum) -> {

            DbColumn entity = new DbColumn();
            entity.setName(rs.getString("field"));
            entity.setType(rs.getString("type"));
            entity.setNullable(rs.getString("notnull"));

            return entity;
        };
    }

}
