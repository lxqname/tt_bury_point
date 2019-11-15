package com.deepexi.bury.point.domain.eo;


import com.deepexi.common.domain.eo.SuperEntity;

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


    public void setContent(String content){
        this.content = content;
    }

    public String getContent(){
        return this.content;
    }





}

