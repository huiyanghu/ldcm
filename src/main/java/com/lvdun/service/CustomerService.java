package com.lvdun.service;

import java.util.Map;

/**
 * Created by Administrator on 2017/6/19.
 */
public interface CustomerService {
    public Map getCustomerInfo(Long custumerId);

    public void updateCustomerBasicInfo(Long customerId, String customerName, String contactsName, String contactsMobile, String approvalTime, String province, String city, String region);
}
