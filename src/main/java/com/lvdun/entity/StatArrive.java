package com.lvdun.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * 数据审核结果 统计
 */
@Entity
@Table(name = "stat_arrive")
public class StatArrive {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "code_app_id")
    private Long codeAppId;

    @Column(name = "ugc_type")
    private Integer ugcType;//1文本、2图片

    @Column(name = "count_total")
    private Integer total;//到达总数
    //数据最终状态，包括系统和人工审核的状态：0-删除；1-通过；2-人工审核
    @Column(name = "count_pass")
    private Integer pass;//自动化审核通过量1

    @Column(name = "count_delete")
    private Integer delete;//自动化审核不通过量0

    @Column(name = "count_review")
    private Integer review;//自动化审核不确认量2
    /*
    @Column(name = "count_delete_reason_zz")
    private Integer zz;//不通过原因 政治

    @Column(name = "count_delete_reason_sq")
    private Integer sq;//不通过原因 政治

    @Column(name = "count_delete_reason_wf")
    private Integer wf;//不通过原因 政治

    @Column(name = "count_delete_reason_wg")
    private Integer wg;//不通过原因 政治
    */

    @Column(name = "time_stamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeStamp;//整点时间戳

    @Column(name = "create_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCodeAppId() {
        return codeAppId;
    }

    public void setCodeAppId(Long codeAppId) {
        this.codeAppId = codeAppId;
    }

    public Integer getUgcType() {
        return ugcType;
    }

    public void setUgcType(Integer ugcType) {
        this.ugcType = ugcType;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getPass() {
        return pass;
    }

    public void setPass(Integer pass) {
        this.pass = pass;
    }

    public Integer getDelete() {
        return delete;
    }

    public void setDelete(Integer delete) {
        this.delete = delete;
    }

    public Integer getReview() {
        return review;
    }

    public void setReview(Integer review) {
        this.review = review;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
