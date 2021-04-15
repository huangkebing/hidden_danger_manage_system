package com.hkb.hdms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hkb.hdms.base.R;
import com.hkb.hdms.model.pojo.Problem;
import com.hkb.hdms.model.pojo.ProblemInfo;
import com.hkb.hdms.model.pojo.ProcessVariable;

import java.util.List;
import java.util.Map;

/**
 * @author huangkebing
 * 2021/04/05
 */
public interface TaskService extends IService<Problem> {
    R createTask(Problem problem, Map<String, Object> processVariables);

    Map<String,Object> getMyTask(int page, int limit);

    R getDetailTask(Long problemId);

    R completeTask(String taskId, Map<String,Object> processVariables);

    List<ProcessVariable> getBeginVariable(Long typeId);

    Map<String,Object> getHistoryTask(String begin, String end, int page, int limit);

    Map<String,Object> getSolveingTask(int page, int limit);

    R updateProblem(Problem problem);

    Map<String, Object> getRemarks(Long problemId, int page, int limit);

    R addRemarks(Long problemId, String remark);

    R updateRemarks(ProblemInfo info);

    R deleteRemarks(Long infoId);
}
