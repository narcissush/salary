package salary.model.services;

import salary.model.entity.Employee;
import salary.model.entity.EmploymentContract;
import salary.model.repository.EmployeeRepository;

import java.util.Collections;
import java.util.List;

public class EmployeeService{

    public static void save(Employee employee) throws Exception {
        try (EmployeeRepository employeeRepository = new EmployeeRepository()) {
            employeeRepository.save(employee);
        }
    }

    public static void edit(Employee employee) throws Exception {
        try (EmployeeRepository employeeRepository = new EmployeeRepository()) {
            if (employeeRepository.findById(employee.getId()) != null) {
                employeeRepository.edit(employee);
            } else {
                throw new Exception("Employee not found");
            }
        }
    }

    public static void delete(int id) throws Exception {
        try (EmployeeRepository employeeRepository = new EmployeeRepository()) {
            if (employeeRepository.findById(id) != null) {
                employeeRepository.delete(id);
            } else {
                throw new Exception("Employee not found");
            }
        }
    }

    public static List<Employee> findAll() throws Exception {
        try (EmployeeRepository employeeRepository = new EmployeeRepository()) {
            return employeeRepository.findAll();
        }
    }

    public static Employee findById(int id) throws Exception {
        try (EmployeeRepository employeeRepository = new EmployeeRepository()) {
            return employeeRepository.findById(id);
        }
    }

}
