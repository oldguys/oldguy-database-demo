package com.example.oldguy.datasource.models;

import lombok.Data;

/**
 * @ClassName: DbColumn
 * @Author: ren
 * @Description:
 * @CreateTIme: 2020/1/30 0030 下午 8:17
 **/
@Data
public class DbColumn {

    private String name;

    private String type;

    private String length;

    private String nullable;

    private String hasKey;
}
