create table if not exists MEMBERS (
    id serial primary key,
    name varchar(100),
    email varchar(100),
    project varchar(100)
);