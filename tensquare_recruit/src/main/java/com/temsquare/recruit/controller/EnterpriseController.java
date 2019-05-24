package com.temsquare.recruit.controller;


import com.temsquare.recruit.pojo.Enterprise;
import com.temsquare.recruit.service.EnterpriseService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import util.IdWorker;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/enterprise")
public class EnterpriseController {
    @Autowired
    private EnterpriseService enterpriseService ;
    @Autowired
    private IdWorker idWorker ;
    @GetMapping("/search/hotlist")
    public Result hotlist(){
        List<Enterprise> list = enterpriseService.hotList() ;
        return new Result(true, StatusCode.OK, "查询成功" , list);
    }
    //findAll
    @GetMapping()
    public Result findAll(){
        return new Result(true, StatusCode.OK, "查询成功", enterpriseService.findAll());
    }

    /**
     * 根据Id查询
     */
    @GetMapping("/{id}")
    public Result findById(@PathVariable("id") String id) {
        return new Result(true, StatusCode.OK, "查询成功", enterpriseService.findById(id));
    }
    /**
     * 分页+多条件查询
     */
    @PostMapping("/search/{page}/{size}")
    public Result findSearch(Map searchMap  , @PathVariable("page") int page , @PathVariable("size") int size)
    {
        Page<Enterprise> pageList = enterpriseService.findSearch(searchMap, page, size);
        PageResult<Enterprise> pageResult = new PageResult<>(pageList.getTotalElements(), pageList.getContent());
        return new Result(true, StatusCode.OK, "查询成功", pageResult);
    }

    /**
     * 根据条件查询
     */
    @PostMapping("/search")
    public Result findSearch(Map searchMap)
    {
        return new Result(true, StatusCode.OK,
                "查询成功",
                enterpriseService.findSearch(searchMap));
    }
    /**
     * 增加
     */
    @PostMapping
    public Result add(Enterprise enterprise)
    {
        enterpriseService.add(enterprise);
        return new Result(true, StatusCode.OK, "增加成功");
    }
    /**
     * 修改
     */
    @PutMapping("/{id}")
    public Result update(Enterprise enterprise , @PathVariable("id") String id)
    {
        enterprise.setId(id);
        enterpriseService.update(enterprise);
        return new Result(true, StatusCode.OK, "修改成功");
    }
    /**
     * 删除
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable("id") String id)
    {
        enterpriseService.deleteById(id);
        return new Result(true, StatusCode.OK, "删除成功");
    }

}
