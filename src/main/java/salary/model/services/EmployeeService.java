package salary.model.services;

import salary.model.entity.Employee;
import salary.model.repository.EmployeeRepository;

import java.util.List;

public class EmployeeService {
    public static void save(Employee employee) throws Exception {
        try (EmployeeRepository repository = new EmployeeRepository()) {
            repository.save(employee);
        }
    }

        public static void edit (Employee employee) throws Exception {
            try (EmployeeRepository repository = new EmployeeRepository()) {
                if (repository.findById(employee.getId()) != null) {
                    repository.edit(employee);
                } else {
                    throw new Exception("Employee not found");
                }
            }
        }

        public static void delete (int id) throws Exception {
            try (EmployeeRepository repository = new EmployeeRepository()) {
                if (repository.findById(id) != null) {
                    repository.delete(id);
                } else {
                    throw new Exception("Employee not found");
                }
            }
        }

        public static List<Employee> findAll () throws Exception {
            try (EmployeeRepository repository = new EmployeeRepository()) {
                return repository.findAll();
            }
        }

        public static Employee findById (int id) throws Exception {
            try (EmployeeRepository repository = new EmployeeRepository()) {
                return repository.findById(id);
            }
        }

        public static Employee findByNationalId (String nationalId) throws Exception {
            try (EmployeeRepository repository = new EmployeeRepository()) {
                return repository.findByNationalId(nationalId);
            }
        }

}