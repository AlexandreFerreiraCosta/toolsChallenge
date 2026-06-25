INSERT INTO transaction
(id, transaction_id, card, value, "date", establishment, nsu, authorization_code, status, payment_method_type, installments)
VALUES('6165364e-8491-4a05-b029-af2350536e03'::uuid, '102030405062', '5353276659629461', 100.50, '1992-07-30 12:33:20.000', 'Loja teste 1', '336830470902', 'W8TKL4M8B', 'AUTHORIZED', 'CASH', 1);

INSERT INTO transaction
(id, transaction_id, card, value, "date", establishment, nsu, authorization_code, status, payment_method_type, installments)
VALUES('af7f7c04-c714-4f20-b0ad-a46125bf2e1b'::uuid, '102030405067', '5353276659629461', 150.50, '2026-06-25 10:59:20.000', 'Loja Teste 1', '554016765882', 'YRZ19JIDB', 'REVERSED', 'CASH', 1);

INSERT INTO transaction
(id, transaction_id, card, value, "date", establishment, nsu, authorization_code, status, payment_method_type, installments)
VALUES('3e7be137-5117-48b0-bb9c-78b1c0d5754e'::uuid, '102030405068', '5353276659629461', 1500.50, '2026-06-25 10:59:20.000', 'Loja Teste', NULL, NULL, 'DENIED', 'CASH', 1);