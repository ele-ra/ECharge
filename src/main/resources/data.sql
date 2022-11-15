-- Roles and users
INSERT INTO role (id, name) VALUES (1, 'Admin');
INSERT INTO role (id, name) VALUES (2, 'Customer');

-- Password for all = password
INSERT INTO users (username, password, roleid) 
    select 'User1', '$2a$12$pVHT8TnhFyzibjDev0Xkie7WJyDDWu17wxr.7WZir2ZTO9GtWeRwi', id 
        from role where lower(name) = 'customer';
INSERT INTO users (username, password, roleid)
    select 'User2', '$2a$12$pVHT8TnhFyzibjDev0Xkie7WJyDDWu17wxr.7WZir2ZTO9GtWeRwi', id
        from role where lower(name) = 'customer';
INSERT INTO users (username, password, roleid) 
    select 'User3', '$2a$12$pVHT8TnhFyzibjDev0Xkie7WJyDDWu17wxr.7WZir2ZTO9GtWeRwi', id
        from role where lower(name) = 'customer';
INSERT INTO users (username, password, roleid) 
    select 'Admin', '$2a$12$pVHT8TnhFyzibjDev0Xkie7WJyDDWu17wxr.7WZir2ZTO9GtWeRwi', id
        from role where lower(name) = 'admin';


INSERT INTO customer(firstname, surname, userid)
    select 'fname_1Has2cpoints', 'Surname1', id from users where username = 'User1';
INSERT INTO customer(firstname, surname, userid)
    select 'fname_2Has1cpoint', 'Surname2', id from users where username = 'User2';
INSERT INTO customer(firstname, surname, userid)
    select 'fname_3Has1cpoint', 'Surname3', id from users where username = 'User3';
INSERT INTO customer(firstname, surname, userid)
    select 'fname_4HasNocpoints', 'Surname4', id from users where username = 'User4';

INSERT INTO chargepoint(name, sn, customerid) select 'cpoint_1Has2Connectors', 'sn1', id from customer where firstname =  'fname_1Has2cpoints';
INSERT INTO chargepoint(name, sn, customerid) select 'cpoint_11Has1Connector', 'sn11', id from customer where firstname =  'fname_1Has2cpoints';
INSERT INTO chargepoint(name, sn, customerid) select 'cpoint_2HasNoConnectors', 'sn2', id from customer where firstname =  'fname_2Has1cpoint';
INSERT INTO chargepoint(name, sn, customerid) select 'cpoint_3Has2Connectors', 'sn3', id from customer where firstname =  'fname_3Has1cpoint';

INSERT INTO connector(number, chargepointid) select 1, id from chargepoint where sn = 'sn1';
INSERT INTO connector(number, chargepointid) select 11, id from chargepoint where sn = 'sn1';
INSERT INTO connector(number, chargepointid) select 111, id from chargepoint where sn = 'sn11';
INSERT INTO connector(number, chargepointid) select 3, id from chargepoint where sn = 'sn3';
INSERT INTO connector(number, chargepointid) select 4, id from chargepoint where sn = 'sn3';

INSERT INTO vehicle(name, registrationplate, meter) VALUES ('v_1', 'rplate1', 10);
INSERT INTO vehicle(name, registrationplate, meter) VALUES ('v_11', 'rplate11', 5);
INSERT INTO vehicle(name, registrationplate, meter) VALUES ('v_2CantCharge_noConnectors', 'rplate2', 1);
INSERT INTO vehicle(name, registrationplate, meter) VALUES ('v_3', 'rplate3', 0);
INSERT INTO vehicle(name, registrationplate, meter) VALUES ('v_4CantCharge_noCpoints', 'rplate4', 0);

INSERT INTO rfidtag(name, number, customerid, vehicleid)
select 'rfidTag1Belongs2fname1', 1, (select id from customer where firstname = 'fname_1Has2cpoints'), (select id from vehicle where name = 'v_1');
INSERT INTO rfidtag(name, number, customerid, vehicleid)
select 'rfidTag2Belongs2fname1', 11, (select id from customer where firstname = 'fname_1Has2cpoints'), (select id from vehicle where name = 'v_11');
INSERT INTO rfidtag(name, number, customerid, vehicleid)
select 'rfidTag2Belongs2fname2', 2, (select id from customer where firstname = 'fname_2Has1cpoint'), (select id from vehicle where name = 'v_2CantCharge_noConnectors');
INSERT INTO rfidtag(name, number, customerid, vehicleid)
select 'rfidTag3Belongs2fname3', 3, (select id from customer where firstname = 'fname_3Has1cpoint'), (select id from vehicle where name = 'v_3');
INSERT INTO rfidtag(name, number, customerid, vehicleid)
select 'rfidTag3Belongs2fname4', 4, (select id from customer where firstname = 'fname_4HasNocpoints'), (select id from vehicle where name = 'v_4CantCharge_noCpoints');





