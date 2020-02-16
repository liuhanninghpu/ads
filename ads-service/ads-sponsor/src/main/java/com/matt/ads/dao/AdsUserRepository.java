package com.matt.ads.dao;

import com.matt.ads.entity.AdsUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;


@NoRepositoryBean
public interface AdsUserRepository extends JpaRepository<AdsUser,Long> {

    /*
     *根据用户名查找用户记录
     */
    AdsUser findByUsername(String username);

}
