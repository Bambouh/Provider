#un petit test :)
@Name=selectAllTrades
SELECT * FROM DOUDOU

#un autre avec des paramètres
@Name=select_All_Doudou_per_date
SELECT * FROM DOUDOU WHERE START = :0 and id in (:1); 

