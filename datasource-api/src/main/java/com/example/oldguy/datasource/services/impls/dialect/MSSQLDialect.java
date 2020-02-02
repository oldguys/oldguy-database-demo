package com.example.oldguy.datasource.services.impls.dialect;

import com.example.oldguy.datasource.models.DbColumn;
import com.example.oldguy.datasource.services.AbstractDialect;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.StringUtils;

import java.sql.ResultSet;

/**
 * @ClassName: MSSQLDialect
 * @Author: ren
 * @Description:
 * @CreateTIme: 2020/1/31 0031 下午 8:07
 **/
public class MSSQLDialect extends AbstractDialect {

    @Override
    public String useDatabase(String dbName) {
        return "use " + dbName;
    }

    @Override
    public RowMapper<DbColumn> rowMapper() {
        return (ResultSet rs, int rowNum) -> {

            DbColumn entity = new DbColumn();
            entity.setName(rs.getString("COLUMN_NAME"));
            entity.setType(rs.getString("TYPE_NAME"));
            entity.setNullable(rs.getString("IS_NULLABLE"));

            return entity;
        };
    }

    @Override
    public String columns(String dbName, String tableName) {
        return "sp_columns " + tableName + ";";
    }

    @Override
    public String tables(String dbName) {
        return "SELECT Name FROM " + dbName + "..SysObjects Where XType='U' ";
    }

    @Override
    public String database() {
        return "SELECT name FROM Master..SysDatabases";
    }

    private static String getOrderByPart(String sql) {
        String loweredString = sql.toLowerCase();
        int orderByIndex = loweredString.indexOf("order by");
        if (orderByIndex != -1) {
            return sql.substring(orderByIndex);
        } else {
            return "";
        }
    }

    @Override
    public String buildPaginationSql(String originalSql, long offset, long limit) {
        StringBuilder pagingBuilder = new StringBuilder();
        String orderby = getOrderByPart(originalSql);
        String distinctStr = "";

        String loweredString = originalSql.toLowerCase();
        String sqlPartString = originalSql;
        if (loweredString.trim().startsWith("select")) {
            int index = 6;
            if (loweredString.startsWith("select distinct")) {
                distinctStr = "DISTINCT ";
                index = 15;
            }
            sqlPartString = sqlPartString.substring(index);
        }
        pagingBuilder.append(sqlPartString);

        // if no ORDER BY is specified use fake ORDER BY field to avoid errors
        if (StringUtils.isEmpty(orderby)) {
            orderby = "ORDER BY CURRENT_TIMESTAMP";
        }

        StringBuilder sql = new StringBuilder();
        sql.append("WITH selectTemp AS (SELECT ").append(distinctStr).append("TOP 100 PERCENT ")
                .append(" ROW_NUMBER() OVER (").append(orderby).append(") as __row_number__, ").append(pagingBuilder)
                .append(") SELECT * FROM selectTemp WHERE __row_number__ BETWEEN ")
                //FIX#299：原因：mysql中limit 10(offset,size) 是从第10开始（不包含10）,；而这里用的BETWEEN是两边都包含，所以改为offset+1
                .append(offset + 1)
                .append(" AND ")
                .append(offset + limit).append(" ORDER BY __row_number__");
        return sql.toString();
    }

}
