package com.tProgram.epm.mbg.mapper;

import com.tProgram.epm.mbg.model.ProvinceDetail;
import com.tProgram.epm.mbg.model.ProvinceDetailExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProvinceDetailMapper {
    long countByExample(ProvinceDetailExample example);

    int deleteByExample(ProvinceDetailExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ProvinceDetail record);

    int insertSelective(ProvinceDetail record);

    List<ProvinceDetail> selectByExample(ProvinceDetailExample example);

    ProvinceDetail selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ProvinceDetail record, @Param("example") ProvinceDetailExample example);

    int updateByExample(@Param("record") ProvinceDetail record, @Param("example") ProvinceDetailExample example);

    int updateByPrimaryKeySelective(ProvinceDetail record);

    int updateByPrimaryKey(ProvinceDetail record);
}