package com.example.oldguy.datasource.exceptions;

/**
 * @ClassName: DataQueryException
 * @Author: ren
 * @Description: 数据库处理异常
 * @CreateTIme: 2020/1/30 0030 下午 9:06
 **/
public class DataQueryException extends RuntimeException {

    public DataQueryException(String message) {
        super(message);
    }
}
