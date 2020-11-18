package com.fb.relation.domian.graph;

import com.baidu.hugegraph.driver.GraphManager;
import com.baidu.hugegraph.driver.TraverserManager;
import com.baidu.hugegraph.structure.constant.T;
import com.baidu.hugegraph.structure.graph.Path;
import com.baidu.hugegraph.structure.graph.Vertex;
import com.fb.relation.service.DTO.UserDTOForRelation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author: pangminpeng
 * @create: 2020-10-06 18:26
 */
@Service
@Slf4j
public class UserGraphManager {

    @Resource
    private GraphFactory graphFactory;

    private final static String EDGE_LABEL_NAME = "relation";

    private final static String VERTEX_LABEL_NAME = "user";

    private final static String VERTEX_USER_ID = "userId";




    /**
     * 同城的好友关系添加，会调用到这个
     * @param userId1
     * @param userId2
     * relation的关系是小用户id的在前面就行了吧
     */
    public void addRelation(Long userId1, Long userId2, String cityCode) {
        GraphManager graphManager = graphFactory.getGraphManagerByCityCode(cityCode);
        if (Objects.nonNull(graphManager)) {
            graphManager.addVertex( T.label, VERTEX_LABEL_NAME, VERTEX_USER_ID, userId1, T.id, userId1);
            graphManager.addVertex( T.label, VERTEX_LABEL_NAME, VERTEX_USER_ID, userId2, T.id, userId2);
            if (userId1 > userId2) {
                graphManager.addEdge(userId2, EDGE_LABEL_NAME, userId1);
            }else {
                graphManager.addEdge(userId1, EDGE_LABEL_NAME, userId2);
            }
        } else {
            log.warn("no graph");
        }
    }

    /**
     *
     * @param user1
     * @param user2
     */
    public void removeRelation(UserDTOForRelation user1, UserDTOForRelation user2) {
        GraphManager graphManager = graphFactory.getGraphManagerByCityCode(user1.getCityCode());
        String edge;
        if (user1.getUid() > user2.getUid()) {
            edge = "L" + user2.getUid() + ">1>>L" + user1.getUid();
        } else {
            edge = "L" + user1.getUid() + ">1>>L" + user2.getUid();
        }
        if (Objects.nonNull(graphManager)) {
            graphManager.removeEdge(edge);
        }
    }

    public List<Long> listIndirect(UserDTOForRelation user) {
        TraverserManager traverserManager = graphFactory.getTraverserManagerByCityCode(user.getCityCode());
        if (Objects.isNull(traverserManager)) return Collections.emptyList();
        List<Object> list1 = traverserManager.kout(user.getUid(), 2);
        List<Object> list2 = traverserManager.kout(user.getUid(), 3);
        list1.addAll(list2);
        return list1.stream().map(o -> {
            if (o instanceof Integer) return ((Integer) o).longValue();
            return (Long) o;
        }).collect(Collectors.toList());
    }

    public List<Long> shortestPath(Long userId1, Long userId2, String cityCode) {
        TraverserManager traverserManager = graphFactory.getTraverserManagerByCityCode(cityCode);
        if (Objects.isNull(traverserManager)) return Collections.emptyList();
        Path p = traverserManager.shortestPath(userId1, userId2, 4);
        if (Objects.isNull(p)) return Collections.emptyList();
        return p.objects().stream().map(o -> {
            if (o instanceof Integer) return ((Integer) o).longValue();
            return (Long) o;
        }).collect(Collectors.toList());
    }
}
