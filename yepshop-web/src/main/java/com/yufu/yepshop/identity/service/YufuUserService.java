package com.yufu.yepshop.identity.service;

/**
 * @author wang
 * @date 2022/1/5 23:08
 */
public interface YufuUserService {
    void updateAccessFailedCountOrLock(String userName);

    void resetAccessFailedCount(String userName);
}
