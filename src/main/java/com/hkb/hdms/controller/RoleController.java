package com.hkb.hdms.controller;

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

    @RequestMapping("/role.html")
    public Object rolePage(){
        return "role/role";
    }

    @GetMapping("/getRole")
    @ResponseBody
    public Object getRole(){
        return "{\n" +
                "  \"code\": 0,\n" +
                "  \"msg\": \"\",\n" +
                "  \"count\": 1000,\n" +
                "  \"data\": [\n" +
                "    {\n" +
                "      \"id\": 10000,\n" +
                "      \"username\": \"user-0\",\n" +
                "      \"sex\": \"女\",\n" +
                "      \"city\": \"城市-0\",\n" +
                "      \"sign\": \"签名-0\",\n" +
                "      \"experience\": 255,\n" +
                "      \"logins\": 24,\n" +
                "      \"wealth\": 82830700,\n" +
                "      \"classify\": \"作家\",\n" +
                "      \"score\": 57\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 10001,\n" +
                "      \"username\": \"user-1\",\n" +
                "      \"sex\": \"男\",\n" +
                "      \"city\": \"城市-1\",\n" +
                "      \"sign\": \"签名-1\",\n" +
                "      \"experience\": 884,\n" +
                "      \"logins\": 58,\n" +
                "      \"wealth\": 64928690,\n" +
                "      \"classify\": \"词人\",\n" +
                "      \"score\": 27\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 10002,\n" +
                "      \"username\": \"user-2\",\n" +
                "      \"sex\": \"女\",\n" +
                "      \"city\": \"城市-2\",\n" +
                "      \"sign\": \"签名-2\",\n" +
                "      \"experience\": 650,\n" +
                "      \"logins\": 77,\n" +
                "      \"wealth\": 6298078,\n" +
                "      \"classify\": \"酱油\",\n" +
                "      \"score\": 31\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 10003,\n" +
                "      \"username\": \"user-3\",\n" +
                "      \"sex\": \"女\",\n" +
                "      \"city\": \"城市-3\",\n" +
                "      \"sign\": \"签名-3\",\n" +
                "      \"experience\": 362,\n" +
                "      \"logins\": 157,\n" +
                "      \"wealth\": 37117017,\n" +
                "      \"classify\": \"诗人\",\n" +
                "      \"score\": 68\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 10004,\n" +
                "      \"username\": \"user-4\",\n" +
                "      \"sex\": \"男\",\n" +
                "      \"city\": \"城市-4\",\n" +
                "      \"sign\": \"签名-4\",\n" +
                "      \"experience\": 807,\n" +
                "      \"logins\": 51,\n" +
                "      \"wealth\": 76263262,\n" +
                "      \"classify\": \"作家\",\n" +
                "      \"score\": 6\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 10005,\n" +
                "      \"username\": \"user-5\",\n" +
                "      \"sex\": \"女\",\n" +
                "      \"city\": \"城市-5\",\n" +
                "      \"sign\": \"签名-5\",\n" +
                "      \"experience\": 173,\n" +
                "      \"logins\": 68,\n" +
                "      \"wealth\": 60344147,\n" +
                "      \"classify\": \"作家\",\n" +
                "      \"score\": 87\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 10006,\n" +
                "      \"username\": \"user-6\",\n" +
                "      \"sex\": \"女\",\n" +
                "      \"city\": \"城市-6\",\n" +
                "      \"sign\": \"签名-6\",\n" +
                "      \"experience\": 982,\n" +
                "      \"logins\": 37,\n" +
                "      \"wealth\": 57768166,\n" +
                "      \"classify\": \"作家\",\n" +
                "      \"score\": 34\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 10007,\n" +
                "      \"username\": \"user-7\",\n" +
                "      \"sex\": \"男\",\n" +
                "      \"city\": \"城市-7\",\n" +
                "      \"sign\": \"签名-7\",\n" +
                "      \"experience\": 727,\n" +
                "      \"logins\": 150,\n" +
                "      \"wealth\": 82030578,\n" +
                "      \"classify\": \"作家\",\n" +
                "      \"score\": 28\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 10008,\n" +
                "      \"username\": \"user-8\",\n" +
                "      \"sex\": \"男\",\n" +
                "      \"city\": \"城市-8\",\n" +
                "      \"sign\": \"签名-8\",\n" +
                "      \"experience\": 951,\n" +
                "      \"logins\": 133,\n" +
                "      \"wealth\": 16503371,\n" +
                "      \"classify\": \"词人\",\n" +
                "      \"score\": 14\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 10009,\n" +
                "      \"username\": \"user-9\",\n" +
                "      \"sex\": \"女\",\n" +
                "      \"city\": \"城市-9\",\n" +
                "      \"sign\": \"签名-9\",\n" +
                "      \"experience\": 484,\n" +
                "      \"logins\": 25,\n" +
                "      \"wealth\": 86801934,\n" +
                "      \"classify\": \"词人\",\n" +
                "      \"score\": 75\n" +
                "    }\n" +
                "  ]\n" +
                "}";
    }
}
