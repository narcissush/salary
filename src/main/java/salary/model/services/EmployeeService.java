package salary.model.services;

import salary.model.entity.Employee;
import salary.model.repository.EmployeeRepository;

import java.util.Collections;
import java.util.List;

public class EmployeeService implements Service<Employee> {

    @Override
    public void save(Employee employee) throws Exception {
        try (EmployeeRepository employeeRepository = new EmployeeRepository()) {
            employeeRepository.save(employee);
        }
    }

    @Override
    public void edit(Employee employee) throws Exception {
        try (EmployeeRepository employeeRepository = new EmployeeRepository()) {
            if (employeeRepository.findById(employee.getId()) != null) {
                employeeRepository.edit(employee);
            } else {
                throw new Exception("Employee not found");
            }
        }
    }

    @Override
    public void delete(int id) throws Exception {
        try (EmployeeRepository employeeRepository = new EmployeeRepository()) {
            if (employeeRepository.findById(id) != null) {
                employeeRepository.delete(id);
            } else {
                throw new Exception("Employee not found");
            }
        }
    }

    @Override
    public List<Employee> findAll() throws Exception {
        try (EmployeeRepository employeeRepository = new EmployeeRepository()) {
            return employeeRepository.findAll();
        }
    }

    @Override
    public Employee findById(int id) throws Exception {
        try (EmployeeRepository employeeRepository = new EmployeeRepository()) {
            return employeeRepository.findById(id);
        }
    }

    @Override
    public void close() throws Exception {

    }
}
