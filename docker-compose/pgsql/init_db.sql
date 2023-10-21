CREATE TABLE "customers"
(
    "customer_id" serial PRIMARY KEY,
    "name"        varchar,
    "location"    text
);

CREATE TABLE "employees"
(
    "employee_id" serial PRIMARY KEY,
    "id"          varchar,
    "name"        varchar,
    "photo"       text
);

CREATE TABLE "assignments"
(
    "assignment_id" serial PRIMARY KEY,
    "customer_id"   int,
    "employee_id"   int,
    "start"         timestamp,
    "end"           timestamp
);

CREATE TABLE "attendance"
(
    "attendance_id" serial PRIMARY KEY,
    "customer_id"   int,
    "employee_id"   int,
    "time_in"       timestamp,
    "time_out"      timestamp,
    "photo"         text,
    "audit"         int default 0,
    "location"      text
);

ALTER TABLE "assignments"
    ADD FOREIGN KEY ("employee_id") REFERENCES "employees" ("employee_id");

ALTER TABLE "assignments"
    ADD FOREIGN KEY ("customer_id") REFERENCES "customers" ("customer_id");

ALTER TABLE "attendance"
    ADD FOREIGN KEY ("employee_id") REFERENCES "employees" ("employee_id");

ALTER TABLE "attendance"
    ADD FOREIGN KEY ("customer_id") REFERENCES "customers" ("customer_id");

INSERT INTO customers (name, location)
values ('Exito', '4.152555716412993,-73.63473686868544');

INSERT INTO customers (name, location)
values ('Carulla', '4.133690626214267,-73.63955784191091');

INSERT INTO customers (name, location)
values ('Olímpica', '4.133640558017952,-73.63816908062279');
