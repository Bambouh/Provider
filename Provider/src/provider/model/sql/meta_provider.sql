#--
@Name=provider.meta_provider.select_all
SELECT * FROM PROVIDER

@Name=provider.meta_provider.select_unique_by_name
SELECT * FROM PROVIDER WHERE NAME=:0