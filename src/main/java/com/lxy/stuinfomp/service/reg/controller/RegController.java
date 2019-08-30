package com.lxy.stuinfomp.service.reg.controller;

import com.lxy.stuinfomp.commons.domain.Users;
import com.lxy.stuinfomp.commons.dto.AbstractBaseResult;
import com.lxy.stuinfomp.commons.service.UserService;
import com.lxy.stuinfomp.commons.validator.BeanValidator;
import com.lxy.stuinfomp.commons.web.AbstractBaseController;
import com.lxy.stuinfomp.service.reg.entity.UserDTO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "user")
public class RegController extends AbstractBaseController<Users> {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "用户注册",notes = "用户名和邮箱不可重复")
    @PostMapping(value = "reg")
    public AbstractBaseResult reg(@ApiParam(name = "user",value = "注册用户") UserDTO user){
        String message = BeanValidator.validator(user);
        if (StringUtils.isNotBlank(message)){
            return error(message,null);

        }
        Users userPojo = new Users();
        BeanUtils.copyProperties(user,userPojo);
        /**
         * 验证用户名是否重复
         */
        if (!userService.unique("username",userPojo.getUsername())){
            return error("用户名重复，请重试",null);
        }

        /**
         * 验证邮箱是否重复
         */
        if (!userService.unique("email",userPojo.getEmail())){
            return error("邮箱重复，请重试",null);
        }
        userPojo.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
        userPojo.setIsDeleted(0);
        Users resultUser = userService.save(userPojo);
        if (null != resultUser){
            response.setStatus(HttpStatus.CREATED.value());
            return success(request.getRequestURI(),resultUser);
        }
        return error("注册失败，请重试",null);
    }

    @ApiOperation(value = "用户登录",notes = "")
    @PostMapping(value = "login")
    public AbstractBaseResult login(@RequestBody UserDTO user){
        if(user.getPassword() == null || user.getUsername() == null){
            return error("用户名或密码不能为空",null);
        }
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
        Users resultUser = userService.selectByUserName(user.getUsername());

        if (null != resultUser && resultUser.getPassword().equals(user.getPassword())){
            response.setStatus(HttpStatus.OK.value());
            return success(request.getRequestURI(),resultUser);
        }
        return error("用户名或密码错误",null);
    }
}
