INSERT INTO MASTER (ID, TYPE, DATA_KEY, DATA_VALUE, PARENT_ID, IS_DISABLED)
VALUES(0, 'data-status', 'json', '{ 1: "临时数据", 2: "无效数据", 4: "有效数据", 8: "过期数据" }', null, false);

INSERT INTO MASTER (ID, TYPE, DATA_KEY, DATA_VALUE, PARENT_ID, IS_DISABLED)
VALUES(1, 'sex', 'json', '{ 0: "不详", 1: "男", 2: "女" }', null, false);