package com.hkb.hdms.controller;

import com.hkb.hdms.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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

    /**
     * 跳转到角色管理页面
     */
    @RequestMapping("/role.html")
    public Object rolePage(){
        return "role/role";
    }

    /**
     * 获取角色接口，分页查询
     */
    @GetMapping("/getRole")
    @ResponseBody
    public Object getRole(int limit, int page){
        return roleService.getRoles(limit, page);
    }

    /**
     * 增加角色接口
     */
    @PostMapping("/addRole")
    @ResponseBody
    public Object addRole(){
        return null;
    }

    /**
     * 修改角色接口
     */
    @PostMapping("/updateRole")
    @ResponseBody
    public Object updateRole(){
        return null;
    }

    /**
     * 删除角色接口
     */
    @PostMapping("/deleteRole")
    @ResponseBody
    public Object deleteRole(){
        return null;
    }

    /**
     * 角色页面资源绑定
     */
    @PostMapping("/roleToMenu")
    @ResponseBody
    public Object roleToMenu(){
        return null;
    }
}
