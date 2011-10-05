#--
@Name=provider.trade_movement.select_all
SELECT * FROM PROVIDER

@Name=provider.trade_movement.select_unique_by_name
SELECT * FROM PROVIDER WHERE NAME=:0