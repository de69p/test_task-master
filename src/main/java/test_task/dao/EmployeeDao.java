package test_task.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import test_task.model.Employee;

import java.util.List;

@Repository
public interface EmployeeDao extends CrudRepository<Employee, Long> {

    //TODO Get a list of employees receiving a salary greater than that of the boss
    @Query(
            value = "SELECT e.* FROM Employee e INNER JOIN Employee boss ON e.boss_id = boss.id WHERE e.salary > boss.salary",
            nativeQuery = true)
    List<Employee> findAllWhereSalaryGreaterThatBoss();

    //TODO Get a list of employees receiving the maximum salary in their department
    @Query(
            value = "SELECT e.* FROM Employee e INNER JOIN (SELECT department_id, MAX(salary) AS max_salary FROM Employee GROUP BY department_id) max_salary_per_department ON e.department_id = max_salary_per_department.department_id AND e.salary = max_salary_per_department.max_salary",
            nativeQuery = true)
    List<Employee> findAllByMaxSalary();

    //TODO Get a list of employees who do not have boss in the same department
    @Query(
            value = "SELECT e.* FROM Employee e LEFT JOIN Employee boss ON e.boss_id = boss.id WHERE boss.id IS NULL OR boss.department_id != e.department_id",
            nativeQuery = true)
    List<Employee> findAllWithoutBoss();
}
