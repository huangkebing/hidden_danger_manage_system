package com.hkb.hdms.mapper;

import com.hkb.hdms.model.dto.InstanceDto;
import org.activiti.engine.history.HistoricProcessInstance;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author huangkebing
 * 2021/04/11
 */
@Repository
public interface TaskMapper {
    List<String> getTodoInstances(@Param("email") String email, @Param("groups") List<String> groups, int limit , int offset);

    Long getTodoCount(@Param("email") String email, @Param("groups") List<String> groups);

    List<InstanceDto> getHistoryInstances(@Param("email") String email, @Param("groups") List<String> groups, int limit , int offset, String begin, String end);

    Long getHistoryCount(@Param("email") String email, @Param("groups") List<String> groups, String begin, String end);

    List<InstanceDto> getSolveingInstances(@Param("email") String email, @Param("groups") List<String> groups, int limit , int offset);

    Long getSolveingCount(@Param("email") String email, @Param("groups") List<String> groups);
}
