package int202.repository;

import int202.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    List<Employee> findAllByJobTitleContainingOrderById(String job);
    List<Employee> findAllByReportsToOrderById(Employee mgr);
    List<Employee> findByJobTitleContainingAndReportsToOrderById(String job, Employee mgr);
}
