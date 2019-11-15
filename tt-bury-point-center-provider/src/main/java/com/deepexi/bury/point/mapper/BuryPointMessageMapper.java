package com.deepexi.bury.point.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deepexi.bury.point.domain.dto.BuryPointMessageDto;
import com.deepexi.bury.point.domain.eo.BuryPointMessage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BuryPointMessageMapper extends BaseMapper<BuryPointMessage> {

    List<BuryPointMessage> findList(@Param("eo") BuryPointMessageDto eo);

    int deleteByIds(@Param("pks") String... pks);

    int batchInsert(@Param("eo") List<BuryPointMessageDto> eo);

    int batchUpdate(@Param("eo") List<BuryPointMessageDto> eo);

    int realDelete(@Param("pk") String id);
}
