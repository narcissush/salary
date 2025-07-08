CREATE TABLE users
(
    id                 int primary key,
    first_Name         nvarchar2(30),
    last_Name          nvarchar2(30),
    national_Id        nvarchar2(10),
    education          nvarchar2(30),
    married            number(1),
    number_Of_Children int,
    gender             nvarchar2(5),
    birth_Date         date,
    user_name          nvarchar2(30),
    password           nvarchar2(30)
);
create sequence usrs_seq start with 1 increment by 1;


CREATE TABLE employees
(
    id                  int primary key,
    first_Name          nvarchar2(30),
    last_Name           nvarchar2(30),
    national_Id         nvarchar2(10),
    education           nvarchar2(30),
    married             number(1),
    number_Of_Children  int,
    gender              nvarchar2(5),
    birth_Date          date,
    insurance_Number    nvarchar2(10),
    bank_Account_Number nvarchar2(10),
    job_Title           nvarchar2(10),
    position            nvarchar2(10),
    hire_Date           date,
    termination_Date    date,
    daily_Salary        numeric(12, 2)
);
create sequence employees_seq start with 1 increment by 1;



CREATE TABLE work_records
(
    id               int primary key,
    days_Worked      number,
    over_time_Hours  number,
    under_Time_Hours number,
    advance          numeric(12, 2)
);
create sequence work_records_seq start with 1 increment by 1;

create TABLE Payslips
(
    id              int primary key,
    users_id        int references users (id),
    employees_id    int REFERENCES employees (id),
    work_Records_id int REFERENCES work_records (id),
    issueDate       date,
    period          nvarchar2(10)
);
create sequence Payslips_seq start with 1 increment by 1;


CREATE TABLE loans
(
    id                 number PRIMARY KEY,
    employees_id       int REFERENCES employees (id),
    loan_type          nVARCHAR2(10),
    loan_amount        NUMBER,
    loan_interest      NUMBER,
    total_installments NUMBER,
    loan_start_date    Date
);
create sequence loan_seq start with 1 increment by 1;


CREATE TABLE loan_items
(
    id            number PRIMARY KEY,
    loans_id      int REFERENCES loans (id) ON DELETE CASCADE,
    payslips_id   int REFERENCES payslips (id),
    amount_paid   number,
    payment_date  DATE,
    total_payment number
);
create sequence loan_items_seq start with 1 increment by 1;

CREATE TABLE Employee_Allowances
(
    HOUSING_ALLOWANCE   numeric(12, 2),
    MARAGE_ALLOWANCE    numeric(12, 2),
    CHILD_ALLOWANCE     numeric(12, 2),
    FOOD_ALLOWANCE      numeric(12, 2),
    TRANSPORT_ALLOWANCE numeric(12, 2)
);
create sequence Employee_Allowances_seq start with 1 increment by 1;



CREATE OR REPLACE VIEW v_test_summary AS
WITH base_data AS (SELECT p.id                                                                    AS payslip_id,
                          e.id                                                                    AS employee_id,
                          e.first_name,
                          e.last_name,
                          e.married,
                          e.number_of_children,
                          e.daily_salary,
                          p.period,
                          p.issuedate,
                          wr.days_worked,
                          wr.over_time_hours,
                          wr.under_time_hours,
                          wr.advance,

                          -- پایه حقوق
                          wr.days_worked * e.daily_salary                                         AS base_salary,

                          -- مزایا
                          NVL(e.number_of_children, 0) * (500000 / 30) * wr.days_worked           AS child_allowance,
                          CASE WHEN e.married = 1 THEN (1000000 / 30) * wr.days_worked ELSE 0 END AS marriage_allowance,
                          (2000000 / 30) * wr.days_worked                                         AS housing_allowance,
                          (1000000 / 30) * wr.days_worked                                         AS food_allowance,
                          (800000 / 30) * wr.days_worked                                          AS transport_allowance,

                          -- اضافه‌کاری
                          (e.daily_salary / 8) * 1.4 * wr.over_time_hours                         AS overtime,

                          -- مجموع اقساط وام ثبت‌شده
                          (SELECT NVL(SUM(li.amount_paid), 0)
                           FROM loan_items li
                           WHERE li.payslips_id = p.id)                                           AS loan_payment

                   FROM payslips p
                            JOIN employees e ON p.employees_id = e.id
                            JOIN work_records wr ON p.work_records_id = wr.id)
SELECT bd.payslip_id,
       bd.employee_id,
       bd.first_name,
       bd.last_name,
       bd.married,
       bd.number_of_children,
       bd.daily_salary,
       bd.period,
       bd.issuedate,
       bd.days_worked,
       bd.over_time_hours,
       bd.under_time_hours,
       bd.advance,
       bd.base_salary,
       bd.child_allowance,
       bd.marriage_allowance,
       bd.housing_allowance,
       bd.food_allowance,
       bd.transport_allowance,
       bd.overtime,
       bd.loan_payment,

       -- محاسبه حقوق ناخالص
       bd.base_salary + bd.child_allowance + bd.marriage_allowance + bd.housing_allowance +
       bd.food_allowance + bd.transport_allowance + bd.overtime AS gross_salary


FROM base_data bd;


CREATE view SalaryReport as
WITH base_data AS (select Pay.id                                                                     Payslips_id,
                          Pay.users_id,
                          u.user_name                                                                users_user_name,
                          u.password                                                                 users_password,

                          Pay.employees_id,
                          e.first_Name                                                               employees_first_Name,
                          e.last_Name                                                                employees_last_Name,
                          e.national_Id                                                              employees_national_Id,
                          e.education                                                                employees_education,
                          e.married                                                                  employees_married,
                          e.number_Of_Children                                                       employees_number_Of_Children,
                          e.gender                                                                   employees_gender,
                          e.birth_Date                                                               employees_birth_Date,
                          e.insurance_Number                                                         employees_insurance_Number,
                          e.bank_Account_Number                                                      employees_bank_Account_Number,
                          e.job_Title                                                                employees_job_Title,
                          e.position                                                                 employees_position,
                          e.hire_Date                                                                employees_hire_Date,
                          e.termination_Date                                                         employees_termination_Date,
                          e.daily_Salary                                                             employees_daily_Salary,

                          Pay.work_Records_id,
                          wr.days_Worked                                                             work_records_days_Worked,
                          wr.over_time_Hours                                                         work_records_over_time_Hours,
                          wr.under_Time_Hours                                                        work_records_under_Time_Hours,
                          wr.advance                                                                 work_recordsc_advance,

                          wr.days_worked * e.daily_salary                                         AS base_salary, -- یکماه کارکرد
                          wr.over_time_Hours * (e.daily_Salary / 8 * 1.4)                         AS over_time,   --اضافه کار
                          NVL(e.number_of_children, 0) * (5000000 / 30) * wr.days_worked          AS child_allowance,
                          CASE WHEN e.married = 1 THEN (8000000 / 30) * wr.days_worked ELSE 0 END AS marriage_allowance,
                          (9000000 / 30) * wr.days_worked                                         AS housing_allowance,
                          (60000000 / 30) * wr.days_worked                                        AS food_allowance,
                          (30000000 / 30) * wr.days_worked                                        AS transport_allowance,

                          wr.days_worked * e.daily_salary +
                          wr.over_time_Hours * (e.daily_Salary / 8 * 1.4) +
                          NVL(e.number_of_children, 0) * (5000000 / 30) * wr.days_worked +
                          CASE WHEN e.married = 1 THEN (8000000 / 30) * wr.days_worked ELSE 0 END +
                          (9000000 / 30) * wr.days_worked+
                          (60000000 / 30) * wr.days_worked+
                          (30000000 / 30) * wr.days_worked                                        As gross_salary,




                          -- مجموع اقساط وام ثبت‌شده
                          (SELECT NVL(SUM(li.amount_paid), 0)
                           FROM loan_items li
                           WHERE li.payslips_id = Pay.id)                                         AS loan_payment,

                          wr.under_Time_Hours * (e.daily_Salary / 8)                              AS Under_Time,


                          Pay.issueDate      AS issueDate,
                          Pay.period            as period

                   from Payslips Pay
                            join users u on Pay.users_id = u.id
                            join employees e on Pay.employees_id = e.id
                            join work_Records wr on Pay.work_Records_id = wr.id)
select
    bd.Payslips_id,

    bd.users_user_name,
    bd.users_password,

    bd.employees_first_Name,
    bd.employees_last_Name,
    bd.employees_national_Id,
    bd.employees_education,
    bd.employees_married,
    bd.employees_number_Of_Children,
    bd.employees_gender,
    bd.employees_birth_Date,
    bd.employees_insurance_Number,
    bd.employees_bank_Account_Number,
    bd.employees_job_Title,
    bd.employees_position,
    bd.employees_hire_Date,
    bd.employees_termination_Date,
    bd.employees_daily_Salary,

    bd.work_records_days_Worked,
    bd.work_records_over_time_Hours,
    bd.work_records_under_Time_Hours,
    bd.work_recordsc_advance,  --مساعده

    bd.base_salary, -- یکماه کارکرد
    bd.over_time,   --اضافه کار
    bd.child_allowance,
    bd.marriage_allowance,
    bd.housing_allowance,
    bd.food_allowance,
    bd.transport_allowance,
    bd.gross_salary,        --جمع حقوق و مزایا

    -- سهم کارمند برای بیمه اجتماعی
    ROUND(bd.gross_salary * 0.07, 2) AS employee_ins,

    -- سهم کارفرما برای بیمه اجتماعی ( 23%)
    ROUND(bd.gross_salary * 0.23, 2) AS employer_ins,

    -- محاسبه مالیات
    ROUND(
            CASE
                WHEN gross_salary <= 240000000 THEN 0
                WHEN gross_salary <= 300000000 THEN (gross_salary - 240000000) * 0.10
                WHEN gross_salary <= 380000000 THEN (300000000 - 240000000) * 0.10
                    + (gross_salary - 300000000) * 0.15
                WHEN gross_salary <= 500000000 THEN (300000000 - 240000000) * 0.10
                    + (380000000 - 300000000) * 0.15
                    + (gross_salary - 380000000) * 0.20
                WHEN gross_salary <= 667000000 THEN (300000000 - 240000000) * 0.10
                    + (380000000 - 300000000) * 0.15
                    + (500000000 - 380000000) * 0.20
                    + (gross_salary - 500000000) * 0.25
                ELSE (300000000 - 240000000) * 0.10
                    + (380000000 - 300000000) * 0.15
                    + (500000000 - 380000000) * 0.20
                    + (667000000 - 500000000) * 0.25
                    + (gross_salary - 667000000) * 0.30
                END
        , 0) AS monthly_tax,


    bd.issueDate,
    bd.period
from base_data bd;

