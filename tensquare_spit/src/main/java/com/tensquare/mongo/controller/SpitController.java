package com.tensquare.mongo.controller;

import com.tensquare.mongo.pojo.Spit;
import com.tensquare.mongo.service.SpitService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/spit")
@CrossOrigin
@RefreshScope
public class SpitController {
    @Autowired
    private SpitService spitService ;
    @Autowired
    private RedisTemplate redisTemplate ;

    @Value("${ip}")
    private String ip;

    @GetMapping
    public Result  findAll(){
        return new Result(true, StatusCode.OK, "查询成功", spitService.findAll());
    }
    @GetMapping("/{id}")
    public Result findById(@PathVariable("id")String id)
    {
        System.out.println("测试ip为: " + ip);
        return new Result(true, StatusCode.OK, "查询成功", spitService.findById(id));
    }
    @PostMapping
    @ResponseBody
    public Result save(Spit spit)
    {
        spitService.save(spit);
            return new Result(true, StatusCode.OK, "" +
                    "" +
                    "" +
                    "" +
                    "" +
                    "成功");
    }

    @PutMapping("/{spitId}")
    public Result update(@PathVariable("spitId") String spitId, Spit spit) {
        spit.set_id(spitId);
        spitService.update(spit);
        return new Result(true, StatusCode.OK, "修改成功");
    }
    @DeleteMapping("/{spitId}")
    public Result delete(@PathVariable String spitId)
    {
        spitService.deleteById(spitId
        );
        return new Result(true, StatusCode.OK, "删除成功");
    }

    /**
     * 根据parentid查
     * @param parentid
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/comment/{parentid}/{page}/{size}")
    public Result comment(@PathVariable("parentid") String parentid ,
                          @PathVariable("page") int page ,
                          @PathVariable("size") int size)
    {
        Page<Spit> pageData = spitService.pageQuery(parentid, page, size);
        return new Result(true, StatusCode.OK, "查询成功", new PageResult<Spit>(pageData.getTotalElements(), pageData.getContent()));
    }
    @PutMapping("/thumbup/{spitid}")
    public Result addthumbup(@PathVariable("spitid")String spitId)
    {
        //userid是写死的 不是登录的
        String userid = "11111";
        if(redisTemplate.opsForValue().get("spit_"+userid +"_"+spitId) != null)
        {
            // == null
            return new Result(false, StatusCode.ERROR, "不能重复点赞");
        }
        spitService.addthumbup(spitId);
        redisTemplate.opsForValue().set("spit_" + userid + "_" + spitId, 1);
        return new Result(true, StatusCode.OK, "点赞成功");
    }



}
