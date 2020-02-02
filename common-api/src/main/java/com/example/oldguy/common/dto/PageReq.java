package com.example.oldguy.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName: PageReq
 * @Author: ren
 * @Description:
 * @CreateTIme: 2020/1/30 0030 下午 6:00
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageReq {

    /**
     * 起始页
     */
    private Integer startPage = 1;

    /**
     * 每页记录数
     */
    private Integer size = 30;

    public Integer offset() {

        size = size == null ? 30 : size;
        startPage = startPage == null ? 1 : startPage;

        int offset = startPage > 0 ? (startPage - 1) * size : 0;
        return offset;
    }

}
