package com.fb.user.dao;

import com.fb.user.BaseTest;
import com.fb.user.domain.CommonUser;
import com.fb.user.repository.UserPO;
import org.junit.Test;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDate;

public class UserDaoTest extends BaseTest {

    @Resource
    private UserDAO userDao;

    @Test
    public void testInsertUser() {
//        CommonUser user = new CommonUser("民朋", "13520053770",
//                new BigDecimal("12.22"), new BigDecimal("12.33"),
//                "1", "1", LocalDate.now(), (byte)1, "df");
//        UserPO userPO =  user.convert2PO();
        System.out.println(userDao.selectById(1));
        System.out.println(userDao.selectById(1));
    }


}
