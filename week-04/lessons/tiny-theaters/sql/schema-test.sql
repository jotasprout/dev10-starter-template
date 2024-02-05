drop database if exists tiny_theaters_test;
create database tiny_theaters_test;
use tiny_theaters_test;

create table theater (
	theater_id int primary key auto_increment,
    theater_name varchar(128) not null,
    address varchar(256) not null,
    phone varchar(15) not null,
    email_address varchar(256) not null,
    hidden boolean not null default false
);

create table seat (
	seat_id int primary key auto_increment,
	label varchar(5) not null,
    theater_id int not null,
	constraint fk_seat_theater_id
		foreign key (theater_id)
        references theater(theater_id),
	constraint uq_seat_theater
		unique (label, theater_id)
);

create table customer (
	customer_id int primary key auto_increment,
    first_name varchar(50) not null,
    last_name varchar(50) not null,
    phone varchar(15) null,
    email_address varchar(256) not null unique
);

create table `show` (
	show_id int primary key auto_increment,
    show_name varchar(128) not null,
    hidden boolean not null default false
);

create table performance (
	performance_id int primary key auto_increment,
    performance_date date not null,
    ticket_price decimal(6, 2) not null,
    show_id int not null,
    theater_id int not null,
    constraint fk_performance_show_id
		foreign key (show_id)
        references `show`(show_id),
	constraint fk_performance_theater_id
		foreign key (theater_id)
        references theater(theater_id),
	constraint uq_performance_date
		unique (performance_date, theater_id)
);

create table ticket (
	ticket_id int primary key auto_increment,
    customer_id int not null,
    performance_id int not null,
    seat_id int not null,
    constraint fk_ticket_customer_id
		foreign key(customer_id)
        references customer(customer_id),
	constraint fk_ticket_performance_id
		foreign key (performance_id)
        references performance(performance_id),
	constraint fk_ticket_seat_id
		foreign key (seat_id)
        references seat(seat_id),
	constraint uq_ticket_performance_seat
		unique (performance_id, seat_id)
);

delimiter //
create procedure set_known_good_state()
begin
	delete from ticket;
	delete from performance;
	delete from `show`;
	delete from customer;
	delete from seat;
	delete from theater;
	alter table ticket auto_increment=1;
	alter table performance auto_increment=1;
	alter table `show` auto_increment=1;
	alter table customer auto_increment=1;
	alter table seat auto_increment=1;
	alter table theater auto_increment=1;
    
    insert into theater (theater_name, address, phone, email_address) values
		('Theater 1', '1 E Exchange St, St Paul, MN 55101', '(651) 555-5551', '1@rcttc.com'),
		('Theater 2', '2 E Exchange St, St Paul, MN 55101', '(651) 555-5552', '2@rcttc.com'),
		('Theater 3', '3 E Exchange St, St Paul, MN 55101', '(651) 555-5553', '3@rcttc.com');
    
	insert into seat (label, theater_id) values
		('A1',1),
		('A2',1),
		('A3',1),
		('A4',1);
        
	insert into `show` (show_name) values
		('Show 1'),
		('Show 2');
        
	set @next_year = year(curdate()) + 1;
        
	insert into performance (performance_date, ticket_price, show_id, theater_id) values
		(date(concat(@next_year, '-01-01')), 11.00, 1, 1),
        (date(concat(@next_year, '-02-01')), 12.00, 2, 2),
        (date(concat(@next_year, '-03-01')), 13.00, 1, 3);
        
	insert into customer (first_name, last_name, phone, email_address) values
		('First 1','Last 1', '555-5551','fl1@example.com'),
		('First 2','Last 2', '555-5552','fl2@example.com'),
		('First 3','Last 3', '555-5553','fl3@example.com'),
		('First 4','Last 4', '555-5554','fl4@example.com');
	
    insert into ticket (customer_id, performance_id, seat_id) values
		(1, 1, 1),
        (2, 1, 2),
        (3, 1, 3),
        (4, 2, 1);
end//
delimiter ;