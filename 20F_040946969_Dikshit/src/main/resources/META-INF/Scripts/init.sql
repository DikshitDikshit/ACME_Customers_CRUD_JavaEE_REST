insert into SECURITY_ROLE values(1, 'ADMIN_ROLE');
insert into SECURITY_ROLE values(2, 'USER_ROLE');
INSERT INTO SECURITY_USER VALUES (1, 'PBKDF2WithHmacSHA256:2048:xHT5hKIZlvl29wfD7IkEecWPsPMtWEVbuL3J/Kv3enw=:GtwAap6eOV8aXrgr/YolsFNX4FHJG+byn/Ijpe5m7P0=', 'admin', NULL);
INSERT INTO SECURITY_USER_SECURITY_ROLE values(1,1);
INSERT INTO SECURITY_USER VALUES (2, 'PBKDF2WithHmacSHA256:2048:shaRbqHg7s/hd/4UNHZVyXwXYRUWoRBZoKa7j+Ufi+0=:SGyohAOFCBzqSkB9t63WtzspD3xk0JuVf76ADOEzMK0=', 'user', NULL);
INSERT INTO SECURITY_USER_SECURITY_ROLE values(2,2);