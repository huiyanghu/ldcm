package com.lvdun.queryModel;

import java.util.Date;

/**
 * Created by Administrator on 2017/6/17.
 */
public class StatArriveQuery {
    private Integer sum_total;
    private Integer sum_delete;
    private Date time_stamp;

    public Integer getSum_total() {
        return sum_total;
    }

    public void setSum_total(Integer sum_total) {
        this.sum_total = sum_total;
    }

    public Integer getSum_delete() {
        return sum_delete;
    }

    public void setSum_delete(Integer sum_delete) {
        this.sum_delete = sum_delete;
    }

    public Date getTime_stamp() {
        return time_stamp;
    }

    public void setTime_stamp(Date time_stamp) {
        this.time_stamp = time_stamp;
    }
}
