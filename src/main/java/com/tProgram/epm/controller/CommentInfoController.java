package com.tProgram.epm.controller;

import com.tProgram.epm.common.api.CommonResult;
import com.tProgram.epm.dto.CommentInfoAddParam;
import com.tProgram.epm.service.CommentInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/*
* 评论相关
* */
@Controller
@Api(tags = "CommentInfoController", description = "评论相关")
@RequestMapping("/comment")
public class CommentInfoController {

    @Autowired
    public CommentInfoService commentInfoService;

    @ApiOperation("返回地图上相应的 city的讨论数据")
    @RequestMapping(value = "/all_list", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult all_list(
            @RequestParam(value = "cityId", defaultValue = "0") @ApiParam("城市id") Integer cityId) {
        return CommonResult.success(commentInfoService.all_list(cityId));
    }

    @ApiOperation("添加评论")
    @RequestMapping(value = "/add_comment", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult add_comment(@RequestBody CommentInfoAddParam commentInfo) {
        commentInfoService.add_comment(commentInfo);
        return CommonResult.success(null);
    }

    @ApiOperation("评论详情")
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult detail(
            @RequestParam(value = "commentId", defaultValue = "0") @ApiParam("评论id")  Integer commentId) {
        return CommonResult.success(commentInfoService.detail(commentId));
    }

    @ApiOperation("返回地图上的讨论数据")
    @RequestMapping(value = "/latest", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult latest() {
        return CommonResult.success(commentInfoService.latest());
    }

    @ApiOperation("收藏的帖子list")
    @RequestMapping(value = "/collect_list", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult collect_list() {
        return CommonResult.success(commentInfoService.collect_list());
    }

    @ApiOperation("收藏帖子的动作")
    @RequestMapping(value = "/collect", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult collect(
            @RequestParam(value = "commentId", defaultValue = "0") @ApiParam("评论id") Integer commentId,
            @RequestParam(value = "isCollect", defaultValue = "false") @ApiParam("是否收藏") boolean isCollect
                            ) {
        commentInfoService.collect(commentId,isCollect);
        return CommonResult.success(null);
    }
}
