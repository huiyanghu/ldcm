package com.lvdun.dao;

import com.lvdun.entity.StatArriveDelete;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * Created by Administrator on 2017/6/15.
 */
public interface StatArriveDeleteRepository extends JpaRepository<StatArriveDelete, Long>, StatArriveDeleteDao {

}
