create table user_transaction
(
    id               SERIAL,
    user_id          bigint,
    amount           int,
    transaction_date timestamp,
    foreign key (user_id) references users (id) on delete cascade
);