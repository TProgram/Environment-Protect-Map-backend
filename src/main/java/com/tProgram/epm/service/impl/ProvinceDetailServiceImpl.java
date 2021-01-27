package com.tProgram.epm.service.impl;

import com.tProgram.epm.dto.ProvinceCityItem;
import com.tProgram.epm.dto.ProvinceCityListResult;
import com.tProgram.epm.mbg.mapper.ProvinceDetailMapper;
import com.tProgram.epm.mbg.model.ProvinceDetail;
import com.tProgram.epm.mbg.model.ProvinceDetailExample;
import com.tProgram.epm.service.ProvinceDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProvinceDetailServiceImpl implements ProvinceDetailService {
    @Autowired
    private ProvinceDetailMapper provinceDetailMapper;

    @Override
    public ProvinceCityListResult city_list(Integer provinceId) {
        ProvinceDetailExample example = new ProvinceDetailExample();
        example.createCriteria().andProvinceIdEqualTo(provinceId);

        List<ProvinceDetail> list =provinceDetailMapper.selectByExample(example);

        ProvinceCityListResult listResult = new ProvinceCityListResult();
        for (ProvinceDetail provinceDetail : list){
            listResult.getProvinceCityItemList().add(new ProvinceCityItem(provinceDetail));
        }
        return listResult;
    }

}
