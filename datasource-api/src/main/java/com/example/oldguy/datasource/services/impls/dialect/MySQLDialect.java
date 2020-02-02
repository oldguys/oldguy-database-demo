package com.example.oldguy.datasource.services.impls.dialect;

import com.example.oldguy.datasource.models.DbColumn;
import com.example.oldguy.datasource.services.AbstractDialect;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;

/**
 * @ClassName: MySQLDialect
 * @Author: ren
 * @Description:
 * @CreateTIme: 2020/1/30 0030 下午 10:26
 **/
public class MySQLDialect extends AbstractDialect {


    @Override
    public RowMapper<DbColumn> rowMapper() {
        return (ResultSet rs, int rowNum) -> {

            DbColumn entity = new DbColumn();
            entity.setName(rs.getString("Field"));
            entity.setType(rs.getString("Type"));
            entity.setNullable(rs.getString("Null"));
            entity.setHasKey(rs.getString("Key"));
            entity.setLength(rs.getString("Extra"));
            return entity;
        };
    }

}
