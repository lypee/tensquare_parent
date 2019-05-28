package com.temsquare.recruit.controller;

import com.temsquare.recruit.pojo.Recruit;
import com.temsquare.recruit.service.RecruitService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/recruit")
@RefreshScope
public class RecruitController {
    @Autowired
    private RecruitService recruitService ;
    @GetMapping("/search/recommend")
    public Result recommend(){
        List<Recruit> list = recruitService.findTop6ByStateOrderByCreateTime();
        return new Result(true, StatusCode.OK, "查询成功", list);
    }
    /**
     * 最新文章
     */
    @GetMapping("/search/newList")
    public Result newList()
    {
        List<Recruit> list = recruitService.findTop6ByStateNotOrderByCreatetimeDesc();
        return new Result(true, StatusCode.OK, "查询成功", list);
    }
    /**
     * 查询全部数据
     */
    @GetMapping
    public Result findAll(){
        return new Result(true, StatusCode.OK, "查询成功", recruitService.findAll());
    }

    /**
     * 根据Id查询
     */
    @GetMapping("{/{id}")
    public Result findById(@PathVariable("/{id}") String id) {
        return new Result(true , StatusCode.OK
          ,"查询成功" ,recruitService.findById(id));
    }
    /**
     * 分页+ 条件从查询
     */
    @PostMapping("/search/{page}/{size}")
    public Result findSearch(Map searchMap , @PathVariable("page") int page ,
                             @PathVariable("size") int size)
    {
        Page<Recruit> pageList = recruitService.findSearch(searchMap, page, size);
        return  new Result(true,StatusCode.OK,"查询成功",  new PageResult<Recruit>(pageList.getTotalElements(), pageList.getContent()) );
    }
    /**
     * 根据条件查询
     * @param searchMap
     * @return
     */
    @RequestMapping(value="/search",method = RequestMethod.POST)
    public Result findSearch( @RequestBody Map searchMap){
        return new Result(true,StatusCode.OK,"查询成功",recruitService.findSearch(searchMap));
    }


    /**
     * 增加
     * @param recruit
     */
    @RequestMapping(method=RequestMethod.POST)
    public Result add(@RequestBody Recruit recruit  ){
        recruitService.add(recruit);
        return new Result(true,StatusCode.OK,"增加成功");
    }

    /**
     * 修改
     * @param recruit
     */
    @RequestMapping(value="/{id}",method= RequestMethod.PUT)
    public Result update(@RequestBody Recruit recruit, @PathVariable String id ){
        recruit.setId(id);
        recruitService.update(recruit);
        return new Result(true,StatusCode.OK,"修改成功");
    }

    /**
     * 删除
     * @param id
     */
    @RequestMapping(value="/{id}",method= RequestMethod.DELETE)
    public Result delete(@PathVariable String id ){
        recruitService.deleteById(id);
        return new Result(true,StatusCode.OK,"删除成功");
    }

}
