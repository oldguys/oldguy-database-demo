package com.example.oldguy.datasource.services.impls.template;

import com.example.oldguy.datasource.services.AbstractDataQueryTemplate;
import com.example.oldguy.datasource.services.impls.dialect.MSSQLDialect;

import javax.sql.DataSource;

/**
 * @ClassName: MSSQLDataQueryTemplate
 * @Author: ren
 * @Description:
 * @CreateTIme: 2020/1/30 0030 下午 10:20
 **/
public class MSSQLDataQueryTemplate extends AbstractDataQueryTemplate {
    public MSSQLDataQueryTemplate(DataSource dataSource) {
        super(dataSource);
        dbDialect = new MSSQLDialect();
    }
}
