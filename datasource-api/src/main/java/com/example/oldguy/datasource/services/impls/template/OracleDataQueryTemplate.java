package com.example.oldguy.datasource.services.impls.template;

import com.example.oldguy.datasource.services.AbstractDataQueryTemplate;
import com.example.oldguy.datasource.services.impls.dialect.DB2Dialect;
import com.example.oldguy.datasource.services.impls.dialect.OracleDialect;

import javax.sql.DataSource;

/**
 * @ClassName: OracleDataQueryTemplate
 * @Author: ren
 * @Description:
 * @CreateTIme: 2020/1/30 0030 下午 10:20
 **/
public class OracleDataQueryTemplate  extends AbstractDataQueryTemplate {
    public OracleDataQueryTemplate(DataSource dataSource) {
        super(dataSource);
        dbDialect = new OracleDialect();
    }
}
