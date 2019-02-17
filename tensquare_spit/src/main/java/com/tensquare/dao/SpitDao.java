package com.tensquare.dao;

import com.tensquare.pojo.Spit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author LiangDong.
 */
public interface SpitDao extends MongoRepository<Spit, String> {

    Page<Spit> findByParentid(String parentid, Pageable pageable);
}
