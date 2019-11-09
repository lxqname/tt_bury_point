package com.deepexi.bury.point.domain.eo;


import com.deepexi.util.mapper.SuperEntity;

import java.io.Serializable;
//import io.swagger.annotations.ApiModel;
//import io.swagger.annotations.ApiModelProperty;


/**
 * @desc bpc_bury_point
 * @author admin
 */
//@ApiModel(description = "埋点记录")
public class BpcBuryPoint extends SuperEntity implements Serializable{

    //@ApiModelProperty(value = "埋点内容")
    private String content;
    //@ApiModelProperty(value = "租户")
    private String tenantCode;
    //@ApiModelProperty(value = "版本记录,修改时自增")
    private Integer  version;
    //@ApiModelProperty(value = "删除标志 1=已删除, 0=未删除")

    public void setContent(String content){
        this.content = content;
    }

    public String getContent(){
        return this.content;
    }
    public void setTenantCode(String tenantCode){
        this.tenantCode = tenantCode;
    }

    public String getTenantCode(){
        return this.tenantCode;
    }
    public void setVersion(Integer  version){
        this.version = version;
    }

    public Integer  getVersion(){
        return this.version;
    }




}

