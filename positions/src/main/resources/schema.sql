CREATE TABLE IF NOT EXISTS customers(
id integer primary key auto_increment,
email varchar(100) unique not null
);