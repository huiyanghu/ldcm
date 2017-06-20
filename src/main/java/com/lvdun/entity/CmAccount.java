package com.lvdun.entity;

import javax.persistence.*;

/**
 * Created by Administrator on 2017/6/14.
 */

@Entity
@Table(name = "cm_account")
public class CmAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    //@ManyToOne(cascade = {CascadeType.PERSIST}, fetch = FetchType.LAZY)
    //@JoinColumn(name = "customer_id")
    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "account")
    private String account;//登录账号，邮箱登录(唯一)

    @Column(name = "name")
    private String name;

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "email")
    private String email;//与登录账号相同

    @Column(name = "password")
    private String password;

    @Column(name = "status")
    private Integer status;//0新注册用户（未经过超级管理员审核）-1、体验用户2、正式收费用户3、正式免费用户4、停用

    @Column(name = "role_flag")
    private Integer roleFlag;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getRoleFlag() {
        return roleFlag;
    }

    public void setRoleFlag(Integer roleFlag) {
        this.roleFlag = roleFlag;
    }
}
