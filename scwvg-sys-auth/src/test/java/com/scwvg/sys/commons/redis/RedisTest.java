package com.scwvg.sys.commons.redis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;


/**
 * @Description: Redis 测试链接
 * @author: chen.baihoo
 * @date: 2019/1/20 14:29
 * version 0.1
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class RedisTest {

    @Resource
    Redis redis;

    @Test
    public void testJdkSerializer(){

        redis.lpush("list1",1,2,3,4,5);
        List<Integer> list = redis.lrange("list1",0,-1);
        list.forEach(e-> System.out.println(e));
    }
    @Test
    public void testFstSerializer(){
        //FstSerializer 序列化的参数
        redis.lpush("list2","a","b","c","d");
        List<Object> list = redis.lrange("list2",0,-1);
        list.forEach(e-> System.out.println(e));
    }
}
