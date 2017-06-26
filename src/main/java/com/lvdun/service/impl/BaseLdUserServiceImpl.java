package com.lvdun.service.impl;

import com.lvdun.dao.BaseLdUserRepository;
import com.lvdun.dao.CmAccountRepository;
import com.lvdun.dao.CmCustomerRepository;
import com.lvdun.entity.BaseLdUser;
import com.lvdun.entity.CmAccount;
import com.lvdun.entity.CmCustomer;
import com.lvdun.service.BaseLdUserService;
import com.lvdun.util.ConstantsUtil;
import com.lvdun.util.MD5;
import com.lvdun.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import javax.transaction.Transactional;
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
    @Autowired
    CmAccountRepository accountDao;
    @Autowired
    CmCustomerRepository customerDao;

    @Override
    public Map getBaseLdUserPage(Long customerId, Integer page, Integer pageSize) {
        Sort.Order order = new Sort.Order(Sort.Direction.DESC, "id");
        Sort sort = new Sort(order);
        Pageable pageable = new PageRequest(page - 1, pageSize, sort);

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
        Map result = new HashMap();
        List<Map> list = new ArrayList<>();
        for (BaseLdUser baseLdUser : baseLdUserList) {
            Map map = new HashMap();
            map.put("baseLdUserId", baseLdUser.getId());
            map.put("account", baseLdUser.getAccount() == null ? "" : baseLdUser.getAccount().getAccount());
            map.put("accountId", baseLdUser.getAccount() == null ? "" : baseLdUser.getAccount().getId());
            map.put("name", baseLdUser.getName());
            map.put("mobile", baseLdUser.getMobile());
            map.put("roleFlag", baseLdUser.getAccount() == null ? "" : baseLdUser.getAccount().getRoleFlag());
            map.put("roleFlagStr", baseLdUser.getAccount() == null ? "" : ConstantsUtil.getConstants("CmAccount_roleFlag", "" + baseLdUser.getAccount().getRoleFlag()));
            list.add(map);
        }

        result.put("content", list);
        result.put("total", baseLdUserPage.getTotalElements());
        result.put("pageCount", baseLdUserPage.getTotalPages());
        result.put("currentPage", baseLdUserPage.getNumber() + 1);
        result.put("pageSize", baseLdUserPage.getSize());

        return result;
    }

    @Override
    @Transactional
    public void addUser(Long customerId, String email, String name, String mobile, Integer roleFlag, String password) {
        /*账户*/
        CmAccount account = new CmAccount();
        account.setAccount(email);
        account.setName(name);
        account.setEmail(email);
        account.setMobile(mobile);
        account.setPassword(MD5.MD5(password));
        account.setStatus(0);//0新注册用户（未经过超级管理员审核）-1、体验用户2、正式收费用户3、正式免费用户4、停用
        if (roleFlag == 1) {
            accountDao.updateAccountRoleFlagToZero();
        }
        account.setRoleFlag(roleFlag);
        account.setCustomerId(customerId);
        accountDao.save(account);

        /*运营人员*/
        BaseLdUser baseLdUser = new BaseLdUser();
        baseLdUser.setCustomerId(customerId);
        baseLdUser.setAccount(account);
        baseLdUser.setEmail(email);
        baseLdUser.setMobile(mobile);
        baseLdUser.setName(name);
        baseLdUserDao.save(baseLdUser);
    }

    @Override
    @Transactional
    public void updateUser(Long accountId, String name, String mobile, Integer roleFlag,String newPassword) {
        BaseLdUser baseLdUser = baseLdUserDao.getByAccountId(accountId);
        baseLdUser.setName(name);
        baseLdUser.setMobile(mobile);
        baseLdUserDao.save(baseLdUser);

        CmAccount account = accountDao.findOne(accountId);
        account.setName(name);
        account.setMobile(mobile);
        if (StringUtil.isNotEmpty(roleFlag)){
            if (roleFlag == 1) {
                accountDao.updateAccountRoleFlagToZero();
            }
            account.setRoleFlag(roleFlag);
        }
        if (StringUtil.isNotEmpty(newPassword)){
            account.setPassword(MD5.MD5(newPassword).toUpperCase());
        }

        accountDao.save(account);
    }

    @Override
    @Transactional
    public void deleteUser(Long baseLdUserId) {
        BaseLdUser baseLdUser = baseLdUserDao.findOne(baseLdUserId);
        baseLdUserDao.delete(baseLdUserId);
        accountDao.delete(baseLdUser.getAccount().getId());
    }

    @Override
    @Transactional
    public void changeBaseLdUserRole(Long baseLdUserId) {
        BaseLdUser baseLdUser = baseLdUserDao.findOne(baseLdUserId);
        CmAccount account = accountDao.findOne(baseLdUser.getAccount().getId());
        if (account.getRoleFlag() == 0) {
            accountDao.updateAccountRoleFlagToZero();
            account.setRoleFlag(1);
        } else {
            account.setRoleFlag(0);
        }
        accountDao.save(account);
    }

    @Override
    public Map getUserDetail(Long accountId) {
        Map map = new HashMap();
        CmAccount account = accountDao.findOne(accountId);
        map.put("account", account.getAccount());
        map.put("name", account.getName());
        map.put("mobile", account.getMobile());
        CmCustomer customer = customerDao.getOne(account.getCustomerId());
        map.put("companyName", customer.getCustomerName());
        return map;
    }
}
