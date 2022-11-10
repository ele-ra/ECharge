create table if not exists role
(
    id int auto_increment,
    name text unique not null,
    primary key (id)
);

create table if not exists users
(
    id bigint auto_increment,
    username text unique,
    password text,
    roleid int,
    primary key(id),
        foreign key (roleid) references role(id)
);

create table if not exists customer
(
    id bigint auto_increment,
    firstname text,
    surname text,
    userid bigint unique,
    primary key(id),
        foreign key (userid) references users(id)
            on update cascade on delete set null
);

create unique index if not exists customer_userid_uindex
    on customer (id, userid);

create table if not exists chargepoint
(
    id bigint auto_increment,
    name text,
    sn text unique,
    customerid bigint,
    primary key(id),
        foreign key (customerid) references customer(id)
            on update cascade on delete cascade
);

create table if not exists connector
(
    id bigint auto_increment,
    number int,
    chargepointid bigint,
    primary key(id),
        foreign key (chargepointid) references chargepoint(id)
            on update cascade on delete cascade
);

-- Assume connectors belong are not shared between charge points
create unique index if not exists connector_chargepointid_uindex
    on connector (id, chargepointid);

create table if not exists vehicle
(
    id bigint auto_increment,
    name text,
    registrationplate text unique,
    meter double precision,
    primary key(id)
);

create table if not exists rfidtag
(
    id bigint auto_increment,
    name text,
    number int unique,
    customerid bigint,
    vehicleid bigint,
    primary key(id),
        foreign key (customerid) references customer(id)
            on update cascade on delete cascade,
        foreign key (vehicleid) references vehicle(id)
            on update cascade on delete cascade
);

create table if not exists chargingsession
(
    id bigint auto_increment,
    starttime timestamp with time zone,
    startmeter double precision,
    endtime timestamp with time zone,
    endmeter double precision,
    message text,
    customerid bigint,
    connectorid bigint,
    vehicleid bigint,
    primary key(id),
        foreign key (customerid) references customer(id)
            on update cascade on delete no action,
        foreign key (connectorid) references connector(id)
            on update cascade on delete no action,
        foreign key (vehicleid) references vehicle(id)
            on update cascade on delete no action
);


