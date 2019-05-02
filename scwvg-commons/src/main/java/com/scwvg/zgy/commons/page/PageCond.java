package com.scwvg.zgy.commons.page;


import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @ClassName: Page
 * @Description:
 * 		分页实体类
 * @author: Administrator
 * @date: 2019年1月16日 下午5:19:39
 * @param <T>
 */
@Getter
public class PageCond<T> {

    /**
     * @param list					分页查询条件，数据库检索的数据集
     * @param totalPage			    分页状态信息，总页数
     * @param length		        分页查询条件，返回记录数长度
     * @param currentPage		    分页状态信息，当前页号。默认当前页是1
     * @param size			        分页状态信息，当前页记录数 。默认是10
     * @param begin		            分页查询条件，数据库检索的起始记录号
     * @param pagingBar		        分页状态信息，返回的分页码栏
     * @param defaultBar		    分页状态信息，默认的分页码栏显示 [1-10] 区间
     * @param
     */

    private List<T> list = new ArrayList<T>();  //分页查询条件，数据库检索的数据集

    private Integer totalPage;          //分页状态信息，总页数

    private Integer length;             //分页查询条件，返回记录数长度

    private Integer currentPage=1;      //分页状态信息，当前页号。默认当前页是1

    private Integer size=10;            //分页状态信息，当前页记录数 。默认是10

    private Integer begin;              //分页查询条件，数据库检索的起始记录号

    private Integer[] pagingBar;        //分页状态信息，返回的分页码栏

    private  Integer defaultBar =10;    //分页状态信息，默认的分页码栏显示 [1-10] 区间

    /**
     * 无参构造函数
     */
    public PageCond() {
        super();
    }
    /**
     * 注意執行順序
     * @param list
     * @param length
     * @param currentPage
     * @param size
     * @param begin
     */
    public PageCond (List list , Integer length , Integer currentPage , Integer size , Integer begin) {
        this.list = list;
        this.length = length;
        this.currentPage = currentPage;
        this.size = size;
        this.begin = begin;
        this.calculateTotalPage();//先计算总共多少页
        this.calculatePagingBar();//再计算分页码栏
    }
    /**
     * 获取计算的总页数
     * @return
     */
    public Integer calculateTotalPage() {
        if(length%size==0){
            this.totalPage=this.length/this.size;
        }else{
            this.totalPage=this.length/this.size+1;
        }
        return this.totalPage;
    }

    /**
     * 默认的页面分页的显示状态码
     * @return
     */
    protected Integer[] calculatePagingBar() {
        Integer startPage;
        Integer endPage;
        Integer x;
        //默认是奇数爲真
        boolean isOdd=true;
        //如果是偶数
        if(defaultBar%2==0) {
            x = defaultBar>>1;
            isOdd=false;
        }//否则是奇数
        else {
            x = defaultBar>>1;
        }
        if(totalPage<defaultBar){
            startPage=1;
            endPage=this.totalPage;
        }else{
            //如果非奇数
            if(!isOdd){
                startPage=this.currentPage-(x-1);
            }//否则是奇数
            else {
                startPage=this.currentPage-x;
            }
            endPage = this.currentPage+x;
            if(startPage<1){
                startPage=1;
                endPage=defaultBar;
            }
            if(endPage>this.totalPage){
                endPage=this.totalPage;
                startPage=this.totalPage-defaultBar-1;
            }
        }
        //防止出现空指针异常，1-1+1
        pagingBar = new Integer[endPage-startPage+1];
        Integer index=0;
        for(;startPage<=endPage;startPage++){
            pagingBar[index++]=startPage;
        }
        return pagingBar;
    }

    /**
     * 重写toString
     * @return
     */
    @Override
    public String toString() {
        return "Page [totalPage=" + totalPage + ", length=" + length + ", currentPage=" + currentPage
                + ", size=" + size + ", begin=" + begin + ", pagingBar="
                + Arrays.toString(pagingBar) + ", defaultBar=" + defaultBar + "]";
    }
}
