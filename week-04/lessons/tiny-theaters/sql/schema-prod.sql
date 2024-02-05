drop database if exists tiny_theaters;
create database tiny_theaters;
use tiny_theaters;

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