--创建电影信息表
create table movie_info(
id int(5) NOT NULL AUTO_INCREMENT,
film_name varchar(200) unique NOT NULL ,
upload_time datetime DEFAULT NULL,
size bigint(20) NOT NULL,
file_type varchar(10) NOT NULL,
hot_word varchar(10) DEFAULT NULL,
play_times int(10) DEFAULT '0',
download_times int(10) DEFAULT '0',
primary key(id)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
--创建电影用户表
create table movie_user(
id int(5) NOT NULL AUTO_INCREMENT,
user_name varchar(40) unique NOT NULL ,
user_password varchar(40) NOT NULL ,
primary key(id)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;