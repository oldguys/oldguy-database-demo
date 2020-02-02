package com.example.oldguy.datasource.services.impls.template;

import com.example.oldguy.datasource.services.AbstractDataQueryTemplate;
import com.example.oldguy.datasource.services.impls.dialect.PgSQLDialect;

import javax.sql.DataSource;

/**
 * @ClassName: PgSQLDataQueryTemplate
 * @Author: ren
 * @Description:
 * @CreateTIme: 2020/1/30 0030 下午 10:21
 **/
public class PgSQLDataQueryTemplate  extends AbstractDataQueryTemplate {

    public PgSQLDataQueryTemplate(DataSource dataSource) {
        super(dataSource);
        dbDialect = new PgSQLDialect();
    }
}
