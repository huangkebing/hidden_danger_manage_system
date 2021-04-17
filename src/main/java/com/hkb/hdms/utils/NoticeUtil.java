package com.hkb.hdms.utils;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.hkb.hdms.base.Constants;
import com.hkb.hdms.base.MailConstants;
import com.hkb.hdms.mapper.ProblemInfoMapper;
import com.hkb.hdms.mapper.ProblemMapper;
import com.hkb.hdms.mapper.ProblemObserverMapper;
import com.hkb.hdms.model.pojo.Problem;
import com.hkb.hdms.model.pojo.ProblemInfo;
import com.hkb.hdms.model.pojo.ProblemObserver;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 通知模块相关的实现
 *
 * @author huangkebing
 * 2021/04/16
 */
@Component
public class NoticeUtil {

    private final ProblemMapper problemMapper;

    private final RedisTemplate<String,Object> redisTemplate;

    private final ProblemInfoMapper problemInfoMapper;

    private final ProblemObserverMapper problemObserverMapper;

    private final NoticeMailSender mailSender;

    @Autowired
    public NoticeUtil(ProblemMapper problemMapper, RedisTemplate<String, Object> redisTemplate, ProblemInfoMapper problemInfoMapper, ProblemObserverMapper problemObserverMapper, NoticeMailSender mailSender) {
        this.problemMapper = problemMapper;
        this.redisTemplate = redisTemplate;
        this.problemInfoMapper = problemInfoMapper;
        this.problemObserverMapper = problemObserverMapper;
        this.mailSender = mailSender;
    }

    public void updateProblemModify(Long problemId){
        Problem problem = new Problem();
        problem.setModify(new Date());
        problemMapper.update(problem, new UpdateWrapper<Problem>().eq("id",problemId));
    }

    public void insertRedis(Map<String, Object> map){
        redisTemplate.opsForZSet().add(Constants.REDIS_KEY,JSON.toJSONString(map),System.currentTimeMillis());
    }

    public void insertRemark(ProblemInfo problemInfo){
        problemInfoMapper.insert(problemInfo);
    }

    public void insertObserve(ProblemObserver problemObserver){
        ProblemObserver observer = problemObserverMapper.selectOne(new QueryWrapper<ProblemObserver>()
                .eq("problem_id", problemObserver.getProblemId())
                .eq("user_id", problemObserver.getUserId()));

        if(ObjectUtils.isEmpty(observer)){
            problemObserverMapper.insert(problemObserver);
        }
    }

    public void noticeMail(Long problemId, String message){
        List<ProblemObserver> observers = problemObserverMapper.selectList(new QueryWrapper<ProblemObserver>().eq("problem_id", problemId));
        String[] emailArray = (String[]) observers.stream().map(ProblemObserver::getEmail).toArray();
        mailSender.sendMail(MailConstants.NOTICE, message, emailArray);
    }
}
