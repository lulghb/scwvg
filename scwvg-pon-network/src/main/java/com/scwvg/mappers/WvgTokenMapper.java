package com.scwvg.mappers;

import com.scwvg.entitys.scwvgponnetwork.WvgToken;
import org.apache.ibatis.annotations.*;

/**
 * @aothor: lul
 * @unit: 智谷园网络科技有限公司
 * @iphone:18482297774
 * @date：20192019/5/2
 * @Explain：TokenMapper
 **/
@Mapper
public interface WvgTokenMapper {
    @Insert("insert into wvg_token(wvg_token_id," +
            "wvg_user_id," +
            "wvg_token_val," +
            "wvg_token_expireTime," +
            "wvg_token_addTime, wvg_token_updateTime) " +
            "values (#{id},#{wvg_user_id}, #{wvg_token_val}, " +
            "#{wvg_token_expireTime}, #{createTime}," +
            "#{updateTime})")
    int save(WvgToken wvgToken);

    @Select("select * from wvg_token t where t.wvg_token_id = #{wvg_token_id}")
    WvgToken getByTokenId(String wvg_token_id);

    @Update("update wvg_token t set t.wvg_token_expireTime = #{wvg_token_expireTime}, " +
            "wvg_token_updateTime=#{updateTime}, " +
            "wvg_token_val=#{wvg_token_val} " +
            " where t.wvg_token_id = #{id}")
    int update(WvgToken model);

    @Delete("delete from t_token where t_token = #{t_token}")
    int delete(String id);
}
