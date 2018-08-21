CREATE KEYSPACE IF NOT EXISTS myRetail WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : 1 };

CREATE TABLE products (
id bigint PRIMARY KEY,
name text,
price decimal,
currency_code varchar
)