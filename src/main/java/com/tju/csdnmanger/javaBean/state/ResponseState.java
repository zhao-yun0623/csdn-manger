package com.tju.csdnmanger.javaBean.state;

/**
 * ResponseState枚举类：返回请求信息、状态码
 *
 * @author 赵云
 * @date 2020/09/03
 */
public enum ResponseState {
    SUCCESS("操作成功",200),
    TOKEN_NOT_PROVIDE("未传入token",101),
    TOKEN_IS_ERROR("token错误",102),
    TOKEN_IS_EXPIRED("token已过期",103),
    REFRESH_TOKEN_IS_ERROR("token错误",104),
    REFRESH_TOKEN_IS_EXPIRED("token已过期",105),
    USER_NOT_EXIST("用户不存在",106),
    PASSWORD_IS_ERROR("密码错误",107),
    USER_IS_EXIST("用户已存在",108),
    CODE_NOT_EXIST("验证码未获取或已过期",109),
    CODE_IS_ERROR("验证码错误",110),
    PARAM_IS_ERROR("参数错误",111),
    RESUME_NOT_EXIST("该用户未提交简历",112),
    RESUME_IS_EXIST("请误重复提交简历",113),
    RESUME_IS_CHECKED("该简历已被审核",114),
    RESUME_NOT_CHECKED("该简历尚未进行审核",115),
    RESUME_IS_INTERVIEW("请勿重复面试",116),
    RESUME_NOT_PASS_CHECK("该简历未通过审核",117),
    ERROR("操作失败",100);

    private String message;
    private int value;
    ResponseState(String message,int value)
    {
        this.message=message;
        this.value=value;
    }

    public String getMessage() {
        return message;
    }

    public int getValue() {
        return value;
    }
}
