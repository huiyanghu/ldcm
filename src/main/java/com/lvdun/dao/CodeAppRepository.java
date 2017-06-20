package com.lvdun.dao;

import com.lvdun.entity.CodeApp;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Administrator on 2017/6/19.
 */
public interface CodeAppRepository extends CrudRepository<CodeApp, Long> {
    @Query("select app from CodeApp app where app.platformId= :platformId ")
    public List<CodeApp> getCodeAppByPlatformId(@Param("platformId") Long platformId);
}
