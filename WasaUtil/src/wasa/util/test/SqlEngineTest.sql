#un petit test :)
@Name=selectAllTrades
SELECT * FROM DOUDOU

#un autre avec des paramètres
@Name=select_All_Doudou_per_date
SELECT * FROM DOUDOU WHERE START = :0 and id in (:1); 

#--Le broker
@Name=provider.init.create_table_broker
CREATE TABLE BROKER
(
	ID 					INT GENERATED BY DEFAULT AS IDENTITY(START WITH 1) PRIMARY KEY,
	NAME				VARCHAR(100) NOT NULL,
	CONSTRAINT			BROKER_NAME_UNIQUE UNIQUE(NAME)
)