package com.hkb.hdms.model.dto;

import com.hkb.hdms.model.pojo.UserRole;
import lombok.Data;

import java.util.Date;

/**
 * hdms_user表 dto
 *
 * @author huangkebing
 * 2021/03/21
 */
@Data
public class UserDto {
    private Long id;

    private String username;

    private String email;

    private String phone;

    private String remark;

    private UserRole role;

    private Integer live;

    private Date create;

    private Date modify;
}
