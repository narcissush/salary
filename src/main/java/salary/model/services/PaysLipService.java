package salary.model.services;

import salary.model.entity.Payslip;
import salary.model.repository.EmployeeRepository;
import salary.model.repository.PayslipRepository;

import java.util.Collections;
import java.util.List;

public class PaysLipService {

    public static void save(Payslip payslip) throws Exception {
        try (PayslipRepository payslipRepository = new PayslipRepository()) {
            payslipRepository.save(payslip);
        }
    }

    public static void edit(Payslip payslip) throws Exception {
        try (PayslipRepository payslipRepository = new PayslipRepository()) {
            if (payslipRepository.findById(payslip.getId()) != null) {
                payslipRepository.edit(payslip);
            } else {
                throw new Exception("Payslip not found");
            }
        }
    }

    public static void delete(int id) throws Exception {
        try (PayslipRepository payslipRepository = new PayslipRepository()) {
            if (payslipRepository.findById(id) != null) {
                payslipRepository.delete(id);
            } else {
                throw new Exception("Payslip not found");
            }
        }
    }

    public static List<Payslip> findAll() throws Exception {
        try (PayslipRepository payslipRepository = new PayslipRepository()) {
            return payslipRepository.findAll();
        }    }

    public static Payslip findById(int id) throws Exception {
        try (PayslipRepository payslipRepository = new PayslipRepository()) {
            return payslipRepository.findById(id);
        }    }

}
