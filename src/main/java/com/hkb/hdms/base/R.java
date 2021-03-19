package com.hkb.hdms.base;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

/**
 * 统一数据格式
 *
 * @author huangkebing
 * 2021/03/07
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class R {

    private int code;

    private String message;

    private Map<String, Object> data;

    public R(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
