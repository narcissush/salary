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

CREATE TABLE Employment_Contracts
(
    id                  NUMBER PRIMARY KEY,
    employee_id         NUMBER NOT NULL,
    issuance_personnel_order_date nvarchar2(10),
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
create sequence Employment_Contracts_seq start with 1 increment by 1;


CREATE TABLE Work_Record_Monthly
(
    id               number primary key,
    days_Worked      number,
    over_time_Hours  nvarchar2(10),
    under_Time_Hours nvarchar2(10),
    leave               number,
    advance          numeric(12, 2)
);
create sequence Work_Record_Monthly_seq start with 1 increment by 1;


create TABLE Payslips
(
    id              number primary key,
    users_id        number references users (id),
    employees_id    number REFERENCES employees (id),
    work_Records_id number REFERENCES Work_Record_Monthly (id),
    issueDate       date,
    month           nvarchar2(10),
    year            nvarchar2(4)
);
create sequence Payslips_seq start with 1 increment by 1;

CREATE TABLE Missions
(
    id               number primary key,
    Payslips         number references Payslips (id),
    start_mission    Timestamp,
    end_mission      Timestamp
);
create sequence Missions_seq start with 1 increment by 1;


CREATE TABLE loans
(
    id                 NUMBER PRIMARY KEY,
    employee_id        NUMBER REFERENCES employees (id),
    loan_type          NVARCHAR2(20),
    loan_amount        NUMBER, --مبلغ وام
    loan_interest      NUMBER, --بهره وام
    total_installments NUMBER ,--تعداد افساط
    Loan_Start_Date     Date,
    Loan_Finish_date    Date

);
create sequence loans_seq start with 1 increment by 1;


CREATE TABLE Loan_Installments
(
    id           NUMBER PRIMARY KEY,
    loan_id      NUMBER REFERENCES loans (id) ON DELETE CASCADE,
    payslip_id   NUMBER REFERENCES payslips (id),
    amount_paid  NUMBER, --مبلغ_پرداختی
    payment_date DATE
);
create sequence Loan_Installments_seq start with 1 increment by 1;



