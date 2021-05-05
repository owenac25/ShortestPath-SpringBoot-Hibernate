drop table if exists planet;
drop table if exists route;

create table planet (planet_node varchar(250) primary key, planet_name varchar(250) not null);
create table route (route_id int primary key, planet_origin varchar(250), planet_destination varchar(250), distance double);