create table if not exists "order"
(
    order_id   bigserial               not null
        constraint order_pkey
            primary key,
    amount     numeric   default 0     not null,
    created_at timestamp default now() not null,
    discount   numeric   default 0     not null,
    name       varchar(100)            not null,
    updated_at timestamp default now() not null
);
