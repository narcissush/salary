CREATE TABLE users
(
    id                 number primary key,
    first_Name         nvarchar2(30),
    last_Name          nvarchar2(30),
    father_name        nvarchar2(20),
    national_Id        nvarchar2(10),
    certificate_Number nvarchar2(10),
    birth_Date         date,
    birth_Place        nvarchar2(20),
    gender             nvarchar2(5),
    education          nvarchar2(30),
    major              nvarchar2(20),
    marriage           nvarchar2(5),
    number_Of_Children number,
    Phone_number       nvarchar2(20),
    user_name          nvarchar2(20),
    pass_word          nvarchar2(20)
);
create sequence users_seq start with 1 increment by 1;



CREATE TABLE employees
(
    id                  number primary key,
    first_Name          nvarchar2(30),
    last_Name           nvarchar2(30),
    national_Id         nvarchar2(10),
    father_name         nvarchar2(20),
    certificate_Number  nvarchar2(10),
    birth_Date          date,
    birth_Place         nvarchar2(20),
    gender              nvarchar2(5),
    education           nvarchar2(30),
    major               nvarchar2(20),
    marriage            nvarchar2(5),
    number_Of_Children  number,
    Phone_number        nvarchar2(20),
    insurance_Number    nvarchar2(15),
    bank_Account_Number nvarchar2(15)
);
create sequence employees_seq start with 1 increment by 1;

CREATE TABLE Employment_Contracts
(
    id                 NUMBER PRIMARY KEY,
    employee_id        NUMBER REFERENCES employees (id),
    issuance_date      DATE,
    hire_date          DATE,
    termination_date   DATE,
    contract_type      VARCHAR2(50),
    department         VARCHAR2(50),
    job_title          VARCHAR2(50),
    position           VARCHAR2(50),

    daily_salary       NUMBER(15, 2),
    bazar_kar          NUMBER(15, 2),
    fogholade_shoghl   NUMBER(15, 2),
    housing_allowance  NUMBER(15, 2),
    marriage_allowance NUMBER(15, 2),
    child_allowance    NUMBER(15, 2),
    food_allowance     NUMBER(15, 2)

);
create sequence Employment_Contracts_seq start with 1 increment by 1;


CREATE TABLE Work_Record_Monthly
(
    id               number primary key,
    employees_id     number REFERENCES employees (id),
    month            nvarchar2(10),
    year             nvarchar2(5),
    days_Worked      number,
    over_time_Hours  nvarchar2(10),
    under_Time_Hours nvarchar2(10),
    leave            nvarchar2(10),
    advance          numeric(12, 2)
);
create sequence Work_Record_Monthly_seq start with 1 increment by 1;


create TABLE Payslips
(
    id              number primary key,
    users_id        number references users (id),
    employees_id    number REFERENCES employees (id),
    work_Records_id number REFERENCES Work_Record_Monthly (id),
    issueDate       date
);
create sequence Payslips_seq start with 1 increment by 1;

CREATE TABLE Missions
(
    id            number primary key,
    employee_id   NUMBER REFERENCES employees (id),
    start_mission Timestamp,
    end_mission   Timestamp
);
create sequence Missions_seq start with 1 increment by 1;


CREATE TABLE loan_Type
(
    id                 NUMBER PRIMARY KEY,
    loan_type          NVARCHAR2(20),
    loan_amount        NUMBER(15, 2), --مبلغ وام
    loan_interest      NUMBER(5, 2),  --بهره وام
    total_installments NUMBER--تعداد افساط
);
create sequence loans_type_seq start with 1 increment by 1;

CREATE TABLE Employee_Loan
(
    id               NUMBER PRIMARY KEY,
    employee_id      NUMBER REFERENCES employees (id),
    loan_type_id     NUMBER REFERENCES loan_type (id), -- ربط به نوع وام
    loan_start_date  DATE,
    loan_finish_date DATE
);
create sequence Employee_Loan_seq start with 1 increment by 1;

CREATE TABLE Loan_Installments
(
    id               NUMBER PRIMARY KEY,
    employee_loan_id NUMBER REFERENCES Employee_Loan (id) ON DELETE CASCADE,
    payslip_id       NUMBER REFERENCES payslips (id),
    amount_paid      NUMBER(15, 2),
    payment_date     DATE
);
create sequence Loan_Installments_seq start with 1 increment by 1;


CREATE TABLE allowance
(
    id                 NUMBER PRIMARY KEY,
    YEAR               NVARCHAR2(8),
    HOUSING_ALLOWANCE  NUMBER(15, 2),
    MARRIAGE_ALLOWANCE NUMBER(15, 2),
    CHILD_ALLOWANCE    NUMBER(15, 2),
    FOOD_ALLOWANCE     NUMBER(15, 2)
);
create sequence allowances_seq start with 1 increment by 1;







