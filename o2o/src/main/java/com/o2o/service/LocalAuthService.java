package com.o2o.service;

import com.o2o.dto.LocalAuthExecution;
import com.o2o.entity.LocalAuth;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

public interface LocalAuthService {

    /**
     * 通过密码和账号获取平台账号信息
     * @param userName
     * @return
     */
    LocalAuth getLocalAuthByUserNameAndPwd(String userName, String password);

    /**
     * 通过userID获取平台账号信息
     * @param userId
     * @return
     */
    LocalAuth getLocalAuthByUserId(long userId);

    /**
     * 绑定微信，生成平台专属的账号
     * @param localAuth
     * @param profileImg
     * @return
     * @throws RuntimeException
     */
    LocalAuthExecution register(LocalAuth localAuth,
                                 CommonsMultipartFile profileImg) throws RuntimeException;

    /**
     * @param localAuth
     * @return
     * @throws RuntimeException
     */
    LocalAuthExecution bindLocalAuth(LocalAuth localAuth)
            throws RuntimeException;

    /**
     * 修改平台账号的登录密码
     * @param userName
     * @param password
     * @param newPassword
     * @return
     */
    LocalAuthExecution modifyLocalAuth(Long userId, String userName,
                                        String password, String newPassword);
}
