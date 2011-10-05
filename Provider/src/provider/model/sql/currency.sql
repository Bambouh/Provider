#--
@Name=provider.currency.select_one
SELECT * FROM CURRENCY WHERE ID = :0

#--
@Name=provider.currency.select_id_by_cur
SELECT ID FROM CURRENCY WHERE CUR_1 = :0 AND CUR_2 = :1

#--
@Name=provider.currency.select_all_id_per_pair
SELECT CONCAT(CONCAT(CUR_1,:0),CUR_2), ID FROM CURRENCY ORDER BY CUR_1 ASC

#--
@Name=provider.currency.upsert_one
MERGE INTO CURRENCY
USING (SELECT CUR_1, CUR_2 FROM CURRENCY WHERE CUR_1 = :0 AND CUR_2 = :1)
   AS VALS(CUR_1, CUR_2) ON CURRENCY.CUR_1 = VALS.CUR_1 
							  AND CURRENCY.CUR_2 = VALS.CUR_2
 WHEN NOT MATCHED THEN INSERT VALUES NULL, :2, :3