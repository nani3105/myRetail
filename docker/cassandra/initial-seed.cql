CREATE KEYSPACE IF NOT EXISTS myretail WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : 1 };

USE myretail;

CREATE TABLE products (
id bigint PRIMARY KEY,
name text,
price decimal,
currency_code varchar
);