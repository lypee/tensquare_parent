package com.temsquare.recruit.dao;

import com.temsquare.recruit.pojo.Enterprise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface EnterpriseDao extends JpaRepository<Enterprise,String>, JpaSpecificationExecutor<Enterprise> {
    //面向对象的查询语句jphl。jphl类似于hql。hql是hibernate内部面向对象的查询语句
    public List<Enterprise> findByIshot(String ishot);
}