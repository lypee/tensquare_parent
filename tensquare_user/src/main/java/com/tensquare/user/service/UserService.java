package com.tensquare.user.service;

import com.tensquare.user.dao.UserDao;
import com.tensquare.user.pojo.User;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import util.IdWorker;
import util.JwtUtil;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
@Transactional(rollbackFor = Exception.class)
public class UserService {
    @Autowired
    private UserDao userDao ;
    @Autowired
    private IdWorker idWorker ;
    @Autowired
    private RedisTemplate redisTemplate ;
    @Autowired
    private RabbitTemplate rabbitTemplate ;
    @Autowired
    private BCryptPasswordEncoder encoder ;
    @Autowired
    private HttpServletRequest request;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 查看全部
     */
    public List<User> findAll(){
        return userDao.findAll() ;
    }

    /**
     * 条件+分页
     * @param
     */
    public Page<User> findSearch(Map whereMap , int page , int size)
    {
        Specification<User> specification = createSpecification(whereMap);
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        return userDao.findAll(specification, pageRequest);
    }
    /**
     * 条件
     */
    public List<User> findSearch(Map whereMap)
    {
        Specification<User> specification = createSpecification(whereMap);
        return userDao.findAll(specification);
    }
    /**
     * id查实体
     */
    public User findById(String id)
    {
        return userDao.findById(id).get() ;
    }
    /**
     * 增加
     */
    public void add(User user)
    {
        user.setId(idWorker.nextId() + "");
        user.setPassword(encoder.encode(user.getPassword()));
        user.setFollowcount(0);
        user.setFanscount(0);
        user.setOnline(0L);
        user.setRegdate(new Date());
        user.setUpdatedate(new Date());
        user.setLastdate(new Date());
        userDao.save(user);
    }
    /**
     * 修改
     * @param user
     */
    public void update(User user) {
        userDao.save(user);
    }
/**
 * 删除
 */
public void deleteById(String id)
{
    String token = (String) request.getAttribute("claims_admin");
    if (token == null || "".equals(token)) {
        throw new RuntimeException("权限不足") ;
    }
    userDao.deleteById(id);
//    String header = request.getHeader("Authorization");
//    if (StringUtils.isBlank(header) ||  ! header.startsWith("Bearer ")) {
//        throw new RuntimeException("权限不足");
//    }
//    String token = header.substring(7);
//    try{
//        Claims claims = jwtUtil.parseJWT(token);
//        String roles = (String) claims.get("roles");
//        if (roles == null || !StringUtils.equals(roles, "admin")) {
//            throw new RuntimeException("权限不足");
//        }
//    }catch (Exception e)
//    {
//        throw new RuntimeException("权限不足");
//    }
//    userDao.deleteById(id);
}

    /**
     * 动态条件构建
     * @param searchMap
     * @return
     */
    private Specification<User> createSpecification(Map searchMap) {

        return new Specification<User>() {

            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicateList = new ArrayList<Predicate>();
                // ID
                if (searchMap.get("id")!=null && !"".equals(searchMap.get("id"))) {
                    predicateList.add(cb.like(root.get("id").as(String.class), "%"+(String)searchMap.get("id")+"%"));
                }
                // 手机号码
                if (searchMap.get("mobile")!=null && !"".equals(searchMap.get("mobile"))) {
                    predicateList.add(cb.like(root.get("mobile").as(String.class), "%"+(String)searchMap.get("mobile")+"%"));
                }
                // 密码
                if (searchMap.get("password")!=null && !"".equals(searchMap.get("password"))) {
                    predicateList.add(cb.like(root.get("password").as(String.class), "%"+(String)searchMap.get("password")+"%"));
                }
                // 昵称
                if (searchMap.get("nickname")!=null && !"".equals(searchMap.get("nickname"))) {
                    predicateList.add(cb.like(root.get("nickname").as(String.class), "%"+(String)searchMap.get("nickname")+"%"));
                }
                // 性别
                if (searchMap.get("sex")!=null && !"".equals(searchMap.get("sex"))) {
                    predicateList.add(cb.like(root.get("sex").as(String.class), "%"+(String)searchMap.get("sex")+"%"));
                }
                // 头像
                if (searchMap.get("avatar")!=null && !"".equals(searchMap.get("avatar"))) {
                    predicateList.add(cb.like(root.get("avatar").as(String.class), "%"+(String)searchMap.get("avatar")+"%"));
                }
                // E-Mail
                if (searchMap.get("email")!=null && !"".equals(searchMap.get("email"))) {
                    predicateList.add(cb.like(root.get("email").as(String.class), "%"+(String)searchMap.get("email")+"%"));
                }
                // 兴趣
                if (searchMap.get("interest")!=null && !"".equals(searchMap.get("interest"))) {
                    predicateList.add(cb.like(root.get("interest").as(String.class), "%"+(String)searchMap.get("interest")+"%"));
                }
                // 个性
                if (searchMap.get("personality")!=null && !"".equals(searchMap.get("personality"))) {
                    predicateList.add(cb.like(root.get("personality").as(String.class), "%"+(String)searchMap.get("personality")+"%"));
                }

                return cb.and( predicateList.toArray(new Predicate[predicateList.size()]));

            }
        };
    }

    public void sendSms(String mobile) {
        //生成六位数字随机数
        String checkcode = RandomStringUtils.randomNumeric(6);
        //向缓存中放一份
        redisTemplate.opsForValue().set("checkcode_"+mobile, checkcode, 6, TimeUnit.HOURS);
        //给用户发一份
        Map<String, String> map = new HashMap<>();
        map.put("mobile", mobile);
        map.put("checkcode", checkcode);
        rabbitTemplate.convertAndSend("sms", map);
        //在控制台显示一份【方便测试】
        System.out.println("验证码为："+checkcode);
    }

    public User login(String mobile, String password) {
        User user = userDao.findByMobile(mobile);
        if(user!=null && encoder.matches(password, user.getPassword())){
            return user;
        }
        return null;
    }
    @Transactional
    public void updatefanscountandfollowcount(int x , String userid , String friendid)
    {
        userDao.updatefanscount(x, friendid);
        userDao.updatefollowcount(x , userid);
    }
}
