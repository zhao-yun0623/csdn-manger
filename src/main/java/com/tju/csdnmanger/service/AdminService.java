package com.tju.csdnmanger.service;

import com.tju.csdnmanger.javaBean.UserBean;
import com.tju.csdnmanger.javaBean.state.RedisHeader;
import com.tju.csdnmanger.javaBean.ResponseData;
import com.tju.csdnmanger.javaBean.state.ResponseState;
import com.tju.csdnmanger.javaBean.AdminBean;
import com.tju.csdnmanger.mapper.AdminMapper;
import io.jsonwebtoken.ExpiredJwtException;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * AdminService类
 *
 * @author 赵云
 * @date 2020/09/10
 */

@Service
public class AdminService {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private Logger logger;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 用户登录逻辑
     * @param phone：用户手机号
     * @param password：用户提供的密码
     * @return user信息、token
     */
    public ResponseData login(String phone, String password) {
        logger.info("手机号为"+phone+"的用户正在尝试登录");
        AdminBean adminBean =adminMapper.getUserByPhone(phone);
        if(adminBean ==null){
            logger.warn("手机号为"+phone+"的用户不存在");
            return new ResponseData(ResponseState.USER_NOT_EXIST.getMessage(), ResponseState.USER_NOT_EXIST.getValue());
        }
        if(!passwordEncoder.matches(password, adminBean.getPassword())){
            logger.warn("手机号为"+phone+"的用户登录密码错误");
            return new ResponseData(ResponseState.PASSWORD_IS_ERROR.getMessage(),ResponseState.PASSWORD_IS_ERROR.getValue());
        }
        try {
            AdminBean admin=new AdminBean(adminBean.getId(), adminBean.getPhone());
            String token=jwtService.getToken(admin);
//            stringRedisTemplate.opsForValue().set(RedisHeader.USER_TOKEN.getHeader() +phone,token);
//            stringRedisTemplate.expire("token"+phone,1,TimeUnit.HOURS);
            String refreshToken=jwtService.getRefreshToken(admin);
//            stringRedisTemplate.opsForValue().set(RedisHeader.USER_REFRESH_TOKEN.getHeader()+phone,refreshToken);
//            stringRedisTemplate.expire("refreshToken"+phone,10,TimeUnit.DAYS);
            logger.info("手机号为"+phone+"的用户登录成功");
            return new ResponseData(ResponseState.SUCCESS.getMessage(),ResponseState.SUCCESS.getValue(),token,refreshToken,"admin", adminBean);
        } catch (Exception e) {
            logger.error("手机号为"+phone+"的用户登录时发生错误");
            return new ResponseData(ResponseState.ERROR.getMessage(),ResponseState.ERROR.getValue());
        }
    }

    /**
     * 用户注册
     * @param adminBean：注册的用户信息
     * @return responseData
     */
    public ResponseData register(AdminBean adminBean, String code) {
        logger.info("手机号为"+ adminBean.getPhone()+"的用户正在尝试注册");
        try {
            String rightCode=stringRedisTemplate.opsForValue().get(RedisHeader.REGISTER_CODE.getHeader()+ adminBean.getPhone());
            if(rightCode==null){
                logger.warn("手机号为"+ adminBean.getPhone()+"的用户注册时未获取验证码或验证码已过期");
                return new ResponseData(ResponseState.CODE_NOT_EXIST.getMessage(),ResponseState.CODE_NOT_EXIST.getValue());//107
            }
            if(code==null||!code.equals(rightCode)){
                logger.warn("手机号为"+ adminBean.getPhone()+"的用户注册时验证码输入错误");
                return new ResponseData("验证码错误",ResponseState.CODE_IS_ERROR.getValue());
            }
            AdminBean admin=adminMapper.getUserByPhone(adminBean.getPhone());
            if(admin!=null){
                logger.warn("手机号为"+ adminBean.getPhone()+"的用户已经被注册");
                return new ResponseData("该用户已存在",ResponseState.USER_IS_EXIST.getValue());//106
            }
            adminBean.setPassword(passwordEncoder.encode(adminBean.getPassword()));
            adminMapper.insertUser(adminBean);
            stringRedisTemplate.delete("code"+ adminBean.getPhone());
        } catch (Exception e) {
            logger.error("手机号为"+ adminBean.getPhone()+"的用户注册时发生错误");
            logger.error(e.getMessage());
            return new ResponseData("系统错误",ResponseState.ERROR.getValue());
        }
        logger.info("手机号为"+ adminBean.getPhone()+"的用户注册成功");
        return new ResponseData("注册成功",ResponseState.SUCCESS.getValue());
    }

    /**
     * 获取验证码
     * @param phone：手机号
     * @return responseData
     */
    public ResponseData getCode(String phone) {
        logger.info("手机号为"+phone+"的用户正在尝试获取验证码");
        try {
            stringRedisTemplate.opsForValue().set(RedisHeader.REGISTER_CODE.getHeader()+phone,"111111");
            stringRedisTemplate.expire(RedisHeader.REGISTER_CODE.getHeader()+phone,5, TimeUnit.MINUTES);
            return new ResponseData(ResponseState.SUCCESS.getMessage(),ResponseState.SUCCESS.getValue());
        } catch (Exception e) {
            logger.error("手机号为"+phone+"的用户获取验证码失败");
            logger.error(e.getMessage());
            return new ResponseData(ResponseState.ERROR.getMessage(),ResponseState.ERROR.getValue());
        }
    }

    /**
     * 退出登录
     * @param admin：当前登录的用户信息
     * @return responseData
     */
    public ResponseData logout(AdminBean admin) {
        logger.info("手机号为"+admin.getPhone()+"的用户正在尝试退出登录");
        try {
            stringRedisTemplate.delete(RedisHeader.USER_TOKEN.getHeader()+admin.getPhone());
            stringRedisTemplate.delete(RedisHeader.REGISTER_CODE.getHeader()+admin.getPhone());
            logger.info("手机号为"+admin.getPhone()+"的用户退出登录成功");
            return new ResponseData(ResponseState.SUCCESS.getMessage(),ResponseState.SUCCESS.getValue());
        } catch (Exception e) {
            logger.error("手机号为"+admin.getPhone()+"的用户退出登录时发生错误");
            logger.error(e.getMessage());
            return new ResponseData(ResponseState.ERROR.getMessage(),ResponseState.ERROR.getValue());
        }
    }

    /**
     * 获取用户信息
     * @param phone：手机号，可为空，为空查询自动的信息
     * @param admin：当前登录的用户信息
     * @return responseData(user)
     */
    public ResponseData getUser(String phone, AdminBean admin) {
        if(phone==null){
            logger.info("手机号为"+admin.getPhone()+"的用户正在查询个人信息");
            phone=admin.getPhone();
        }else {
            logger.info("手机号为"+admin.getPhone()+"的用户正在查询手机号为"+phone+"的用户的信息");
        }
        try {
            AdminBean adminBean =adminMapper.getUserByPhone(phone);
            if(adminBean ==null){
                logger.info("手机号为"+phone+"的用户不存在");
                return new ResponseData(ResponseState.USER_NOT_EXIST.getMessage(),ResponseState.USER_NOT_EXIST.getValue());//104
            }
            logger.info("手机号为"+admin.getPhone()+"的用户查询用户信息成功");
            return new ResponseData(ResponseState.SUCCESS.getMessage(),ResponseState.SUCCESS.getValue(),"admin", adminBean);
        } catch (Exception e) {
            logger.error("手机号为"+admin.getPhone()+"的用户查询用户信息时发生错误");
            logger.error(e.getMessage());
            return new ResponseData(ResponseState.ERROR.getMessage(),ResponseState.ERROR.getValue());
        }
    }

    /**
     * 更新token
     * @param refreshToken：refreshToken
     * @return responseData:token,refreshToken
     */
    public ResponseData refreshToken(String refreshToken) {
        logger.info("正在进行更新token的操作");
        try {
            AdminBean adminBean = null;
            try {
                adminBean = jwtService.getUser(refreshToken);
            } catch (ExpiredJwtException e) {
                logger.warn("refreshToken已过期");
                logger.warn(e.getMessage());
                return new ResponseData(ResponseState.REFRESH_TOKEN_IS_EXPIRED.getMessage(),ResponseState.REFRESH_TOKEN_IS_EXPIRED.getValue());
            } catch (Exception e) {
                logger.warn("refreshToken解析时发生错误");
                logger.warn(e.getMessage());
                return new ResponseData(ResponseState.REFRESH_TOKEN_IS_ERROR.getMessage(),ResponseState.REFRESH_TOKEN_IS_ERROR.getValue());
            }
//            String rightRefreshToken=stringRedisTemplate.opsForValue().get(RedisHeader.USER_REFRESH_TOKEN.getHeader()+ adminBean.getPhone());
//            if(!refreshToken.equals(rightRefreshToken)){
//                logger.warn("refreshToken已过期");
//                return new ResponseData(ResponseState.REFRESH_TOKEN_IS_EXPIRED.getMessage(),ResponseState.REFRESH_TOKEN_IS_EXPIRED.getValue());
//            }
            String newToken=jwtService.getToken(adminBean);
//            stringRedisTemplate.opsForValue().set(RedisHeader.USER_TOKEN.getHeader()+ adminBean.getPhone(),newToken);
            String newRefreshToken=jwtService.getRefreshToken(adminBean);
//            stringRedisTemplate.opsForValue().set(RedisHeader.USER_REFRESH_TOKEN.getHeader()+ adminBean.getPhone(),newRefreshToken);
            ResponseData responseData=new ResponseData(ResponseState.SUCCESS.getMessage(),ResponseState.SUCCESS.getValue());
            responseData.setTokens(newToken,newRefreshToken);
            return responseData;
            } catch (Exception e){
            logger.error("发生错误");
            return new ResponseData(ResponseState.ERROR.getMessage(),ResponseState.ERROR.getValue());
        }
    }

    public ResponseData forgetPassword(AdminBean adminBean, String code) {
        logger.info("手机号为"+ adminBean.getPhone()+"的用户正在尝试找回密码");
        try {
            String rightCode=stringRedisTemplate.opsForValue().get(RedisHeader.RETRIEVE_PASSWORD_CODE.getHeader()+ adminBean.getPhone());
            if(rightCode==null){
                logger.warn("手机号为"+ adminBean.getPhone()+"的用户找回密码时未获取验证码或验证码已过期");
                return new ResponseData(ResponseState.CODE_NOT_EXIST.getMessage(),ResponseState.CODE_NOT_EXIST.getValue());//107
            }
            if(code==null||!code.equals(rightCode)){
                logger.warn("手机号为"+ adminBean.getPhone()+"的用户找回密码时验证码输入错误");
                return new ResponseData(ResponseState.CODE_IS_ERROR.getMessage(),ResponseState.CODE_IS_ERROR.getValue());
            }
            AdminBean admin=adminMapper.getUserByPhone(adminBean.getPhone());
            if(admin==null){
                logger.warn("手机号为"+ adminBean.getPhone()+"的用户不存在");
                return new ResponseData(ResponseState.USER_NOT_EXIST.getMessage(),ResponseState.USER_NOT_EXIST.getValue());
            }
            adminBean.setPassword(passwordEncoder.encode(adminBean.getPassword()));
            adminMapper.changePassword(adminBean);
            stringRedisTemplate.delete(RedisHeader.RETRIEVE_PASSWORD_CODE.getHeader()+ adminBean.getPhone());
        } catch (Exception e) {
            logger.error("手机号为"+ adminBean.getPhone()+"的用户找回密码时发生错误");
            logger.error(e.getMessage());
            return new ResponseData(ResponseState.ERROR.getMessage(),ResponseState.ERROR.getValue());
        }
        logger.info("手机号为"+ adminBean.getPhone()+"的用户找回密码成功");
        return new ResponseData(ResponseState.SUCCESS.getMessage(),ResponseState.SUCCESS.getValue());
    }

    public ResponseData getForgetCode(String phone) {
        logger.info("手机号为"+phone+"的用户正在尝试获取验证码");
        try {
            stringRedisTemplate.opsForValue().set(RedisHeader.RETRIEVE_PASSWORD_CODE.getHeader()+phone,"111111");
            stringRedisTemplate.expire(RedisHeader.RETRIEVE_PASSWORD_CODE.getHeader()+phone,5, TimeUnit.MINUTES);
            return new ResponseData(ResponseState.SUCCESS.getMessage(),ResponseState.SUCCESS.getValue());
        } catch (Exception e) {
            logger.error("手机号为"+phone+"的用户获取验证码失败");
            logger.error(e.getMessage());
            return new ResponseData(ResponseState.ERROR.getMessage(),ResponseState.ERROR.getValue());
        }
    }

    public ResponseData changePassword(String oldPassword, String newPassword, AdminBean admin) {
        logger.info("手机号为"+admin.getPhone()+"的管理员正在修改密码");
        try {
            AdminBean adminBean=adminMapper.getUserByPhone(admin.getPhone());
            if(adminBean==null){
                logger.warn("手机号为"+admin.getPhone()+"的管理员不存在");
                return new ResponseData(ResponseState.USER_NOT_EXIST.getMessage(), ResponseState.USER_NOT_EXIST.getValue());
            }
            if(!passwordEncoder.matches(oldPassword,adminBean.getPassword())){
                logger.warn("手机号为"+admin.getPhone()+"的管理员原密码错误");
                return new ResponseData(ResponseState.PASSWORD_IS_ERROR.getMessage(),ResponseState.PASSWORD_IS_ERROR.getValue());
            }
            adminMapper.changePassword(new AdminBean(admin.getPhone(),passwordEncoder.encode(newPassword)));
            logger.info("修改成功");
            return new ResponseData(ResponseState.SUCCESS.getMessage(),ResponseState.SUCCESS.getValue());
        } catch (Exception e) {
            logger.error("修改失败");
            return new ResponseData(ResponseState.ERROR.getMessage(),ResponseState.ERROR.getValue());
        }
    }
}
