package salary.model.services;

import salary.model.entity.Payslip;

import java.util.Collections;
import java.util.List;

public class PaysLipService implements Service<Payslip>{

    @Override
    public void save(Payslip payslip) throws Exception {

    }

    @Override
    public void edit(Payslip payslip) throws Exception {

    }

    @Override
    public void delete(int id) throws Exception {

    }

    @Override
    public List<Payslip> findAll() throws Exception {
        return Collections.emptyList();
    }

    @Override
    public Payslip findById(int id) throws Exception {
        return null;
    }

    @Override
    public void close() throws Exception {

    }
}
