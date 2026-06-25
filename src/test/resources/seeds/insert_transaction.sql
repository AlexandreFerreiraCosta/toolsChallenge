INSERT INTO transaction
(transaction_id, card, value, "date", establishment, nsu, authorization_code, status, payment_method_type, installments)
VALUES('102030405062', '5353276659629461', 100.50, '1992-07-30 12:33:20.000', 'Loja teste 1', '336830470902', 'W8TKL4M8B', 'AUTHORIZED', 'CASH', 1);

INSERT INTO transaction
(transaction_id, card, value, "date", establishment, nsu, authorization_code, status, payment_method_type, installments)
VALUES('102030405067', '5353276659629461', 150.50, '2026-06-25 10:59:20.000', 'Loja Teste 1', '554016765882', 'YRZ19JIDB', 'REVERSED', 'CASH', 1);

INSERT INTO transaction
(transaction_id, card, value, "date", establishment, nsu, authorization_code, status, payment_method_type, installments)
VALUES('102030405068', '5353276659629461', 1500.50, '2026-06-25 10:59:20.000', 'Loja Teste', NULL, NULL, 'DENIED', 'CASH', 1);