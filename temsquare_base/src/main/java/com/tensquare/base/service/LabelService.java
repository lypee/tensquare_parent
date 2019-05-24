package com.tensquare.base.service;


import com.tensquare.base.dao.LabelDao;
import com.tensquare.base.pojo.Label;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import util.IdWorker;
import org.apache.commons.lang3.StringUtils ;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class LabelService {
    @Autowired
    private LabelDao labelDao ;
    @Autowired
    private IdWorker idWorker ;

    public void save( Label label)
    {
        label.setId(idWorker.nextId() + "");
        labelDao.save(label);
    }

    public List<Label> findAll()
    {

        return labelDao.findAll() ;
    }
    public Label findById(String id)
    {

        return labelDao.findById(id).get() ;
    }

    public void update(Label label)
    {

        labelDao.save(label);
    }

    public void deleteById(String labelId)
    {
        labelDao.deleteById(labelId);

    }

    public List<Label> findSearch(Label label)
    {
        return labelDao.findAll(new Specification<Label>() {
            /**
             *
             * @param root 根对象 也就是要把条件封装到哪个对象中 ,
             *             类似于where 列名 = label.getId()
             * @param query 封装查询的关键字 比如group by ,order by  基本不用
             * @param criteriaBuilder 封装条件对象 .  如果直接返回null  意味没有条件
             * @return
             */
            @Override
            public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                //cb的模糊查询用like , 准确查询用equal
                List<Predicate> list = new ArrayList<>();
//                if(StringUtils.isBlank(label.getLabelname))
                if(label.getLabelname()!=null && !"".equals(label.getLabelname())){
                    //as 指定类型 get 选择列 , 最后一个参数封装where后的条件
                    Predicate predicate = criteriaBuilder.like(root.get("labelname").as(String.class), "%" + label.getLabelname() + "%");
                    list.add(predicate);
                }
//                if(StringUtils.isBlank(label.getState()))
                if(label.getState()!=null && !"".equals(label.getState())){
                    Predicate predicate = criteriaBuilder.equal(root.get("state").as(String.class), label.getState());
                    list.add(predicate);
                }
                Predicate[] parr = new Predicate[list.size()];
                parr = list.toArray(parr) ;
                //此处and表示使用
                return criteriaBuilder.and(parr);
            }
        }) ;
    }

//


    public Page<Label> pageQuery(Label label, int page, int size){
        //封装了一个分页对象，在springdatajpa中想要实现分页，直接传一个分页对象即可
        Pageable pageable = PageRequest.of(page-1, size);
        return labelDao.findAll(new Specification<Label>(){
            @Override
            public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<>();
                if(label.getLabelname()!=null && !"".equals(label.getLabelname())){
                    Predicate predicate = cb.like(root.get("labelname").as(String.class), "%" + label.getLabelname() + "%");
                    list.add(predicate);
                }
                if(label.getState()!=null && !"".equals(label.getState())){
                    Predicate predicate = cb.equal(root.get("state").as(String.class), label.getState());
                    list.add(predicate);
                }
                Predicate[] parr = new Predicate[list.size()];
                //把集合中的属性复制到数组中
                parr = list.toArray(parr);
                return cb.and(parr);
            }
        }, pageable);
    }
}
