package com.tensquare.base.controller;


import com.tensquare.base.pojo.Label;
import com.tensquare.base.service.LabelService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import jdk.net.SocketFlow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.domain.Page;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/label")
@CrossOrigin
//热部署注解
@RefreshScope
public class LabelController {
    @Autowired
    private HttpServletRequest request ;
    @Autowired
    private LabelService labelService ;
//    @Value("${ip}")
//    private String ip ;
//    @GetMapping
//    public Result findAll()
//    {
//        System.out.println("ip : " + ip);
//        String header = request.getHeader("Authorization");
//        System.out.println(header);
//        List<Lable> list = labelService.findAll() ;
//        return new Result(true, StatusCode.OK, "查询成功", list);
//    }
    @GetMapping
    public Result findAll(){
        String header = request.getHeader("Authorization");
        System.out.println("头信息: "+header);
        return new Result(true, StatusCode.OK, "查询成功", labelService.findAll());
    }
    @GetMapping("/{labelId}")
    public Result findById(@PathVariable("labelId") String labelId)
    {
        return new Result(true, StatusCode.OK, "查询成功", labelService.findById(labelId));
    }
    @PostMapping("/save")
    @ResponseBody
    public Result save(Label label)
    {
        labelService.save(label);
        return new Result(true, StatusCode.OK, "保存成功");
    }

    @PutMapping("/{labelId}")
    @ResponseBody
    public Result update(@PathVariable("labelId") String id
    ,  Label label) {
        label.setId(id);
        labelService.update(label);

        return new Result(true, StatusCode.OK, "更新成功");
    }
    @DeleteMapping("/{labelId}")
    public Result delete(@PathVariable("labelId") String labelId)
    {
        labelService.deleteById(labelId);
        return new Result(true, StatusCode.OK, "删除成功");
    }

    /**\
     * 根据labelName 找相似的
     * @param label
     * @return
     */
    @PostMapping("/search")
    @ResponseBody
    public Result findSearch(Label label)
    {
        List<Label> list = labelService.findSearch(label);
        return new Result(true, StatusCode.OK, "查询成功", list);
    }

    @PostMapping("/search/{page}/{size}")
    public Result pageQuery(@PathVariable("page")Integer page
            , @PathVariable("size") Integer size  , Label label)
    {
        Page<Label> pageData = labelService.pageQuery(label , page , size) ;
        return new Result(true,StatusCode.OK, "查询成功",
                new PageResult<Label>(pageData.getTotalElements(), pageData.getContent()));
    }

}
