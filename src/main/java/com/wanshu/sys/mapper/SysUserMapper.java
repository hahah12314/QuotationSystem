package com.wanshu.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.wanshu.sys.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 系统用户 Mapper 接口
 * </p>
 *
 * @author hehe
 * @since 2024-10-08
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {


    void deleteRoleByUserId(Long userId);

    void insertRoleByUserId(@Param("roleList") List<Long> roleList, @Param("userId") Long userId);

    Long getUserIdByUserName(String username);
}
