INSERT INTO user (id, email, name, monthly_salary, monthly_expense)
VALUES (1, 'Michael@gmail.com', 'Michael', '20000.0', '10000.0');

INSERT INTO user (id, email, name, monthly_salary, monthly_expense)
VALUES (2, 'Jesse@gmail.com', 'Jesse', '2000.0', '1200.0');

INSERT INTO user (id, email, name, monthly_salary, monthly_expense)
VALUES (3, 'Tim@gmail.com', 'Tim', '2.0', '2.0');

INSERT INTO user (id, email, name, monthly_salary, monthly_expense)
VALUES (4, 'Peter@gmail.com', 'Peter', '3998.00', '1167.03');

INSERT INTO user (id, email, name, monthly_salary, monthly_expense)
VALUES (5, 'Jane@gmail.com', 'Jane', '7865.0', '7865.0');

INSERT INTO account(id, account_number, account_type, account_balance, date_created, interest, minimal_weekly_repayment,
                    monthly_account_fee, user_id)
VALUES (1, '1211211', 'ZIP_PAY', 0, '2023-02-14T10:10:15+11:00', 0.03, 10, 10, 4);

INSERT INTO account(id, account_number, account_type, account_balance, date_created, interest, minimal_weekly_repayment,
                    monthly_account_fee, user_id)
VALUES (2, '2321321', 'ZIP_MONEY', 0, '2023-02-13T10:10:15+11:00', 0.02, 60, 3.33, 5);
