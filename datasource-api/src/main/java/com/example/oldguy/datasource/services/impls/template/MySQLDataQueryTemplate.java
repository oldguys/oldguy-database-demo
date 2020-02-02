package com.example.oldguy.datasource.services.impls.template;

import com.example.oldguy.datasource.services.AbstractDataQueryTemplate;
import com.example.oldguy.datasource.services.impls.dialect.MySQLDialect;

import javax.sql.DataSource;

/**
 * @ClassName: MySQLDataQueryTemplate
 * @Author: ren
 * @Description:
 * @CreateTIme: 2020/1/30 0030 下午 8:33
 **/
public class MySQLDataQueryTemplate extends AbstractDataQueryTemplate {


    public MySQLDataQueryTemplate(DataSource dataSource) {
        super(dataSource);
        dbDialect = new MySQLDialect();
    }
}
