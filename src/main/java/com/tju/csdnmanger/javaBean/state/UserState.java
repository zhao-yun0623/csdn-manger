package com.tju.csdnmanger.javaBean.state;

public enum UserState {
    SIGN_UP(0),//已报名但未审核
    PASS_CHECK(1),//已通过审核
    NOT_PASS_CHECK(-1),//未通过审核
    PASS_INTERVIEW(2),//通过面试
    NOT_PASS_INTERVIEW(-2);//未通过面试

    private int value;

    UserState(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
