package com.hkb.hdms.controller;

import com.hkb.hdms.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 系统中用户角色相关接口
 *
 * @author huangkebing
 * 2021/03/18
 */
@Controller
@RequestMapping("/role")
public class RoleController {

    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @RequestMapping("/role.html")
    public Object rolePage(){
        return "role/role";
    }

    @GetMapping("/getRole")
    @ResponseBody
    public Object getRole(int limit, int page){
        return roleService.getRoles(limit, page);
    }
}
