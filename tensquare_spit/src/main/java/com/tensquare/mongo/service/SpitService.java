package com.tensquare.mongo.service;


import com.tensquare.mongo.dao.SpitDao;
import com.tensquare.mongo.pojo.Spit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import util.IdWorker;

import java.util.Date;
import java.util.List;

@Service
public class SpitService {
    @Autowired
    private SpitDao spitDao ;
    @Autowired
    private IdWorker idWorker ;
    @Autowired
    private MongoTemplate mongoTemplate ;

    public List<Spit> findAll(){
        return spitDao.findAll() ;
    }

    public Spit findById(String id){
        return spitDao.findById(id).get() ;
    }
    public void save(Spit spit)
    {
        spit.set_id(idWorker.nextId() + "");
        spit.setPublishtime(new Date());
        spit.setVisits(0) ;
        spit.setShare(0);//分享数
        spit.setThumbup(0);//点赞数
        spit.setComment(0);//回复数
        spit.setState("1");//状态
    }
}
