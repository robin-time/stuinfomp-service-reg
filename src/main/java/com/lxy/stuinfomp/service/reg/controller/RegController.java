package com.lxy.stuinfomp.service.reg.controller;

import com.lxy.stuinfomp.commons.domain.Users;
import com.lxy.stuinfomp.commons.mapper.UsersMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "reg")
public class RegController {

    @Autowired
    private UsersMapper usersMapper;

    @GetMapping(value = {"{id}"})
    public String reg(@PathVariable int id){
        Users user = usersMapper.selectByPrimaryKey(id);
        return user.getUserName();
    }
}
