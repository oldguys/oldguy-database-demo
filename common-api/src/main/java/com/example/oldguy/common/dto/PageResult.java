package com.example.oldguy.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

/**
 * @ClassName: PageResult
 * @Author: ren
 * @Description:
 * @CreateTIme: 2020/1/31 0031 下午 5:52
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageResult<T> {

    private int total;

    private List<T> records = Collections.emptyList();
}
