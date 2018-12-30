-- Copyright (c) David Cody Burrows 2018
-- This creates the Fridginator database schema in case
-- I want to make a fresh database, or decide to change db
-- vendors
-- -----------------------------------------------------------------------------
create table fridge(
    id          integer primary key,
    items       integer,
    users       integer,
    secret_key  varchar(16),
    name        varchar(24)
);

create table audit(
    id          integer primary key,
    date        timestamp,
    message     varchar(128),

    -- foreign keys
    user_id     integer,

    foreign key (user_id) references user(id)
);

create table item(
    id          integer primary key,
    name        varchar(24),
    weekly      boolean, -- might need to change to integer
    unit        varchar(10)
);

create table purchasable_quantity(
    id          integer primary key,
    active      boolean,
    price       real,
    qty         real,

    -- foreign keys
    item_id     integer,

    foreign key (item_id) references item(id)
);

create table misc_list(
    id          integer primary key,
    line        varchar(64),
    checked     boolean,

    -- foreign keys
    user_id     integer,

    foreign key (user_id) references user(id)
);

create table shared_list(
    id          integer primary key,
    checked     boolean, -- whether or not the user crossed it off the list
    confirmed   boolean, -- whether or not the user verified that this item
                         -- is actually needed 
    date        timestamp,
    num         integer, -- the number of PQs to purchase

    -- foreign keys
    user_id     integer,
    pq_id       integer,

    foreign key (user_id) references user(id),
    foreign key (pq_id) references purchasable_quantity(id)
);

create table user(
    id          integer primary key,
    name        varchar(32),
    password    varchar(64), -- this will be encrypted maybe ;)
    joined      date,
    saved       real,
    spent       real,
    shop_day    integer,
    shop_weeks  integer  -- generate a list for this user every X weeks.
                         -- typically it will be 1, but enter 2 for biweekly
);

create table sharing(
    id          integer primary key,
    expected    real,
    actual      real,

    -- foreign keys
    item_id     integer,
    user_id     integer,

    foreign key (item_id) references item(id),
    foreign key (user_id) references user(id)
);

create table inventory(
    id          integer primary key,
    qty         real,
    point       boolean, -- hard data point or not for usage calculation
    restock     boolean, -- this update represents a restock
    auto        boolean, -- this update represents consumption
    time        timestamp,

    -- foreign keys
    user_id     integer,
    item_id     integer,

    foreign key (user_id) references user(id),
    foreign key (item_id) references item(id)
);
