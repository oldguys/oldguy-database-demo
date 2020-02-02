package com.example.oldguy.datasource.services.impls.template;

import com.example.oldguy.datasource.services.AbstractDataQueryTemplate;
import com.example.oldguy.datasource.services.impls.dialect.DB2Dialect;

import javax.sql.DataSource;

/**
 * @ClassName: MySQLDataQueryTemplate
 * @Author: ren
 * @Description:
 * @CreateTIme: 2020/1/30 0030 下午 8:33
 **/
public class DB2DataQueryTemplate extends AbstractDataQueryTemplate {

    public DB2DataQueryTemplate(DataSource dataSource) {
        super(dataSource);
        dbDialect = new DB2Dialect();
    }
}
