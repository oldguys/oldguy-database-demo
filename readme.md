#### 简易版-多类型SQL通用操作 datasource-api


>  之前接到一个好玩的需求：通过UI界面配置简单的动态报表，数据源可以直接配置，需要兼容各个类型的数据库（MySQL，Oracle，DB2，sqlserver，PgSQL 等 ）。所以对于通用的数据库API层进行抽象封装。此处主要介绍数据库API的设计逻辑。
> 
> 版本：springboot 2.2.4.RELEASE
> Git地址：https://github.com/oldguys/oldguy-database-demo.git
> 相关文章：[使用 docker-compose 一键构建 MySQL，MSSQL，Oracle，DB2，PgSQL ](https://www.jianshu.com/p/0b729633d3f4)

类图如下：
![类图设计](https://upload-images.jianshu.io/upload_images/14387783-68228500472ef223.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


核心接口类：（代码附在文章末尾）

1. **DataSourceFactory**： 基于**“抽象工厂模式”**，编写通用构建 “DataQueryApi” Bean工厂，可以根据需求进行不同的定制化如：缓存数据源工厂。
2. **DbDialect**：基于**“策略模式”**，个性化常用的SQL，如：不同数据库的分页SQL写法不一样。
3. **DataQueryApi**：基于**“模板方法模式”**，编写常用SQL的调用接口，利用 **DbDialect** 的个性化开发，提供给其他服务调用。接口方法：
    1. 获取指定datasource下的所有db列表。 
    2. 获取指定db下的所有table。
    3. 获取指定table的列信息。
    4. 分页获取指定表的数据。

>
>分页SQL参考于 mybatis-plus 源码
> 模块：mybatis-plus-extension，
> 包：com.baomidou.mybatisplus.extension.plugins.pagination.dialects 
>

##### 代码示例：
---
DataQueryApi ：
```
package com.example.oldguy.datasource.services.api;

import com.example.oldguy.common.dto.PageResult;
import com.example.oldguy.datasource.models.DbColumn;

import java.util.List;
import java.util.Map;

/**
 * @ClassName: DataQueryApi
 * @Author: ren
 * @Description:
 * @CreateTIme: 2020/1/30 0030 下午 8:21
 **/
public interface DataQueryApi {

    /**
     * 使用其他数据库
     *
     * @param dbName
     * @return
     */
    void useDatabase(String dbName);

    /**
     * 关闭数据源
     */
    void close();

    /**
     * 获取版本
     *
     * @return
     */
    String getVersion();

    /**
     *  获取指定表 具有的所有字段列表
     * @param dbName
     * @param tableName
     * @return
     */
    List<DbColumn> getTableColumns(String dbName, String tableName);

    /**
     * 获取指定数据库下 所有的表信息
     *
     * @param dbName
     * @return
     */
    List<String> getTables(String dbName);

    /**
     * 获取数据库列表
     *
     * @return
     */
    List<String> getDatabases();

    /**
     * 获取总数
     *
     * @param sql
     * @return
     */
    int count(String sql);

    /**
     * 查询结果列表
     *
     * @param sql
     * @return
     */
    List<Map<String, Object>> queryList(String sql);

    /**
     * 查询结果分页
     *
     * @param sql
     * @param offset
     * @param size
     * @return
     */
    PageResult<Map<String, Object>> queryByPage(String sql, long offset, long size);
}

```
AbstractDataQueryTemplate：
```
package com.example.oldguy.datasource.services;

import com.example.oldguy.common.dto.PageResult;
import com.example.oldguy.datasource.exceptions.DataQueryException;
import com.example.oldguy.datasource.models.DbColumn;
import com.example.oldguy.datasource.models.DbQueryProperty;
import com.example.oldguy.datasource.services.api.DataQueryApi;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: AbstractDataQueryTemplate
 * @Author: ren
 * @Description:
 * @CreateTIme: 2020/1/30 0030 下午 8:24
 **/
public abstract class AbstractDataQueryTemplate implements DataQueryApi {

    protected DataSource dataSource;

    protected JdbcTemplate jdbcTemplate;

    protected DbDialect dbDialect;

    public AbstractDataQueryTemplate(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void close() {

        if (dataSource instanceof HikariDataSource) {
            ((HikariDataSource) dataSource).close();
        } else {
            throw new DataQueryException("不合法数据源类型");
        }
    }


    @Override
    public void useDatabase(String dbName) {

        String sql = dbDialect.useDatabase(dbName);
        jdbcTemplate.execute(sql);
    }

    @Override
    public List<Map<String, Object>> queryList(String sql) {
        return jdbcTemplate.queryForList(sql);
    }

    @Override
    public String getVersion() {
        return jdbcTemplate.queryForObject(dbDialect.version(), String.class);
    }

    @Override
    public List<DbColumn> getTableColumns(String dbName, String tableName) {

        String sql = dbDialect.columns(dbName, tableName);
        return jdbcTemplate.query(sql, dbDialect.rowMapper());
    }

    @Override
    public List<String> getTables(String dbName) {

        useDatabase(dbName);
        return jdbcTemplate.queryForList(dbDialect.tables(dbName), String.class);
    }

    @Override
    public List<String> getDatabases() {
        return jdbcTemplate.queryForList(dbDialect.database(), String.class);
    }

    @Override
    public int count(String sql) {
        return jdbcTemplate.queryForObject(dbDialect.count(sql), Integer.class);
    }

    @Override
    public PageResult<Map<String, Object>> queryByPage(String sql, long offset, long size) {

        int total = count(sql);

        String pageSql = dbDialect.buildPaginationSql(sql, offset, size);
        List<Map<String, Object>> records = jdbcTemplate.queryForList(pageSql);

        return new PageResult<>(total, records);
    }
}

```
---

DataSourceFactory：
```
package com.example.oldguy.datasource.services;

import com.example.oldguy.datasource.models.DbQueryProperty;
import com.example.oldguy.datasource.services.api.DataQueryApi;

/**
 * @ClassName: DataSourceFactory
 * @Author: ren
 * @Description:
 * @CreateTIme: 2020/1/30 0030 下午 8:22
 **/
public interface DataSourceFactory {

    /**
     * 创建数据源实例
     *
     * @param property
     * @return
     */
    DataQueryApi createDataQueryTemplate(DbQueryProperty property);


}

```
DataSourceTemplateFactory：
```

/**
 * @ClassName: DataSourceTemplateFactory
 * @Author: ren
 * @Description:
 * @CreateTIme: 2020/1/31 0031 下午 4:12
 **/
public interface DataSourceTemplateFactory extends DataSourceFactory {

    /**
     * 配置实体及映射关系
     * @param dataSource
     * @param dbType
     * @return
     */
    DataQueryApi createTemplateFromMap(DataSource dataSource, DbType dbType);

    /**
     * 创建数据源
     * @param property
     * @return
     */
    DataSource createDataSource(DbQueryProperty property);
}

```

```
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

```
---
DbDialect：
```
package com.example.oldguy.datasource.services;

import com.example.oldguy.datasource.models.DbColumn;
import org.springframework.jdbc.core.RowMapper;

/**
 * @ClassName: DbDialect
 * @Author: ren
 * @Description:
 * @CreateTIme: 2020/1/30 0030 下午 8:22
 **/
public interface DbDialect {


    RowMapper<DbColumn> rowMapper();

    /**
     * 使用数据库
     *
     * @param dbName
     * @return
     */
    String useDatabase(String dbName);

    /**
     * 获取指定表的所有列
     *
     * @param tableName
     * @return
     */
    String columns(String dbName, String tableName);

    /**
     * 获取数据库下的 所有表
     *
     * @param dbName
     * @return
     */
    String tables(String dbName);

    /**
     * 获取数据库版本
     *
     * @return
     */
    String version();

    /**
     * 获取指定数据源具有的数据库
     *
     * @return
     */
    String database();

    /**
     * 构建 分页 sql
     *
     * @param sql
     * @param offset
     * @param count
     * @return
     */
    String buildPaginationSql(String sql, long offset, long count);

    /**
     * 包装 count sql
     *
     * @param sql
     * @return
     */
    String count(String sql);
}

```

AbstractDialect：
```
package com.example.oldguy.datasource.services;

/**
 * @ClassName: AbstractDialect
 * @Author: ren
 * @Description:
 * @CreateTIme: 2020/1/30 0030 下午 10:27
 **/
public abstract class AbstractDialect implements DbDialect {

    @Override
    public String count(String sql) {
        return "SELECT COUNT(*) FROM ( " + sql + " ) temp";
    }

    @Override
    public String useDatabase(String dbName) {
        return "use `" + dbName + "`";
    }

    @Override
    public String columns(String dbName,String tableName) {
        return "show columns from `" + tableName + "`;";
    }

    @Override
    public String tables(String dbName) {
        return "show tables";
    }

    @Override
    public String version() {
        return "SELECT VERSION()";
    }

    @Override
    public String database() {
        return "show databases";
    }

    @Override
    public String buildPaginationSql(String sql, long offset, long count) {

        // 获取 分页实际条数
        StringBuilder builder = new StringBuilder(sql);
        builder.append(" LIMIT ").append(offset).append(" , ").append(count);

        return builder.toString();
    }
}

```
