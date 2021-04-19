package com.hkb.hdms.mapper;

import com.hkb.hdms.model.pojo.Problem;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author huangkebing
 * 2021/04/11
 */
@Repository
public interface TaskMapper {
    List<Problem> getTodoInstances(@Param("email") String email, @Param("groups") List<String> groups, int limit , int offset);

    Long getTodoCount(@Param("email") String email, @Param("groups") List<String> groups);

    List<Problem> getHistoryInstances(@Param("email") String email, @Param("groups") List<String> groups, int limit , int offset,String name, String priority, String begin, String end);

    Long getHistoryCount(@Param("email") String email, @Param("groups") List<String> groups, String name, String priority, String begin, String end);

    List<Problem> getSolveingInstances(@Param("email") String email, @Param("groups") List<String> groups, int limit , int offset);

    Long getSolveingCount(@Param("email") String email, @Param("groups") List<String> groups);

    void deleteHiUsers(String taskId, String instanceId);

    void deleteRuUsers(String taskId, String instanceId);
}
