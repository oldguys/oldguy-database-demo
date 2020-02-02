package com.example.oldguy.modules.mgr.dao.entities;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @ClassName: DataSourceEntity
 * @Author: ren
 * @Description:
 * @CreateTIme: 2020/1/27 0027 下午 6:15
 **/
@TableName
@Data
public class DataSourceEntity {

    @TableId(type = IdType.ID_WORKER_STR)
    private String id;

    @NotBlank(message = "name 不能为空!")
    private String name;

    private String description;

    @NotBlank(message = "type 不能为空!")
    private String type;

    @ApiModelProperty(name = "数据库连接host")
    @NotBlank(message = "host 不能为空!")
    private String host;

    @ApiModelProperty(name = "数据库连接端口")
    @NotNull(message = "port 不能为空!")
    private Integer port;

    @ApiModelProperty(name = "数据库连接用户名")
    @NotBlank(message = "username 不能为空!")
    private String username;

    @JsonIgnoreProperties(allowSetters = true)
    @ApiModelProperty(name = "数据库连接密码")
    @NotBlank(message = "password 不能为空!")
    private String password;

    private String defaultDatabase;
}
