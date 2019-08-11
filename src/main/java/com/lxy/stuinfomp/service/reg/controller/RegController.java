package com.lxy.stuinfomp.service.reg.controller;

import com.lxy.stuinfomp.commons.domain.Users;
import com.lxy.stuinfomp.commons.dto.AbstractBaseResult;
import com.lxy.stuinfomp.commons.service.UserService;
import com.lxy.stuinfomp.commons.validator.BeanValidator;
import com.lxy.stuinfomp.commons.web.AbstractBaseController;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "user")
public class RegController extends AbstractBaseController<Users> {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "用户注册",notes = "用户名和邮箱不可重复")
    @PostMapping(value = "reg")
    public AbstractBaseResult reg(@ApiParam(name = "user",value = "登录用户") Users user){
        String message = BeanValidator.validator(user);
        if (StringUtils.isNotBlank(message)){
            return error(message,null);

        }

        /**
         * 验证用户名是否重复
         */
        if (!userService.unique("userName",user.getUserName())){
            return error("用户名重复，请重试",null);
        }

        /**
         * 验证邮箱是否重复
         */
        if (!userService.unique("email",user.getEmail())){
            return error("邮箱重复，请重试",null);
        }
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
        Users resultUser = userService.save(user);
        if (null != resultUser){
            response.setStatus(HttpStatus.CREATED.value());
            return success(request.getRequestURI(),resultUser);
        }
        return error("注册失败，请重试",null);
    }

    @ApiOperation(value = "用户登录",notes = "")
    @PostMapping(value = "login")
    public AbstractBaseResult login(@ApiParam(name = "user",value = "登录用户") Users user){

        System.out.println(user.toString());
        response.setStatus(HttpStatus.CREATED.value());
        return success(request.getRequestURI(),user);
    }
}
