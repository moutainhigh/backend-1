package com.fb.user.repository;

import com.baomidou.mybatisplus.extension.service.additional.query.impl.LambdaQueryChainWrapper;
import com.fb.user.dao.UserDAO;
import com.fb.user.domain.AbstractUser;
import com.fb.user.domain.CommonUser;
import com.fb.user.domain.MerchantUser;
import com.fb.user.enums.UserTypeEnum;
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
    public AbstractUser save(AbstractUser user) {
        UserPO userPO = user.convert2PO();
        if (Objects.nonNull(userPO.getId())) {
            userDAO.updateById(userPO);
        } else {
            userDAO.insert(userPO);
        }
        user.setUid(userPO.getId());
        return user;
    }

    /**
     * 通过条件获取单个user
     * @return
     */
    public AbstractUser getOneUserById(Long id) {
        UserPO userPO = new LambdaQueryChainWrapper<UserPO>(userDAO).eq(UserPO::getId, id).one();
        return getAbstractUser(userPO);
    }

    private AbstractUser getAbstractUser(UserPO userPO) {
        if (Objects.isNull(userPO)) return null;
        if (UserTypeEnum.COMMON_USER.getCode() == userPO.getUserType()) {
            return new CommonUser(userPO);
        }
        if (UserTypeEnum.MERCHANT_USER.getCode() == userPO.getUserType()) {
            return new MerchantUser(userPO);
        }
        return null;
    }

    public AbstractUser getOneUserByPhoneNumber(String phoneNumber) {
        UserPO userPO = new LambdaQueryChainWrapper<>(userDAO).eq(UserPO::getPhoneNumber, phoneNumber).one();
        return getAbstractUser(userPO);
    }


    public List<AbstractUser> listAbstractUser() {
        return null;
    }
}
