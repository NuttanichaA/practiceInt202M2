package int202.service;

import ch.qos.logback.core.model.conditional.ElseModel;
import int202.repository.EmployeeRepository;
import int202.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class EmployeeService {
    private final EmployeeRepository repo;

    public EmployeeService(EmployeeRepository repo) {
        this.repo = repo;
    }

    public List<Employee> findAll(){
        return repo.findAll();
    }

    public Page<Employee> findAll(Pageable pageable){
        return repo.findAll(pageable);
    }

    public Employee getEmployee(Integer employeeNumber){
        if(employeeNumber == null){ return null;}
        return repo.findById(employeeNumber).orElse(null);
    }

    public Employee deleteEmployee(Integer employeeNumber){
        Employee emp = getEmployee(employeeNumber);
        if(emp == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    String.format("Employee id '%d' does not exists",
                            employeeNumber));
        }
        repo.deleteById(employeeNumber);
        return emp;
    }

    public Employee add(Employee emp){
        if(emp.getId() == null || repo.existsById(emp.getId())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    String.format("Employee id '%d' already exists",
                            emp.getId()));
        }
        return repo.save(emp);
    }

    public Employee update(Employee emp){
        if(!repo.existsById(emp.getId()) || emp.getId() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    String.format("Employee id '%d' does not exists",
                            emp.getId()));
        }
        return repo.save(emp);
    }

    public List<Employee> findByJob(String job){
        return repo.findAllByJobTitleContainingOrderById(job);
    }

    public List<Employee> findByManager(Employee mgr){
        return repo.findAllByReportsToOrderById(mgr);
    }

    public List<Employee> findByJobAndMgr(String job, Employee mgr){
        if(job == null || job.isEmpty()){
            return repo.findAllByReportsToOrderById(mgr);
        } else if (mgr == null){
            return repo.findAllByJobTitleContainingOrderById(job);
        } else {
            return repo.findByJobTitleContainingAndReportsToOrderById(job, mgr);
        }
    }
 }
