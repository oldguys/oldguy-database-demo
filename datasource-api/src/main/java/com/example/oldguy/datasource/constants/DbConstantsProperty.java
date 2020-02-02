package com.example.oldguy.datasource.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: DbConstantsProperty
 * @Author: ren
 * @Description:
 * @CreateTIme: 2020/1/30 0030 下午 9:02
 **/
public class DbConstantsProperty {

    public static final String DB_HOST_EXP = "${host}";
    public static final String DB_PORT_EXP = "${port}";
    public static final String DB_NAME_EXP = "${dbName}";

    public static final Map<String, DbType> DB_TYPE_MAP = new HashMap<>();

    public static final Map<String, String> DATASOURCE_URL_MAP = new HashMap<>();

    static {
        DB_TYPE_MAP.put(DbType.MySQL.getType(), DbType.MySQL);
        DB_TYPE_MAP.put(DbType.BD2.getType(), DbType.BD2);
        DB_TYPE_MAP.put(DbType.Oracle.getType(), DbType.Oracle);
        DB_TYPE_MAP.put(DbType.MSSQL.getType(), DbType.MSSQL);
        DB_TYPE_MAP.put(DbType.PgSQL.getType(), DbType.PgSQL);

        DATASOURCE_URL_MAP.put(DbType.MySQL.getType(), "jdbc:mysql://${host}:${port}/${dbName}?useUnicode=true&characterEncoding=utf8&useSSL=false&allowMultiQueries=true");
        DATASOURCE_URL_MAP.put(DbType.Oracle.getType(), "jdbc:oracle:thin:@${host}:${port}:XE");
        DATASOURCE_URL_MAP.put(DbType.BD2.getType(), "jdbc:db2://${host}:${port}/${dbName}");
        DATASOURCE_URL_MAP.put(DbType.MSSQL.getType(), "jdbc:sqlserver://${host}:${port};${dbName}");
        DATASOURCE_URL_MAP.put(DbType.PgSQL.getType(), "jdbc:postgresql://${host}:${port}/${dbName}");
    }


}
