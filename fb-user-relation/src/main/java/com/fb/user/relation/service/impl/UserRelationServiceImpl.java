package com.fb.user.relation.service.impl;

import com.baidu.hugegraph.driver.HugeClient;
import com.baidu.hugegraph.driver.SchemaManager;
import com.baomidou.mybatisplus.extension.service.additional.query.impl.LambdaQueryChainWrapper;
import com.fb.user.relation.dao.FollowRelationDAO;
import com.fb.user.relation.dao.IDirectRelationDAO;
import com.fb.user.relation.dao.IIndirectRelationDao;
import com.fb.user.relation.repository.DirectRelationPO;
import com.fb.user.relation.repository.FollowRelationPO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * @author: pangminpeng
 * @create: 2020-06-10 23:55
 */
@Service
public class UserRelationServiceImpl {


    @Resource
    private IDirectRelationDAO directRelationDao;
    @Resource
    private IIndirectRelationDao indirectRelationDao;
    @Resource
    private FollowRelationDAO followRelationDAO;


    void addFriend(Long userId1, Long userId2) {
        //更新两个人的直接好友关系
        DirectRelationPO directRelationPO = new DirectRelationPO(userId1, userId2, LocalDateTime.now());
        directRelationDao.insert(directRelationPO);

        HugeClient hugeClient = new HugeClient("http://localhost:8080", "beijing");
        SchemaManager schemaManager = hugeClient.schema();
        //定义顶点类型

        //userId1和userId2的所有直接好友变成2级好友；userId1和userId2的所有二级好友变成3级好友
        //userId2和userId1的所有直接好友变成2级好友；userId2和userId1的所有二级好友变成3级好友
        //都要比较好友关系，变小才去更新。需要放在异步线程池里慢慢执行，肯定是需要考虑并发问题的， 需要先查出来，把要构造的信息放在
        //10个线程 0， 1， 2， 3， 4， 5， 6， 7， 8， 9。每个线程处理这种就行了。这应该用什么模式呢？
        //组装要处理的事件，生产事件；10个线程池消费事件。 之后是要使用消息队列的。消息队列

    }


    void followUser(Long userId, Long followUserId) {
        FollowRelationPO followRelationPO = new FollowRelationPO();
        followRelationPO.setUserId(userId);
        followRelationPO.setFollowedUserId(followUserId);
        followRelationPO.setCreateTime(LocalDateTime.now());
        followRelationDAO.insert(followRelationPO);
    }

    void unFollowUser(Long userId, Long followUserId) {
        FollowRelationPO followRelationPO = new FollowRelationPO();
        followRelationPO.setUserId(userId);
        followRelationPO.setFollowedUserId(followUserId);
        followRelationDAO.delete(new LambdaQueryChainWrapper<>(followRelationDAO)
                .eq(FollowRelationPO::getUserId, userId)
                .eq(FollowRelationPO::getFollowedUserId, followUserId));
    }


}
