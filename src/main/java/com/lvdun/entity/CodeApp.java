package com.lvdun.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Administrator on 2017/6/15.
 */
@Entity
@Table(name = "code_app")
public class CodeApp {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    //@ManyToOne(cascade = {CascadeType.REFRESH}, fetch = FetchType.LAZY)
    //@JoinColumn(name = "platform_id")
    @Column(name = "platform_id")
    private Long platformId;

    @Column(name = "app_id")
    private String appId;//app代码

    @Column(name = "app_name")
    private String appName;//App名称


    @Column(name = "type")
    private Integer type;//类型：1-文字；2-图片

    @Column(name = "feedback_url")
    private String feedbackUrl;//回调地址

    @Column(name = "description")
    private String description;//描述信息

    @Column(name = "filter_group_id")
    private Integer filterGroupId;//敏感词分组(code表):FilterGroupId

    @Column(name = "create_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date craateTime;//最后更新时间

    @Column(name = "policy_template_id")
    private Integer policyTemplateId;//策略模板

    @Column(name = "feedback_type")
    private Integer feedbackType;//反馈方式：1-删除恢复反馈，其余不反馈；2-全量反馈

    @Column(name = "authenticated")
    private Integer authenticated;//是否需要鉴权：0-不需要，1-需要；默认为1

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

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getFeedbackUrl() {
        return feedbackUrl;
    }

    public void setFeedbackUrl(String feedbackUrl) {
        this.feedbackUrl = feedbackUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getFilterGroupId() {
        return filterGroupId;
    }

    public void setFilterGroupId(Integer filterGroupId) {
        this.filterGroupId = filterGroupId;
    }

    public Date getCraateTime() {
        return craateTime;
    }

    public void setCraateTime(Date craateTime) {
        this.craateTime = craateTime;
    }

    public Integer getPolicyTemplateId() {
        return policyTemplateId;
    }

    public void setPolicyTemplateId(Integer policyTemplateId) {
        this.policyTemplateId = policyTemplateId;
    }

    public Integer getFeedbackType() {
        return feedbackType;
    }

    public void setFeedbackType(Integer feedbackType) {
        this.feedbackType = feedbackType;
    }

    public Integer getAuthenticated() {
        return authenticated;
    }

    public void setAuthenticated(Integer authenticated) {
        this.authenticated = authenticated;
    }
}
