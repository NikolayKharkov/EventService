create table locations (
    id SERIAL PRIMARY KEY,
    name varchar NOT NULL,
    address varchar NOT NULL,
    capacity int,
    description varchar NOT NULL,
    UNIQUE(name, address, description),
    constraint location_capacity_not_negative check (capacity >= 0)
);