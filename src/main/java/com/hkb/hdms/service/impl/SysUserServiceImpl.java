package com.hkb.hdms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hkb.hdms.base.MailConstants;
import com.hkb.hdms.base.R;
import com.hkb.hdms.base.Constants;
import com.hkb.hdms.base.ReturnConstants;
import com.hkb.hdms.mapper.UserMapper;
import com.hkb.hdms.model.dto.UserDto;
import com.hkb.hdms.model.pojo.User;
import com.hkb.hdms.service.SysUserService;
import com.hkb.hdms.utils.PasswordUtil;
import com.hkb.hdms.utils.impl.RegisterMailSender;
import com.mysql.cj.util.StringUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author huangkebing
 * 2021/03/15
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<UserMapper, User> implements SysUserService {

    private final HttpSession session;

    private final UserMapper userMapper;

    private final RegisterMailSender mailSender;

    @Autowired
    public SysUserServiceImpl(HttpSession session, UserMapper userMapper, RegisterMailSender mailSender) {
        this.session = session;
        this.userMapper = userMapper;
        this.mailSender = mailSender;
    }

    @Override
    public R resetPassword(String old, String now, String check) {
        if (StringUtils.isNullOrEmpty(old) || StringUtils.isNullOrEmpty(now) || StringUtils.isNullOrEmpty(check)) {
            return ReturnConstants.PARAMS_EMPTY;
        }
        User loginUser = (User) session.getAttribute(Constants.LOGIN_USER_KEY);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        //旧密码不匹配
        if (!passwordEncoder.matches(old, loginUser.getPassword())) {
            return ReturnConstants.OLD_PASSWORD_ERROR;
        }
        //两次密码不匹配
        if (!now.equals(check)) {
            return ReturnConstants.PASSWORD_NOT_SAME;
        }
        //匹配完成进行update
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", loginUser.getId());
        User user = new User();
        user.setPassword(passwordEncoder.encode(now));
        //修改
        if (this.update(user, updateWrapper)) {
            return ReturnConstants.SUCCESS;
        } else {
            return ReturnConstants.FAILURE;
        }
    }

    @Override
    public Map<String, Object> getUser(int limit, int page) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("code", 0);
        map.put("msg", "");
        if (page < 1) {
            page = 1;
        }
        Page<UserDto> UserPage = new Page<>(page, limit);
        List<UserDto> userDtos = userMapper.selectUsers(UserPage);

        map.put("count", UserPage.getTotal());
        map.put("data", userDtos);
        return map;
    }

    @Override
    public R addUser(User user) {
        if (StringUtils.isNullOrEmpty(user.getEmail())) {
            return ReturnConstants.PARAMS_EMPTY;
        }
        User hasUser = this.getOne(new QueryWrapper<User>().eq("email", user.getEmail()));
        if (ObjectUtils.isNotEmpty(hasUser)) {
            return ReturnConstants.EMAIL_EXIST;
        }

        user.setName(user.getEmail().substring(0, user.getEmail().length() - 8));
        String password = PasswordUtil.getPassword();
        user.setPassword(new BCryptPasswordEncoder().encode(password));

        if (this.save(user)) {
            mailSender.sendMail(MailConstants.REGISTER,new String[]{password},user.getEmail());
            return ReturnConstants.SUCCESS;
        } else {
            return ReturnConstants.FAILURE;
        }
    }

    @Override
    public R updateUser(User user) {
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", user.getId());

        if (this.update(user, updateWrapper)) {
            return ReturnConstants.SUCCESS;
        } else {
            return ReturnConstants.FAILURE;
        }
    }

    @Override
    public R deleteUser(Long id) {
        if (this.removeById(id)) {
            return ReturnConstants.SUCCESS;
        } else {
            return ReturnConstants.FAILURE;
        }
    }
}
