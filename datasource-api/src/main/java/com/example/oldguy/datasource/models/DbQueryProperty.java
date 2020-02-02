package com.example.oldguy.datasource.models;

import com.example.oldguy.datasource.constants.DbType;
import com.example.oldguy.datasource.exceptions.DataQueryException;
import lombok.Data;
import org.springframework.util.StringUtils;

/**
 * @ClassName: DbQueryProperty
 * @Author: ren
 * @Description:
 * @CreateTIme: 2020/1/30 0030 下午 8:30
 **/
@Data
public class DbQueryProperty {

    /**
     * 数据库类型
     *
     * @Link com.example.oldguy.datasource.constants.DbType
     */
    private String dbType;

    private String host;

    private String username;

    private String password;

    private Integer port;

    private String dbName;

    /**
     * 参数合法性校验
     */
    public void viald() {

        if (
                StringUtils.isEmpty(dbType) ||
                        StringUtils.isEmpty(host) ||
                        StringUtils.isEmpty(username) ||
                        StringUtils.isEmpty(password) ||
                        StringUtils.isEmpty(port)
        ) {
            throw new DataQueryException("参数不完整!");
        }
        if (DbType.BD2.getType().equals(dbType) && StringUtils.isEmpty(dbName)) {
            throw new DataQueryException("DB2 默认数据库 不能为空 !");
        }
    }
}
