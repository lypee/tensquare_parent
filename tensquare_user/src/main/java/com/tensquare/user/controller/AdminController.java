package com.tensquare.user.controller;


import com.tensquare.user.pojo.Admin;
import com.tensquare.user.service.AdminService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import util.JwtUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService ;
    @Autowired
    private JwtUtil jwtUtil ;
    @Autowired
    private HttpServletRequest request ;

    @PostMapping("/login")
    public Result login(@RequestBody Admin admin) {
        Admin adminlogin = adminService.login(admin);
        if (adminlogin == null) {
            return new Result(false, StatusCode.LOGINERROR, "登录失败");
        }
        //前后端通话的操作 , 通过JWT
        //生成令牌
        String token = jwtUtil.createJWT(adminlogin.getId(), adminlogin.getLoginname(), "admin");
        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        map.put("role", "admin");
        request.setAttribute("user_token", map);
        return new Result(true, StatusCode.OK, "登录成功", map);
    }
    /**
     * 查询全部数据
     */
    @GetMapping
    public Result findAll(){
        return new Result(true, StatusCode.OK, "查询 成功", adminService.findAll());
    }
    /**
     * by Id
     */
    @GetMapping("/{id}")
    public Result findById(@PathVariable("id") String id)
    {
        return new Result(true, StatusCode.OK, "查询成功", adminService.findById(id));
    }

    /**
     * 分页 + 条件查询
     */
    @PostMapping("/search/{page}/{size}")
    public Result findSearch(@RequestBody Map searchMap, @PathVariable("page")  int page, @PathVariable("size") int size) {
        Page<Admin> pageList = adminService.findSearch(searchMap , page , size) ;
        return  new Result(true, StatusCode.OK, "查询成功", new PageResult<Admin>(pageList.getTotalElements(), pageList.getContent()));
    }

    /**
     * 条件
     */
    @PostMapping("/search")
    public Result findSearch(@RequestBody Map searchMap) {
        return new Result(true, StatusCode.OK, "查询成功", adminService.findSearch(searchMap));
    }
    /**
     * 增加
     * @param admin
     */
    @PostMapping
    public Result add(@RequestBody Admin admin ){
        adminService.add(admin);
        return new Result(true,StatusCode.OK,"增加成功");
    }
    /**
     * 修改
     * @param admin
     */
    @PutMapping("/{id}")
    public Result update(@RequestBody Admin admin, @PathVariable String id ){
        admin.setId(id);
        adminService.update(admin);
        return new Result(true,StatusCode.OK,"修改成功");
    }
    /**
     * 删除
     * @param id
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable String id ){
        adminService.deleteById(id);
        return new Result(true,StatusCode.OK,"删除成功");
    }
}
