CREATE TABLE attachment (
    id VARCHAR2(255) PRIMARY KEY,
    file_name VARCHAR2(255),
    file_type VARCHAR2(255),
    data BLOB
);

select * from attachment

drop table attachment

