package com.example.oldguy;

import com.example.oldguy.datasource.EnableDataSourceFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableDataSourceFactory
@SpringBootApplication
public class DatasourceMgrDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DatasourceMgrDemoApplication.class, args);
    }

}
