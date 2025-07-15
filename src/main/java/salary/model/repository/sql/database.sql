CREATE TABLE users
(
    id                 number primary key,
    first_Name         nvarchar2(30),
    last_Name          nvarchar2(30),
    national_Id        nvarchar2(10),
    education          nvarchar2(30),
    married            nvarchar2(5),
    number_Of_Children number,
    gender             nvarchar2(5),
    birth_Date         date,
    user_name          nvarchar2(20),
    password           nvarchar2(20)
);
create sequence users_seq start with 1 increment by 1;


CREATE TABLE employees
(
    id                  number primary key,
    first_Name          nvarchar2(30),
    last_Name           nvarchar2(30),
    national_Id         nvarchar2(10),
    education           nvarchar2(30),
    married             nvarchar2(5),
    number_Of_Children  number,
    gender              nvarchar2(5),
    birth_Date          date,
    insurance_Number    nvarchar2(15),
    bank_Account_Number nvarchar2(15)
);
create sequence employees_seq start with 1 increment by 1;

CREATE TABLE EmploymentContract
(
    id                  NUMBER PRIMARY KEY,
    employee_id         NUMBER NOT NULL,
    start_contract_date DATE,
    end_contract_date   DATE,
    contract_type       VARCHAR2(50),
    department          VARCHAR2(50),
    job_title           VARCHAR2(50),
    position            VARCHAR2(50),
    hire_date           DATE,
    termination_date    DATE,
    daily_salary        NUMBER(15, 2),
    bazar_kar           NUMBER(15, 2),
    fogholade_shoghl    NUMBER(15, 2),
    housing_allowance   NUMBER(15, 2),
    marriage_allowance  NUMBER(15, 2),
    child_allowance     NUMBER(15, 2),
    food_allowance      NUMBER(15, 2)

);

create sequence EmploymentContract_seq start with 1 increment by 1;


CREATE TABLE work_records
(
    id               number primary key,
    days_Worked      number,
    over_time_Hours  number,
    under_Time_Hours number,
    advance          numeric(12, 2),
    start_mission    Timestamp,
    end_mission      Timestamp
);
create sequence work_records_seq start with 1 increment by 1;

create TABLE Payslips
(
    id              number primary key,
    users_id        number references users (id),
    employees_id    number REFERENCES employees (id),
    work_Records_id number REFERENCES work_records (id),
    issueDate       date,
    month           nvarchar2(10),
    year            nvarchar2(4)
);
create sequence Payslips_seq start with 1 increment by 1;


CREATE TABLE loans
(
    id                 NUMBER PRIMARY KEY,
    employee_id        NUMBER REFERENCES employees (id),
    loan_type          NVARCHAR2(20),
    loan_amount        NUMBER, --مبلغ وام
    loan_interest      NUMBER, --بهره وام
    total_installments NUMBER, --تعداد افساط
    loan_start_date    DATE
);
create sequence loan_seq start with 1 increment by 1;


CREATE TABLE loan_items
(
    id           NUMBER PRIMARY KEY,
    loan_id      NUMBER REFERENCES loans (id) ON DELETE CASCADE,
    payslip_id   NUMBER REFERENCES payslips (id),
    amount_paid  NUMBER, --مبلغ_پرداختی
    payment_date DATE
);
create sequence loan_items_seq start with 1 increment by 1;



CREATE OR REPLACE VIEW SalaryReportNet AS
WITH base_data AS (SELECT Pay.id                                                                            AS Payslips_id,
                          u.user_name                                                                       AS users_user_name,
                          e.first_Name,
                          e.last_Name,
                          e.national_Id,
                          e.education,
                          e.gender,
                          e.birth_Date,
                          e.insurance_Number,
                          e.bank_Account_Number,
                          e.job_Title,
                          e.position,
                          e.hire_Date,
                          e.termination_Date,

                          wr.days_worked * NVL(e.daily_salary, 0)                                           AS base_salary,
                          wr.over_time_Hours * (NVL(e.daily_salary, 0) / 8 * 1.4)                           AS over_time,

                          NVL(e.number_of_children, 0) * (NVL(al.child_allowance, 0) / 30) *
                          wr.days_worked                                                                    AS child_allowance,
                          CASE
                              WHEN NVL(e.married, 0) = 1 THEN (NVL(al.marriage_allowance, 0) / 30) * wr.days_worked
                              ELSE 0 END                                                                    AS marriage_allowance,
                          (NVL(al.housing_allowance, 0) / 30) * wr.days_worked                              AS housing_allowance,
                          (NVL(al.food_allowance, 0) / 30) * wr.days_worked                                 AS food_allowance,

                          -- محاسبه حق ماموریت (تعداد روز بین دو تاریخ * حقوق روزانه)
                          ROUND(
                                  (TRUNC(wr.end_mission) - TRUNC(wr.start_mission)) * NVL(e.daily_salary, 0)
                              ,
                                  0)                                                                        AS mission_allowance,

                          (
                              wr.days_worked * NVL(e.daily_salary, 0)
                                  + wr.over_time_Hours * (NVL(e.daily_Salary, 0) / 8 * 1.4)
                                  + NVL(e.number_of_children, 0) * (NVL(al.child_allowance, 0) / 30) * wr.days_worked
                                  + CASE
                                        WHEN NVL(e.married, 0) = 1
                                            THEN (NVL(al.marriage_allowance, 0) / 30) * wr.days_worked
                                        ELSE 0 END
                                  + (NVL(al.housing_allowance, 0) / 30) * wr.days_worked
                                  + (NVL(al.food_allowance, 0) / 30) * wr.days_worked
                                  + ROUND((TRUNC(wr.end_mission) - TRUNC(wr.start_mission)) * NVL(e.daily_salary, 0), 0)
                              )                                                                             AS gross_salary,

                          (SELECT NVL(SUM(li.amount_paid), 0)
                           FROM loan_items li
                           WHERE li.payslip_id = Pay.id)                                                    AS loan_payment,

                          wr.under_Time_Hours * (NVL(e.daily_Salary, 0) / 8)                                AS under_time_payment,

                          Pay.issueDate,
                          Pay.month,
                          Pay.year
                   FROM Payslips Pay
                            JOIN users u ON Pay.users_id = u.id
                            JOIN employees e ON Pay.employees_id = e.id
                            JOIN work_Records wr ON Pay.work_Records_id = wr.id
                            LEFT JOIN allowances al
                                      ON EXTRACT(YEAR FROM Pay.issueDate) = al.year AND al.active = 1)

SELECT bd.*,

       ROUND(bd.gross_salary * 0.07, 2) AS employee_ins,
       ROUND(bd.gross_salary * 0.23, 2) AS employer_ins,

       -- مالیات ماهانه بر اساس پلکان‌ها
       ROUND(
               CASE
                   WHEN bd.gross_salary <= 240000000 THEN 0
                   WHEN bd.gross_salary <= 300000000 THEN (bd.gross_salary - 240000000) * 0.10
                   WHEN bd.gross_salary <= 380000000 THEN (300000000 - 240000000) * 0.10
                       + (bd.gross_salary - 300000000) * 0.15
                   WHEN bd.gross_salary <= 500000000 THEN (300000000 - 240000000) * 0.10
                       + (380000000 - 300000000) * 0.15
                       + (bd.gross_salary - 380000000) * 0.20
                   WHEN bd.gross_salary <= 667000000 THEN (300000000 - 240000000) * 0.10
                       + (380000000 - 300000000) * 0.15
                       + (500000000 - 380000000) * 0.20
                       + (bd.gross_salary - 500000000) * 0.25
                   ELSE (300000000 - 240000000) * 0.10
                       + (380000000 - 300000000) * 0.15
                       + (500000000 - 380000000) * 0.20
                       + (667000000 - 500000000) * 0.25
                       + (bd.gross_salary - 667000000) * 0.30
                   END, 0
       )                                AS monthly_tax,

       -- مجموع کسورات
       ROUND(
               bd.gross_salary * 0.07 + bd.gross_salary * 0.23
                   + CASE
                         WHEN bd.gross_salary <= 240000000 THEN 0
                         WHEN bd.gross_salary <= 300000000 THEN (bd.gross_salary - 240000000) * 0.10
                         WHEN bd.gross_salary <= 380000000
                             THEN (300000000 - 240000000) * 0.10 + (bd.gross_salary - 300000000) * 0.15
                         WHEN bd.gross_salary <= 500000000 THEN (300000000 - 240000000) * 0.10
                             + (380000000 - 300000000) * 0.15 + (bd.gross_salary - 380000000) * 0.20
                         WHEN bd.gross_salary <= 667000000 THEN (300000000 - 240000000) * 0.10
                             + (380000000 - 300000000) * 0.15 + (500000000 - 380000000) * 0.20 +
                                                                (bd.gross_salary - 500000000) * 0.25
                         ELSE (300000000 - 240000000) * 0.10
                             + (380000000 - 300000000) * 0.15 + (500000000 - 380000000) * 0.20
                             + (667000000 - 500000000) * 0.25 + (bd.gross_salary - 667000000) * 0.30
                   END
                   + bd.loan_payment + bd.under_time_payment
           , 0)                         AS total_deductions,

       -- حقوق خالص
       ROUND(
               bd.gross_salary - (
                   bd.gross_salary * 0.07 + bd.gross_salary * 0.23
                       + CASE
                             WHEN bd.gross_salary <= 240000000 THEN 0
                             WHEN bd.gross_salary <= 300000000 THEN (bd.gross_salary - 240000000) * 0.10
                             WHEN bd.gross_salary <= 380000000
                                 THEN (300000000 - 240000000) * 0.10 + (bd.gross_salary - 300000000) * 0.15
                             WHEN bd.gross_salary <= 500000000 THEN (300000000 - 240000000) * 0.10
                                 + (380000000 - 300000000) * 0.15 + (bd.gross_salary - 380000000) * 0.20
                             WHEN bd.gross_salary <= 667000000 THEN (300000000 - 240000000) * 0.10
                                 + (380000000 - 300000000) * 0.15 + (500000000 - 380000000) * 0.20 +
                                                                    (bd.gross_salary - 500000000) * 0.25
                             ELSE (300000000 - 240000000) * 0.10
                                 + (380000000 - 300000000) * 0.15 + (500000000 - 380000000) * 0.20
                                 + (667000000 - 500000000) * 0.25 + (bd.gross_salary - 667000000) * 0.30
                       END
                       + bd.loan_payment + bd.under_time_payment
                   )
           , 0)                         AS total_salary

FROM base_data bd;



CREATE OR REPLACE VIEW EmployeeLoanReport AS
SELECT e.id                                AS employee_id,
       e.first_name || ' ' || e.last_name  AS employee_name,
       l.id                                AS loan_id,
       l.loan_type,
       l.loan_amount,
       l.loan_interest,
       l.total_installments,
       l.loan_start_date,

       -- قسط ماهیانه با بهره مرکب
       ROUND(
               CASE
                   WHEN l.loan_interest = 0 THEN l.loan_amount / l.total_installments
                   ELSE
                       (l.loan_amount * ((l.loan_interest / 100) / 12) *
                        POWER(1 + (l.loan_interest / 100) / 12, l.total_installments)) /
                       (POWER(1 + (l.loan_interest / 100) / 12, l.total_installments) - 1)
                   END
           , 0)                            AS monthly_installment,

       -- اقساط پرداخت‌شده تا امروز
       COUNT(li.id)                        AS installments_paid,
       NVL(SUM(li.amount_paid), 0)         AS total_paid,

       -- اقساط باقی‌مانده
       l.total_installments - COUNT(li.id) AS remaining_installments,

       -- مانده وام
       ROUND(
               (l.total_installments - COUNT(li.id)) *
               CASE
                   WHEN l.loan_interest = 0 THEN l.loan_amount / l.total_installments
                   ELSE
                       (l.loan_amount * ((l.loan_interest / 100) / 12) *
                        POWER(1 + (l.loan_interest / 100) / 12, l.total_installments)) /
                       (POWER(1 + (l.loan_interest / 100) / 12, l.total_installments) - 1)
                   END
           , 0)                            AS remaining_amount

FROM loans l
         JOIN employees e ON l.employee_id = e.id
         LEFT JOIN loan_items li ON li.loan_id = l.id
GROUP BY e.id, e.first_name, e.last_name,
         l.id, l.loan_type, l.loan_amount, l.loan_interest,
         l.total_installments, l.loan_start_date;
