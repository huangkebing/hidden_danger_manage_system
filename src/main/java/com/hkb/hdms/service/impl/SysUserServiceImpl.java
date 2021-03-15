package com.hkb.hdms.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hkb.hdms.base.BaseReturnDto;
import com.hkb.hdms.base.Constants;
import com.hkb.hdms.base.ReturnConstants;
import com.hkb.hdms.mapper.UserMapper;
import com.hkb.hdms.model.pojo.User;
import com.hkb.hdms.service.SysUserService;
import com.mysql.cj.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

/**
 * @author huangkebing
 * 2021/03/15
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<UserMapper, User> implements SysUserService {

    private final HttpSession session;

    @Autowired
    public SysUserServiceImpl(HttpSession session) {
        this.session = session;
    }

    @Override
    public BaseReturnDto resetPassword(String old, String now, String check) {
        if(StringUtils.isNullOrEmpty(old) || StringUtils.isNullOrEmpty(now) || StringUtils.isNullOrEmpty(check)){
            return ReturnConstants.PASSWORD_EMPTY;
        }
        User loginUser = (User) session.getAttribute(Constants.LOGIN_USER_KEY);
        //旧密码不匹配
        if(!loginUser.getPassword().equals(old)){
            return ReturnConstants.OLD_PASSWORD_ERROR;
        }
        //两次密码不匹配
        if(!now.equals(check)){
            return ReturnConstants.PASSWORD_NOT_SAME;
        }
        //匹配完成进行update
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",loginUser.getId());
        User user = new User();
        user.setPassword(now);
        user.setRole(loginUser.getRole());
        //修改
        if (this.update(user,updateWrapper)) {
            return ReturnConstants.SUCCESS;
        }
        else {
            return ReturnConstants.FAILURE;
        }
    }
}
