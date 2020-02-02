package com.example.oldguy.datasource.constants;

import lombok.Getter;

/**
 * @ClassName: DbType
 * @Author: ren
 * @Description:
 * @CreateTIme: 2020/1/30 0030 下午 8:58
 **/
@Getter
public enum DbType {

    /**
     * mysql
     */
    MySQL("mysql"),
    /**
     * sql server
     */
    MSSQL("mssql"),
    /**
     * db2
     */
    BD2("db2"),
    /***
     * postgresql
     */
    PgSQL("pgSQL"),
    /**
     * oracle
     */
    Oracle("oracle"),
    ;

    private String type;

    DbType(String type) {
        this.type = type;
    }
}
