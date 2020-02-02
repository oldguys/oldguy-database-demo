package com.example.oldguy.modules.dbquery.controller;

import com.example.oldguy.common.dto.CommonRsp;
import com.example.oldguy.common.dto.PageResult;
import com.example.oldguy.datasource.models.DbColumn;
import com.example.oldguy.datasource.services.api.DataQueryApi;
import com.example.oldguy.modules.dbquery.dto.DbQueryReq;
import com.example.oldguy.modules.dbquery.services.DataQueryApiService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: DbQueryController
 * @Author: ren
 * @Description:
 * @CreateTIme: 2020/1/30 0030 下午 8:14
 **/
@Api(tags = "通用查询Api")
@RestController
@RequestMapping("db/query")
public class DbQueryController {

    @Autowired
    private DataQueryApiService dataQueryApiService;

    @ApiOperation("数据库列表")
    @GetMapping("{datasourceId}/databases")
    public CommonRsp<List<String>> databases(@PathVariable String datasourceId) {

        DataQueryApi queryApi = dataQueryApiService.getDataQueryApi(datasourceId);
        if (null == queryApi) {
            return CommonRsp.DEFAULT_RSP;
        }
        List<String> list = queryApi.getDatabases();
        return new CommonRsp<>(list);
    }

    @ApiOperation("数据表列表")
    @GetMapping("{datasourceId}/{dbName}/tables")
    public CommonRsp<List<String>> tables(@PathVariable String datasourceId, @PathVariable String dbName) {

        DataQueryApi queryApi = dataQueryApiService.getDataQueryApi(datasourceId);
        if (null == queryApi) {
            return CommonRsp.DEFAULT_RSP;
        }
        queryApi.useDatabase(dbName);
        List<String> list = queryApi.getTables(dbName);
        return new CommonRsp<>(list);
    }

    @ApiOperation("数据库版本")
    @GetMapping("{datasourceId}/version")
    public CommonRsp<String> version(@PathVariable String datasourceId) {

        DataQueryApi queryApi = dataQueryApiService.getDataQueryApi(datasourceId);
        if (null == queryApi) {
            return CommonRsp.DEFAULT_RSP;
        }
        String version = queryApi.getVersion();
        return new CommonRsp<>(version);
    }

    @ApiOperation("表结构")
    @GetMapping("{datasourceId}/{dbName}/{tableName}/columns")
    public CommonRsp<List<DbColumn>> tableColumns(
            @PathVariable String datasourceId,
            @PathVariable String dbName,
            @PathVariable String tableName
    ) {
        DataQueryApi queryApi = dataQueryApiService.getDataQueryApi(datasourceId);
        if (null == queryApi) {
            return CommonRsp.DEFAULT_RSP;
        }
        queryApi.useDatabase(dbName);
        List<DbColumn> columns = queryApi.getTableColumns(dbName, tableName);
        return new CommonRsp<>(columns);
    }

    @ApiOperation("获取SQL结果")
    @PostMapping("/queryList")
    public CommonRsp<List<Map<String, Object>>> queryList(@Valid @RequestBody DbQueryReq req) {
        DataQueryApi queryApi = dataQueryApiService.getDataQueryApi(req.getDatasourceId());
        if (null == queryApi) {
            return CommonRsp.DEFAULT_RSP;
        }
        queryApi.useDatabase(req.getDbName());
        List<Map<String, Object>> records = queryApi.queryList(req.getSql());
        return new CommonRsp<>(records);
    }

    @ApiOperation("分页获取SQL结果")
    @PostMapping("/queryByPage")
    public CommonRsp queryByPage(@Valid @RequestBody DbQueryReq req) {
        DataQueryApi queryApi = dataQueryApiService.getDataQueryApi(req.getDatasourceId());
        if (null == queryApi) {
            return CommonRsp.DEFAULT_RSP;
        }
        queryApi.useDatabase(req.getDbName());
        PageResult page = queryApi.queryByPage(req.getSql(), req.getPage().offset(), req.getPage().getSize());
        return new CommonRsp<>(page);
    }

}
