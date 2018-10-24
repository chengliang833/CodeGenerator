package com.gencode.generate.web.controller;
import com.gencode.generate.persistence.model.Sites;
import com.gencode.generate.service.SitesService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;
import com.codegen.vo.Result;
import com.codegen.vo.Page;

import java.util.List;

/**
 *
 * Created by fenglibin on 2018/10/24.
 */
@Controller
@RequestMapping("/sites/")
public class SitesController {

    @Autowired
    SitesService sitesService;

    @RequestMapping("selectAll")
    @ResponseBody
    public Result<List<Sites>> selectAll( ){
        return Result.success(sitesService.selectAll());
    }

    @RequestMapping("select")
    @ResponseBody
    public Result<Sites> selectByPrimaryKey(Integer id){
        return Result.success(sitesService.selectByPrimaryKey(id));
    }

    @RequestMapping("count")
    @ResponseBody
    public Result<Integer> count(Sites record){
        return Result.success(sitesService.count(record));
    }

    @RequestMapping("insert")
    @ResponseBody
    public Result<Integer> insertSelective(Sites record){
        return Result.success(sitesService.insertSelective(record));
    }

    @RequestMapping("update")
    @ResponseBody
    public Result<Integer> updateByPrimaryKeySelective(Sites record){
        return Result.success(sitesService.updateByPrimaryKeySelective(record));
    }

    @RequestMapping("selectByPage")
    @ResponseBody
    public Result<Page<List<Sites>>> selectByPage(Page<Sites> page){
        List<Sites> list = null;
        page = page.sequentialDisplayCalculate(sitesService.count(page.getObj()));
        if (page.getTotalCount() > 0) {
            list = sitesService.selectByPage(page);
        }
        Page<List<Sites>> result = new Page<List<Sites>>(page, list);
        return Result.success(result);
    }

    @RequestMapping("deleteByCondition")
    @ResponseBody
    public Result<Integer> deleteByCondition(Sites record){
        return Result.success(sitesService.deleteByCondition(record));
    }

    @RequestMapping("delete")
    @ResponseBody
    public Result<Integer> deleteByPrimaryKey(Integer id){
        return Result.success(sitesService.deleteByPrimaryKey(id));
    }

}
