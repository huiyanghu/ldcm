package com.lvdun.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * 数据不通过原因 统计
 */
@Entity
@Table(name = "stat_arrive_delete")
public class StatArriveDelete {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "code_app_id")
    private Long codeAppId;

    @Column(name = "ugc_type")
    private Integer ugcType;//1文本、2图片

    @Column(name = "count_zz")
    private Integer zz;//1

    @Column(name = "count_sq")
    private Integer sq;//2

    @Column(name = "count_wf")
    private Integer wf;//3

    @Column(name = "count_wg")
    private Integer wg;//5

    @Column(name = "count_total")
    private Integer total;


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

    public Integer getZz() {
        return zz;
    }

    public void setZz(Integer zz) {
        this.zz = zz;
    }

    public Integer getSq() {
        return sq;
    }

    public void setSq(Integer sq) {
        this.sq = sq;
    }

    public Integer getWf() {
        return wf;
    }

    public void setWf(Integer wf) {
        this.wf = wf;
    }

    public Integer getWg() {
        return wg;
    }

    public void setWg(Integer wg) {
        this.wg = wg;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
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
