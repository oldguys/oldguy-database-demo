package com.example.oldguy.datasource.services.impls.dialect;

import com.example.oldguy.datasource.models.DbColumn;
import com.example.oldguy.datasource.services.AbstractDialect;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;

/**
 * @ClassName: OracleDialect
 * @Author: ren
 * @Description:
 * @CreateTIme: 2020/1/31 0031 下午 8:36
 **/
public class OracleDialect extends AbstractDialect {

    @Override
    public RowMapper<DbColumn> rowMapper() {
        return (ResultSet rs, int rowNum) -> {

            DbColumn entity = new DbColumn();
            entity.setName(rs.getString("COLUMN_NAME"));
            entity.setType(rs.getString("DATA_TYPE"));
            entity.setNullable(rs.getString("NULLABLE"));

//            entity.setField(rs.getString("COLUMN_NAME"));
//            entity.setDefaultValue(rs.getString("DATA_DEFAULT"));
//            entity.setType(rs.getString("DATA_TYPE"));
//            entity.setNullIs(rs.getString("NULLABLE"));
//            entity.setDataScale(rs.getInt("DATA_SCALE"));

//            entity.setDataScale(rs.getInt("SCALE"));
            return entity;
        };
    }

    @Override
    public String tables(String dbName) {
        return "SELECT u.TABLE_NAME FROM user_tables u";
    }

    @Override
    public String database() {
        return "SELECT name FROM v$database";
    }

    @Override
    public String columns(String dbName, String tableName) {
        return "select * from user_tab_columns where table_name = '" + tableName + "' ";
    }

    @Override
    public String buildPaginationSql(String sql, long offset, long count) {

        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("SELECT * FROM ( SELECT TMP.*, ROWNUM ROW_ID FROM ( ");
        sqlBuilder.append(sql).append(" ) TMP WHERE ROWNUM <=").append((offset >= 1) ? (offset + count) : count);
        sqlBuilder.append(") WHERE ROW_ID > ").append(offset);

        return sqlBuilder.toString();
    }
}
