package com.deepexi.bury.point.service;

import com.deepexi.bury.point.domain.dto.BuryPointMessageDto;
import com.deepexi.bury.point.domain.eo.BuryPointMessage;
import com.deepexi.util.pageHelper.PageBean;

import java.util.List;

public interface BuryPointMessageService {

    PageBean<BuryPointMessage> findPage(BuryPointMessageDto eo, Integer page, Integer size);

    List<BuryPointMessage> findAll(BuryPointMessageDto eo);

    BuryPointMessage detail(String pk);

    Boolean update(String pk, BuryPointMessageDto eo);

    Boolean create(BuryPointMessageDto eo);

    Boolean delete(String... pk);
}