package com.lvdun.service.impl;

import com.lvdun.dao.BaseLdUserRepository;
import com.lvdun.entity.BaseLdUser;
import com.lvdun.service.BaseLdUserService;
import com.lvdun.util.ConstantsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/20.
 */
@Service
public class BaseLdUserServiceImpl implements BaseLdUserService {
    @Autowired
    BaseLdUserRepository baseLdUserDao;

    public Map getBaseLdUserPage(Long customerId, Integer page, Integer pageSize) {
        Sort.Order order = new Sort.Order(Sort.Direction.DESC, "id");
        Sort sort = new Sort(order);
        Pageable pageable = new PageRequest(page-1, pageSize, sort);

        Specification<BaseLdUser> specification = new Specification<BaseLdUser>() {
            @Override
            public Predicate toPredicate(Root<BaseLdUser> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Path path = root.get("customerId");
                Predicate predicate = criteriaBuilder.equal(path, customerId);
                return predicate;
            }
        };


        Page<BaseLdUser> baseLdUserPage = baseLdUserDao.findAll(specification, pageable);
        List<BaseLdUser> baseLdUserList = baseLdUserPage.getContent();
        Map result=new HashMap();
        List<Map> list = new ArrayList<>();
        for (BaseLdUser baseLdUser : baseLdUserList) {
            Map map = new HashMap();
            map.put("id", baseLdUser.getId());
            map.put("account", baseLdUser.getAccount() == null ? "" : baseLdUser.getAccount().getAccount());
            map.put("name", baseLdUser.getName());
            map.put("mobile", baseLdUser.getMobile());
            map.put("roleFlag", baseLdUser.getAccount() == null ? "" : baseLdUser.getAccount().getRoleFlag());
            map.put("roleFlagStr", baseLdUser.getAccount() == null ? "" : ConstantsUtil.getConstants("CmAccount_RoleFlag", "" + baseLdUser.getAccount().getRoleFlag()));
            list.add(map);
        }

        result.put("content",list);
        result.put("total",baseLdUserPage.getTotalElements());
        result.put("pageCount",baseLdUserPage.getTotalPages());
        result.put("currentPage",baseLdUserPage.getNumber()+1);
        result.put("pageSize",baseLdUserPage.getSize());

        return result;
    }
}
