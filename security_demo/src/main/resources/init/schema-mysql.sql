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
    id                      bigint unsigned not null auto_increment comment '主键',
    username                varchar(60)     not null comment '用户账户名称',                          -- account
    `password`              varchar(4000)   not null,
    account_non_expired     tinyint(1)      DEFAULT 1 CHECK (account_non_expired IN (0, 1)) comment '账户没有过期',
    account_non_locked      tinyint(1)      DEFAULT 1 CHECK (account_non_locked IN (0, 1)) comment '账户没有锁定',
    credentials_non_expired tinyint(1)      DEFAULT 1 CHECK (credentials_non_expired IN (0, 1)) comment '账户凭证没有过期',
    authorities             varchar(500) comment '用户角色',
    enabled                 tinyint(1)      DEFAULT 1 CHECK (enabled IN (0, 1)) comment '是否使用',
    note                    varchar(255),
    expire_time             datetime comment '失效截止时间时间',
    deleted                 bigint unsigned default 0 comment '删除标记位,除了0, 其余值都是已删除 ,删除时改为id值,', --
    primary key (id),
    unique (username, deleted)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

/**用户角色表**/
create table if not exists user_role
(
    id        bigint unsigned not null auto_increment,
    role_id   bigint unsigned not null,
    user_id   bigint unsigned not null,
    deleted bigint unsigned, -- 0, 其余值都是已删除
    primary key (id),
    unique (role_id, user_id, deleted)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;


