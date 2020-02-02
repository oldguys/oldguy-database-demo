package com.example.oldguy.datasource;

import com.example.oldguy.datasource.configuration.AutoDataQueryApiConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @ClassName: EnableDataSourceFactory
 * @Author: ren
 * @Description:
 * @CreateTIme: 2020/1/31 0031 下午 8:00
 **/
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(AutoDataQueryApiConfiguration.class)
public @interface EnableDataSourceFactory {
}
