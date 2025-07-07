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
    Salary_Components int REFERENCES Salary_Components (id),
    Deductions        int references Deductions (id),
    issueDate         date,
    period nvarchar(10)
);
create sequence Payslips_seq start with 1 increment by 1;


create TABLE Salary_Components
(
    int         id primary key,
    Payslips_id int REFERENCES Payslips (id)
);
create sequence Salary_Components_seq start with 1 increment by 1;



create TABLE Deductions
(
    int         id primary key,
    Payslips_id int REFERENCES Payslips (id)
);
create sequence Deductions_seq start with 1 increment by 1;



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
    loan_type          nVARCHAR2(10),
    loan_amount        NUMBER,
    loan_interest      NUMBER,
    total_installments NUMBER
);
create sequence loan_seq start with 1 increment by 1;


CREATE TABLE loan_items
(
    id            number PRIMARY KEY,
    loans_id      int REFERENCES loans (id) ON DELETE CASCADE,
    amount_paid   number,
    payment_date  DATE,
    total_payment number
);

create sequence loan_items_seq start with 1 increment by 1;



