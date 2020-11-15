package com.fb.relation.domian.graph;


import com.baidu.hugegraph.driver.GraphManager;
import com.baidu.hugegraph.driver.HugeClient;
import com.baidu.hugegraph.driver.SchemaManager;
import com.baidu.hugegraph.driver.TraverserManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: pangminpeng
 * @create: 2020-10-06 16:31
 */
@Service
public class GraphFactory {

    @Value("#{configProperties['graph.url']}")
    private String graphUrl;

    /**
     * 图管理器用来增加顶点，增加边
     */
    private final Map<String, GraphManager> graphManagerMap = new ConcurrentHashMap<>();

    /**
     * 遍历管理器用来查找
     * 1.一个用户4级以内的全部好友
     * 2.两个用户之间的最短路径
     * https://hugegraph.github.io/hugegraph-doc/clients/restful-api/traverser.html
     */
    private final Map<String, TraverserManager> traverserManagerMap = new ConcurrentHashMap<>();

    private final static Map<String, String> cityGraphMap = new ConcurrentHashMap<>();

    static {
        cityGraphMap.put("010", "beijing_010");
        cityGraphMap.put("021", "shanghai_021");
    }


    /**
     * 通过cityCode获取图管理器，如果Map中存在，则使用，不存在则new
     * @param cityCode
     * @return
     */
    public GraphManager getGraphManagerByCityCode(String cityCode) {
        String graphName = cityGraphMap.get(cityCode);
        if (Objects.isNull(graphName)) return null;
        return graphManagerMap.computeIfAbsent(graphName, k -> {
            HugeClient hugeClient = new HugeClient(graphUrl, k);
            return hugeClient.graph();
        });
    }

    public TraverserManager getTraverserManagerByCityCode(String cityCode) {
        String graphName = cityGraphMap.get(cityCode);
        if (Objects.isNull(graphName)) return null;
        return traverserManagerMap.computeIfAbsent(graphName, k -> {
            HugeClient hugeClient = new HugeClient(graphUrl, k);
            return hugeClient.traverser();
        });
    }

    /**
     * 每增加一个城市，都需要增加一个图的实例，图的名称以cityCode命名
     * 这个图的配置需要在hugegraph中进行配置一次
     * 同时需要调用本方法来初始化这个图的元数据，包括顶点元数据和边的元数据
     * @param cityCode
     */
    public void initGraphByCityCode(String cityCode) {
        String graphName = cityGraphMap.get(cityCode);
        HugeClient hugeClient = new HugeClient(graphUrl, graphName);
        SchemaManager schemaManager = hugeClient.schema();

        schemaManager.propertyKey("userId").asLong().ifNotExist().create();

        // 顶点叫做user，顶点只包含顶点id的属性
        schemaManager.vertexLabel("user")
                .properties("userId")
                //使用uid作为图的顶点的唯一标识
                .useCustomizeNumberId()
                .ifNotExist()
                .create();

        //边的名称为relation，顶点是user。
        schemaManager.edgeLabel("relation")
                .sourceLabel("user")
                .targetLabel("user")
                .ifNotExist()
                .create();
    }

}
