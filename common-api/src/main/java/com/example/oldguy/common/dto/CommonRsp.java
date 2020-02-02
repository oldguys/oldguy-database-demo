package com.example.oldguy.common.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName: CommonRsp
 * @Author: ren
 * @Description:
 * @CreateTIme: 2020/2/1 0001 上午 10:34
 **/
@Data
@NoArgsConstructor
public class CommonRsp<T> {

    /**
     *  默认空返回
     */
    public static CommonRsp DEFAULT_RSP = new CommonRsp();

    private Integer status = 0;

    private String msg;

    private T data;

    public CommonRsp(T data) {
        this.data = data;
    }
}
