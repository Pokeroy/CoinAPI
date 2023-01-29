create table Test(
id int(10) AUTO_INCREMENT,
currency varchar(200) UNIQUE,
chinese_name varchar(200) not null,
PRIMARY KEY (id)
);