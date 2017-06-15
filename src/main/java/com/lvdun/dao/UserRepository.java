package com.lvdun.dao;

import com.lvdun.entity.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.Table;
import java.util.List;

/**
 * Created by Administrator on 2017/6/14.
 */
@Repository
@Table(name = "user")
@Qualifier("userRepository")
public interface UserRepository extends CrudRepository<User, Long> {

    // 使用query 注解进行update 或者 delete 语句时，需要添加 modifying 注解修饰
    @Query(value = "delete from spj_student_2", nativeQuery = true)
    @Modifying
    public void deleteAllBySql();

    @Query("select t from User t where t.userName=:name")
    public User findUserByName(@Param("name") String name);


    @Query(value = "select u from User u")
    public List<User> test();
}
