package com.fb.activty.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.fb.activty.dao.DeptDao;
import com.fb.activty.entity.Dept;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.parser.Entity;
import java.util.List;

@Service
public class DeptService {
    @Autowired
    private DeptDao deptDao;

    public void insert() {
        Dept dept = new Dept();
        dept.setName("pangminpeng1");
        deptDao.insert(dept);
    }
    public List<Dept> selectByPage() {
        EntityWrapper<Dept> query = new EntityWrapper<Dept>();
        query.orderBy("id");

        return deptDao.selectPage(new Page<Dept>(2, 2), query);
    }


}
