package com.chinasoftinc.minihr.web.domain;

import lombok.Data;

/**
 * Created by Aizhanglin on 2018/4/16.
 */
@Data
public class Department {
    private Long id;
    private String name;
    private Integer level;
    private Department parent;
}
