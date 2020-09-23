package com.tju.csdnmanger.javaBean.state;

/**
 * RedisHeader枚举类：设置缓存键的头部
 *
 * @author 赵云
 * @date 2020/09/10
 */
public enum RedisHeader {
    USER_TOKEN("userToken"),
    USER_REFRESH_TOKEN("userRefreshToken"),
    RETRIEVE_PASSWORD_CODE("retrievePasswordCode"),
    REGISTER_CODE("registerCode");

    private String header;

    RedisHeader(String header) {
        this.header = "csdn"+header;
    }

    public String getHeader() {
        return header;
    }
}
