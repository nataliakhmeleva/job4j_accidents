CREATE TABLE accidents (
  id serial primary key,
  name varchar        not null,
  text varchar        not null,
  address varchar        not null,
  type_id int not null references accident_types(id)
);