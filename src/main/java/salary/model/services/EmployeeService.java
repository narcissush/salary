package salary.model.services;

import salary.model.entity.Employee;

import java.util.Collections;
import java.util.List;

public class EmployeeService implements Service<Employee> {

    @Override
    public void save(Employee employee) throws Exception {

    }

    @Override
    public void edit(Employee employee) throws Exception {

    }

    @Override
    public void delete(int id) throws Exception {

    }

    @Override
    public List<Employee> findAll() throws Exception {
        return Collections.emptyList();
    }

    @Override
    public Employee findById(int id) throws Exception {
        return null;
    }

    @Override
    public void close() throws Exception {

    }
}
