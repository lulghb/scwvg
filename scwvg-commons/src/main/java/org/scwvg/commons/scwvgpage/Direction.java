package org.scwvg.commons.scwvgpage;

/**
 * 排序枚举
 * Description: estore
 * auther Administrator on 2019/1/18
 */
public enum Direction {
    ASC(true,"ASC" , "升序"),
    DESC(false,"DESC" , "降序");
    /**
     * @param ascending true升序，false降序
     * @param code      asc , desc
     * @param explain   说明
     */
    private boolean ascending;
    private String code;
    private String explain;
    Direction(boolean ascending , String code , String explain){
        this.ascending = ascending;
        this.code = code;
        this.explain = explain;
    }

    /**
     * 返回布尔结果，是升序；否则降序
     * @return
     */
    public boolean isAscending(){
        return this.ascending;
    }

    /**
     * 返回排序编码
     * @return
     */
    public String getCode(){
        return this.code;
    }
}
