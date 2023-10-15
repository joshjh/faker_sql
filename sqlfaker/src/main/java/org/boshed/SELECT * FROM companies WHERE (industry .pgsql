SELECT * INTO TEMPORARY TABLE big_companies  
FROM companies WHERE (employees > 100);