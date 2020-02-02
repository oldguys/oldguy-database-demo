package com.example.oldguy.service;

import com.example.oldguy.common.dto.PageReq;
import com.example.oldguy.common.dto.PageResult;
import com.example.oldguy.datasource.constants.DbType;
import com.example.oldguy.datasource.models.DbColumn;
import com.example.oldguy.datasource.models.DbQueryProperty;
import com.example.oldguy.datasource.services.DataSourceFactory;
import com.example.oldguy.datasource.services.api.DataQueryApi;
import com.example.oldguy.datasource.services.impls.DefaultDataSourceFactory;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

/**
 * @ClassName: DataQueryApiTests
 * @Author: ren
 * @Description:
 * @CreateTIme: 2020/1/31 0031 下午 4:10
 **/
//@SpringBootTest
public class DataQueryApiTests {

    DbQueryProperty property = new DbQueryProperty();
    DataSourceFactory dataSourceFactory = new DefaultDataSourceFactory();

    void before() {
        property.setPassword("Root123456");
        property.setUsername("sa");
        property.setHost("192.168.62.161");
        property.setPort(1433);
        property.setDbType(DbType.MSSQL.getType());
    }

    @Test
    void testDefault() {

        before();

        DataQueryApi queryApi = dataSourceFactory.createDataQueryTemplate(property);

        System.out.println();
//        System.out.println(queryApi.getVersion());
        System.out.println();
        List<String> databases = queryApi.getDatabases();
        System.out.println(databases);

    }

    @Test
    void testShowTableList() {
        before();
//        property.setDbName("flow-study");

        DataQueryApi queryApi = dataSourceFactory.createDataQueryTemplate(property);

        List<String> tables = queryApi.getTables("flow-study");
        tables.forEach(obj -> {

            System.out.println(obj);

        });
    }


    @Test
    void testGetTableColumns() {

        before();
        property.setDbName("flow-study");

        DataQueryApi queryApi = dataSourceFactory.createDataQueryTemplate(property);

        String tableName = "act_de_model";

        List<DbColumn> columns = queryApi.getTableColumns("", tableName);

        columns.forEach(obj -> {

            System.out.println(obj);

        });
    }

    @Test
    void testCount() {

        before();
        property.setDbName("flow-study");

        DataQueryApi queryApi = dataSourceFactory.createDataQueryTemplate(property);

        String sql = "select * FROM act_de_model";

        int count = queryApi.count(sql);

        System.out.println(count);
    }

    @Test
    void testQueryList() {

        before();
        property.setDbName("flow-study");

        DataQueryApi queryApi = dataSourceFactory.createDataQueryTemplate(property);

        String sql = "select * FROM act_de_model";


        List<Map<String, Object>> records = queryApi.queryList(sql);

        records.forEach(obj -> {
            System.out.println();
            obj.forEach((k, v) -> {
                System.out.println("column:" + k + "\t value:" + v);
            });
        });
    }

    @Test
    void testQueryByPage() {

        before();
        property.setDbName("flow-study");

        DataQueryApi queryApi = dataSourceFactory.createDataQueryTemplate(property);

        queryApi.useDatabase("uap");
        String sql = "SELECT * FROM tb_sys_menu_info";

        PageReq page = new PageReq(1, 10);

        PageResult result = queryApi.queryByPage(sql, page.offset(), page.getSize());

        System.out.println(result);
    }


}
