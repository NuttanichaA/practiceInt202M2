package int202.controller;

import int202.service.EmployeeService;
import int202.entity.Employee;
import jakarta.servlet.http.HttpServletResponse;
import int202.service.OfficeService;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

    private final OfficeService serviceO;

    private final EmployeeService service;

    public EmployeeController(OfficeService serviceO, EmployeeService service) {
        this.serviceO = serviceO;
        this.service = service;
    }

    @GetMapping("/all")
    public  String allEmp(Model model){
        List<Employee> emps = service.findAll();
        model.addAttribute("empslist", emps);
        return "all_employees";
    }

    @GetMapping("")
    public String getEmpByID(@RequestParam Integer employeeNumber, Model model){
        Employee emp = service.getEmployee(employeeNumber);
        model.addAttribute("emp", emp);
        return "emp_detail";
    }

    @GetMapping("/delete")
    public String deleteEmp(@RequestParam Integer employeeNumber, Model model){
        Employee emp = service.deleteEmployee(employeeNumber);
        model.addAttribute("emp", emp);
        model.addAttribute("message", "Delete Success");
        return "emp_detail";
    }

    @GetMapping("/add")
    public String addEmpForm(){
        return "add_emp_form";
    }

    @PostMapping("/add")
    public void addEmp(Employee emp, HttpServletResponse response, @RequestParam String officeCode) throws IOException {
        emp.setOfficeCode(serviceO.getOffice(officeCode));
        service.add(emp);
        response.sendRedirect("/employees/all");
    }

    @GetMapping("/update")
    public String updateEmpForm(@RequestParam Integer employeeNumber, Model model){
        Employee emp = service.getEmployee(employeeNumber);
        model.addAttribute("emp", emp);
        return "update_emp_form";
    }

    @PostMapping("/update")
    public void updateEmp(Employee emp, HttpServletResponse response, @RequestParam String officeCode) throws IOException {
        emp.setOfficeCode(serviceO.getOffice(officeCode));
        service.update(emp);
        response.sendRedirect("/employees/all");
    }

    @GetMapping("/searchByJob")
    public String searchByJob(@RequestParam(required = true) String searchParam, Model model){
        model.addAttribute("empslist", service.findByJob(searchParam));
        return "all_employees";
    }

    @GetMapping("/searchByMgr")
    public String searchByMgr(@RequestParam(required = true) Integer searchMgr, Model model){
        Employee mgr = service.getEmployee(searchMgr);
        model.addAttribute("empslist", service.findByManager(mgr));
        return "all_employees";
    }

    @GetMapping("/searchByJobAndMgr")
    public String searchByJobAndMgr(@RequestParam(required = false) String searchParam, @RequestParam(required = false) Integer searchMgr, Model model){
        Employee mgr = service.getEmployee(searchMgr);
        model.addAttribute("empslist", service.findByJobAndMgr(searchParam, mgr));
        return "all_employees";
    }

    @GetMapping("/page")
    public String getAllProductsPaging(@RequestParam(defaultValue = "0") int pageNumber,
                                       @RequestParam(defaultValue = "5") int pageSize, Model model){
        model.addAttribute("pageEmp", service.findAll(PageRequest.of(pageNumber, pageSize)));
        return "all_emp_page";
    }


}
