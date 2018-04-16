package com.chinasoftinc.minihr.web.mapper;

import com.chinasoftinc.minihr.web.domain.Department;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * Created by Aizhanglin on 2017/11/27.
 */
@org.apache.ibatis.annotations.Mapper
public interface DepartmentMapper extends Mapper<Department> {
    int saveDepartments(List<Department> departments);
}
