create table users
(
    id      SERIAL,
    name    varchar(50),
    balance int,
    primary key (id)
);

create table user_transaction
(
    id               SERIAL,
    user_id          bigint,
    amount           int,
    transaction_date timestamp,
    foreign key (user_id) references users (id) on delete cascade
);

insert into users
    (name, balance)
values ('sam', 10000),
       ('mike', 12000),
       ('jake', 8000),
       ('marshal', 20000);