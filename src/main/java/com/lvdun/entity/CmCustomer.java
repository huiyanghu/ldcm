package com.lvdun.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Administrator on 2017/6/14.
 */
@Entity
@Table(name = "cm_customer")
public class CmCustomer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;


    //@ManyToOne(cascade = {CascadeType.PERSIST}, fetch = FetchType.LAZY)
    //@JoinColumn(name = "platform_id")
    @Column(name = "platform_id")
    private Long platformId;

    @Column(name = "customer_name")
    private String customerName;//客户名称，从账号

    @Column(name = "contacts_name")
    private String contactsName;//联系人姓名

    @Column(name = "contacts_tel")
    private String contactsTel;//联系人办公座机

    @Column(name = "contacts_mobile")
    private String contactsMobile;//联系人手机

    @Column(name = "approval_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date approvalTime;//超级管理员审批时间

    @Column(name = "province")
    private String province;//所在省

    @Column(name = "city")
    private String city;//所在市

    @Column(name = "region")
    private String region;//所在区县

    @Column(name = "status")
    private Integer status;//0新注册用户（未经过超级管理员审核）-1、体验用户2、正式收费用户3、正式免费用户4、停用


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPlatformId() {
        return platformId;
    }

    public void setPlatformId(Long platformId) {
        this.platformId = platformId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getContactsName() {
        return contactsName;
    }

    public void setContactsName(String contactsName) {
        this.contactsName = contactsName;
    }

    public String getContactsTel() {
        return contactsTel;
    }

    public void setContactsTel(String contactsTel) {
        this.contactsTel = contactsTel;
    }

    public String getContactsMobile() {
        return contactsMobile;
    }

    public void setContactsMobile(String contactsMobile) {
        this.contactsMobile = contactsMobile;
    }

    public Date getApprovalTime() {
        return approvalTime;
    }

    public void setApprovalTime(Date approvalTime) {
        this.approvalTime = approvalTime;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }


}
