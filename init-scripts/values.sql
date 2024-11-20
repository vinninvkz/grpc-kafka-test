create table usver (
    id serial primary key ,
    lastname varchar(255) not null ,
    name varchar(255) not null
);


insert into usver(id, lastname, name)
values
    (1, 'Ivanov', 'Ivan'),
    (2, 'Petrov', 'Petr'),
    (3, 'Alekseev', 'Alex');
