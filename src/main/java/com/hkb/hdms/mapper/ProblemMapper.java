package com.hkb.hdms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hkb.hdms.model.dto.IndexDto;
import com.hkb.hdms.model.dto.ProblemDto;
import com.hkb.hdms.model.pojo.Problem;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author huangkebing
 * 2021/04/04
 */
@Repository
public interface ProblemMapper extends BaseMapper<Problem> {
    ProblemDto selectDetailById(Long problemId);

    List<IndexDto> countByType();

    List<IndexDto> countByTypeAndWeekDay();
}
