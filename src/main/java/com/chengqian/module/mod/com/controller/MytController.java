package com.chengqian.module.mod.com.controller;

import com.chengqian.module.frame.config.ResultData;
import com.chengqian.module.frame.config.ResultViewModel;
import com.chengqian.module.frame.entity.PageParam;
import com.chengqian.module.frame.utils.PageUtils;
import com.chengqian.module.mod.com.entity.MytEntity;
import com.chengqian.module.mod.com.service.MytService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author cq
 * @since 2021-12-30
 */
@RestController
@RequestMapping("/mytEntity")
@Api(value = "",tags = "")
@Slf4j
public class MytController {

    @Autowired
    MytService iMytService;

    private final String messageBlock="";

    @PostMapping("/listPage")
    //@RequiresPermissions("com_myt:MytEntity:listPage")
    @ApiOperation(value = "查询" + messageBlock, notes = "POST", httpMethod = "POST")
    public ResultViewModel listPage(@RequestParam PageParam<MytEntity> params){
        PageUtils page=iMytService.queryPage(params);

        return ResultData.success(page);
    }
    
    /**
     * 列表
     */
    @GetMapping("/list")
    //@RequiresPermissions("com_myt:MytEntity:list")
    @ApiOperation(value = "查询列表" + messageBlock, notes = "GET", httpMethod = "GET")
    public List<MytEntity> list(){
        List<MytEntity> ret=iMytService.list(null);

        return ret;
    }
    
    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    //@RequiresPermissions("com_myt:MytEntity:info")
    @ApiOperation(value = "查询详情" + messageBlock, notes = "GET", httpMethod = "GET")
    public ResultViewModel info(@PathVariable("id") Long id){
        MytEntity ent=iMytService.getById(id);

        return ResultData.success(ent);
    }
    
    /**
     * 新增
     */
    //@SysLog(messageBlock + "新增")
    @PostMapping("/add")
    //@RequiresPermissions("com_myt:MytEntity:add")
    @ApiOperation(value = "新增" + messageBlock, notes = "POST", httpMethod = "POST")
    public ResultViewModel add(@RequestBody MytEntity ent){
        String me="新增" + messageBlock;
            //long id = getMaxID()+1;
            //ent.setUnitId(unitService.getMaxId()+1);
        iMytService.save(ent);
    
        return  ResultData.successNoData(me);
    }
    
    /**
     * 修改
     */
//    @SysLog(messageBlock + "修改")
    @PostMapping("/edit")
    //@RequiresPermissions("com_myt:MytEntity:edit")
    @ApiOperation(value = "修改" + messageBlock, notes = "POST", httpMethod = "POST")
    public ResultViewModel edit(@RequestBody MytEntity ent){
        String me="修改" + messageBlock;
            //ValidatorUtils.validateEntity(ent);
        iMytService.updateById(ent);
        return ResultData.successNoData(me);
    }
    
    /**
     * 删除
     */
//    @SysLog(messageBlock + "删除")
    @PostMapping("/delete")
    //@RequiresPermissions("com_myt:MytEntity:delete")
    @ApiOperation(value = "删除" + messageBlock, notes = "POST", httpMethod = "POST")
    public ResultViewModel delete(@RequestBody Long...ids){
        String me="删除" + messageBlock;
        iMytService.removeByIds(Arrays.asList(ids));
    
        return ResultData.successNoData(me);
    }
}

