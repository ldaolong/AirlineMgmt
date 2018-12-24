drop table t_user;
CREATE TABLE t_user(
   id INT PRIMARY KEY     NOT NULL,
   userName           TEXT    NOT NULL,
   password      VARCHAR(100),
    email		  TEXT,
    status	  VARCHAR(10),
   address        VARCHAR(50),
   phoneNumber    VARCHAR(50),
   superviseBy   VARCHAR(50),
   department    VARCHAR(50),

   description TEXT  ,
   isLocked 	 VARCHAR(50),
   isDisabled	 VARCHAR(50)
); 
CREATE SEQUENCE seq_user_id
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1 
  CYCLE; 
  
  -- SELECT nextval('seq_user_id')；
  
 drop table t_airline_source;
CREATE TABLE t_airline_source(
   id INT PRIMARY KEY     NOT NULL,
   url            VARCHAR(200)    NOT NULL,
   url2            VARCHAR(200)    NOT NULL,
   param      VARCHAR(200)
); 
insert into t_airline_source(id,url,url2,param) values('1','http://172.18.166.8/js_fois/index.do','http://172.18.166.8/js_fois/history.do','?typoe=3');
  
 drop table t_airline_rule;
 CREATE TABLE t_airline_rule(
   id INT PRIMARY KEY     NOT NULL,
   name            VARCHAR(200)    NOT NULL,
   rule      VARCHAR(200)
); 
insert into t_airline_rule(id,name,rule) values('1','flight_number','MU%');

drop table t_airline;
CREATE TABLE t_airline(
   id INT PRIMARY KEY     NOT NULL,
    device_number  VARCHAR(20),
    flight_number  VARCHAR(50),
    take_off_loc  VARCHAR(50),
    take_off_time  time,
    landing_loc  VARCHAR(50),
    landing_time  time,
    type  VARCHAR(20),
    airline_date  date,
    createdBy VARCHAR(50),
    createdAt  date,
    updatedBy VARCHAR(50),
    updatedAt  date
);   

CREATE SEQUENCE seq_airline_id
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1 
  CYCLE; 
  
drop table t_airline_rule;
CREATE TABLE t_airline_rule(
   id INT PRIMARY KEY     NOT NULL,
   name      VARCHAR(200) ,
   rule      VARCHAR(200),
   createdBy VARCHAR(50),
   createAt  date,
   updatedBy VARCHAR(50),
   updateAt  date
) ;
insert into t_airline_rule(id,name,rule) values(1,'flight_number','MU%');

CREATE SEQUENCE seq_airline_rule_id
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1 
  CYCLE; 
  
drop table t_airline_template;
CREATE TABLE t_airline_template(
   id INT PRIMARY KEY     NOT NULL,
   name      VARCHAR(200) ,
   file_suffix      VARCHAR(200),
   createdBy VARCHAR(50),
   createAt  date,
   updatedBy VARCHAR(50),
   updateAt  date
) ;  
insert into t_airline_template(id,name,file_suffix) values(1,'demo','.xls');

CREATE INDEX t_airline_idx ON t_airline (airline_date);

