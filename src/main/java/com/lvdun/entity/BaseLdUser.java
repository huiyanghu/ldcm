package com.lvdun.entity;

import javax.persistence.*;

/**
 * Created by Administrator on 2017/6/16.
 */
@Entity
@Table(name = "base_lduser")
public class BaseLdUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "customer_id")
    private Long custumerId;

    @Column(name = "user_name")
    private String name;

    @Column(name = "user_mobile")
    private String mobile;

    @Column(name = "user_email")
    private String email;

    @Column(name = "user_qq")
    private String qq;

    @Column(name = "user_weixin")
    private String weixin;

    @Column(name = "user_icon")
    private String icon;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustumerId() {
        return custumerId;
    }

    public void setCustumerId(Long custumerId) {
        this.custumerId = custumerId;
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

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getWeixin() {
        return weixin;
    }

    public void setWeixin(String weixin) {
        this.weixin = weixin;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
