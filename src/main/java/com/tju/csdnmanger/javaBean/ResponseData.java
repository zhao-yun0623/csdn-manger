package com.tju.csdnmanger.javaBean;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * ResponseData类：返回信息
 *
 * @author 赵云
 * @date 2020/09/10
 */

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseData {
    private String message;
    private int state;
    private String token;
    private String refreshToken;
    private Map<String,Object> data=new HashMap<>();

    public ResponseData(String message, int state, String token, String refreshToken,String key,Object value) {
        this.message = message;
        this.state = state;
        this.token = token;
        this.refreshToken=refreshToken;
        this.data.put(key,value);
    }

    public ResponseData() {
    }

    public ResponseData(String message, int state) {
        this.message = message;
        this.state = state;
    }

    public ResponseData(String token,String refreshToken, String key,Object value) {
        this.token = token;
        this.refreshToken=refreshToken;
        this.data.put(key,value);
    }

    public ResponseData(String message, int state, String key,Object value) {
        this.message = message;
        this.state = state;
        this.data.put(key,value);
    }

    public void setMessageState(String message,int state){
        this.message=message;
        this.state=state;
    }

    public void setData(String key,Object value){
        this.data.put(key,value);
    }

    public Object getData(String key){
        return this.data.get(key);
    }

    public void setTokens(String token,String refreshToken){
        this.token=token;
        this.refreshToken=refreshToken;
    }
}
