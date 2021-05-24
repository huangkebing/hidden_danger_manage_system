package com.hkb.hdms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hkb.hdms.base.Constants;
import com.hkb.hdms.base.R;
import com.hkb.hdms.base.ReturnConstants;
import com.hkb.hdms.mapper.TypeMapper;
import com.hkb.hdms.mapper.UserTypeMapper;
import com.hkb.hdms.model.pojo.Type;
import com.hkb.hdms.model.pojo.User;
import com.hkb.hdms.model.pojo.UserRole;
import com.hkb.hdms.model.pojo.UserType;
import com.hkb.hdms.model.vo.QueryTypeVo;
import com.hkb.hdms.model.vo.TypeVo;
import com.hkb.hdms.service.RoleService;
import com.hkb.hdms.service.TypeService;
import com.mysql.cj.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author huangkebing
 * 2021/03/24
 */
@Service
public class TypeServiceImpl extends ServiceImpl<TypeMapper, Type> implements TypeService {

    private final UserTypeMapper userTypeMapper;

    private final RoleService roleService;

    private final TypeMapper typeMapper;

    private final HttpSession session;

    @Autowired
    public TypeServiceImpl(UserTypeMapper userTypeMapper, TypeMapper typeMapper, HttpSession session, RoleService roleService) {
        this.userTypeMapper = userTypeMapper;
        this.typeMapper = typeMapper;
        this.session = session;
        this.roleService = roleService;
    }

    @Override
    public Map<String, Object> getQuestion(int limit, int page) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("code", 0);
        map.put("msg", "");

        if (page < 1) {
            page = 1;
        }

        Page<QueryTypeVo> pageParam = new Page<>(page, limit);
        List<QueryTypeVo> queryTypeVos = typeMapper.selectTypes(pageParam);

        map.put("count", pageParam.getTotal());
        map.put("data", queryTypeVos);
        return map;
    }

    @Override
    public R addType(Type type) {
        if (StringUtils.isNullOrEmpty(type.getName())) {
            return ReturnConstants.PARAMS_EMPTY;
        }
        if (this.save(type)) {
            return ReturnConstants.SUCCESS;
        } else {
            return ReturnConstants.FAILURE;
        }
    }

    @Override
    public R updateType(Type type) {
        if (StringUtils.isNullOrEmpty(type.getName())) {
            return ReturnConstants.PARAMS_EMPTY;
        }
        UpdateWrapper<Type> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", type.getId());
        if (this.update(type, updateWrapper)) {
            return ReturnConstants.SUCCESS;
        } else {
            return ReturnConstants.FAILURE;
        }
    }

    @Override
    @Transactional
    public R deleteType(Long id) {
        userTypeMapper.delete(new QueryWrapper<UserType>().eq("type_id",id));

        if (this.removeById(id)) {
            return ReturnConstants.SUCCESS;
        } else {
            return ReturnConstants.FAILURE;
        }
    }

    @Override
    public List<TypeVo> getQuestionWithUserId(Long userId) {
        List<TypeVo> res = new ArrayList<>();

        //查询所有的隐患类型
        List<Type> typeList = this.list(null);

        //查询userId下绑定的隐患类型
        List<UserType> ownTypeList = userTypeMapper.selectList(new QueryWrapper<UserType>().eq("user_id", userId));

        //取出已绑定的隐患类型的id
        List<Long> checkedTypes = ownTypeList.stream().map(UserType::getTypeId).collect(Collectors.toList());

        for (Type type : typeList) {
            TypeVo typeVo = new TypeVo();
            typeVo.setId(type.getId());
            typeVo.setName(type.getName());
            typeVo.setChecked(checkedTypes.contains(type.getId()));
            res.add(typeVo);
        }

        return res;
    }

    @Override
    public List<TypeVo> getQuestionFilterByUser() {
        User user = (User) session.getAttribute(Constants.LOGIN_USER_KEY);
        UserRole role = roleService.getById(user.getRole());
        if(role.getQuestion() == 0){
            return typeMapper.selectAll();
        }
        return typeMapper.selectTypesWithUser(user.getId());
    }
}
