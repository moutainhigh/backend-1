package com.fb.user.relation.domian.graph;

import com.baidu.hugegraph.driver.GraphManager;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @author: pangminpeng
 * @create: 2020-10-06 18:26
 */
public class UserGraphManager {

    @Resource
    private GraphFactory graphFactory;

    private final static String EDGE_LABEL_NAME = "relation";



    /**
     * 同城的好友关系添加，会调用到这个
     * @param userId1
     * @param userId2
     * relation的关系是小用户id的在前面就行了吧
     */
    public void addRelation(Long userId1, Long userId2, String cityCode) {
        GraphManager graphManager = graphFactory.getGraphManagerByCityCode(cityCode);
        graphManager.addEdge(userId1, EDGE_LABEL_NAME, userId2);
    }

    public void removeRelation(Long userId1, Long userId2, String cityCode) {
        GraphManager graphManager = graphFactory.getGraphManagerByCityCode(cityCode);
        graphManager.removeEdge("L" + userId1 + "2>>" + userId2);
    }
}
