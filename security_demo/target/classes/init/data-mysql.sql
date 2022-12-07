INSERT INTO security_test.user (id, username, password, account_non_expired, account_non_locked,
                                credentials_non_expired, authorities, enabled, note, deleted)
VALUES (1, 'root', '8c67c2127a2ef5b78ffdadb8efa36a5f2c22426e1cfe85f7efc3cbacc01d04d09a272dacf60532a0', 1, 1, 1, 'ADMIN', 1, null, 0);
INSERT INTO security_test.user (id, username, password, account_non_expired, account_non_locked,
                                credentials_non_expired, authorities, enabled, note, deleted)
VALUES (2, 'jenny', '8c67c2127a2ef5b78ffdadb8efa36a5f2c22426e1cfe85f7efc3cbacc01d04d09a272dacf60532a0', 1, 1, 1, 'USER', 1, null, 0);