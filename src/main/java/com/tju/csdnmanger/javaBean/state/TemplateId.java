package com.tju.csdnmanger.javaBean.state;

public enum TemplateId {
    PASS(724044),//申请通过
    SIGN_UP_SUCCESS(724043),//报名成功
    REGISTER(725883),//注册验证码
    FORGET_PASSWORD(725890);//找回密码验证码

    private int value;

    TemplateId(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
