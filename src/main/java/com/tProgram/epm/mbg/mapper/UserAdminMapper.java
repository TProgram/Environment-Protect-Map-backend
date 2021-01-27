package com.tProgram.epm.mbg.mapper;

import com.tProgram.epm.mbg.model.UserAdmin;
import com.tProgram.epm.mbg.model.UserAdminExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserAdminMapper {
    long countByExample(UserAdminExample example);

    int deleteByExample(UserAdminExample example);

    int deleteByPrimaryKey(Long id);

    int insert(UserAdmin record);

    int insertSelective(UserAdmin record);

    List<UserAdmin> selectByExample(UserAdminExample example);

    UserAdmin selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") UserAdmin record, @Param("example") UserAdminExample example);

    int updateByExample(@Param("record") UserAdmin record, @Param("example") UserAdminExample example);

    int updateByPrimaryKeySelective(UserAdmin record);

    int updateByPrimaryKey(UserAdmin record);
}