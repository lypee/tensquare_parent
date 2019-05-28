package com.tensquare.friend.controller;

import com.tensquare.friend.client.UserClient;
import com.tensquare.friend.service.FriendService;
import entity.Result;
import entity.StatusCode;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import util.JwtUtil;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/friend")
public class FriendController {
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private FriendService friendService;
    @Autowired
    private UserClient userClient;
    @Autowired
    private JwtUtil jwtUtil ;
    @PutMapping("/like/{friendid}/{type}")
    public Result addFriend(@PathVariable("friendid") String friendid
            , @PathVariable("type") String type) {
//        Claims claims = (Claims) request.getAttribute("claims_admin");
//         if(claims == null){
//             claims = (Claims) request.getAttribute("claims_user");
//         }
//         if(claims == null)
//         {
//             return new Result(false, StatusCode.LOGINERROR, "无对应权限");
//         }
        String  string = (String) request.getAttribute("claims_user");
        Claims claims = (Claims) jwtUtil.parseJWT(string);
        if(claims==null){
            //说明当前用户没有user角色
            return new Result(false, StatusCode.LOGINERROR, "权限不足");
        }
         String userid = claims.getId() ;
         if(type != null)
         {
             //判断添加的是好友还是非好友
             if (StringUtils.equals("1", type)) {
                 int flag = friendService.addFriend(userid, friendid);
                 if (flag == 0) {
                     return new Result(false, StatusCode.ERROR, "请勿重复添加");
                 }
                 if (flag == 1) {
                     userClient.updatefanscountandfollowcount(userid, friendid, 1);
                     return new Result(true, StatusCode.OK, "添加成功");
                 }
             }else if (StringUtils.equals("2", type)) {
                     int flag = friendService.addNoFriend(userid, friendid);
                     if(flag == 0)
                     {
                         return new Result(false , StatusCode.ERROR , "不能重复添加非好友");
                 }
                 if(flag==1){
                     return new Result(true, StatusCode.OK, "添加成功");
                 }
             }
             return new Result(false , StatusCode.ERROR , "参数异常") ;
         }else{
             return new Result(false, StatusCode.ERROR, "参数异常");
         }
    }

    @DeleteMapping("/{friendid}")
    public Result deleteFriend(@PathVariable("friendid") String friendid) {

          String  string = (String) request.getAttribute("claims_user");
          Claims  claims = (Claims) jwtUtil.parseJWT(string);

        if(claims == null)
        {
            return new Result(false, StatusCode.LOGINERROR, "无对应权限");
        }
        String userid = claims.getId() ;

        friendService.deleteFriend(userid, friendid);
        userClient.updatefanscountandfollowcount(userid, friendid, -1);
        return new Result(true, StatusCode.OK, "删除成功");
    }
}
