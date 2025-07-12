package salary.model.services;

import salary.model.entity.Payslip;
import salary.model.repository.EmployeeRepository;
import salary.model.repository.PayslipRepository;

import java.util.Collections;
import java.util.List;

public class PaysLipService implements Service<Payslip>{

    @Override
    public void save(Payslip payslip) throws Exception {
        try (PayslipRepository payslipRepository = new PayslipRepository()) {
            payslipRepository.save(payslip);
        }
    }

    @Override
    public void edit(Payslip payslip) throws Exception {
        try (PayslipRepository payslipRepository = new PayslipRepository()) {
            if (payslipRepository.findById(payslip.getId()) != null) {
                payslipRepository.edit(payslip);
            } else {
                throw new Exception("Payslip not found");
            }
        }
    }

    @Override
    public void delete(int id) throws Exception {
        try (PayslipRepository payslipRepository = new PayslipRepository()) {
            if (payslipRepository.findById(id) != null) {
                payslipRepository.delete(id);
            } else {
                throw new Exception("Payslip not found");
            }
        }
    }

    @Override
    public List<Payslip> findAll() throws Exception {
        try (PayslipRepository payslipRepository = new PayslipRepository()) {
            return payslipRepository.findAll();
        }    }

    @Override
    public Payslip findById(int id) throws Exception {
        try (PayslipRepository payslipRepository = new PayslipRepository()) {
            return payslipRepository.findById(id);
        }    }

    @Override
    public void close() throws Exception {

    }
}
