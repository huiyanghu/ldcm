package com.lvdun.dao;

import com.lvdun.entity.CodePlatform;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Administrator on 2017/6/15.
 */
@Repository
public interface CodePlatformRepository extends CrudRepository<CodePlatform, Long> {

}
