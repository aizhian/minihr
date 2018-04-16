package com.chinasoftinc.minihr.web.mapper;

import com.chinasoftinc.minihr.web.domain.Employee;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * Created by Aizhanglin on 2017/11/27.
 *
 */
@org.apache.ibatis.annotations.Mapper
public interface EmployeeMapper extends Mapper<Employee> {
    int saveEmployees(List<Employee> employees);
}
