#--
@Name=provider.broker.select_one
SELECT * FROM BROKER WHERE ID = :0

#--
@Name=provider.broker.select_id_by_name
SELECT ID FROM BROKER WHERE NAME = :0

#--
@Name=provider.broker.select_all_names
SELECT NAME FROM BROKER ORDER BY NAME ASC

#--
@Name=provider.broker.select_all
SELECT * FROM BROKER

#--
@Name=provider.broker.upsert_one
MERGE INTO BROKER
USING (SELECT NAME FROM BROKER WHERE NAME = :0)
   AS VALS(NAME) ON BROKER.NAME = VALS.NAME
 WHEN NOT MATCHED THEN INSERT VALUES NULL, :1

