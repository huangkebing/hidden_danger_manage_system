package com.hkb.hdms.controller;

import com.hkb.hdms.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 系统资源相关接口
 *
 * @author huangkebing
 * 2021/03/13
 */
@Controller
@RequestMapping("/system")
public class SystemController {

    private final MenuService menuService;

    @Autowired
    public SystemController(MenuService menuService) {
        this.menuService = menuService;
    }

    @RequestMapping("/test.html")
    public Object test(){
        return "test";
    }

    /**
     * 跳转到资源管理页面
     */
    @RequestMapping("/menu.html")
    public Object menuPage(){
         return "system/menu";
    }

    /**
     * 首页渲染数据
     */
    @GetMapping("/menuInit")
    @ResponseBody
    public Object menuInit(){
        return menuService.initMenu();
    }

    @GetMapping("/getMenu")
    @ResponseBody
    public Object getMenu(){
        return "{\n" +
                "  \"code\": 0,\n" +
                "  \"msg\": \"\",\n" +
                "  \"count\": 19,\n" +
                "  \"data\": [\n" +
                "    {\n" +
                "      \"authorityId\": 1,\n" +
                "      \"authorityName\": \"系统管理\",\n" +
                "      \"orderNumber\": 1,\n" +
                "      \"menuUrl\": null,\n" +
                "      \"menuIcon\": \"layui-icon-set\",\n" +
                "      \"createTime\": \"2018/06/29 11:05:41\",\n" +
                "      \"authority\": null,\n" +
                "      \"checked\": 0,\n" +
                "      \"updateTime\": \"2018/07/13 09:13:42\",\n" +
                "      \"isMenu\": 0,\n" +
                "      \"parentId\": -1\n" +
                "    },\n" +
                "    {\n" +
                "      \"authorityId\": 2,\n" +
                "      \"authorityName\": \"用户管理\",\n" +
                "      \"orderNumber\": 2,\n" +
                "      \"menuUrl\": \"system/user\",\n" +
                "      \"menuIcon\": null,\n" +
                "      \"createTime\": \"2018/06/29 11:05:41\",\n" +
                "      \"authority\": null,\n" +
                "      \"checked\": 0,\n" +
                "      \"updateTime\": \"2018/07/13 09:13:42\",\n" +
                "      \"isMenu\": 0,\n" +
                "      \"parentId\": 1\n" +
                "    },\n" +
                "    {\n" +
                "      \"authorityId\": 3,\n" +
                "      \"authorityName\": \"查询用户\",\n" +
                "      \"orderNumber\": 3,\n" +
                "      \"menuUrl\": \"\",\n" +
                "      \"menuIcon\": \"\",\n" +
                "      \"createTime\": \"2018/07/21 13:54:16\",\n" +
                "      \"authority\": \"user:view\",\n" +
                "      \"checked\": 0,\n" +
                "      \"updateTime\": \"2018/07/21 13:54:16\",\n" +
                "      \"isMenu\": 1,\n" +
                "      \"parentId\": 2\n" +
                "    },\n" +
                "    {\n" +
                "      \"authorityId\": 4,\n" +
                "      \"authorityName\": \"添加用户\",\n" +
                "      \"orderNumber\": 4,\n" +
                "      \"menuUrl\": null,\n" +
                "      \"menuIcon\": null,\n" +
                "      \"createTime\": \"2018/06/29 11:05:41\",\n" +
                "      \"authority\": \"user:add\",\n" +
                "      \"checked\": 0,\n" +
                "      \"updateTime\": \"2018/07/13 09:13:42\",\n" +
                "      \"isMenu\": 1,\n" +
                "      \"parentId\": 2\n" +
                "    },\n" +
                "    {\n" +
                "      \"authorityId\": 5,\n" +
                "      \"authorityName\": \"修改用户\",\n" +
                "      \"orderNumber\": 5,\n" +
                "      \"menuUrl\": null,\n" +
                "      \"menuIcon\": null,\n" +
                "      \"createTime\": \"2018/06/29 11:05:41\",\n" +
                "      \"authority\": \"user:edit\",\n" +
                "      \"checked\": 0,\n" +
                "      \"updateTime\": \"2018/07/13 09:13:42\",\n" +
                "      \"isMenu\": 1,\n" +
                "      \"parentId\": 2\n" +
                "    },\n" +
                "    {\n" +
                "      \"authorityId\": 6,\n" +
                "      \"authorityName\": \"删除用户\",\n" +
                "      \"orderNumber\": 6,\n" +
                "      \"menuUrl\": null,\n" +
                "      \"menuIcon\": null,\n" +
                "      \"createTime\": \"2018/06/29 11:05:41\",\n" +
                "      \"authority\": \"user:delete\",\n" +
                "      \"checked\": 0,\n" +
                "      \"updateTime\": \"2018/07/13 09:13:42\",\n" +
                "      \"isMenu\": 1,\n" +
                "      \"parentId\": 2\n" +
                "    },\n" +
                "    {\n" +
                "      \"authorityId\": 7,\n" +
                "      \"authorityName\": \"角色管理\",\n" +
                "      \"orderNumber\": 7,\n" +
                "      \"menuUrl\": \"system/role\",\n" +
                "      \"menuIcon\": null,\n" +
                "      \"createTime\": \"2018/06/29 11:05:41\",\n" +
                "      \"authority\": null,\n" +
                "      \"checked\": 0,\n" +
                "      \"updateTime\": \"2018/07/13 09:13:42\",\n" +
                "      \"isMenu\": 0,\n" +
                "      \"parentId\": 1\n" +
                "    },\n" +
                "    {\n" +
                "      \"authorityId\": 8,\n" +
                "      \"authorityName\": \"查询角色\",\n" +
                "      \"orderNumber\": 8,\n" +
                "      \"menuUrl\": \"\",\n" +
                "      \"menuIcon\": \"\",\n" +
                "      \"createTime\": \"2018/07/21 13:54:59\",\n" +
                "      \"authority\": \"role:view\",\n" +
                "      \"checked\": 0,\n" +
                "      \"updateTime\": \"2018/07/21 13:54:58\",\n" +
                "      \"isMenu\": 1,\n" +
                "      \"parentId\": 7\n" +
                "    },\n" +
                "    {\n" +
                "      \"authorityId\": 9,\n" +
                "      \"authorityName\": \"添加角色\",\n" +
                "      \"orderNumber\": 9,\n" +
                "      \"menuUrl\": \"\",\n" +
                "      \"menuIcon\": \"\",\n" +
                "      \"createTime\": \"2018/06/29 11:05:41\",\n" +
                "      \"authority\": \"role:add\",\n" +
                "      \"checked\": 0,\n" +
                "      \"updateTime\": \"2018/07/13 09:13:42\",\n" +
                "      \"isMenu\": 1,\n" +
                "      \"parentId\": 7\n" +
                "    },\n" +
                "    {\n" +
                "      \"authorityId\": 10,\n" +
                "      \"authorityName\": \"修改角色\",\n" +
                "      \"orderNumber\": 10,\n" +
                "      \"menuUrl\": \"\",\n" +
                "      \"menuIcon\": \"\",\n" +
                "      \"createTime\": \"2018/06/29 11:05:41\",\n" +
                "      \"authority\": \"role:edit\",\n" +
                "      \"checked\": 0,\n" +
                "      \"updateTime\": \"2018/07/13 09:13:42\",\n" +
                "      \"isMenu\": 1,\n" +
                "      \"parentId\": 7\n" +
                "    },\n" +
                "    {\n" +
                "      \"authorityId\": 11,\n" +
                "      \"authorityName\": \"删除角色\",\n" +
                "      \"orderNumber\": 11,\n" +
                "      \"menuUrl\": \"\",\n" +
                "      \"menuIcon\": \"\",\n" +
                "      \"createTime\": \"2018/06/29 11:05:41\",\n" +
                "      \"authority\": \"role:delete\",\n" +
                "      \"checked\": 0,\n" +
                "      \"updateTime\": \"2018/07/13 09:13:42\",\n" +
                "      \"isMenu\": 1,\n" +
                "      \"parentId\": 7\n" +
                "    },\n" +
                "    {\n" +
                "      \"authorityId\": 12,\n" +
                "      \"authorityName\": \"角色权限管理\",\n" +
                "      \"orderNumber\": 12,\n" +
                "      \"menuUrl\": \"\",\n" +
                "      \"menuIcon\": \"\",\n" +
                "      \"createTime\": \"2018/06/29 11:05:41\",\n" +
                "      \"authority\": \"role:auth\",\n" +
                "      \"checked\": 0,\n" +
                "      \"updateTime\": \"2018/07/13 15:27:18\",\n" +
                "      \"isMenu\": 1,\n" +
                "      \"parentId\": 7\n" +
                "    },\n" +
                "    {\n" +
                "      \"authorityId\": 13,\n" +
                "      \"authorityName\": \"权限管理\",\n" +
                "      \"orderNumber\": 13,\n" +
                "      \"menuUrl\": \"system/authorities\",\n" +
                "      \"menuIcon\": null,\n" +
                "      \"createTime\": \"2018/06/29 11:05:41\",\n" +
                "      \"authority\": null,\n" +
                "      \"checked\": 0,\n" +
                "      \"updateTime\": \"2018/07/13 15:45:13\",\n" +
                "      \"isMenu\": 0,\n" +
                "      \"parentId\": 1\n" +
                "    },\n" +
                "    {\n" +
                "      \"authorityId\": 14,\n" +
                "      \"authorityName\": \"查询权限\",\n" +
                "      \"orderNumber\": 14,\n" +
                "      \"menuUrl\": \"\",\n" +
                "      \"menuIcon\": \"\",\n" +
                "      \"createTime\": \"2018/07/21 13:55:57\",\n" +
                "      \"authority\": \"authorities:view\",\n" +
                "      \"checked\": 0,\n" +
                "      \"updateTime\": \"2018/07/21 13:55:56\",\n" +
                "      \"isMenu\": 1,\n" +
                "      \"parentId\": 13\n" +
                "    },\n" +
                "    {\n" +
                "      \"authorityId\": 15,\n" +
                "      \"authorityName\": \"添加权限\",\n" +
                "      \"orderNumber\": 15,\n" +
                "      \"menuUrl\": \"\",\n" +
                "      \"menuIcon\": \"\",\n" +
                "      \"createTime\": \"2018/06/29 11:05:41\",\n" +
                "      \"authority\": \"authorities:add\",\n" +
                "      \"checked\": 0,\n" +
                "      \"updateTime\": \"2018/06/29 11:05:41\",\n" +
                "      \"isMenu\": 1,\n" +
                "      \"parentId\": 13\n" +
                "    },\n" +
                "    {\n" +
                "      \"authorityId\": 16,\n" +
                "      \"authorityName\": \"修改权限\",\n" +
                "      \"orderNumber\": 16,\n" +
                "      \"menuUrl\": \"\",\n" +
                "      \"menuIcon\": \"\",\n" +
                "      \"createTime\": \"2018/07/13 09:13:42\",\n" +
                "      \"authority\": \"authorities:edit\",\n" +
                "      \"checked\": 0,\n" +
                "      \"updateTime\": \"2018/07/13 09:13:42\",\n" +
                "      \"isMenu\": 1,\n" +
                "      \"parentId\": 13\n" +
                "    },\n" +
                "    {\n" +
                "      \"authorityId\": 17,\n" +
                "      \"authorityName\": \"删除权限\",\n" +
                "      \"orderNumber\": 17,\n" +
                "      \"menuUrl\": \"\",\n" +
                "      \"menuIcon\": \"\",\n" +
                "      \"createTime\": \"2018/06/29 11:05:41\",\n" +
                "      \"authority\": \"authorities:delete\",\n" +
                "      \"checked\": 0,\n" +
                "      \"updateTime\": \"2018/06/29 11:05:41\",\n" +
                "      \"isMenu\": 1,\n" +
                "      \"parentId\": 13\n" +
                "    },\n" +
                "    {\n" +
                "      \"authorityId\": 18,\n" +
                "      \"authorityName\": \"登录日志\",\n" +
                "      \"orderNumber\": 18,\n" +
                "      \"menuUrl\": \"system/loginRecord\",\n" +
                "      \"menuIcon\": null,\n" +
                "      \"createTime\": \"2018/06/29 11:05:41\",\n" +
                "      \"authority\": null,\n" +
                "      \"checked\": 0,\n" +
                "      \"updateTime\": \"2018/06/29 11:05:41\",\n" +
                "      \"isMenu\": 0,\n" +
                "      \"parentId\": 1\n" +
                "    },\n" +
                "    {\n" +
                "      \"authorityId\": 19,\n" +
                "      \"authorityName\": \"查询登录日志\",\n" +
                "      \"orderNumber\": 19,\n" +
                "      \"menuUrl\": \"\",\n" +
                "      \"menuIcon\": \"\",\n" +
                "      \"createTime\": \"2018/07/21 13:56:43\",\n" +
                "      \"authority\": \"loginRecord:view\",\n" +
                "      \"checked\": 0,\n" +
                "      \"updateTime\": \"2018/07/21 13:56:43\",\n" +
                "      \"isMenu\": 1,\n" +
                "      \"parentId\": 18\n" +
                "    }\n" +
                "  ]\n" +
                "}";
    }
}
