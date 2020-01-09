package com.o2o.dao;

import com.o2o.BaseTest;
import com.o2o.entity.LocalAuth;
import com.o2o.entity.PersonInfo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class LocalAuthDaoTest extends BaseTest {

    @Autowired
    private LocalAuthDao localAuthDao;

    public static final String username = "testusername";
    public static final String password = "testpassword";

    @Test
    public void testAInsertLocalAuth() throws Exception{
        // 新增一条平台账户信息
        LocalAuth localAuth = new LocalAuth();
        PersonInfo personInfo = new PersonInfo();
        personInfo.setUserId(1L);
        // 给平台账号绑定用户信息
        localAuth.setPersonInfo(personInfo);
        localAuth.setUserId(1L);
        // 设置用户名和密码
        localAuth.setUsername(username);
        localAuth.setPassword(password);
        localAuth.setCreateTime(new Date());
        int effectNum = localAuthDao.insertLocalAuth(localAuth);
        assertEquals(1, effectNum);
    }

    @Test
    public void testQueryLocalByUsernameAndPwd() throws Exception{
        // 按照账号和密码查询用户信息
        LocalAuth localAuth = localAuthDao.queryLocalByUserNameAndPwd(username, password);
        assertEquals("测试", localAuth.getPersonInfo().getName());
    }

    @Test
    public void testCQueryLocalByUserId() throws Exception {
        // 按照用户ID查询平台账号，获取用户信息
        LocalAuth localAuth = localAuthDao.queryLocalByUserId(1L);
        assertEquals("测试", localAuth.getPersonInfo().getName());
    }

    @Test
    public void testDUpdateLocalAuth() throws Exception {
        // 根据用户ID，平台账号，及旧密码修改平台账号密码
        Date now = new Date();
        int effectNum = localAuthDao.updateLocalAuth(1L, username, password, password + "new", now);
        assertEquals(1, effectNum);
        // 查询该账号的最新信息
        LocalAuth localAuth = localAuthDao.queryLocalByUserId(1L);
        // 输出新密码
        System.out.println(localAuth.getPassword());
    }
}
