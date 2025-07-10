package salary.model.services;

import salary.model.entity.Loan;

import java.util.Collections;
import java.util.List;

public class LoanService implements Service<Loan> {

    @Override
    public void save(Loan loan) throws Exception {

    }

    @Override
    public void edit(Loan loan) throws Exception {

    }

    @Override
    public void delete(int id) throws Exception {

    }

    @Override
    public List<Loan> findAll() throws Exception {
        return Collections.emptyList();
    }

    @Override
    public Loan findById(int id) throws Exception {
        return null;
    }

    @Override
    public void close() throws Exception {

    }
}
