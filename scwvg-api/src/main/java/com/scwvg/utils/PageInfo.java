package com.scwvg.utils;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * @author: tangyl
 * @unit: 南京星邺汇捷网络科技有限公司
 * @tel: 18080921750
 * @date: 2019/05/04 17:20
 * @desc: 分页
**/
@Getter
@Setter
public class PageInfo<T> {
	
	// 当前页
    private Integer currentPage = 1;
    // 每页显示的总条数
    private Integer pageSize = 10;
    // 总条数
    private Long totalNum;
    // 是否有下一页
    private Integer isMore;
    // 总页数
    private Integer totalPage;
    // 开始索引
    private Integer startIndex;
    // 分页结果
    private List<T> items;

    public PageInfo() {
        super();
    }

    public PageInfo(Integer currentPage, Integer pageSize, Long totalNum) {
        super();
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.totalNum = totalNum;
        this.totalPage = (int) ((this.totalNum + this.pageSize - 1) / this.pageSize);
        this.startIndex = (this.currentPage - 1) * this.pageSize;
        this.isMore = this.currentPage >= this.totalPage ? 0 : 1;
    }

}
