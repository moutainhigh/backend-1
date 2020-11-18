package com.fb.relation.service;

import com.baidu.hugegraph.driver.GraphManager;
import com.fb.relation.BaseTest;
import com.fb.relation.domian.graph.GraphFactory;
import com.fb.relation.domian.graph.UserGraphManager;
import com.fb.relation.service.DTO.UserDTOForRelation;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * @author: pangminpeng
 * @create: 2020-11-08 00:39
 */
public class GraphTest extends BaseTest {

    @Resource
    private IUserRelationService userRelationService;
    @Resource
    private GraphFactory graphFactory;
    @Resource
    private UserGraphManager userGraphManager;

    @Test
    public void testInit() {
        graphFactory.initGraphByCityCode("010");
    }

    @Test
    public void testAddUser() {
        UserDTOForRelation user1 = new UserDTOForRelation(1L, "010");
        UserDTOForRelation user2 = new UserDTOForRelation(2L, "010");
        userRelationService.addFriend(user1, user2);
        UserDTOForRelation user3 = new UserDTOForRelation(3L, "010");
        UserDTOForRelation user4 = new UserDTOForRelation(4L, "010");
        userRelationService.addFriend(user3, user4);
        userRelationService.addFriend(user2, user3);
        userRelationService.addFriend(user1, user4);
        UserDTOForRelation user5 = new UserDTOForRelation(2147483648L, "010");
        userRelationService.addFriend(user4, user5);
    }

    @Test
    public void testListId() {
        System.out.println(Arrays.toString(userGraphManager.listIndirect(new UserDTOForRelation(2147483648L, "010")).toArray()));
    }

    @Test
    public void testShorTestPath() {
        userGraphManager.shortestPath(1L, 2147483648L, "010");
    }

    @Test
    public void testRemoveFriend() {
        UserDTOForRelation user1 = new UserDTOForRelation(3L, "010");
        UserDTOForRelation user2 = new UserDTOForRelation(4L, "010");
        userRelationService.removeFriend(user1, user2);
    }

    @Test
    public void testRemoveVertex() {
        GraphManager graphManager = graphFactory.getGraphManagerByCityCode("010");
        graphManager.removeVertex(2147483648L);
        graphManager.removeVertex(2L);
        graphManager.removeVertex(3L);
        graphManager.removeVertex(4L);
        graphManager.removeVertex(1L);
    }

    @Test
    public void initProdGraph() {
        graphFactory.initGraphByCityCode("010");
        graphFactory.initGraphByCityCode("021");
    }
}
