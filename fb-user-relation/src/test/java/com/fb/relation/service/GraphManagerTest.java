package com.fb.relation.service;

import com.baidu.hugegraph.driver.HugeClient;
import com.fb.relation.BaseTest;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * @author: pangminpeng
 * @create: 2020-10-06 14:26
 */
public class GraphManagerTest extends BaseTest {

    @Resource
    GraphManager graphManager;

    @Test
    public void testGraphLabel() throws Exception{
        graphManager.managerBeijingLabel();
        Thread.sleep(3000L);
    }

    @Test
    public void testGraphVertex() {
        graphManager.manageVertex(1L);
        graphManager.manageVertex(2L);
        graphManager.manageVertex(3L);
        graphManager.manageVertex(4L);
        graphManager.manageVertex(1L);
    }

    @Test
    public void testEdge() {
        System.out.println(graphManager.getRelationPath(2L, 1L));
    }

    @Test
    public void testPrintAllVertex() {
        HugeClient hugeClient = new HugeClient("http://192.168.124.10:8080", "beijing");
        com.baidu.hugegraph.driver.GraphManager graph = hugeClient.graph();
        System.out.println(graph.listVertices());
    }

    @Test
    public void testRemoveEdge() {
        HugeClient hugeClient = new HugeClient("http://192.168.124.10:8080", "beijing");
        com.baidu.hugegraph.driver.GraphManager graph = hugeClient.graph();
        graph.addEdge(2L, "relation", 1L);
        System.out.println(graph.listEdges());
        graph.removeEdge("L2>2>>L1");
    }


}
