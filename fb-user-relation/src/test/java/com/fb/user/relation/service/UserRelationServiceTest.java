package com.fb.user.relation.service;

import com.fb.user.relation.BaseTest;
import com.fb.user.relation.service.DTO.UserDTOForRelation;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * @author: pangminpeng
 * @create: 2020-11-08 21:52
 */
public class UserRelationServiceTest extends BaseTest {

    @Resource
    private IUserRelationService userRelationService;

    UserDTOForRelation user1 = new UserDTOForRelation(1L, "010");
    UserDTOForRelation user2 = new UserDTOForRelation(2L, "010");
    UserDTOForRelation user3 = new UserDTOForRelation(3L, "010");
    UserDTOForRelation user4 = new UserDTOForRelation(4L, "010");
    UserDTOForRelation user5 = new UserDTOForRelation(2147483648L, "010");

    @Test
    public void testAddFriend() {
        userRelationService.addFriend(user1, user2);
        userRelationService.addFriend(user1, user4);
        userRelationService.addFriend(user2, user3);
        userRelationService.addFriend(user3, user4);
        userRelationService.addFriend(user4, user5);
    }

    @Test
    public void testRemoveFriend() {
        userRelationService.removeFriend(user1, user4);
    }

    @Test
    public void testGetAllRelation() {
        System.out.println(Arrays.toString(userRelationService.getAllRelation(user4).toArray()));
        System.out.println(Arrays.toString(userRelationService.listDirectFriends(user4.getUid()).toArray()));
    }

    @Test
    public void testFollow() {
        userRelationService.followUser(1L, 4L);
        userRelationService.followUser(1L, 3L);
        userRelationService.followUser(2L, 3L);
    }

    @Test
    public void testListFans() {
        System.out.println(Arrays.toString(userRelationService.listFansUserId(3L).toArray()));
        System.out.println(Arrays.toString(userRelationService.listFollowUserId(1L).toArray()));
    }

    @Test
    public void testShortestPath() {
        System.out.println(Arrays.toString(userRelationService.shortestPath(1L, 4L, "010").toArray()));
    }

}
