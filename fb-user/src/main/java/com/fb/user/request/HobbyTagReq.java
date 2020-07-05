package com.fb.user.request;

import com.fb.user.repository.HobbyTagPO;
import lombok.Data;

/**
 * @author: pangminpeng
 * @create: 2020-07-05 14:33
 */
@Data
public class HobbyTagReq {

    private Integer id;

    private String name;

    public HobbyTagPO convert2PO() {
        HobbyTagPO hobbyTagPO = new HobbyTagPO();
        hobbyTagPO.setId(this.id);
        hobbyTagPO.setName(this.name);
        return hobbyTagPO;
    }
}
