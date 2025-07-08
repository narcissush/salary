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


CREATE OR REPLACE VIEW SalaryReportNet AS
WITH base_data AS (
    /* زیر‌پرسش فعلی شما برای محاسبه gross_salary و loan_payment و under_time */
    SELECT
        Pay.id                     AS Payslips_id,
        u.user_name               AS users_user_name,
        -- ... سایر ستون‌ها ...
        wr.days_worked * e.daily_salary AS base_salary,
        wr.over_time_Hours * (e.daily_Salary/8*1.4) AS over_time,
        NVL(e.number_of_children,0)*(5000000/30)*wr.days_worked AS child_allowance,
        CASE WHEN e.married=1 THEN (8000000/30)*wr.days_worked ELSE 0 END AS marriage_allowance,
        (9000000/30)*wr.days_worked AS housing_allowance,
        (60000000/30)*wr.days_worked AS food_allowance,
        (30000000/30)*wr.days_worked AS transport_allowance,

        /* جمع حقوق و مزایا */
        (
            wr.days_worked * e.daily_salary
                + wr.over_time_Hours * (e.daily_Salary/8*1.4)
                + NVL(e.number_of_children,0)*(5000000/30)*wr.days_worked
                + CASE WHEN e.married=1 THEN (8000000/30)*wr.days_worked ELSE 0 END
                + (9000000/30)*wr.days_worked
                + (60000000/30)*wr.days_worked
                + (30000000/30)*wr.days_worked
            ) AS gross_salary,

        /* مجموع اقساط وام ثبت‌شده */
        (SELECT NVL(SUM(li.amount_paid),0)
         FROM loan_items li
         WHERE li.payslips_id = Pay.id
        ) AS loan_payment,

        wr.under_Time_Hours * (e.daily_Salary/8) AS under_time_payment,

        Pay.issueDate,
        Pay.period
    FROM Payslips Pay
             JOIN users u ON Pay.users_id = u.id
             JOIN employees e ON Pay.employees_id = e.id
             JOIN work_Records wr ON Pay.work_Records_id = wr.id
)
SELECT
    bd.*,
    /* کسرهای کارمند و کارفرما (برای گزارش فقط employee_ins نیاز هست) */
    ROUND(bd.gross_salary * 0.07, 2) AS employee_ins,
    ROUND(bd.gross_salary * 0.23, 2) AS employer_ins,

    /* مالیات ماهیانه پلکانی */
    ROUND(
            CASE
                WHEN bd.gross_salary <= 240000000 THEN 0
                WHEN bd.gross_salary <= 300000000 THEN (bd.gross_salary - 240000000) * 0.10
                WHEN bd.gross_salary <= 380000000 THEN (300000000 - 240000000)*0.10
                    + (bd.gross_salary - 300000000)*0.15
                WHEN bd.gross_salary <= 500000000 THEN (300000000 - 240000000)*0.10
                    + (380000000 - 300000000)*0.15
                    + (bd.gross_salary - 380000000)*0.20
                WHEN bd.gross_salary <= 667000000 THEN (300000000 - 240000000)*0.10
                    + (380000000 - 300000000)*0.15
                    + (500000000 - 380000000)*0.20
                    + (bd.gross_salary - 500000000)*0.25
                ELSE (300000000 - 240000000)*0.10
                    + (380000000 - 300000000)*0.15
                    + (500000000 - 380000000)*0.20
                    + (667000000 - 500000000)*0.25
                    + (bd.gross_salary - 667000000)*0.30
                END, 0
    ) AS monthly_tax,

    /* مجموع کسرها */
    ROUND(
            bd.gross_salary * 0.07
                + bd.gross_salary * 0.23
                + (
                CASE
                    WHEN bd.gross_salary <= 240000000 THEN 0
                    WHEN bd.gross_salary <= 300000000 THEN (bd.gross_salary - 240000000)*0.10
                    WHEN bd.gross_salary <= 380000000 THEN (300000000 - 240000000)*0.10
                        + (bd.gross_salary - 300000000)*0.15
                    WHEN bd.gross_salary <= 500000000 THEN (300000000 - 240000000)*0.10
                        + (380000000 - 300000000)*0.15
                        + (bd.gross_salary - 380000000)*0.20
                    WHEN bd.gross_salary <= 667000000 THEN (300000000 - 240000000)*0.10
                        + (380000000 - 300000000)*0.15
                        + (500000000 - 380000000)*0.20
                        + (bd.gross_salary - 500000000)*0.25
                    ELSE (300000000 - 240000000)*0.10
                        + (380000000 - 300000000)*0.15
                        + (500000000 - 380000000)*0.20
                        + (667000000 - 500000000)*0.25
                        + (bd.gross_salary - 667000000)*0.30
                    END
                )
                + bd.loan_payment
                + bd.under_time_payment
        , 0) AS total_deductions,

    /* حقوق خالص */
    ROUND(
            bd.gross_salary
                - (
                bd.gross_salary * 0.07
                    + bd.gross_salary * 0.23
                    + CASE
                          WHEN bd.gross_salary <= 240000000 THEN 0
                          WHEN bd.gross_salary <= 300000000 THEN (bd.gross_salary - 240000000)*0.10
                          WHEN bd.gross_salary <= 380000000 THEN (300000000 - 240000000)*0.10
                              + (bd.gross_salary - 300000000)*0.15
                          WHEN bd.gross_salary <= 500000000 THEN (300000000 - 240000000)*0.10
                              + (380000000 - 300000000)*0.15
                              + (bd.gross_salary - 380000000)*0.20
                          WHEN bd.gross_salary <= 667000000 THEN (300000000 - 240000000)*0.10
                              + (380000000 - 300000000)*0.15
                              + (500000000 - 380000000)*0.20
                              + (bd.gross_salary - 500000000)*0.25
                          ELSE (300000000 - 240000000)*0.10
                              + (380000000 - 300000000)*0.15
                              + (500000000 - 380000000)*0.20
                              + (667000000 - 500000000)*0.25
                              + (bd.gross_salary - 667000000)*0.30
                    END
                    + bd.loan_payment
                    + bd.under_time_payment
                )
        , 0) AS net_salary

FROM base_data bd;
