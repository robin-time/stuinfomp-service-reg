package com.lxy.stuinfomp.service.reg.controller;

import com.lxy.stuinfomp.commons.domain.Users;
import com.lxy.stuinfomp.commons.dto.AbstractBaseResult;
import com.lxy.stuinfomp.commons.service.UserService;
import com.lxy.stuinfomp.commons.validator.BeanValidator;
import com.lxy.stuinfomp.commons.web.AbstractBaseController;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "reg")
public class RegController extends AbstractBaseController<Users> {

    @Autowired
    private UserService userService;

    @PostMapping(value = "")
    public AbstractBaseResult reg(Users user){
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
}
