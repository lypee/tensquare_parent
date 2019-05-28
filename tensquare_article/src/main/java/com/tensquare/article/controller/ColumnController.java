package com.tensquare.article.controller;


import com.tensquare.article.pojo.Column;
import com.tensquare.article.service.ColumnService;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
@RefreshScope
@RestController
@CrossOrigin
@RequestMapping("/column")
public class ColumnController {
    @Autowired
    private ColumnService columnService;
    @GetMapping
public Result findAll(){
    return new Result(true, StatusCode.OK, "查询成功", columnService.findAll());
}

    @GetMapping("/{id}")
    public Result findById(@PathVariable("id")  String id)
    {
        return new Result(true, StatusCode.OK, "查询成功", columnService.findAll());
    }

    /**
     * 分页和条件查询
     */
    @PostMapping("/search/{page}/{size}")
    public Result findSearch(Map searchMap , @PathVariable("page") int page , @PathVariable("size") int size)
    {
        Page<Column> pageList = columnService.findSearch(searchMap, page, size);
        return new Result(true, StatusCode.OK, "查询成功", pageList);
    }
    @PostMapping("/search")
    public Result findSearch(Map searchMap)
    {
        return new Result(true , StatusCode.OK , "查询成功" , columnService.findSearch(searchMap));
    }
    /**
     * 增加
     * @param column
     */
    @RequestMapping(method=RequestMethod.POST)
    public Result add(@RequestBody Column column  ){
        columnService.add(column);
        return new Result(true,StatusCode.OK,"增加成功");
    }

    /**
     * 修改
     * @param column
     */
    @RequestMapping(value="/{id}",method= RequestMethod.PUT)
    public Result update(@RequestBody Column column, @PathVariable String id ){
        column.setId(id);
        columnService.update(column);
        return new Result(true,StatusCode.OK,"修改成功");
    }

    /**
     * 删除
     * @param id
     */
    @RequestMapping(value="/{id}",method= RequestMethod.DELETE)
    public Result delete(@PathVariable String id ){
        columnService.deleteById(id);
        return new Result(true,StatusCode.OK,"删除成功");
    }


}
