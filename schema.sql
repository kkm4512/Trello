CREATE TABLE attachment (
                            id BIGINT NOT NULL AUTO_INCREMENT,
                            created_at DATETIME(6),
                            updated_at DATETIME(6),
                            origin_file_name VARCHAR(255),
                            path VARCHAR(255),
                            card_id BIGINT,
                            user_id BIGINT,
                            PRIMARY KEY (id)
) ENGINE=InnoDB;

CREATE TABLE board (
                       id BIGINT NOT NULL AUTO_INCREMENT,
                       created_at DATETIME(6),
                       updated_at DATETIME(6),
                       background_color VARCHAR(7) NOT NULL,
                       title VARCHAR(100) NOT NULL,
                       workspace_id BIGINT NOT NULL,
                       PRIMARY KEY (id)
) ENGINE=InnoDB;

CREATE TABLE board_list (
                            id BIGINT NOT NULL AUTO_INCREMENT,
                            created_at DATETIME(6),
                            updated_at DATETIME(6),
                            order_num INTEGER,
                            title VARCHAR(255) NOT NULL,
                            board_id BIGINT NOT NULL,
                            user_id BIGINT NOT NULL,
                            PRIMARY KEY (id)
) ENGINE=InnoDB;

CREATE TABLE card (
                      id BIGINT NOT NULL AUTO_INCREMENT,
                      created_at DATETIME(6),
                      updated_at DATETIME(6),
                      content VARCHAR(255),
                      title VARCHAR(255),
                      board_list_id BIGINT,
                      user_id BIGINT,
                      PRIMARY KEY (id)
) ENGINE=InnoDB;

CREATE TABLE card_member (
                             id BIGINT NOT NULL AUTO_INCREMENT,
                             created_at DATETIME(6),
                             updated_at DATETIME(6),
                             card_id BIGINT,
                             user_id BIGINT,
                             PRIMARY KEY (id)
) ENGINE=InnoDB;

CREATE TABLE comment (
                         id BIGINT NOT NULL AUTO_INCREMENT,
                         created_at DATETIME(6),
                         updated_at DATETIME(6),
                         comment VARCHAR(255),
                         card_id BIGINT,
                         user_id BIGINT,
                         PRIMARY KEY (id)
) ENGINE=InnoDB;

CREATE TABLE member (
                        id BIGINT NOT NULL AUTO_INCREMENT,
                        created_at DATETIME(6),
                        updated_at DATETIME(6),
                        email VARCHAR(255) NOT NULL,
                        member_role ENUM('BOARD_MEMBER', 'READ_ONLY', 'WORKSPACE_ADMIN') NOT NULL,
                        user_id BIGINT NOT NULL,
                        workspace_id BIGINT NOT NULL,
                        PRIMARY KEY (id)
) ENGINE=InnoDB;

CREATE TABLE users (
                       id BIGINT NOT NULL AUTO_INCREMENT,
                       created_at DATETIME(6),
                       updated_at DATETIME(6),
                       email VARCHAR(255),
                       password VARCHAR(255) NOT NULL,
                       role ENUM('ADMIN', 'USER') NOT NULL,
                       deleted BIT NOT NULL COMMENT 'Soft-delete indicator',
                       PRIMARY KEY (id)
) ENGINE=InnoDB;

CREATE TABLE workspace (
                           id BIGINT NOT NULL AUTO_INCREMENT,
                           created_at DATETIME(6),
                           updated_at DATETIME(6),
                           description VARCHAR(300) NOT NULL,
                           title VARCHAR(100) NOT NULL,
                           PRIMARY KEY (id)
) ENGINE=InnoDB;

ALTER TABLE board_list
DROP INDEX UKqraqqtg7ec9n4hgg2og4br8p2;

ALTER TABLE board_list
    ADD CONSTRAINT UKqraqqtg7ec9n4hgg2og4br8p2 UNIQUE (title);

ALTER TABLE attachment
    ADD CONSTRAINT FKpyjq6uiperx43dbsny1gjvxne
        FOREIGN KEY (card_id) REFERENCES card (id);

ALTER TABLE attachment
    ADD CONSTRAINT FKbj8rm4iort67j9jp8ibdftkmq
        FOREIGN KEY (user_id) REFERENCES users (id);

ALTER TABLE board
    ADD CONSTRAINT FKh8r4ryxrng25r7ko3yh5eaudu
        FOREIGN KEY (workspace_id) REFERENCES workspace (id);

ALTER TABLE board_list
    ADD CONSTRAINT FKhr9yvsrsbk1gp346h44jsovv6
        FOREIGN KEY (board_id) REFERENCES board (id);

ALTER TABLE board_list
    ADD CONSTRAINT FKmhemj28ukt33oao8xbtcn2ndt
        FOREIGN KEY (user_id) REFERENCES users (id);

ALTER TABLE card
    ADD CONSTRAINT FK8ah8qm5rxxlf22ekmq9v9u5fa
        FOREIGN KEY (board_list_id) REFERENCES board_list (id);

ALTER TABLE card
    ADD CONSTRAINT FKq5apcc4ddrab8t48q2uqvyquq
        FOREIGN KEY (user_id) REFERENCES users (id);

ALTER TABLE card_member
    ADD CONSTRAINT FKgp6lai9ewkcfodigcua5taanf
        FOREIGN KEY (card_id) REFERENCES card (id);

ALTER TABLE card_member
    ADD CONSTRAINT FKcoy0y9394gd8yp0nq0yuck2q1
        FOREIGN KEY (user_id) REFERENCES users (id);

ALTER TABLE comment
    ADD CONSTRAINT FKqgv5aujiclf0iihwxf4gmkf18
        FOREIGN KEY (card_id) REFERENCES card (id);

ALTER TABLE comment
    ADD CONSTRAINT FKqm52p1v3o13hy268he0wcngr5
        FOREIGN KEY (user_id) REFERENCES users (id);

ALTER TABLE member
    ADD CONSTRAINT FKe6yo8tn29so0kdd1mw4qk8tgh
        FOREIGN KEY (user_id) REFERENCES users (id);

ALTER TABLE member
    ADD CONSTRAINT FKnhqfvlg5wv3c3qok7st4cuvii
        FOREIGN KEY (workspace_id) REFERENCES workspace (id);
