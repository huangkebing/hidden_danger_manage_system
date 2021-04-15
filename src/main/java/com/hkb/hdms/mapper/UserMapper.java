package com.hkb.hdms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hkb.hdms.model.dto.UserDto;
import com.hkb.hdms.model.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author huangkebing
 * 2021/03/08
 */
@Repository
public interface UserMapper extends BaseMapper<User> {
    List<UserDto> selectUsers(Page<UserDto> page, @Param("user") User user);

    UserDto selectUserById(Long userId);

    List<User> selectUsersByTypeAndRole(Long roleId, Long typeId);
}
