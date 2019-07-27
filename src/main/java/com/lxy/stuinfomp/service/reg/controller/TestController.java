package com.lxy.stuinfomp.service.reg.controller;

import com.google.common.collect.Lists;
import com.lxy.stuinfomp.commons.domain.Users;
import com.lxy.stuinfomp.commons.dto.AbstractBaseResult;
import com.lxy.stuinfomp.commons.dto.BaseResultFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("test")
public class TestController {

    //@Value("")这种取配置值只能取一次，不能满足动态配置的需求，ConfigurableApplicationContext是可以做的动态配置及时更新
    @Autowired
    private ConfigurableApplicationContext applicationContext;

    @GetMapping(value = "records/{id}")
    public AbstractBaseResult getByid(HttpServletRequest request, @PathVariable long id){
        Users users = new Users();
        users.setId(1L);
        users.setUserName("robin");
        if (id ==1){
            return BaseResultFactory.getInstance().build(request.getRequestURI(),users);
        }else {
            return BaseResultFactory.build(HttpStatus.UNAUTHORIZED.value(),"参考错误","id只能是1",applicationContext.getEnvironment().getProperty("logging.level.com.lxy.stuinfomp"));
        }


    }

    @GetMapping(value = "records")
    public AbstractBaseResult getByid(HttpServletRequest request){
        Users user = new Users();
        user.setId(1L);
        user.setUserName("robin");

        Users user2 = new Users();
        user2.setId(2L);
        user2.setUserName("time");

        List<Users> users1 = Lists.newArrayList();
        users1.add(user);
        users1.add(user2);
        return BaseResultFactory.getInstance().build(request.getRequestURI(),2,10,users1);
    }
}
