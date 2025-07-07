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

CREATE OR REPLACE VIEW v_test_summary AS
WITH base_data AS (
    SELECT
        p.id AS payslip_id,
        e.id AS employee_id,
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
        wr.days_worked * e.daily_salary AS base_salary,

        -- مزایا
        NVL(e.number_of_children, 0) * (500000 / 30) * wr.days_worked AS child_allowance,
        CASE WHEN e.married = 1 THEN (1000000 / 30) * wr.days_worked ELSE 0 END AS marriage_allowance,
        (2000000 / 30) * wr.days_worked AS housing_allowance,
        (1000000 / 30) * wr.days_worked AS food_allowance,
        (800000 / 30) * wr.days_worked AS transport_allowance,

        -- اضافه‌کاری
        (e.daily_salary / 8) * 1.4 * wr.over_time_hours AS overtime,

        -- مجموع اقساط وام ثبت‌شده
        (SELECT NVL(SUM(li.amount_paid), 0)
         FROM loan_items li
         WHERE li.payslips_id = p.id) AS loan_payment

    FROM payslips p
             JOIN employees e ON p.employees_id = e.id
             JOIN work_records wr ON p.work_records_id = wr.id
)
SELECT
    bd.payslip_id,
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
