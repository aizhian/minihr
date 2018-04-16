package com.chinasoftinc.minihr.web.service.impl;

import com.chinasoftinc.minihr.common.utils.ExcelUtils;
import com.chinasoftinc.minihr.web.bean.Constants;
import com.chinasoftinc.minihr.web.domain.Area;
import com.chinasoftinc.minihr.web.domain.Department;
import com.chinasoftinc.minihr.web.domain.Employee;
import com.chinasoftinc.minihr.web.dto.EmployeeExcelDto;
import com.chinasoftinc.minihr.web.mapper.DepartmentMapper;
import com.chinasoftinc.minihr.web.mapper.EmployeeMapper;
import com.chinasoftinc.minihr.web.service.EmployeeService;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Aizhanglin on 2018/4/16.
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final String EXCEL_TITLE = "姓名,项目,部门1,部门2,区域";

    @Autowired
    DepartmentMapper departmentMapper;
    @Autowired
    EmployeeMapper employeeMapper;

//    @Override
//    public void relationDelEmployeeByProjectName(String projectName){
//        Department project=new Department();
//        project.setName(projectName);
//        project = departmentMapper.selectOne(project);
//        project
//        departmentMapper.deleteByExample()
//    }


    @Override
    public void readAndSaveFromExcel(MultipartFile multipartFile) {
        if (ExcelUtils.checkFile(multipartFile)) {
            Workbook workbook = ExcelUtils.readFileToWorkbook(multipartFile);
            List<String[]> datas = ExcelUtils.readExcel(workbook);
            EmployeeExcelDto employeeExcelDto = swapDataToEmployeeExcelDto(datas);

            departmentMapper.saveDepartments(employeeExcelDto.getDepartments());
            employeeMapper.saveEmployees(employeeExcelDto.getEmployees());
        }
    }

    private EmployeeExcelDto swapDataToEmployeeExcelDto(List<String[]> datas) {
        EmployeeExcelDto employeeExcelDto=new EmployeeExcelDto();
        List<Employee> employees = new ArrayList<>();
        List<Department> departments = new ArrayList<>();

        Map<String, Integer> tileLocation = getTileLocation();
        for (String[] data : datas) {
            List<Department> partDepartments = swapDataLineToDept(data, tileLocation);
            departments.addAll(partDepartments);

            Department lowestDept = getLowestDept(departments);
            Employee employee = swapDataLineToEmployee(data, tileLocation, lowestDept);
            employees.add(employee);
        }
        employeeExcelDto.setDepartments(departments);
        employeeExcelDto.setEmployees(employees);
        return employeeExcelDto;
    }

    private Department getLowestDept(List<Department> departments) {
        return departments.size() > 0 ? departments.get(departments.size() - 1) : null;
    }

    private List<Department> swapDataLineToDept(String[] data, Map<String, Integer> tileLocation) {
        List<Department> departments = new ArrayList<>();

        String projectValue = data[tileLocation.get("项目")];
        Department project = null;
        if (!StringUtils.isEmpty(projectValue)) {
            project = new Department();
            project.setName(projectValue);
            project.setLevel(Constants.DeptLevel.PROJECT.getCode());
            project.setParent(new Department());
            departments.add(project);
        }

        String dept1Value = data[tileLocation.get("部门1")];
        Department department1 = null;
        if (!StringUtils.isEmpty(dept1Value)) {
            department1 = new Department();
            department1.setName(dept1Value);
            department1.setLevel(Constants.DeptLevel.DEPT_FIRST.getCode());
            department1.setParent(project);
            departments.add(department1);
        }

        String dept2Value = data[tileLocation.get("部门2")];
        if (!StringUtils.isEmpty(dept2Value)) {
            Department department2 = new Department();
            department2.setName(dept2Value);
            department2.setLevel(Constants.DeptLevel.DEPT_SEC.getCode());
            department2.setParent(department1);
            departments.add(department2);
        }

        return departments;
    }

    private Employee swapDataLineToEmployee(String[] data, Map<String, Integer> tileLocation, Department lowestDept) {
        Employee employee = new Employee();
        employee.setName(data[tileLocation.get("姓名")]);

        Area area = new Area();
        area.setName(data[tileLocation.get("区域")]);
        employee.setArea(area);

        employee.setDepartment(lowestDept);
        return employee;
    }

    private Map<String, Integer> getTileLocation() {
        Map<String, Integer> titleLocation = new HashMap<>();
        String[] titles = EXCEL_TITLE.split(",");
        for (int i = 0; i < titles.length; i++) {
            titleLocation.put(titles[i], i);
        }
        return titleLocation;
    }
}
