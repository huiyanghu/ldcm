package com.lvdun.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Administrator on 2017/6/14.
 */
@Entity
@Table(name = "code_platform")
public class CodePlatform {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "status")
    private Integer status;//新注册用户-1、体验客户1、正式收费2、正式免费3、暂停客户4

    @Column(name = "feedback")
    private String feedback;//回调地址，客户输入

    @Column(name = "domain")
    private String domain;//域名，客户简称，英文

    @Column(name = "sign_type")
    private Integer signType;//签名类型： 2-Hmac-Digest

    @Column(name = "sha_key")
    private String shaKey;//Hmac算法key

    @Column(name = "public_filter_id")
    private String publicFilterId;//Hmac算法key

    @Column(name = "keep_alive_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date keepAliveTime;//Hmac算法key

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public Integer getSignType() {
        return signType;
    }

    public void setSignType(Integer signType) {
        this.signType = signType;
    }

    public String getShaKey() {
        return shaKey;
    }

    public void setShaKey(String shaKey) {
        this.shaKey = shaKey;
    }

    public String getPublicFilterId() {
        return publicFilterId;
    }

    public void setPublicFilterId(String publicFilterId) {
        this.publicFilterId = publicFilterId;
    }

    public Date getKeepAliveTime() {
        return keepAliveTime;
    }

    public void setKeepAliveTime(Date keepAliveTime) {
        this.keepAliveTime = keepAliveTime;
    }
}
