package com.tju.csdnmanger.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * LoggerConfig类
 *
 * @author 赵云
 * @date 2020/09/03
 */

@Configuration
public class LoggerConfig {
    @Bean
    public Logger logger(){
        return LoggerFactory.getLogger(this.getClass());
    }
}
