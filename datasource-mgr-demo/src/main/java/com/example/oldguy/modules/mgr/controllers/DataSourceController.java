package com.example.oldguy.modules.mgr.controllers;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.oldguy.common.dto.CommonRsp;
import com.example.oldguy.common.dto.PageReq;
import com.example.oldguy.constants.DataSourceApiConstants;
import com.example.oldguy.modules.mgr.dao.entities.DataSourceEntity;
import com.example.oldguy.modules.mgr.dao.jpas.DataSourceEntityMapper;
import com.example.oldguy.utils.RsaUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.PublicKey;


/**
 * @ClassName: DataSourceController
 * @Author: ren
 * @Description:
 * @CreateTIme: 2020/1/30 0030 下午 5:44
 **/
@Api(tags = "数据源管理")
@RequestMapping("datasource")
@RestController
public class DataSourceController {

    @Autowired
    private DataSourceEntityMapper dataSourceEntityMapper;

    @ApiOperation("持久化数据源信息")
    @PostMapping("persist")
    public CommonRsp<DataSourceEntity> persist(@Valid @RequestBody DataSourceEntity entity) throws Exception {

        entity.setPassword(RsaUtils.encryptToHexString(entity.getPassword(), DataSourceApiConstants.PUBLIC_KEY));
        if (StringUtils.isEmpty(entity.getId())) {
            dataSourceEntityMapper.insert(entity);
        } else {
            dataSourceEntityMapper.updateById(entity);
        }
        entity.setPassword("");
        return new CommonRsp<>(entity);
    }

    @ApiOperation("分页获取数据源信息")
    @GetMapping("page")
    public CommonRsp<IPage> findByPage(PageReq req) {
        QueryWrapper<DataSourceEntity> wrapper = new QueryWrapper<>();
        wrapper.select("id","name","port","description","type","host","username","default_database");
        IPage result = dataSourceEntityMapper.selectPage(new Page<>(req.offset(), req.getSize()), wrapper);
        return new CommonRsp<>(result);
    }

}
