package com.hkb.hdms.utils;

import com.hkb.hdms.base.MailConstants;
import com.hkb.hdms.mapper.TaskMapper;
import com.hkb.hdms.mapper.UserMapper;
import com.hkb.hdms.model.pojo.Problem;
import com.hkb.hdms.model.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.activiti.api.runtime.shared.identity.UserGroupManager;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
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

    @Autowired
    public CronUtil(UserMapper userMapper, TaskMapper taskMapper, UserGroupManager userGroupManager, CronMailSender mailSender) {
        this.userMapper = userMapper;
        this.taskMapper = taskMapper;
        this.userGroupManager = userGroupManager;
        this.mailSender = mailSender;
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
                mailSender.sendMail(MailConstants.CRON, problems, user, user.getEmail());
            }
        }
        log.info(LocalDate.now() + "每日提醒完成，处理了" + users.size() + "个用户");
    }
}
