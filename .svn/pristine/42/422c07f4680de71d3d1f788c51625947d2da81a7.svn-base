INSERT INTO user_v1_01 VALUES(1,"admin","admin");
INSERT INTO user_v1_01 VALUES(2,"register","register");
INSERT INTO tomcatuserroles_v1_01 VALUES(1,"manager-gui");
INSERT INTO tomcatuserroles_v1_01 VALUES(2,"admin");
INSERT INTO tomcatuserroles_v1_01 VALUES(3,"player");
INSERT INTO tomcatuserroles_v1_01 VALUES(4,"register");
INSERT INTO tomcatuserroles_v1_01_pk VALUES("TomcatUserRoles_V1_01_Value",5);
INSERT INTO user_v1_01_pk VALUES("User_V1_01_Value",3);
INSERT INTO user_v1_01_tomcatuserroles_v1_01 VALUES(1,1);
INSERT INTO user_v1_01_tomcatuserroles_v1_01 VALUES(1,2);
INSERT INTO user_v1_01_tomcatuserroles_v1_01 VALUES(2,4);
CREATE OR REPLACE VIEW GroupView AS SELECT u.username,tr.rolename FROM `user_v1_01_tomcatuserroles_v1_01` AS tu, `user_v1_01` AS u, tomcatuserroles_v1_01 as tr WHERE u.user_id = tu.User_v1_01_user_id AND tr.role_id = tu.roles_role_id