package com.deepexi.bury.point.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.deepexi.bury.point.domain.dto.BpcBuryPointDto;
import com.deepexi.bury.point.domain.eo.BpcBuryPoint;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BpcBuryPointMapper extends BaseMapper<BpcBuryPoint> {

    List<BpcBuryPoint> findList(@Param("eo") BpcBuryPointDto eo);

    int deleteByIds(@Param("pks") String... pks);

    int batchInsert(@Param("eo") List<BpcBuryPointDto> eo);

    int batchUpdate(@Param("eo") List<BpcBuryPointDto> eo);
}
