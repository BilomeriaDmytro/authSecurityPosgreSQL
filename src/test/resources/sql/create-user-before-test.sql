delete from user_roles;
delete from users;

insert into users
values('1','admin','admin', 'admin', 'admin@gmail.com','$2a$10$6qA53xVmH43Q/tqlmwVrt.NfXBoK.VEhanFvc19kDPAXKsxvKN6kW', '0');

insert into user_roles values('1','1');
insert into user_roles values('1','2');
