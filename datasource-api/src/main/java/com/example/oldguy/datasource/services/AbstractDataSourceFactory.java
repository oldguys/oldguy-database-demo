package com.example.oldguy.datasource.services;

import com.example.oldguy.datasource.constants.DbConstantsProperty;
import com.example.oldguy.datasource.constants.DbType;
import com.example.oldguy.datasource.exceptions.DataQueryException;
import com.example.oldguy.datasource.models.DbQueryProperty;
import com.example.oldguy.datasource.services.api.DataQueryApi;
import com.example.oldguy.datasource.services.impls.template.*;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Data;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;

/**
 * @ClassName: AbstractDataSourceFactory
 * @Author: ren
 * @Description:
 * @CreateTIme: 2020/1/30 0030 下午 8:34
 **/
@Data
public abstract class AbstractDataSourceFactory implements DataSourceTemplateFactory {

    private DbQueryProperty property;

    @Override
    public DataSource createDataSource(DbQueryProperty property) {

        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setUsername(property.getUsername());
        dataSource.setPassword(property.getPassword());
        dataSource.setJdbcUrl(trainToJdbcUrl(property));

        return dataSource;
    }

    @Override
    public DataQueryApi createDataQueryTemplate(DbQueryProperty property) {

        property.viald();

        DbType dbType = getDialect(property.getDbType());
        DataSource dataSource = createDataSource(property);
        DataQueryApi dataQueryApi = createTemplateFromMap(dataSource, dbType);

        return dataQueryApi;
    }

    @Override
    public DataQueryApi createTemplateFromMap(DataSource dataSource, DbType dbType) {

        DataQueryApi target;

        switch (dbType) {
            case MySQL: {
                target = new MySQLDataQueryTemplate(dataSource);
                break;
            }
            case MSSQL: {
                target = new MSSQLDataQueryTemplate(dataSource);
                break;
            }
            case Oracle: {
                target = new OracleDataQueryTemplate(dataSource);
                break;
            }
            case PgSQL: {
                target = new PgSQLDataQueryTemplate(dataSource);
                break;
            }
            case BD2:{
                target = new DB2DataQueryTemplate(dataSource);
                break;
            }
            default: {
                throw new DataQueryException("不合法数据库类型");
            }
        }

        return target;
    }

    protected DbType getDialect(String type) {
        DbType result = DbConstantsProperty.DB_TYPE_MAP.get(type);
        if (null == result) {
            throw new DataQueryException("无效数据库类型!");
        }
        return result;
    }


    protected String trainToJdbcUrl(DbQueryProperty property) {

        String url = DbConstantsProperty.DATASOURCE_URL_MAP.get(property.getDbType());
        if (StringUtils.isEmpty(url)) {
            throw new DataQueryException("无效数据库类型!");
        }

        url = url.replace(DbConstantsProperty.DB_HOST_EXP, property.getHost());
        url = url.replace(DbConstantsProperty.DB_PORT_EXP, String.valueOf(property.getPort()));

        if (null == property.getDbName()){
            property.setDbName("");
        }
        url = url.replace(DbConstantsProperty.DB_NAME_EXP, property.getDbName());
        return url;
    }




}
