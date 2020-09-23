package com.tju.csdnmanger.config;


import com.tju.csdnmanger.javaBean.ResponseData;
import com.tju.csdnmanger.javaBean.AdminBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import java.net.UnknownHostException;

/**
 * RedisConfig配置类
 *
 * @author 赵云
 * @date 2020/09/06
 */
@Configuration
public class RedisConfig {
    @Bean
    public RedisTemplate<Object, AdminBean> redisTemplate(RedisConnectionFactory redisConnectionFactory) throws UnknownHostException {
        RedisTemplate<Object, AdminBean> template = new RedisTemplate<Object, AdminBean>();
        template.setConnectionFactory(redisConnectionFactory);
        Jackson2JsonRedisSerializer<AdminBean> serializer=new Jackson2JsonRedisSerializer<AdminBean>(AdminBean.class);
        template.setDefaultSerializer(serializer);
        return template;
    }
    @Bean
    public RedisTemplate<Object, ResponseData> redisTemplate2(RedisConnectionFactory redisConnectionFactory) throws UnknownHostException {
        RedisTemplate<Object,ResponseData> template = new RedisTemplate<Object, ResponseData>();
        template.setConnectionFactory(redisConnectionFactory);
        Jackson2JsonRedisSerializer<ResponseData> serializer=new Jackson2JsonRedisSerializer<ResponseData>(ResponseData.class);
        template.setDefaultSerializer(serializer);
        return template;
    }
}
