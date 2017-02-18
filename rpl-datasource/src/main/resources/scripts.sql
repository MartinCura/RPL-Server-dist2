DROP TABLE IF EXISTS course cascade;
CREATE TABLE course (
	id serial PRIMARY KEY,
	name text NOT NULL,
	customization text,
	state text NOT NULL DEFAULT 'ENABLED'
);

insert into course (name, customization) values ('First course', '{cust: a customization}');

DROP TABLE IF EXISTS topic cascade;
CREATE TABLE topic (
	id serial PRIMARY KEY,
	name text NOT NULL,
	course_id integer REFERENCES course,
	state text NOT NULL DEFAULT 'ENABLED'
);

insert into topic (name, course_id) values ('this is a topic', 1);

DROP TABLE IF EXISTS activity cascade;
CREATE TABLE activity (
	id serial PRIMARY KEY, 
	name text NOT NULL,
	description text,
	language text NOT NULL, 
	points integer NOT NULL, 
	topic_id integer REFERENCES topic,
	test_type text NOT NULL,
	template text,
	input text,
	output text,
	tests text,
	state text NOT NULL DEFAULT 'ENABLED'
);

CREATE TABLE activity_file (
	id serial PRIMARY KEY, 
	content blob NOT NULL
);

insert into activity (
	name,
	language,
	points,
	topic_id,
	test_type,
	input,
	output,
	template
	) values (
	'Actividad Input (C)',
	'C',
	3,
	1,
	'INPUT',
	'',
	'hello',
	'#include<stdio.h>

int main() {

}'
	);
	
insert into activity (
	name,
	language,
	points,
	topic_id,
	test_type,
	input,
	output,
	template,
	tests
	) values (
	'Actividad Test (C)',
	'C',
	3,
	1,
	'TEST',
	'',
	'hello',
	'#include <stdio.h>

int test_method_1() {
	return 1;
}',
'
#include <criterion/criterion.h>
#include "solution.c"

Test(misc, failing) {
    cr_assert(test_method_1());
}

Test(misc, passing) {
    cr_assert(test_method_1());
}'
	);

DROP TABLE IF EXISTS result cascade;
CREATE TABLE result (
	id serial PRIMARY KEY,
	stdout text
);


DROP TABLE IF EXISTS result_status cascade;
CREATE TABLE result_status (
	id serial PRIMARY KEY REFERENCES result,
	result text,
	stage text,
	type text,
	stderr text
);



DROP TABLE IF EXISTS tests cascade;
CREATE TABLE tests (
	id serial PRIMARY KEY REFERENCES result,
	success boolean NOT NULL DEFAULT FALSE
);


DROP TABLE IF EXISTS test_result cascade;
CREATE TABLE test_result (
	id serial PRIMARY KEY,
	tests_id integer REFERENCES tests,
	name text NOT NULL,
	success boolean NOT NULL DEFAULT FALSE,
	description text
);

DROP TABLE IF EXISTS person cascade;
CREATE TABLE person (
	id serial PRIMARY KEY,
	name text NOT NULL,
	mail text NOT NULL,
	username text UNIQUE NOT NULL,
	password text NOT NULL,
	token text,
	role text NOT NULL
);
	
DROP TABLE IF EXISTS activity_submission cascade;
CREATE TABLE activity_submission (
	id serial PRIMARY KEY,
	submission_date date NOT NULL,
	activity_id integer REFERENCES activity,
	person_id integer REFERENCES person,
	code text NOT NULL,
	status text NOT NULL,
	execution_output text,
	result_id integer REFERENCES result,
	selected boolean DEFAULT FALSE
);

insert into person (
	name,
	mail,
	username,
	password,
	role
	) values (
	'rpl-student',
	'rpl@rpl.com',
	'rpl-student',
	'rpl',
	'USER'
	);

insert into person (
	name,
	mail,
	username,
	password,
	role
	) values (
	'rpl-assistant',
	'rpl@rpl.com',
	'rpl-assistant',
	'rpl',
	'USER'
	);

insert into person (
	name,
	mail,
	username,
	password,
	role
	) values (
	'rpl-professor',
	'rpl@rpl.com',
	'rpl-professor',
	'rpl',
	'USER'
	);
	
insert into person (
	name,
	mail,
	username,
	password,
	role
	) values (
	'rpl-admin',
	'rpl-admin@rpl.com',
	'rpl-admin',
	'rpl',
	'ADMIN'
	);

DROP TABLE IF EXISTS course_person cascade;
CREATE TABLE course_person (
	id serial PRIMARY KEY,
	course_id integer REFERENCES course,
	person_id integer REFERENCES person,
	role text NOT NULL,
	accepted boolean NOT NULL DEFAULT FALSE,
	assistant_id integer REFERENCES person,
	state text NOT NULL DEFAULT 'ENABLED'
);
ALTER TABLE course_person ADD UNIQUE (course_id, person_id);

insert into course_person (course_id, person_id, role, accepted) values (1, 2, 'ASSISTANT_PROFESSOR', true);
insert into course_person (course_id, person_id, role, accepted, assistant_id) values (1, 1, 'STUDENT', true, 2);
insert into course_person (course_id, person_id, role, accepted) values (1, 3, 'PROFESSOR', true);
insert into course_person (course_id, person_id, role, accepted) values (1, 4, 'PROFESSOR', true);
