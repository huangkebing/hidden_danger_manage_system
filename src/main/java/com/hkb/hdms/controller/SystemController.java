package com.hkb.hdms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 系统构建相关接口
 *
 * @author huangkebing
 * 2021/03/13
 */
@Controller
@RequestMapping("/system")
public class SystemController {

    @RequestMapping("/test.html")
    public Object test(){
        return "test";
    }

    @GetMapping("/menuInit")
    @ResponseBody
    public Object menuInit(){
        return "{\n" +
                "  \"homeInfo\": {\n" +
                "    \"title\": \"首页\",\n" +
                "    \"href\": \"system/test.html\"\n" +
                "  },\n" +
                "  \"logoInfo\": {\n" +
                "    \"title\": \"LAYUI MINI\",\n" +
                "    \"image\": \"images/logo.png\",\n" +
                "    \"href\": \"\"\n" +
                "  },\n" +
                "  \"menuInfo\": [\n" +
                "    {\n" +
                "      \"title\": \"常规管理\",\n" +
                "      \"icon\": \"fa fa-address-book\",\n" +
                "      \"href\": \"\",\n" +
                "      \"target\": \"_self\",\n" +
                "      \"child\": [\n" +
                "        {\n" +
                "          \"title\": \"主页模板\",\n" +
                "          \"href\": \"\",\n" +
                "          \"icon\": \"fa fa-home\",\n" +
                "          \"target\": \"_self\",\n" +
                "          \"child\": [\n" +
                "            {\n" +
                "              \"title\": \"主页一\",\n" +
                "              \"href\": \"page/welcome-1.html\",\n" +
                "              \"icon\": \"fa fa-tachometer\",\n" +
                "              \"target\": \"_self\"\n" +
                "            },\n" +
                "            {\n" +
                "              \"title\": \"主页二\",\n" +
                "              \"href\": \"page/welcome-2.html\",\n" +
                "              \"icon\": \"fa fa-tachometer\",\n" +
                "              \"target\": \"_self\"\n" +
                "            },\n" +
                "            {\n" +
                "              \"title\": \"主页三\",\n" +
                "              \"href\": \"page/welcome-3.html\",\n" +
                "              \"icon\": \"fa fa-tachometer\",\n" +
                "              \"target\": \"_self\"\n" +
                "            }\n" +
                "          ]\n" +
                "        },\n" +
                "        {\n" +
                "          \"title\": \"菜单管理\",\n" +
                "          \"href\": \"page/menu.html\",\n" +
                "          \"icon\": \"fa fa-window-maximize\",\n" +
                "          \"target\": \"_self\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"title\": \"系统设置\",\n" +
                "          \"href\": \"page/setting.html\",\n" +
                "          \"icon\": \"fa fa-gears\",\n" +
                "          \"target\": \"_self\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"title\": \"表格示例\",\n" +
                "          \"href\": \"page/table.html\",\n" +
                "          \"icon\": \"fa fa-file-text\",\n" +
                "          \"target\": \"_self\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"title\": \"表单示例\",\n" +
                "          \"href\": \"\",\n" +
                "          \"icon\": \"fa fa-calendar\",\n" +
                "          \"target\": \"_self\",\n" +
                "          \"child\": [\n" +
                "            {\n" +
                "              \"title\": \"普通表单\",\n" +
                "              \"href\": \"page/form.html\",\n" +
                "              \"icon\": \"fa fa-list-alt\",\n" +
                "              \"target\": \"_self\"\n" +
                "            },\n" +
                "            {\n" +
                "              \"title\": \"分步表单\",\n" +
                "              \"href\": \"page/form-step.html\",\n" +
                "              \"icon\": \"fa fa-navicon\",\n" +
                "              \"target\": \"_self\"\n" +
                "            }\n" +
                "          ]\n" +
                "        },\n" +
                "        {\n" +
                "          \"title\": \"登录模板\",\n" +
                "          \"href\": \"\",\n" +
                "          \"icon\": \"fa fa-flag-o\",\n" +
                "          \"target\": \"_self\",\n" +
                "          \"child\": [\n" +
                "            {\n" +
                "              \"title\": \"登录-1\",\n" +
                "              \"href\": \"page/login-1.html\",\n" +
                "              \"icon\": \"fa fa-stumbleupon-circle\",\n" +
                "              \"target\": \"_blank\"\n" +
                "            },\n" +
                "            {\n" +
                "              \"title\": \"登录-2\",\n" +
                "              \"href\": \"page/login-2.html\",\n" +
                "              \"icon\": \"fa fa-viacoin\",\n" +
                "              \"target\": \"_blank\"\n" +
                "            },\n" +
                "            {\n" +
                "              \"title\": \"登录-3\",\n" +
                "              \"href\": \"page/login-3.html\",\n" +
                "              \"icon\": \"fa fa-tags\",\n" +
                "              \"target\": \"_blank\"\n" +
                "            }\n" +
                "          ]\n" +
                "        },\n" +
                "        {\n" +
                "          \"title\": \"异常页面\",\n" +
                "          \"href\": \"\",\n" +
                "          \"icon\": \"fa fa-home\",\n" +
                "          \"target\": \"_self\",\n" +
                "          \"child\": [\n" +
                "            {\n" +
                "              \"title\": \"404页面\",\n" +
                "              \"href\": \"page/404.html\",\n" +
                "              \"icon\": \"fa fa-hourglass-end\",\n" +
                "              \"target\": \"_self\"\n" +
                "            }\n" +
                "          ]\n" +
                "        },\n" +
                "        {\n" +
                "          \"title\": \"其它界面\",\n" +
                "          \"href\": \"\",\n" +
                "          \"icon\": \"fa fa-snowflake-o\",\n" +
                "          \"target\": \"\",\n" +
                "          \"child\": [\n" +
                "            {\n" +
                "              \"title\": \"按钮示例\",\n" +
                "              \"href\": \"page/button.html\",\n" +
                "              \"icon\": \"fa fa-snowflake-o\",\n" +
                "              \"target\": \"_self\"\n" +
                "            },\n" +
                "            {\n" +
                "              \"title\": \"弹出层\",\n" +
                "              \"href\": \"page/layer.html\",\n" +
                "              \"icon\": \"fa fa-shield\",\n" +
                "              \"target\": \"_self\"\n" +
                "            }\n" +
                "          ]\n" +
                "        }\n" +
                "      ]\n" +
                "    },\n" +
                "    {\n" +
                "      \"title\": \"组件管理\",\n" +
                "      \"icon\": \"fa fa-lemon-o\",\n" +
                "      \"href\": \"\",\n" +
                "      \"target\": \"_self\",\n" +
                "      \"child\": [\n" +
                "        {\n" +
                "          \"title\": \"图标列表\",\n" +
                "          \"href\": \"page/icon.html\",\n" +
                "          \"icon\": \"fa fa-dot-circle-o\",\n" +
                "          \"target\": \"_self\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"title\": \"图标选择\",\n" +
                "          \"href\": \"page/icon-picker.html\",\n" +
                "          \"icon\": \"fa fa-adn\",\n" +
                "          \"target\": \"_self\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"title\": \"颜色选择\",\n" +
                "          \"href\": \"page/color-select.html\",\n" +
                "          \"icon\": \"fa fa-dashboard\",\n" +
                "          \"target\": \"_self\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"title\": \"下拉选择\",\n" +
                "          \"href\": \"page/table-select.html\",\n" +
                "          \"icon\": \"fa fa-angle-double-down\",\n" +
                "          \"target\": \"_self\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"title\": \"文件上传\",\n" +
                "          \"href\": \"page/upload.html\",\n" +
                "          \"icon\": \"fa fa-arrow-up\",\n" +
                "          \"target\": \"_self\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"title\": \"富文本编辑器\",\n" +
                "          \"href\": \"page/editor.html\",\n" +
                "          \"icon\": \"fa fa-edit\",\n" +
                "          \"target\": \"_self\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"title\": \"省市县区选择器\",\n" +
                "          \"href\": \"page/area.html\",\n" +
                "          \"icon\": \"fa fa-rocket\",\n" +
                "          \"target\": \"_self\"\n" +
                "        }\n" +
                "      ]\n" +
                "    },\n" +
                "    {\n" +
                "      \"title\": \"其它管理\",\n" +
                "      \"icon\": \"fa fa-slideshare\",\n" +
                "      \"href\": \"\",\n" +
                "      \"target\": \"_self\",\n" +
                "      \"child\": [\n" +
                "        {\n" +
                "          \"title\": \"多级菜单\",\n" +
                "          \"href\": \"\",\n" +
                "          \"icon\": \"fa fa-meetup\",\n" +
                "          \"target\": \"\",\n" +
                "          \"child\": [\n" +
                "            {\n" +
                "              \"title\": \"按钮1\",\n" +
                "              \"href\": \"page/button.html?v=1\",\n" +
                "              \"icon\": \"fa fa-calendar\",\n" +
                "              \"target\": \"_self\",\n" +
                "              \"child\": [\n" +
                "                {\n" +
                "                  \"title\": \"按钮2\",\n" +
                "                  \"href\": \"page/button.html?v=2\",\n" +
                "                  \"icon\": \"fa fa-snowflake-o\",\n" +
                "                  \"target\": \"_self\",\n" +
                "                  \"child\": [\n" +
                "                    {\n" +
                "                      \"title\": \"按钮3\",\n" +
                "                      \"href\": \"page/button.html?v=3\",\n" +
                "                      \"icon\": \"fa fa-snowflake-o\",\n" +
                "                      \"target\": \"_self\"\n" +
                "                    },\n" +
                "                    {\n" +
                "                      \"title\": \"表单4\",\n" +
                "                      \"href\": \"page/form.html?v=1\",\n" +
                "                      \"icon\": \"fa fa-calendar\",\n" +
                "                      \"target\": \"_self\"\n" +
                "                    }\n" +
                "                  ]\n" +
                "                }\n" +
                "              ]\n" +
                "            }\n" +
                "          ]\n" +
                "        },\n" +
                "        {\n" +
                "          \"title\": \"失效菜单\",\n" +
                "          \"href\": \"page/error.html\",\n" +
                "          \"icon\": \"fa fa-superpowers\",\n" +
                "          \"target\": \"_self\"\n" +
                "        }\n" +
                "      ]\n" +
                "    }\n" +
                "  ]\n" +
                "}";
    }
}
