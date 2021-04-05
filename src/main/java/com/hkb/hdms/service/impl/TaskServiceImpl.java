package com.hkb.hdms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hkb.hdms.base.R;
import com.hkb.hdms.mapper.ProblemMapper;
import com.hkb.hdms.model.pojo.Problem;
import com.hkb.hdms.service.TaskService;
import org.springframework.stereotype.Service;

/**
 * @author huangkebing
 * 2021/04/05
 */
@Service
public class TaskServiceImpl extends ServiceImpl<ProblemMapper, Problem> implements TaskService {
    @Override
    public R createTask(Problem problem) {
        return null;
    }
}
