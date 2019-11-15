package com.deepexi.bury.point.service;

import com.deepexi.bury.point.domain.dto.BpcBuryPointDto;
import com.deepexi.bury.point.domain.eo.BpcBuryPoint;
import com.deepexi.util.pageHelper.PageBean;

import java.util.List;

public interface BpcBuryPointService {

    PageBean<BpcBuryPoint> findPage(BpcBuryPointDto eo, Integer page, Integer size);

    List<BpcBuryPoint> findAll(BpcBuryPointDto eo);

    BpcBuryPoint detail(String pk);

    Boolean update(String pk, BpcBuryPointDto eo);

    /**
     * 插入埋点数据
     *
     * @param eo
     * @return
     */
    Boolean create(BpcBuryPointDto eo);

    Boolean delete(String... pk);
}