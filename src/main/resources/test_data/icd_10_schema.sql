CREATE TABLE ICD_10_CN (
	DISEASE_ID BIGINT GENERATED BY DEFAULT AS IDENTITY(START WITH 0) NOT NULL,
	ICD_NO NVARCHAR(100) NOT NULL,
	ADDITIONAL_NO NVARCHAR(100),
	MNEMONICS NVARCHAR(100) NOT NULL,
	NAME NVARCHAR(500) NOT NULL,
	ENGLISH_NAME NVARCHAR(500),
	CONSTRAINT ICD_10_CN_PK PRIMARY KEY (DISEASE_ID)
);

CREATE INDEX SYS_IDX_ICD_10_CN_PK_10321 ON ICD_10_CN (DISEASE_ID);