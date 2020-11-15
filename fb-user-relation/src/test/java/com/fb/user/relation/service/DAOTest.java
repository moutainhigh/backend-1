package com.fb.user.relation.service;

import com.fb.user.relation.BaseTest;
import com.fb.user.relation.dao.IDirectRelationDAO;
import com.fb.user.relation.repository.DirectRelationPO;
import org.junit.Test;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: pangminpeng
 * @create: 2020-11-07 22:21
 */
public class DAOTest extends BaseTest {

    @Resource
    private IDirectRelationDAO directRelationDAO;

    @Test
    public void testDirectRelationBatchInsert() {
        DirectRelationPO directRelationPO = new DirectRelationPO(1L ,2L, LocalDateTime.now());
        DirectRelationPO directRelationPO1 = new DirectRelationPO(2L, 1L, LocalDateTime.now());
        List<DirectRelationPO> directRelationPOS = new ArrayList<>();
        directRelationPOS.add(directRelationPO);
        directRelationPOS.add(directRelationPO1);
        System.out.println(directRelationDAO.batchInsert(directRelationPOS));
    }
}
