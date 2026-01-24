create table user
(
    id               serial primary key,
    username         varchar(100) not null,
    encoded_password varchar(80)  not null,
    email            varchar(256) not null unique,
    disabled         boolean      not null
);

create table user_role
(
    user_id serial,
    role    varchar(50),
    foreign key (user_id) references user (id),
    primary key (user_id, role)
);

# These are needed for Jdbc implementations of spring authorization server
# https://github.com/spring-projects/spring-authorization-server/blob/main/oauth2-authorization-server/src/main/resources/org/springframework/security/oauth2/server/authorization/oauth2-authorization-schema.sql
create table oauth2_authorization
(
    id                            varchar(100) not null,
    registered_client_id          varchar(100) not null,
    principal_name                varchar(200) not null,
    authorization_grant_type      varchar(100) not null,
    authorized_scopes             varchar(1000) default null,
    attributes                    blob          default null,
    state                         varchar(500)  default null,
    authorization_code_value      blob          default null,
    authorization_code_issued_at  timestamp     default null,
    authorization_code_expires_at timestamp     default null,
    authorization_code_metadata   blob          default null,
    access_token_value            blob          default null,
    access_token_issued_at        timestamp     default null,
    access_token_expires_at       timestamp     default null,
    access_token_metadata         blob          default null,
    access_token_type             varchar(100)  default null,
    access_token_scopes           varchar(1000) default null,
    oidc_id_token_value           blob          default null,
    oidc_id_token_issued_at       timestamp     default null,
    oidc_id_token_expires_at      timestamp     default null,
    oidc_id_token_metadata        blob          default null,
    refresh_token_value           blob          default null,
    refresh_token_issued_at       timestamp     default null,
    refresh_token_expires_at      timestamp     default null,
    refresh_token_metadata        blob          default null,
    user_code_value               blob          default null,
    user_code_issued_at           timestamp     default null,
    user_code_expires_at          timestamp     default null,
    user_code_metadata            blob          default null,
    device_code_value             blob          default null,
    device_code_issued_at         timestamp     default null,
    device_code_expires_at        timestamp     default null,
    device_code_metadata          blob          default null,
    primary key (id)
);

# https://github.com/spring-projects/spring-authorization-server/blob/main/oauth2-authorization-server/src/main/resources/org/springframework/security/oauth2/server/authorization/oauth2-authorization-consent-schema.sql
# create table oauth2_authorization_consent
# (
#     registered_client_id varchar(100)  not null,
#     principal_name       varchar(200)  not null,
#     authorities          varchar(1000) not null,
#     primary key (registered_client_id, principal_name)
# );
