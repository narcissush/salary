CREATE TABLE employees (
                        id NUMBER PRIMARY KEY,
                        first_name nVARCHAR2(30),
                        last_name nVARCHAR2(30),
                        national_id nVARCHAR2(10) UNIQUE,
                        education nVARCHAR2(30),
                        married NUMBER(1),
                        number_of_children INT,
                        gender nVARCHAR(5),
                        birth_date DATE,
                        insurance_number nVARCHAR2(10),
                        bank_account_number nVARCHAR2(20),
                        job_title nVARCHAR2(30),
                        position nVARCHAR2(10),
                        hire_date DATE,
                        termination_date DATE,
                        daily_salary number
);
create sequence employees_seq start with 1 increment by 1;

CREATE TABLE work_record (
                             id NUMBER PRIMARY KEY,
                             employee_id NUMBER REFERENCES employee(id) ON DELETE CASCADE,
                             days_worked NUMBER,
                             overtime_hours NUMBER,
                             under_time_hours NUMBER,
                             advance NUMBER
);
create sequence work_record_seq start with 1 increment by 1;

CREATE TABLE loan (
                      id number PRIMARY KEY,
                      loan_type nVARCHAR2(10),
                      loan_amount NUMBER,
                      loan_interest NUMBER, -- درصد سالیانه (مثلاً 18.00)
                      total_installments NUMBER
);

create sequence loan_seq start with 1 increment by 1;


CREATE TABLE loan_item (
                           id number PRIMARY KEY,
                           loan_id INT REFERENCES loan(id) ON DELETE CASCADE,
                           amount_paid number,
                           payment_date DATE,
                           total_payment number
);

create sequence loan_item_seq start with 1 increment by 1;

CREATE TABLE salary_components (
                                   id number PRIMARY KEY,
                                   payslip_id INT REFERENCES payslip(id),
                                   base_salary number,
                                   child_allowance number,
                                   marriage_allowance number,
                                   housing_allowance number,
                                   food_allowance number,
                                   transport_allowance number,
                                   overtime_payment number,

                                   total_salary NUMERIC(12,2)
);
create sequence salary_components_seq start with 1 increment by 1;

CREATE TABLE deductions (
                            id SERIAL PRIMARY KEY,
                            payslip_id INT REFERENCES payslip(id),
                            tax  nimber,
                            insurance  nimber,
                            under_time_deduction  nimber,
                            loan_repayment  nimber,
                            advance nimber,
                            total_deductions number
);
create sequence deductions_seq start with 1 increment by 1;



CREATE TABLE payslip (
                         id number PRIMARY KEY,
                         employee_id INT REFERENCES employee(id),
                         work_record_id INT REFERENCES work_record(id),
                         issue_date DATE NOT NULL,
                         period nVARCHAR(20), -- مثلا "2025-07"

                         total_components number,       -- حقوق ناخالص (قابل کش)
                         total_deductions number,   -- مجموع کسورات
                         total_salary number,         -- حقوق پرداختی


);
