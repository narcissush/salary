package salary.model.services;

import salary.model.entity.Employee;
import salary.model.repository.EmployeeRepository;

import java.util.List;

public class EmployeeService {

    private static final EmployeeRepository repository = new EmployeeRepository();

    public static void save(Employee employee) throws Exception {
        repository.save(employee);
    }

    public static void edit(Employee employee) throws Exception {
        if (repository.findById(employee.getId()) != null) {
            repository.edit(employee);
        } else {
            throw new Exception("Employee not found");
        }
    }

    public static void delete(int id) throws Exception {
        if (repository.findById(id) != null) {
            repository.delete(id);
        } else {
            throw new Exception("Employee not found");
        }
    }

    public static List<Employee> findAll() throws Exception {
        return repository.findAll();
    }

    public static Employee findById(int id) throws Exception {
        return repository.findById(id);
    }

    public static Employee findByNationalId(String nationalId) throws Exception {
        return repository.findByNationalId(nationalId);
    }
}
