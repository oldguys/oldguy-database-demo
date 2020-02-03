package com.example.oldguy.datasource.services.impls;

import com.example.oldguy.datasource.models.DbQueryProperty;
import com.example.oldguy.datasource.services.AbstractDataSourceFactory;

import javax.sql.DataSource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName: CacheDataSourceFactoryBean
 * @Author: ren
 * @Description:
 * @CreateTIme: 2020/2/3 0003 上午 9:16
 **/
public class CacheDataSourceFactoryBean extends AbstractDataSourceFactory {

    /**
     * 数据源缓存
     */
    private static Map<String, DataSource> dataSourceMap = new ConcurrentHashMap<>();

    @Override
    public DataSource createDataSource(DbQueryProperty property) {

        String key = property.getHost() + property.getPort() + property.getUsername();

        DataSource target = dataSourceMap.get(key);

        if (null == target) {
            synchronized (CacheDataSourceFactoryBean.class) {
                if (null == target) {
                    target = super.createDataSource(property);
                    dataSourceMap.put(key, target);
                }
            }
        }
        return target;
    }

}
