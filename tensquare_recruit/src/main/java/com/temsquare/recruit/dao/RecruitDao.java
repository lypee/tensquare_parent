package com.temsquare.recruit.dao;

import com.temsquare.recruit.pojo.Recruit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface RecruitDao extends JpaRepository<Recruit,String>, JpaSpecificationExecutor<Recruit> {
    public List<Recruit> findTop6ByStateOrderByCreatetimeDesc(String state);

    //前六个 条件是State != 0
    public List<Recruit> findTop6ByStateNotOrderByCreatetimeDesc(String state);

}
