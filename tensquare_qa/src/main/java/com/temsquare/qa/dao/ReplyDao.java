package com.temsquare.qa.dao;

import com.temsquare.qa.pojo.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ReplyDao extends JpaRepository<Reply,String>, JpaSpecificationExecutor<Reply> {

}

