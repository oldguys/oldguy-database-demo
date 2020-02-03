package com.example.oldguy.datasource.configuration;

import com.example.oldguy.datasource.services.DataSourceFactory;
import com.example.oldguy.datasource.services.impls.CacheDataSourceFactoryBean;
import com.example.oldguy.datasource.services.impls.DefaultDataSourceFactoryBean;
import org.springframework.context.annotation.Bean;

/**
 * @ClassName: AutoDataQueryApiConfiguration
 * @Author: ren
 * @Description:
 * @CreateTIme: 2020/1/31 0031 下午 8:01
 **/
public class AutoDataQueryApiConfiguration {

    @Bean
    public DataSourceFactory dataSourceFactory(){
        return new CacheDataSourceFactoryBean();
    }
}
