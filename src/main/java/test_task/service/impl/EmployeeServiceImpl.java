package test_task.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import test_task.dao.EmployeeDao;
import test_task.model.Employee;
import test_task.service.EmployeeService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    EmployeeDao employeeDao;

    @Override
    public List<Employee> findAllBySalaryGreaterThatBoss() {
        return employeeDao.findAllWhereSalaryGreaterThatBoss();
    }

    @Override
    public List<Employee> findAllByMaxSalary() {
        return employeeDao.findAllByMaxSalary();
    }

    @Override
    public List<Employee> findAllWithoutBoss() {
        return employeeDao.findAllWithoutBoss();
    }

    @Override
    public Long fireEmployee(String name) {
        Iterable<Employee> employees = employeeDao.findAll();
        List<Long> employeeIdsToDelete = StreamSupport.stream(employees.spliterator(), false)
                .filter(employee -> employee.getName().equals(name))
                .map(Employee::getId)
                .collect(Collectors.toList());
        employeeIdsToDelete.forEach(employeeDao::deleteById);

        return (long) employeeIdsToDelete.size();
    }

//    @Override
//    public Long changeSalary(String name) {
//        return null;
//    }


    @Override
    public Long changeSalary(String name, BigDecimal newSalary) {
        Iterable<Employee> employees = employeeDao.findAll();
        List<Employee> employeeList = new ArrayList<>();
        employees.forEach(employeeList::add);

        long changedEmployeesCount = 0;
        for (Employee employee : employeeList) {
            if (employee.getName().equals(name)) {
                employee.setSalary(newSalary);
                changedEmployeesCount++;
            }
        }
        employeeDao.saveAll(employeeList);

        return changedEmployeesCount;
    }

    @Override
    public Long hireEmployee(Employee employee) {
        employeeDao.save(employee);

        Iterable<Employee> existingEmployees = employeeDao.findAll();
        List<Employee> employeeList = new ArrayList<>();
        existingEmployees.forEach(employeeList::add);
        employeeList.add(employee);
        employeeDao.saveAll(employeeList);

        return employee.getId();
    }
}
