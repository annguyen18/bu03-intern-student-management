-- liquibase formatted sql

-- intern.`course` definition
-- Add foreign key for LECTURER_ID referencing USER(ID)
ALTER TABLE intern.`COURSE`
    ADD CONSTRAINT FK_Course_Lecturer FOREIGN KEY (`LECTURER_ID`) REFERENCES USER(`ID`);

-- Add foreign key for SCHEDULE_ID referencing CLASS_SCHEDULE(ID)
ALTER TABLE intern.`COURSE`
    ADD CONSTRAINT FK_Course_Schedule FOREIGN KEY (`SCHEDULE_ID`) REFERENCES CLASS_SCHEDULE(`ID`);

-- Add foreign key for DEPARTMENT_ID referencing DEPARTMENT(ID)
ALTER TABLE intern.`COURSE`
    ADD CONSTRAINT FK_Course_Department FOREIGN KEY (`DEPARTMENT_ID`) REFERENCES DEPARTMENT(`ID`);

-- # ALTER TABLE intern.`USER` ADD department_id bigint(20) DEFAULT NULL;
-- #
ALTER TABLE intern.`USER`
ADD CONSTRAINT FK_STUDENT_DEPARTMENT FOREIGN KEY (`department_id`) REFERENCES department(`id`);
