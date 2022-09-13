create schema if not exists security_test default CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;
use security_test;

/**角色表**/
create table if not exists role
(
    id        bigint unsigned not null auto_increment,
    role_name varchar(60)     not null,
    note      varchar(255),
    deleted   bigint unsigned, -- 0, 其余值都是已删除
    primary key (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
create table if not exists user
(
    id                      bigint unsigned not null auto_increment,
    username               varchar(60)     not null, -- account
    `password`              varchar(4000)   not null,
    account_non_expired     tinyint(1) DEFAULT 1 CHECK (account_non_expired IN (0, 1)),
    account_non_locked      tinyint(1) DEFAULT 1 CHECK (account_non_locked IN (0, 1)),
    credentials_non_expired tinyint(1) DEFAULT 1 CHECK (credentials_non_expired IN (0, 1)),
    authorities             varchar(500) ,
    enabled                  tinyint(1) DEFAULT 1 CHECK (enabled IN (0, 1)),
    note                    varchar(255),
    deleted                 bigint unsigned default 0,          -- 0, 其余值都是已删除
    primary key (id),
    unique (username,deleted)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

/**用户角色表**/
create table if not exists user_role
(
    id        bigint unsigned not null auto_increment,
    role_id   bigint unsigned not null,
    user_id   bigint unsigned not null,
    is_delete bigint unsigned, -- 0, 其余值都是已删除
    primary key (id),
    unique (role_id, user_id, is_delete)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;


