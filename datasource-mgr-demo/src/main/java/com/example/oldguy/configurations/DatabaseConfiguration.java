package com.example.oldguy.configurations;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName: DatabaseConfiguration
 * @Author: ren
 * @Description:
 * @CreateTIme: 2020/1/30 0030 下午 6:11
 **/
@MapperScan(basePackages = "com.example.oldguy.modules.mgr.dao.jpas")
@Configuration
public class DatabaseConfiguration {
}
