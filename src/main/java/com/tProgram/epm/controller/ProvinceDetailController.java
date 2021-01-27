package com.tProgram.epm.controller;

import com.tProgram.epm.common.api.CommonResult;
import com.tProgram.epm.service.ProvinceDetailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Api(tags = "ProvinceDetailController", description = "省份相关")
@RequestMapping("/province")
public class ProvinceDetailController {

    @Autowired
    public ProvinceDetailService provinceDetailService;

    @ApiOperation("返回省里对应的市区")
    @RequestMapping(value = "/city_list", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult city_list(
            @RequestParam(value = "provinceId", defaultValue = "0") @ApiParam("省份id") Integer provinceId) {
        return CommonResult.success(provinceDetailService.city_list(provinceId));
    }

}
