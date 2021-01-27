package com.tProgram.epm.service;

import com.tProgram.epm.dto.ProvinceCityListResult;

/*
省份逻辑层
 */
public interface ProvinceDetailService {
    //返回相应地区讨论数据
    ProvinceCityListResult city_list(Integer provinceId);
}
