package com.lvdun.util;

/**
 * @Description:
 * @date 2015年12月17日 下午6:58:06
 */
public class PageUtil {
    private int pageSize = 20;//每页显示的条数
    private int recordCount;//总共的条数
    private int currentPage;//当前页面
    @SuppressWarnings("unused")
    private int pagecount;

    /*public PageUtil(int pageSize, int recordCount, int currentPage){
            this.pageSize = pageSize;
            this.recordCount = recordCount;
            this.setCurrentPage(currentPage);
        }*/
    //构造方法
    public PageUtil() {
    }

    public void setPagecount(int pagecount) {
        this.pagecount = pagecount;
    }

    //总页数
    public int getPagecount() {
        int size = recordCount / pageSize;//总条数/每页显示的条数=总页数
        int mod = recordCount % pageSize;//最后一页的条数
        if (mod != 0)
            size++;
        return recordCount == 0 ? 1 : size;
    }

    //得到当前页
    public int getCurrentPage() {
        return currentPage;
    }//设置当前页

    public void setCurrentPage(int currentPage) {
        int validPage = currentPage <= 0 ? 1 : currentPage;
        validPage = validPage > getPagecount() ? getPagecount() : validPage;
        this.currentPage = validPage;
    }//得到每页显示的条数

    public int getPageSize() {
        return pageSize;
    }//设置每页显示的条数

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }//得到总共的条数

    public int getRecordCount() {
        return recordCount;
    }//设置总共的条数

    public void setRecordCount(int recordCount) {
        this.recordCount = recordCount;
    }

    @Override
    public String toString() {
        return "PageUtil{" +
            "pageSize=" + pageSize +
            ", recordCount=" + recordCount +
            ", currentPage=" + currentPage +
            ", pagecount=" + pagecount +
            '}';
    }
}
