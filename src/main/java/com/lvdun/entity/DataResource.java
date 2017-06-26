package com.lvdun.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Administrator on 2017/6/16.
 */
@Entity
@Table(name = "data_resource")
public class DataResource {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "data_record_id")
    private Long dataRecordId;//数据Id

    @Column(name = "data_type")
    private Integer type;//类型[1-text;2-image;3-video]

    @Column(name = "data_order")
    private Integer order;//数据排序

    @Column(name = "data_content")
    private String content;//内容，包含文本和图片的url

    @Column(name = "create_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;//入库时间

    @Column(name = "filter_desc")
    private String filterDesc;//敏感词


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDataRecordId() {
        return dataRecordId;
    }

    public void setDataRecordId(Long dataRecordId) {
        this.dataRecordId = dataRecordId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getFilterDesc() {
        return filterDesc;
    }

    public void setFilterDesc(String filterDesc) {
        this.filterDesc = filterDesc;
    }
}
