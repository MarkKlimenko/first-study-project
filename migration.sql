CREATE TABLE users
(
    id            VARCHAR(100) PRIMARY KEY,
    login         VARCHAR(100) NOT NULL UNIQUE,
    name          VARCHAR(200) NOT NULL,
    last_name     VARCHAR(200) NOT NULL,
    creation_date TIMESTAMP    NOT NULL
);

insert into users(id,
                  login,
                  name,
                  last_name,
                  creation_date)
values ('123', 'login1', 'name1', 'surname1', '2024-05-12');

select * from users where id = '123';
