package com.lvdun.service;

import java.util.Map;

/**
 * Created by Administrator on 2017/6/19.
 */
public interface CustomerService {
    public Boolean checkCustomerNameIsExists(String customerName);

    public Map getCustomerInfo(Long customerId);

    public void updateCustomerBasicInfo(Long customerId, String customerName, String contactsName, String contactsMobile, String approvalTime, String province, String city, String region);

    public Map getCustomerPage(Integer page, Integer pageSize);

    public void reviewCustomer(Long customerId,Integer status);
}
