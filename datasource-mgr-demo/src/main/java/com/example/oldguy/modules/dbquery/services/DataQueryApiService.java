package com.example.oldguy.modules.dbquery.services;

import com.example.oldguy.constants.DataSourceApiConstants;
import com.example.oldguy.datasource.models.DbQueryProperty;
import com.example.oldguy.datasource.services.DataSourceFactory;
import com.example.oldguy.datasource.services.api.DataQueryApi;
import com.example.oldguy.modules.mgr.dao.entities.DataSourceEntity;
import com.example.oldguy.modules.mgr.dao.jpas.DataSourceEntityMapper;
import com.example.oldguy.utils.RsaUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName: DataQueryApiService
 * @Author: ren
 * @Description:
 * @CreateTIme: 2020/2/1 0001 上午 10:10
 **/
@Service
public class DataQueryApiService {

    @Autowired
    private DataSourceFactory dataSourceFactory;
    @Autowired
    private DataSourceEntityMapper dataSourceEntityMapper;


    public DbQueryProperty trainToDbQueryProperty(String datasourceId) {
        DataSourceEntity dataSourceEntity = dataSourceEntityMapper.selectById(datasourceId);

        if (null == dataSourceEntity) {
            return null;
        }
        return trainToDbQueryProperty(dataSourceEntity);
    }

    public DbQueryProperty trainToDbQueryProperty(DataSourceEntity entity) {

        DbQueryProperty property = new DbQueryProperty();
        property.setPort(entity.getPort());
        property.setDbType(entity.getType());
        property.setUsername(entity.getUsername());

        try {
            property.setPassword(RsaUtils.decryptFromHexString(entity.getPassword(), DataSourceApiConstants.PRIVATE_KEY));
        } catch (Exception e) {
            e.printStackTrace();
        }
        property.setHost(entity.getHost());
        property.setDbName(entity.getDefaultDatabase());
        return property;
    }

    public DataQueryApi getDataQueryApi(String datasourceId) {

        DbQueryProperty property = trainToDbQueryProperty(datasourceId);
        if (null == property) {
            return null;
        }
        DataQueryApi queryApi = dataSourceFactory.createDataQueryTemplate(property);
        return queryApi;
    }
}
