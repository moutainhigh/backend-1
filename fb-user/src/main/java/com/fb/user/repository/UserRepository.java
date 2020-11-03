package com.fb.user.repository;

import com.baomidou.mybatisplus.extension.service.additional.query.impl.LambdaQueryChainWrapper;
import com.fb.user.dao.UserDAO;
import com.fb.user.domain.AbstractUser;
import com.fb.user.domain.CommonUser;
import com.fb.user.domain.MerchantUser;
import com.fb.user.enums.UserTypeEnum;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * @author: pangminpeng
 * @create: 2020-06-25 17:55
 */
@Service
public class UserRepository {

    @Resource
    private UserDAO userDAO;

    @Resource
    private HobbyTagRepository hobbyTagRepository;

    /**
     * 更新，删除
     * @param user
     * @return
     */
    public UserPO save(UserPO user) {
        if (Objects.nonNull(user.getId())) {
            userDAO.updateById(user);
        } else {
            userDAO.insert(user);
        }
        return user;
    }

    /**
     * 通过条件获取单个user
     * @return
     */
    public UserPO getOneUserById(Long id) {
        return new LambdaQueryChainWrapper<UserPO>(userDAO).eq(UserPO::getId, id).one();
    }


    public UserPO getOneUserByPhoneNumber(String phoneNumber) {
        return new LambdaQueryChainWrapper<>(userDAO).eq(UserPO::getPhoneNumber, phoneNumber).one();
    }


    public List<AbstractUser> listAbstractUser() {
        return null;
    }
}
