package com.lvdun.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Administrator on 2017/6/15.
 */
@Entity
@Table(name = "data_record")
public class DataRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_publish_data_id")
    private String userPublishDataId;//用户发布数据Id，客户传入唯一标记

    //@ManyToOne(cascade = {CascadeType.REFRESH}, fetch = FetchType.LAZY)
    //@JoinColumn(name = "code_app_id")
    @Column(name = "user_id")
    private String userId;//用户发布UserId

    @Column(name = "user_ip")
    private String userIp;//用户发布IP

    @Column(name = "device_id")
    private String deviceId;//用户设备ID

    @Column(name = "publish_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date publishDate;//用户发布时间

    @Column(name = "code_app_id")
    private Long codeAppId;//通过app_id到code_app表关联平台信息

    @Column(name = "digest")
    private String digest;//内容摘要

    @Column(name = "update_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDate;//状态更新时间

    @Column(name = "create_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;//入库时间-整型

    @Column(name = "api_version")
    private String apiVersion;//api版本号

    @Column(name = "sys_status")
    private Integer sysStatus;//系统标引的状态: 0-删除；1-通过；2-review

    @Column(name = "status")
    private Integer status;//数据最终状态，包括系统和人工审核的状态：0-删除；1-通过；2-人工审核

    @Column(name = "sys_policy")
    private String sysPolicy;//系统命中策略


    @Column(name = "operator_id")
    private Long operatorId;//操作用户id，默认值-1（即系统自动化），大于-1则是人工审核的

    @Column(name = "image_count")
    private Integer imageCount;//图片数量

    @Column(name = "word_count")
    private Integer wordCount;//文本长度

    @Column(name = "data_type")
    private Integer dataType;//1文本；2图片

    @Column(name = "filter_type_id")
    private Long filterTypeId;//关键词类别

    @Column(name = "reason_code")
    private Integer reasonCode;//数据不通过原因0	正常，1	政治，2色情，3	违法，4	违规，6	用户行为，7疑似广告，9个性化定制

    @Column(name = "has_count")
    private Integer hasCount;//被统计过

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserPublishDataId() {
        return userPublishDataId;
    }

    public void setUserPublishDataId(String userPublishDataId) {
        this.userPublishDataId = userPublishDataId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserIp() {
        return userIp;
    }

    public void setUserIp(String userIp) {
        this.userIp = userIp;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public Long getCodeAppId() {
        return codeAppId;
    }

    public void setCodeAppId(Long codeAppId) {
        this.codeAppId = codeAppId;
    }

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getApiVersion() {
        return apiVersion;
    }

    public void setApiVersion(String apiVersion) {
        this.apiVersion = apiVersion;
    }

    public Integer getSysStatus() {
        return sysStatus;
    }

    public void setSysStatus(Integer sysStatus) {
        this.sysStatus = sysStatus;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getSysPolicy() {
        return sysPolicy;
    }

    public void setSysPolicy(String sysPolicy) {
        this.sysPolicy = sysPolicy;
    }

    public Long getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Long operatorId) {
        this.operatorId = operatorId;
    }

    public Integer getImageCount() {
        return imageCount;
    }

    public void setImageCount(Integer imageCount) {
        this.imageCount = imageCount;
    }

    public Integer getWordCount() {
        return wordCount;
    }

    public void setWordCount(Integer wordCount) {
        this.wordCount = wordCount;
    }

    public Integer getDataType() {
        return dataType;
    }

    public void setDataType(Integer dataType) {
        this.dataType = dataType;
    }

    public Long getFilterTypeId() {
        return filterTypeId;
    }

    public void setFilterTypeId(Long filterTypeId) {
        this.filterTypeId = filterTypeId;
    }

    public Integer getReasonCode() {
        return reasonCode;
    }

    public void setReasonCode(Integer reasonCode) {
        this.reasonCode = reasonCode;
    }

    public Integer getHasCount() {
        return hasCount;
    }

    public void setHasCount(Integer hasCount) {
        this.hasCount = hasCount;
    }
}
