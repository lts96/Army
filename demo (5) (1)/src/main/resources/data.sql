-- 영화 목록
INSERT INTO soldier (soldier_pw , soldier_name , soldier_class , last_time , soldier_phonenumber , soldier_imei) VALUES ('awfeom123','김철수', '병장','201905020000','01092946781', 'sn - 9000');
INSERT INTO soldier (soldier_pw , soldier_name , soldier_class , last_time, soldier_phonenumber, soldier_imei) VALUES ('enviaeh41','이재용','일병','201905030000','01097458754',  'sn - 12300' );
INSERT INTO soldier (soldier_pw , soldier_name , soldier_class , last_time, soldier_phonenumber, soldier_imei) VALUES ('iiii23124','안대용','이병','201911020000', '01078941231', 'sn - 3300');
INSERT INTO soldier (soldier_pw , soldier_name , soldier_class , last_time, soldier_phonenumber, soldier_imei) VALUES ('87951@@','박병장','상병','201905020123', '01078841521', 'sn - 5550');
INSERT INTO soldier (soldier_pw , soldier_name , soldier_class , last_time, soldier_phonenumber, soldier_imei) VALUES ('00291823','최폐급','상병','201105000000', '01055555555', 'sn - 2200');
INSERT INTO soldier (soldier_pw , soldier_name , soldier_class , last_time, soldier_phonenumber, soldier_imei) VALUES ('8829910!!','우강제','일병','200205020000', '01099977787', 'sn - 1100');
INSERT INTO soldier (soldier_pw , soldier_name , soldier_class , last_time, soldier_phonenumber, soldier_imei) VALUES ('&**()@#$','송대장','병장','201205020000', '01033332222', 'sn - 900');




-- 사용자 (비밀번호: demo)
INSERT INTO usr (user_id, name, imei, role_name, phone_number, password ,group_name , last_update , camera , enable , screen)
VALUES ('dlxotjs', '이태선', 'SN - 9010', 'USER','01092946780' ,'1234' , '201정비중대' , '20191122120012',1,1,1);
INSERT INTO usr (user_id, name, imei, role_name, phone_number, password,group_name, last_update, camera , enable , screen)
VALUES ('han', '한상훈', 'SN- 1111', 'USER','01012341234', '5678', '207정비중대', '20181122120012',0,0,0);
INSERT INTO usr (user_id, name, imei, role_name, phone_number, password,group_name, last_update, camera , enable , screen)
VALUES ('chan', '정희찬', 'SN - 7774', 'ADMIN','01084751621', '0000', '101정비중대', '20171122120012',0,1,1);




INSERT INTO soldier_group (group_id,soldier_id,group_name , group_time) VALUES (1,1,'201정비중대' , '201801010122');
INSERT INTO soldier_group (group_id,soldier_id,group_name, group_time) VALUES (1,2,'201정비중대', '201701010122');
INSERT INTO soldier_group (group_id,soldier_id,group_name, group_time) VALUES (1,3,'201정비중대', '201601010122');
INSERT INTO soldier_group (group_id,soldier_id,group_name, group_time) VALUES (2,4,'101정비중대', '201901010122');
INSERT INTO soldier_group (group_id,soldier_id,group_name, group_time) VALUES (2,5,'101정비중대', '201501010122');
INSERT INTO soldier_group (group_id,soldier_id,group_name, group_time) VALUES (2,6,'101정비중대', '201401010122');
INSERT INTO soldier_group (group_id,soldier_id,group_name, group_time) VALUES (3,7,'207정비중대', '201301010122');