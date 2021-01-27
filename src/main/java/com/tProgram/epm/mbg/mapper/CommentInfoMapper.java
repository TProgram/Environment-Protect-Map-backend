package com.tProgram.epm.mbg.mapper;

import com.tProgram.epm.mbg.model.CommentInfo;
import com.tProgram.epm.mbg.model.CommentInfoExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CommentInfoMapper {
    long countByExample(CommentInfoExample example);

    int deleteByExample(CommentInfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CommentInfo record);

    int insertSelective(CommentInfo record);

    List<CommentInfo> selectByExampleWithBLOBs(CommentInfoExample example);

    List<CommentInfo> selectByExample(CommentInfoExample example);

    CommentInfo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CommentInfo record, @Param("example") CommentInfoExample example);

    int updateByExampleWithBLOBs(@Param("record") CommentInfo record, @Param("example") CommentInfoExample example);

    int updateByExample(@Param("record") CommentInfo record, @Param("example") CommentInfoExample example);

    int updateByPrimaryKeySelective(CommentInfo record);

    int updateByPrimaryKeyWithBLOBs(CommentInfo record);

    int updateByPrimaryKey(CommentInfo record);
}