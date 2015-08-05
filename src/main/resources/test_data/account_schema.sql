CREATE TABLE USER_PROFILES (
	USER_ID BIGINT NOT NULL,
	FAMILY_NAME VARCHAR(50) NOT NULL,
	GIVEN_NAME VARCHAR(50) NOT NULL,
	DOB DATE NOT NULL,
	SEX INTEGER NOT NULL,
	EMAIL VARCHAR(255) NOT NULL,
	MOBILE VARCHAR(20) NOT NULL,
	PROVINCE_ID INTEGER NOT NULL,
	CITY_ID INTEGER NOT NULL,
	DISTRICT_ID INTEGER NOT NULL,
	ZIP VARCHAR(10) NOT NULL,
	ADDRESS VARCHAR(255) NOT NULL,
	CREATE_ON TIMESTAMP NOT NULL,
	STATUS INTEGER NOT NULL,
	CONSTRAINT USER_PROFILES_PK PRIMARY KEY (USER_ID)
);

CREATE INDEX SYS_IDX_USER_PROFILE_PK_10103 ON USER_PROFILES (USER_ID);