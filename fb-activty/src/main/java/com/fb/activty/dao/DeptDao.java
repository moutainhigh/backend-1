package com.fb.activty.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.fb.activty.entity.Dept;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
public interface DeptDao extends BaseMapper<Dept> {

}
