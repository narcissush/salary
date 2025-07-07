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
    password           nvarchar2(30),
);
create sequence usrs_seq start with 1 increment by 1;


CREATE TABLE employees
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
    insurance_Number   nvarchar2(10),
    bankAccount_Number nvarchar2(10),
    job_Title          nvarchar2(10),
    position           nvarchar2(10),
    hire_Date          date,
    termination_Date   date,
    daily_Salary       numeric(12, 2)
);
create sequence employees_seq start with 1 increment by 1;


create TABLE Payslips
(
    int               id primary key,
    employees_id      int REFERENCES employees (id),
    work_Records_id   int REFERENCES work_records (id),
    issueDate         date,
    period nvarchar(10)
);
create sequence Payslips_seq start with 1 increment by 1;


CREATE TABLE work_records
(
    id               int primary key,
    payslips_id      int REFERENCES payslips (id),
    days_Worked      number,
    over_time_Hours  number,
    under_Time_Hours number,
    advance          numeric(12, 2)
);
create sequence work_records_seq start with 1 increment by 1;


CREATE TABLE loans
(
    id                 number PRIMARY KEY,
    employees_id       int REFERENCES employees(id),
    loan_type          nVARCHAR2(10),
    loan_amount        NUMBER,
    loan_interest      NUMBER,
    total_installments NUMBER,
    loan_start_date Date
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


CREATE OR REPLACE VIEW v_payslip_summary AS
SELECT
    p.id                     AS payslip_id,
    e.id                    AS employee_id,
    e.full_name             AS employee_name,
    p.period                AS period,
    p.issueDate             AS issue_date,

    -- پایه حقوق
    wr.days_worked * e.daily_salary                      AS base_salary,

    -- مزایا
    NVL(e.num_children, 0) * (500000 / 30) * wr.days_worked       AS child_allowance,
    CASE WHEN e.is_married = 1 THEN (1000000 / 30) * wr.days_worked ELSE 0 END AS marriage_allowance,
    (2000000 / 30) * wr.days_worked                       AS housing_allowance,
    (1000000 / 30) * wr.days_worked                       AS food_allowance,
    (800000 / 30) * wr.days_worked                        AS transport_allowance,

    -- اضافه‌کاری
    (e.daily_salary / 8) * 1.4 * wr.over_time_hours       AS overtime,

    -- مجموع مزایا + حقوق پایه + اضافه‌کاری
    (
        (wr.days_worked * e.daily_salary) +
        NVL(e.num_children, 0) * (500000 / 30) * wr.days_worked +
        CASE WHEN e.is_married = 1 THEN (1000000 / 30) * wr.days_worked ELSE 0 END +
        (2000000 / 30) * wr.days_worked +
        (1000000 / 30) * wr.days_worked +
        (800000 / 30) * wr.days_worked +
        (e.daily_salary / 8) * 1.4 * wr.over_time_hours
        ) AS gross_salary,

    -- بیمه (۲۳٪ از حقوق ناخالص)
    ROUND(
            (
                (wr.days_worked * e.daily_salary) +
                NVL(e.num_children, 0) * (500000 / 30) * wr.days_worked +
                CASE WHEN e.is_married = 1 THEN (1000000 / 30) * wr.days_worked ELSE 0 END +
                (2000000 / 30) * wr.days_worked +
                (1000000 / 30) * wr.days_worked +
                (800000 / 30) * wr.days_worked +
                (e.daily_salary / 8) * 1.4 * wr.over_time_hours
                ) * 0.23, 0
    ) AS insurance,

    -- کسر تاخیر
    (e.daily_salary / 8) * wr.under_time_hours            AS under_time_deduction,

    -- مساعده
    wr.advance                                             AS advance,

    -- وام (جمع مبالغ قسطی ثبت‌شده در loan_items برای این payslip)
    (SELECT NVL(SUM(li.amount_paid), 0)
     FROM loan_items li
     WHERE li.payslips_id = p.id)                         AS loan_payment,

    -- مالیات (محاسبه تقریبی پله‌ای از حقوق ناخالص - معافیت)
    CASE
        WHEN (
                 (wr.days_worked * e.daily_salary) +
                 NVL(e.num_children, 0) * (500000 / 30) * wr.days_worked +
                 CASE WHEN e.is_married = 1 THEN (1000000 / 30) * wr.days_worked ELSE 0 END +
                 (2000000 / 30) * wr.days_worked +
                 (1000000 / 30) * wr.days_worked +
                 (800000 / 30) * wr.days_worked +
                 (e.daily_salary / 8) * 1.4 * wr.over_time_hours
                 ) <= 240000000 THEN 0
        ELSE
            CASE
                WHEN ((...gross...) - 240000000) <= 160000000 THEN ((...gross...) - 240000000) * 0.10
                WHEN ((...gross...) - 240000000) <= 360000000 THEN
                    (160000000 * 0.10) + (((...gross...) - 240000000 - 160000000) * 0.15)
                ELSE
                    (160000000 * 0.10) + (200000000 * 0.15) + (((...gross...) - 240000000 - 160000000 - 200000000) * 0.20)
                END
        END AS tax,

    -- مجموع کسورات
    (
        ROUND(
                (
                    (wr.days_worked * e.daily_salary) +
                    NVL(e.num_children, 0) * (500000 / 30) * wr.days_worked +
                    CASE WHEN e.is_married = 1 THEN (1000000 / 30) * wr.days_worked ELSE 0 END +
                    (2000000 / 30) * wr.days_worked +
                    (1000000 / 30) * wr.days_worked +
                    (800000 / 30) * wr.days_worked +
                    (e.daily_salary / 8) * 1.4 * wr.over_time_hours
                    ) * 0.23, 0
        )
            + (e.daily_salary / 8) * wr.under_time_hours
            + wr.advance
            + (SELECT NVL(SUM(li.amount_paid), 0)
               FROM loan_items li
               WHERE li.payslips_id = p.id)
            + 0 -- مالیات جداگانه بالا آمده، برای خلاصه می‌تونی جمع بزنی
        ) AS total_deductions,

    -- خالص پرداختی
    (
        (
            (wr.days_worked * e.daily_salary) +
            NVL(e.num_children, 0) * (500000 / 30) * wr.days_worked +
            CASE WHEN e.is_married = 1 THEN (1000000 / 30) * wr.days_worked ELSE 0 END +
            (2000000 / 30) * wr.days_worked +
            (1000000 / 30) * wr.days_worked +
            (800000 / 30) * wr.days_worked +
            (e.daily_salary / 8) * 1.4 * wr.over_time_hours
            )
            - ROUND(
                (
                    (wr.days_worked * e.daily_salary) +
                    NVL(e.num_children, 0) * (500000 / 30) * wr.days_worked +
                    CASE WHEN e.is_married = 1 THEN (1000000 / 30) * wr.days_worked ELSE 0 END +
                    (2000000 / 30) * wr.days_worked +
                    (1000000 / 30) * wr.days_worked +
                    (800000 / 30) * wr.days_worked +
                    (e.daily_salary / 8) * 1.4 * wr.over_time_hours
                    ) * 0.23, 0
              )
            - (e.daily_salary / 8) * wr.under_time_hours
            - wr.advance
            - (SELECT NVL(SUM(li.amount_paid), 0)
               FROM loan_items li
               WHERE li.payslips_id = p.id)
        ) AS net_salary

FROM payslips p
         JOIN employees e ON p.employees_id = e.id
         JOIN work_records wr ON p.work_records_id = wr.id;

