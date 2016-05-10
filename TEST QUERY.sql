USE ZTP_zad7

-- SQL CAPSLOCK CREW

--Had insurance in this day
SELECT COUNT(*)     
FROM tb_customer  c 
JOIN tb_insurance i ON i.customerId=c.Id
JOIN tb_model     m ON i.modelId=m.Id
WHERE m.model='Ford Mondeo' AND
(CAST ('2015-05-15' AS DATE)) BETWEEN i.dateFrom AND i.dateTo

--Hadn't insurance in this day
SELECT COUNT(*) 
FROM tb_customer  c 
JOIN tb_insurance i ON i.customerId=c.Id
JOIN tb_model     m ON i.modelId=m.Id
WHERE m.model='Ford Mondeo' AND
(CAST ('2015-05-15' AS DATE)) NOT BETWEEN i.dateFrom AND i.dateTo
