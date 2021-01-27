package com.tProgram.epm.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * MyBatis配置类
 * Created by macro on 2019/4/8.
 */
@Configuration
@MapperScan({"com.tProgram.epm.mbg.mapper","com.tProgram.epm.dao"})
public class MyBatisConfig {
}
