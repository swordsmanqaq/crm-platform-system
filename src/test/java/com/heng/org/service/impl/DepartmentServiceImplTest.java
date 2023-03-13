package com.heng.org.service.impl;

import com.heng.BaseTest;
import com.heng.org.domain.Department;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * @author shkstart
 * @create 2023-03-01 19:02
 */

public class DepartmentServiceImplTest extends BaseTest {

    @Autowired
    private DepartmentServiceImpl departmentService;
    @Test
    public void insert() {
    }

    @Test
    public void update() {
    }

    @Test
    public void remove() {
    }

    @Test
    public void loadById() {
        System.out.println(departmentService.loadById(2));
    }

    @Test
    public void loadAll() {
    }
}