package com.tju.csdnmanger.controller;

import com.tju.csdnmanger.javaBean.ResponseData;
import com.tju.csdnmanger.javaBean.state.ResponseState;
import com.tju.csdnmanger.javaBean.AdminBean;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * CtrlAdvice类：全局控制器
 *
 * @author 赵云
 * @date 2020/09/10
 */
@ControllerAdvice
@ResponseBody
public class CtrlAdvice {

    /**
     * 处理请求参数格式错误的返回信息
     * @param e：BindException异常
     * @return responseData
     */
    @ExceptionHandler
    public ResponseData BindExceptionHandler(BindException e){
        BindingResult bindingResult=e.getBindingResult();
        String message=bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining());
        return new ResponseData(message, ResponseState.PARAM_IS_ERROR.getValue());
    }

    /**
     * 获取Admin对象
     * @param data：储存admin对象
     * @param request：HttpServletRequest
     */
    @ModelAttribute
    public void getModel(Map<String,Object> data, HttpServletRequest request){
        AdminBean admin= (AdminBean) request.getAttribute("admin");
        data.put("admin",admin);
    }
}
