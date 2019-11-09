/**
 * EventConstant  2019/3/29
 * <p>
 * Copyright (c) 2018, DEEPEXI Inc. All rights reserved.
 * DEEPEXI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.deepexi.bury.point.constant;

/**
 * @description: 事件常量
 * @program: dubbo-demo
 * @author: donh
 * @mail: hudong@deepexi.com
 * @date: 2019/3/29 下午2:17
 **/
public interface EventConstant {

    /**
     * log
     */
    String EVENT_LOG = "log";

    /**
     * request
     */
    String EVENT_REQUEST = "request";

    /**
     * 事件名称最大字符
     */
    Integer EVENT_NAME_SIZE = 20;
    /**
     * 负责人最大字符
     */
    Integer RESPONSIBLE_PERSON = 6;
    /**
     * 事件标志（会员生日）
     */
    Integer EVENT_MEMBER_SIGN=1;
    /**
     * 事件其他标志
     */
    Integer EVENT_OTHER_SIGN=2;
    /**
     * 事件未开始状态
     */
    Integer NOT_START_STATUS=1;
    /**
     * 事件进行中状态
     */
    Integer RUN_STATUS=2;
    /**
     * 事件已结束状态
     */
    Integer END_STATUS=3;
    /**
     * 用于计算
     */
    Integer ADD_CONSTANT_CIRCULATE=1;
    /**
     * 用于计算
     */
    Integer DELETE_CONSTANT_CIRCULATE=-1;
    /**
     * 设置时间的时
     */
    Integer TIME_CONSTANT_HOUR=0;
    /**
     * 设置时间的分和秒
     */
    Integer TIME_CONSTANT=0;


}