package com.example.oldguy.modules.dbquery.dto;

import com.example.oldguy.common.dto.PageReq;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @ClassName: DbQueryReq
 * @Author: ren
 * @Description:
 * @CreateTIme: 2020/2/1 0001 上午 11:03
 **/
@Data
public class DbQueryReq {

    @NotBlank(message = "datasourceId 不能为空!")
    @ApiModelProperty(value = "数据源ID")
    private String datasourceId;

    @NotBlank(message = "dbName 不能为空!")
    @ApiModelProperty(value = "数据库名称")
    private String dbName;

    @ApiModelProperty("查询SQL")
    private String sql;

    @ApiModelProperty("分页查询条件")
    private PageReq page;
}
