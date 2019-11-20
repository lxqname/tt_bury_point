package com.deepexi.bury.point.domain.eo;

import com.deepexi.common.domain.eo.SuperEntity;

import java.io.Serializable;
import java.util.Date;
//import io.swagger.annotations.ApiModel;
//import io.swagger.annotations.ApiModelProperty;


/**
 * @author admin
 * @desc bury_point_message
 */
//@ApiModel(description = "用户表")
public class BuryPointMessage extends SuperEntity implements Serializable {

    //@ApiModelProperty(value = "环境")
    private String env;
    //@ApiModelProperty(value = "时间")
    private Date ctime;
    //@ApiModelProperty(value = "动作类型")
    private String type;
    //@ApiModelProperty(value = "具体数据")
    private String event;
    //@ApiModelProperty(value = "租户")


    public void setEnv(String env) {
        this.env = env;
    }

    public String getEnv() {
        return this.env;
    }

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getEvent() {
        return this.event;
    }

}

