package com.o2o.service.impl;

import com.o2o.dao.LocalAuthDao;
import com.o2o.dto.LocalAuthExecution;
import com.o2o.entity.LocalAuth;
import com.o2o.enums.LocalAuthStateEnum;
import com.o2o.service.LocalAuthService;
import com.o2o.util.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.util.Date;

@Service
public class LocalAuthServiceImpl implements LocalAuthService {

    @Autowired
    private LocalAuthDao localAuthDao;

    @Override
    public LocalAuth getLocalAuthByUserNameAndPwd(String userName, String password) {
        return localAuthDao.queryLocalByUserNameAndPwd(userName, MD5.getMd5(password));
    }

    @Override
    public LocalAuth getLocalAuthByUserId(long userId) {
        return localAuthDao.queryLocalByUserId(userId);
    }

    @Override
    @Transactional
    public LocalAuthExecution register(LocalAuth localAuth, CommonsMultipartFile profileImg) throws RuntimeException {
        return null;
    }

    @Override
    @Transactional
    public LocalAuthExecution bindLocalAuth(LocalAuth localAuth) throws RuntimeException {
        // 空值判断，传入的LocalAuth账号密码，用户信息特别是userID不能为空，否则直接返回错误
        if (localAuth == null || localAuth.getPassword() == null || localAuth.getUsername() == null
                || localAuth.getPersonInfo().getUserId() == null) {
            return new LocalAuthExecution(LocalAuthStateEnum.NULL_AUTH_INFO);
        }
        // 查询此用户是否已绑定平台账号
        LocalAuth tempAuth = localAuthDao.queryLocalByUserId(localAuth.getPersonInfo().getUserId());
        if (tempAuth != null) {
            // 如果绑定过则直接退出，以保证平台账号的唯一性
            return new LocalAuthExecution(LocalAuthStateEnum.ONLY_ONE_ACCOUNT);
        }
        try {
            // 如果没有绑定过，则创建一个平台账户与该用户绑定
            localAuth.setCreateTime(new Date());
            localAuth.setLastEditTime(new Date());
            // 对密码进行MD5加密
            localAuth.setPassword(MD5.getMd5(localAuth.getPassword()));
            int effectedNum = localAuthDao.insertLocalAuth(localAuth);
            // 判断是否创建成功
            if (effectedNum <= 0) {
                throw new RuntimeException("帐号绑定失败");
            } else {
                return new LocalAuthExecution(LocalAuthStateEnum.SUCCESS, localAuth);
            }
        } catch (Exception e) {
            throw new RuntimeException("insertLocalAuth error: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public LocalAuthExecution modifyLocalAuth(Long userId, String userName, String password, String newPassword) {
        // 非空判断，判断传入的用户ID，新旧密码是否为空，新旧密码是否相同，若不满足条件则返回错误信息
        if (userId != null && userName != null && password != null
                && newPassword != null && !password.equals(newPassword)) {
            try {
                // 更新密码，并对新密码进行MD5加密
                int effectedNum = localAuthDao.updateLocalAuth(userId, userName, MD5.getMd5(password),
                        MD5.getMd5(newPassword), new Date());
                if (effectedNum <= 0) {
                    throw new RuntimeException("更新密码失败");
                }
                return new LocalAuthExecution(LocalAuthStateEnum.SUCCESS);
            } catch (Exception e) {
                throw new RuntimeException("更新密码失败:" + e.toString());
            }
        } else {
            return new LocalAuthExecution(LocalAuthStateEnum.NULL_AUTH_INFO);
        }
    }
}
