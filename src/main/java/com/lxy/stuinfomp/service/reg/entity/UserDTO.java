package com.lxy.stuinfomp.service.reg.entity;

import com.lxy.stuinfomp.commons.utils.RegexpUtils;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class UserDTO {

    @NotNull(message = "用户名不能为空")
    @Length(min = 5, max = 20, message = "用户名长度必须介于 5 和 20 之间")
    private String username;

    @NotNull(message = "密码不能为空")
    private String password;

    private String phone;

    @NotNull(message = "邮箱不能空")
    @Pattern(regexp = RegexpUtils.EMAIL, message = "邮箱格式不正确")
    private String email;

}