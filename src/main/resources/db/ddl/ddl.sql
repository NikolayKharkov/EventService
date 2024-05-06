create table locations (
    id SERIAL PRIMARY KEY,
    name varchar NOT NULL,
    address varchar NOT NULL,
    capacity int,
    description varchar NOT NULL,
    UNIQUE(name, address, description),
    constraint location_capacity_not_negative check (capacity >= 0)
);

create table users (
   id SERIAL PRIMARY KEY,
   login varchar NOT NULL,
   password varchar NOT NULL,
   role varchar NOT NULL,
   age int NOT NULL,
   UNIQUE(login)
);

create table events(
   id SERIAL PRIMARY KEY,
   event_date timestamp without time zone,
   duration int,
   cost int,
   max_places int,
   location_id int,
   name varchar,
   event_status int,
   owner_id int,
   occupied_places int,
   CONSTRAINT fk_location
       FOREIGN KEY(location_id)
           REFERENCES locations(id),
   CONSTRAINT fk_user_id
       FOREIGN KEY(owner_id)
           REFERENCES users(id)
);

create table registrations(
   id SERIAL PRIMARY KEY,
   events_id int,
   user_id int,
   CONSTRAINT fk_reg_event_id
       FOREIGN KEY(events_id)
           REFERENCES events(id),
   CONSTRAINT fk_req_user_id
       FOREIGN KEY(user_id)
           REFERENCES users(id),
   UNIQUE(events_id, user_id)
);