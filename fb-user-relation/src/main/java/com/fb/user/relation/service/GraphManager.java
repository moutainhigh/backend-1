package com.fb.user.relation.service;

import com.baidu.hugegraph.driver.HugeClient;
import com.baidu.hugegraph.driver.SchemaManager;
import com.baidu.hugegraph.driver.TraverserManager;
import com.baidu.hugegraph.structure.constant.T;
import com.baidu.hugegraph.structure.graph.Path;
import com.baidu.hugegraph.structure.graph.Vertex;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * @author: pangminpeng
 * @create: 2020-10-06 13:33
 */
@Service
public class GraphManager {




    public void managerBeijingLabel() {
        HugeClient hugeClient = new HugeClient("http://192.168.124.10:8080", "beijing");
        SchemaManager schema = hugeClient.schema();
        System.out.println(schema.getVertexLabels());

        schema.removeEdgeLabel("relation");
        schema.vertexLabel("user").remove();

        schema.propertyKey("userId").asLong().ifNotExist().create();

        schema.vertexLabel("user")
                .properties("userId")
                .useCustomizeNumberId()
                .ifNotExist()
                .create();

        schema.edgeLabel("relation")
                .sourceLabel("user")
                .targetLabel("user")
                .ifNotExist()
                .create();
    }


    /**
     * 管理userId的顶点，测试重复的加入会是什么结果
     * @param userId
     */
    public void manageVertex(Long userId) {
        HugeClient hugeClient = new HugeClient("http://192.168.124.10:8080", "beijing");
        com.baidu.hugegraph.driver.GraphManager graph = hugeClient.graph();
        Vertex user = graph.addVertex( T.label, "user", "userId", userId, T.id, userId);
        System.out.println(graph.listVertices());
    }

    public void removeAllVertex() {
        HugeClient hugeClient = new HugeClient("http://192.168.124.10:8080", "beijing");
        com.baidu.hugegraph.driver.GraphManager graph = hugeClient.graph();
        graph.removeVertex("1:11");
    }

    /**
     * 测试添加边
     * @param userId1
     * @param userId2
     *
     * 这是一个这样的图
     *          *
     *          * 1 - 2
     *          * ｜  ｜
     *          * 3 - 4
     */
    public void manageEdge(Long userId1, Long userId2) {
        HugeClient hugeClient = new HugeClient("http://192.168.124.10:8080", "beijing");
        com.baidu.hugegraph.driver.GraphManager graph = hugeClient.graph();
        graph.addEdge(1L, "relation", 2L);
        graph.addEdge(2L, "relation", 4L);
        graph.addEdge(1L, "relation", 3L);
        graph.addEdge(3L, "relation", 4L);
    }

    /**
     * 获取两个用户之间的最短路径
     * @param userId1
     * @param userId2
     * @return
     */
    public String getRelationPath(Long userId1, Long userId2) {
        HugeClient hugeClient = new HugeClient("http://192.168.124.10:8080", "beijing");
        TraverserManager traverserManager = hugeClient.traverser();
        Path path = traverserManager.shortestPath(userId1, userId2, 4);
        return Arrays.toString(path.objects().toArray(new Object[0]));
    }

    /**
     * 获取所有有关系的 4级内的所有用户
     * @param userId
     * @return
     */
    public List<Long> getRelationUserId(Long userId) {
        return null;
    }
}
