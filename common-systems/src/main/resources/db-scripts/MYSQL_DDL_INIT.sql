CREATE USER 'local'@'localhost' IDENTIFIED BY 'password';

GRANT ALL PRIVILEGES ON * . * TO 'local'@'localhost';

GRANT ALL privileges on * . *  TO 'local'@'localhost';

create table file_system_objects (id int not null auto_increment, parent_id int, name varchar(255), path varchar(1000), object_type tinyint, localpath varchar(1000), primary key(id));


insert into file_system_objects(id, name, path, object_type, localpath) values ( 1, 'test', 'some path', 1, 'some local path');
