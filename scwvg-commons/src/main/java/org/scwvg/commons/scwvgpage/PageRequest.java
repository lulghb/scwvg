package org.scwvg.commons.scwvgpage;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 *
 * Description: 分页需要的数据包装类
 * auther Administrator on 2019/1/18
 */
@Data
@AllArgsConstructor
public class PageRequest {
    /**
     * @param currentPage   分页状态信息，当前页号。默认当前页是1
     * @param size          分页状态信息，当前页记录数 。默认是10
     * @param Sort          分页查询条件，定义排序条件
     */

    private int currentPage=1;
    private int size=10;
    /**
     * 获取查询的开始下标
     * @return
     */
    public int getBegin() {
        //从“0”开始显示3条数据
        //相当于     select name , type from customer limit 0,3;   中的“0”是开始数据查询的位置  currentPage=1
        //从“3”开始显示3条数据
        //以此类推  select name , type from customer limit 3,3;   中的“3”是开始数据查询的位置  currentPage=2
        //从“6”开始显示3条数据
        //以此类推  select name , type from customer limit 6,3;   中的“6”是开始数据查询的位置  currentPage=3
        return (this.currentPage-1)*this.size;
    }
}
