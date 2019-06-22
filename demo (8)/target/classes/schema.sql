DROP TABLE IF EXISTS soldier CASCADE;
DROP TABLE IF EXISTS soldier_group CASCADE;
DROP TABLE IF EXISTS management CASCADE;
DROP TABLE IF EXISTS usr CASCADE;

CREATE TABLE IF NOT EXISTS soldier (
    soldier_id SERIAL NOT NULL,
    soldier_pw VARCHAR(255) NOT NULL,
    soldier_class VARCHAR(255) NOT NULL,
    soldier_name VARCHAR(255) NOT NULL,
    last_time VARCHAR(255) NOT NULL,
    soldier_phonenumber VARCHAR(255) NOT NULL,
    soldier_imei VARCHAR(255) NOT NULL,
    vacation INT4 NOT NULL, 
    PRIMARY KEY (soldier_id)
);

CREATE TABLE IF NOT EXISTS soldier_group ( 
	group_id INT4 NOT NULL,
    soldier_id INT4 NOT NULL,
    group_name VARCHAR(255) NOT NULL,
    group_time VARCHAR(255) NOT NULL,
    PRIMARY KEY (group_id, soldier_id)
);

CREATE TABLE IF NOT EXISTS management (
    management_id SERIAL NOT NULL,
    soldier_group_id INT4 NOT NULL,
    end_time TIME NOT NULL,
    start_time TIME NOT NULL,
    reserved_date DATE NOT NULL,
    soldier_id INT4 NOT NULL,
    user_id VARCHAR(255) NOT NULL,
    PRIMARY KEY (management_id)
);

CREATE TABLE IF NOT EXISTS usr (
    user_id VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    imei VARCHAR(255) NOT NULL,
    role_name VARCHAR(255) NOT NULL,
    phone_number VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    group_name VARCHAR(255),
    last_update VARCHAR(255),
    
    camera INT4 NOT NULL,
    enable INT4 NOT NULL,
    screen INT4 NOT NULL,
    group_pin INT4 NOT NULL,
    PRIMARY KEY (user_id)
);

ALTER TABLE soldier_group DROP CONSTRAINT IF EXISTS FK_SOLDIER_ID;
ALTER TABLE management DROP CONSTRAINT IF EXISTS FK_RESERVED_DATE_SOLDIER_ID;
ALTER TABLE management DROP CONSTRAINT IF EXISTS FK_USER_ID;

ALTER TABLE soldier_group ADD CONSTRAINT FK_SOLDIER_ID FOREIGN KEY (soldier_id) REFERENCES soldier;
ALTER TABLE management ADD CONSTRAINT FK_RESERVED_DATE_SOLDIER_ID FOREIGN KEY (soldier_group_id,soldier_id) REFERENCES soldier_group;
ALTER TABLE management ADD CONSTRAINT FK_USER_ID FOREIGN KEY (user_id) REFERENCES usr;