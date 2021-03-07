package com.hkb.hdms.base;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

/**
 * @author huangkebing
 * 2021/03/07
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BaseReturnDto {

    private int code;

    private String message;

    private Map<String, Object> data;
}
