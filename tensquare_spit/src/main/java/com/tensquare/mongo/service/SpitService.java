package com.tensquare.mongo.service;


import com.tensquare.mongo.dao.SpitDao;
import com.tensquare.mongo.pojo.Spit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import util.IdWorker;

import java.util.Date;
import java.util.List;

@Service
@Transactional
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
    //添加吐槽
    public void save(Spit spit)
    {
        spit.set_id(idWorker.nextId() + "");
        spit.setPublishtime(new Date());
        spit.setVisits(0) ;
        spit.setShare(0);//分享数
        spit.setThumbup(0);//点赞数
        spit.setComment(0);//回复数
        spit.setState("1");//状态
        //如果当前吐槽有父节点
        //父节点的吐槽数 + 1 ;
        if (spit.getParentid() != null && !"".equals(spit.getParentid())) {
            Query query = new Query() ;
            query.addCriteria(Criteria.where("_id").is(spit.getParentid()));
            Update update = new Update() ;
            update.inc("comment", 1);
            mongoTemplate.updateFirst(query, update, "spit");
        }
        spitDao.save(spit);
    }
    public void update(Spit spit)
    {
        spitDao.save(spit);
    }
    public void deleteById(String id)
    {
        spitDao.deleteById(id);
    }

    /**
     * 根据parentId差
     * @param parentId
     * @param page
     * @param size
     * @return
     */
    public Page<Spit> pageQuery (String parentId , int page , int size)
    {
        Pageable pageable = PageRequest.of(page - 1, size);
        return spitDao.findByParentid(parentId, pageable);
    }
    public void addthumbup(String id)
    {
//        Spit spit = spitDao.findById(id).get() ;
//        spit.setThumbup( spit.getThumbup() == null ? 0  : spit.getThumbup() + 1   );
//        spitDao.save(spit);
        //db.spit.update({"_id":"1"} , {$inc:{thumbup:NumberInt(1)}})
        //封装查询条件
        Query query = new Query() ;
        query.addCriteria(Criteria.where("_id").is("id"));
        Update update = new Update() ;
        update.inc("thumbup", 1);
        //拼接查询参数 query拼接前面 , update拼接update的后的参数
        mongoTemplate.updateFirst(query, update, "spit");
    }
}
