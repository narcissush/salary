package salary.model.services;

import salary.model.entity.LoanItem;

import java.util.Collections;
import java.util.List;

public class LoanItemService implements Service<LoanItem> {

    @Override
    public void save(LoanItem loanItem) throws Exception {

    }

    @Override
    public void edit(LoanItem loanItem) throws Exception {

    }

    @Override
    public void delete(int id) throws Exception {

    }

    @Override
    public List<LoanItem> findAll() throws Exception {
        return Collections.emptyList();
    }

    @Override
    public LoanItem findById(int id) throws Exception {
        return null;
    }

    @Override
    public void close() throws Exception {

    }
}
