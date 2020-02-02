package com.example.oldguy.datasource.services.impls.dialect;

import com.example.oldguy.datasource.models.DbColumn;
import com.example.oldguy.datasource.services.AbstractDialect;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;

/**
 * @ClassName: DB2Dialect
 * @Author: ren
 * @Description:
 * @CreateTIme: 2020/1/31 0031 下午 8:07
 **/
public class DB2Dialect extends AbstractDialect {

    @Override
    public String tables(String dbName) {
        return "select tabname from syscat.tables where tabschema = '" + dbName + "'";
    }


    @Override
    public String database() {
        return "SELECT SCHEMANAME FROM SYSCAT.SCHEMATA WHERE OWNERTYPE = 'U'";
    }

    @Override
    public RowMapper<DbColumn> rowMapper() {
        return (ResultSet rs, int rowNum) -> {

            DbColumn entity = new DbColumn();
            entity.setName(rs.getString("COLNAME"));
            entity.setType(rs.getString("TYPENAME"));
            entity.setNullable(rs.getString("NULLS"));

//            entity.setDataScale(rs.getInt("SCALE"));
            return entity;
        };
    }

    @Override
    public String columns(String dbName, String tableName) {

        return "SELECT COLNAME,TYPENAME,LENGTH,SCALE,NULLS FROM SYSCAT.COLUMNS WHERE TABSCHEMA = '" + dbName + "' AND TABNAME = '" + tableName
                + "'";
    }

    @Override
    public String buildPaginationSql(String originalSql, long offset, long limit) {
        int startOfSelect = originalSql.toLowerCase().indexOf("select");
        StringBuilder pagingSelect = new StringBuilder(originalSql.length() + 100)
                .append(originalSql, 0, startOfSelect).append("select * from ( select ")
                .append(getRowNumber(originalSql));
        if (hasDistinct(originalSql)) {
            pagingSelect.append(" row_.* from ( ").append(originalSql.substring(startOfSelect)).append(" ) as row_");
        } else {
            pagingSelect.append(originalSql.substring(startOfSelect + 6));
        }

        if (offset > 0) {
            String endString = offset + "+" + limit;
            pagingSelect.append(" fetch first ").append(endString).append(" rows only) as temp_ where rownumber_ ")
                    .append("> ").append(offset);
        } else {
            pagingSelect.append(" fetch first ").append(limit).append(" rows only) as temp_ ");
        }
        return pagingSelect.toString();
    }

    private static String getRowNumber(String originalSql) {
        StringBuilder rownumber = new StringBuilder(50).append("rownumber() over(");
        int orderByIndex = originalSql.toLowerCase().indexOf("order by");
        if (orderByIndex > 0 && !hasDistinct(originalSql)) {
            rownumber.append(originalSql.substring(orderByIndex));
        }
        rownumber.append(") as rownumber_,");
        return rownumber.toString();
    }

    private static boolean hasDistinct(String originalSql) {
        return originalSql.toLowerCase().contains("select distinct");
    }
}
