package com.tensquare.article.controller;


import com.tensquare.article.pojo.Article;
import com.tensquare.article.service.ArticleService;
import entity.PageResult;
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
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService ;
    @RequestMapping(value = "/examine/{articleId}", method= RequestMethod.PUT)
    public Result examine(@PathVariable String articleId){
        articleService.examine(articleId);
        return new Result(true, StatusCode.OK,"操作成功");
    }


    @PutMapping("/thumbup/{articleId}")
    public Result updateThumbup(@PathVariable("articleId") String articleId)
    {
        articleService.updateThumbup(articleId);
        return new Result(true, StatusCode.OK, "操作成功");
    }
    /**
     * 查询全部数据
     * @return
     */
    @RequestMapping(method= RequestMethod.GET)
    public Result findAll(){
        return new Result(true,StatusCode.OK,"查询成功",articleService.findAll());
    }
    /**
     * 根据ID查询
     * @param id ID
     * @return
     */
    @RequestMapping(value="/{id}",method= RequestMethod.GET)
    public Result findById(@PathVariable String id){
        return new Result(true,StatusCode.OK,"查询成功",articleService.findById(id));
    }
    /**
     * 分页+多条件查询
     */
    @PostMapping("/search/{page}/{size}")
    public Result findSearch(Map searchMap ,@PathVariable("page") int page , @PathVariable("size") int size)
    {
        Page<Article> pageList = articleService.findSearch(searchMap, page, size);
        return new Result(true, StatusCode.OK, "查询成功", new PageResult<Article>(pageList.getTotalElements(), pageList.getContent()));
    }
    /**
     * 根据条件查询
     */
    @PostMapping("/search")
    public Result findSearch(Map searchMap)
    {
        return new Result(true, StatusCode.OK, "查询成功", articleService.findSearch(searchMap));
    }

}
