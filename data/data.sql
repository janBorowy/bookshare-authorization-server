insert into user(username, encoded_password, email, disabled)
values ('admin',
        '{bcrypt}$2a$08$2Yz0hOWvfT9tJi4ChIZ2R.Jl.4fjuv9q7YMos6E2uNEu.XSLU3Uum',
        'admin@mail.com',
        false);
-- password is adminadmin

insert into user_role
values (1, 'ADMIN')