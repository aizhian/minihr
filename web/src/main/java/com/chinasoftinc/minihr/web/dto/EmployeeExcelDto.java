package com.chinasoftinc.minihr.web.dto;

import com.chinasoftinc.minihr.web.domain.Department;
import com.chinasoftinc.minihr.web.domain.Employee;
import lombok.Data;

import java.util.List;

/**
 * Created by Aizhanglin on 2018/4/16.
 *
 */
@Data
public class EmployeeExcelDto {
    private List<Employee> employees;
    private List<Department> departments;
}
