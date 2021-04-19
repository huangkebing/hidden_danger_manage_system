package com.hkb.hdms.utils;

import com.hkb.hdms.base.Constants;
import com.hkb.hdms.base.MailConstants;
import com.hkb.hdms.mapper.TaskMapper;
import com.hkb.hdms.mapper.UserMapper;
import com.hkb.hdms.model.pojo.Problem;
import com.hkb.hdms.model.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.activiti.api.runtime.shared.identity.UserGroupManager;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

/**
 * 每天8点检查所有用户的待处理任务，发送邮件提醒
 *
 * @author huangkebing
 * 2021/04/15
 */
@Component
@Slf4j
public class CronUtil {

    private final UserMapper userMapper;
    private final TaskMapper taskMapper;
    private final CronMailSender mailSender;
    private final UserGroupManager userGroupManager;
    private final RedisTemplate<String,Object> redisTemplate;

    @Autowired
    public CronUtil(UserMapper userMapper, TaskMapper taskMapper, UserGroupManager userGroupManager, CronMailSender mailSender, RedisTemplate<String, Object> redisTemplate) {
        this.userMapper = userMapper;
        this.taskMapper = taskMapper;
        this.userGroupManager = userGroupManager;
        this.mailSender = mailSender;
        this.redisTemplate = redisTemplate;
    }

    /**
     * 本来打算用线程池，多线程来处理数据，然后一直报莫名其妙的错，先单线程写一下算了
     */
    @Scheduled(cron="0 30 7 * * ?")
    public void CronJobMethod() {
        List<User> users = userMapper.selectList(null);
        for (User user : users) {
            List<String> groups = userGroupManager.getUserGroups(user.getEmail());
            List<Problem> problems = taskMapper.getTodoInstances(user.getEmail(), groups, Integer.MAX_VALUE, 0);
            if (ObjectUtils.isNotEmpty(problems)) {
                mailSender.sendMail(problems, user, user.getEmail());
            }
        }
        log.info(LocalDate.now() + "每日提醒完成，处理了" + users.size() + "个用户");
    }

    @Scheduled(cron = "0 0 1 * * ? ")
    public void delRedis(){
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime delDay = now.plusDays(-3);
        long beginTime = delDay.withHour(0).withMinute(0).withSecond(0).withNano(0).toInstant(ZoneOffset.ofHours(8)).toEpochMilli();
        long endTime = delDay.withHour(23).withMinute(59).withSecond(59).withNano(999999999).toInstant(ZoneOffset.ofHours(8)).toEpochMilli();
        redisTemplate.opsForZSet().removeRangeByScore(Constants.REDIS_KEY, beginTime, endTime);
    }
}
