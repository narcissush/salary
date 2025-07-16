CREATE OR REPLACE VIEW SalaryReportNet AS
WITH base_data AS (
    SELECT
        p.id AS payslip_id,
        u.user_name,
        e.first_name,
        e.last_name,
        e.national_id,
        e.education,
        e.gender,
        e.birth_date,
        e.insurance_number,
        e.bank_account_number,
        c.job_title,
        c.position,
        c.hire_date,
        c.termination_date,

        wr.days_worked * NVL(c.daily_salary,0)                        AS base_salary,
        wr.over_time_hours * (NVL(c.daily_salary,0) / 8 * 1.4)         AS over_time,
        NVL(e.number_of_children,0) * (NVL(c.child_allowance,0)/30) * wr.days_worked AS child_allowance,
        CASE WHEN e.married='1' THEN (NVL(c.marriage_allowance,0)/30)*wr.days_worked ELSE 0 END AS marriage_allowance,
        (NVL(c.housing_allowance,0)/30) * wr.days_worked               AS housing_allowance,
        (NVL(c.food_allowance,0)/30) * wr.days_worked                  AS food_allowance,

        -- محاسبه ماموریت با تفکیک ساعات
        NVL(
                (
                    -- ساعات داخل ۸ تا ۱۶
                    (
                        GREATEST(
                                LEAST(m.end_mission,
                                      TRUNC(m.start_mission) + INTERVAL '16' HOUR),
                                TRUNC(m.start_mission) + INTERVAL '8' HOUR
                        )
                            - GREATEST(
                                m.start_mission,
                                TRUNC(m.start_mission) + INTERVAL '8' HOUR
                              )
                        ) * 24
                        * (c.daily_salary / 8)
                        * 0.6
                        +
                        -- ساعات خارج شیفت
                    (
                        (m.end_mission - m.start_mission) * 24
                            - (
                            GREATEST(
                                    LEAST(m.end_mission,
                                          TRUNC(m.start_mission) + INTERVAL '16' HOUR),
                                    TRUNC(m.start_mission) + INTERVAL '8' HOUR
                            )
                                - GREATEST(
                                    m.start_mission,
                                    TRUNC(m.start_mission) + INTERVAL '8' HOUR
                                  )
                            )
                        ) * (c.daily_salary / 8)
                        * 0.6 * 1.4
                    ), 0
        ) AS mission_allowance,

        (
            wr.days_worked * NVL(c.daily_salary,0)
                + wr.over_time_hours * (NVL(c.daily_salary,0)/8 * 1.4)
                + NVL(e.number_of_children,0) * (NVL(c.child_allowance,0)/30) * wr.days_worked
                + CASE WHEN e.married='1' THEN (NVL(c.marriage_allowance,0)/30)*wr.days_worked ELSE 0 END
                + (NVL(c.housing_allowance,0)/30)*wr.days_worked
                + (NVL(c.food_allowance,0)/30)*wr.days_worked
                + NVL(
                    (
                        (
                            GREATEST(
                                    LEAST(m.end_mission,
                                          TRUNC(m.start_mission) + INTERVAL '16' HOUR),
                                    TRUNC(m.start_mission) + INTERVAL '8' HOUR
                            )
                                - GREATEST(
                                    m.start_mission,
                                    TRUNC(m.start_mission) + INTERVAL '8' HOUR
                                  )
                            ) * 24
                            * (c.daily_salary / 8)
                            * 0.6
                            +
                        (
                            (m.end_mission - m.start_mission) * 24
                                - (
                                GREATEST(
                                        LEAST(m.end_mission,
                                              TRUNC(m.start_mission) + INTERVAL '16' HOUR),
                                        TRUNC(m.start_mission) + INTERVAL '8' HOUR
                                )
                                    - GREATEST(
                                        m.start_mission,
                                        TRUNC(m.start_mission) + INTERVAL '8' HOUR
                                      )
                                )
                            ) * (c.daily_salary / 8)
                            * 0.6 * 1.4
                        ), 0
                  )
            ) AS gross_salary,

        (SELECT NVL(SUM(li.amount_paid),0)
         FROM Loan_installments li
         WHERE li.payslip_id = p.id
        ) AS loan_payment,

        wr.under_time_hours * (NVL(c.daily_salary,0) / 8) AS under_time_payment,

        p.issueDate,
        p.month,
        p.year

    FROM payslips p
             JOIN users u ON p.users_id = u.id
             JOIN employees e ON p.employees_id = e.id
             JOIN employmentcontract c ON c.employee_id = e.id
             JOIN "Work_Record-Monthly" wr ON p.Work_Record_Monthly = wr.id
             LEFT JOIN MISSIONS m ON m.payslips = p.id
)
SELECT
    bd.*,
    ROUND(bd.gross_salary * 0.07, 2)                          AS employee_ins,
    ROUND(bd.gross_salary * 0.23, 2)                          AS employer_ins,
    ROUND(
            CASE
                WHEN bd.gross_salary <= 240000000 THEN 0
                WHEN bd.gross_salary <= 300000000 THEN (bd.gross_salary-240000000)*0.10
                WHEN bd.gross_salary <= 380000000 THEN (300000000-240000000)*0.10 + (bd.gross_salary-300000000)*0.15
                WHEN bd.gross_salary <= 500000000 THEN (300000000-240000000)*0.10 + (380000000-300000000)*0.15 + (bd.gross_salary-380000000)*0.20
                WHEN bd.gross_salary <= 667000000 THEN (300000000-240000000)*0.10 + (380000000-300000000)*0.15 + (500000000-380000000)*0.20 + (bd.gross_salary-500000000)*0.25
                ELSE (300000000-240000000)*0.10 + (380000000-300000000)*0.15 + (500000000-380000000)*0.20 + (667000000-500000000)*0.25 + (bd.gross_salary-667000000)*0.30
                END
        ,0) AS monthly_tax,
    ROUND(
            bd.gross_salary * 0.07 + bd.gross_salary * 0.23
                + CASE
                      WHEN bd.gross_salary <= 240000000 THEN 0
                      WHEN bd.gross_salary <= 300000000 THEN (bd.gross_salary-240000000)*0.10
                      WHEN bd.gross_salary <= 380000000 THEN (300000000-240000000)*0.10 + (bd.gross_salary-300000000)*0.15
                      WHEN bd.gross_salary <= 500000000 THEN (300000000-240000000)*0.10 + (380000000-300000000)*0.15 + (bd.gross_salary-380000000)*0.20
                      WHEN bd.gross_salary <= 667000000 THEN (300000000-240000000)*0.10 + (380000000-300000000)*0.15 + (500000000-380000000)*0.20 + (bd.gross_salary-500000000)*0.25
                      ELSE (300000000-240000000)*0.10 + (380000000-300000000)*0.15 + (500000000-380000000)*0.20 + (667000000-500000000)*0.25 + (bd.gross_salary-667000000)*0.30
                END
                + bd.loan_payment + bd.under_time_payment
        ,0) AS total_deductions,
    ROUND(
            bd.gross_salary
                - (
                bd.gross_salary * 0.07 + bd.gross_salary * 0.23
                    + CASE
                          WHEN bd.gross_salary <= 240000000 THEN 0
                          WHEN bd.gross_salary <= 300000000 THEN (bd.gross_salary-240000000)*0.10
                          WHEN bd.gross_salary <= 380000000 THEN (300000000-240000000)*0.10 + (bd.gross_salary-300000000)*0.15
                          WHEN bd.gross_salary <= 500000000 THEN (300000000-240000000)*0.10 + (380000000-300000000)*0.15 + (bd.gross_salary-380000000)*0.20
                          WHEN bd.gross_salary <= 667000000 THEN (300000000-240000000)*0.10 + (380000000-300000000)*0.15 + (500000000-380000000)*0.20 + (bd.gross_salary-500000000)*0.25
                          ELSE (300000000-240000000)*0.10 + (380000000-300000000)*0.15 + (500000000-380000000)*0.20 + (667000000-500000000)*0.25 + (bd.gross_salary-667000000)*0.30
                    END
                    + bd.loan_payment + bd.under_time_payment
                ),0
    ) AS net_salary
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
         LEFT JOIN Loan_installments li ON li.loan_id = l.id
GROUP BY e.id, e.first_name, e.last_name,
         l.id, l.loan_type, l.loan_amount, l.loan_interest,
         l.total_installments, l.loan_start_date;
