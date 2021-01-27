package com.tProgram.epm.dto;

import com.tProgram.epm.mbg.model.ProvinceDetail;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ProvinceCityItem {
//    @ApiModelProperty(value = "省份ID")
//    private Integer provinceId;
//
//    @ApiModelProperty(value = "省份名字")
//    private String provinceName;

    @ApiModelProperty(value = "市区ID")
    private Integer cityId;

    @ApiModelProperty(value = "市区名字")
    private String cityName;

    public ProvinceCityItem(ProvinceDetail provinceDetail){
        this.cityId = provinceDetail.getId();
        this.cityName = provinceDetail.getCityName();
    }
}
