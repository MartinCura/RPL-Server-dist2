create table test (id serial PRIMARY KEY, msg varchar(50) NOT NULL);

CREATE TABLE topic (
	id serial PRIMARY KEY,
	name text NOT NULL
);

insert into topic (name) values ('this is a topic');

CREATE TABLE activity (
	id serial PRIMARY KEY, 
	name text NOT NULL, 
	language text NOT NULL, 
	points integer NOT NULL, 
	topic_id integer REFERENCES topic,
	test_type text NOT NULL,
	input text,
	output text,
	tests text
);

insert into activity (
	name,
	language,
	points,
	topic_id,
	test_type,
	input,
	output,
	tests
	) values (
	'this is an activity',
	'JAVA',
	10,
	1,
	'TEST',
	'',
	'',
	'testcode'
	);

CREATE TABLE activity_submission (
	id serial PRIMARY KEY,
	submission_date date NOT NULL,
	activity_id integer REFERENCES activity,
	code text NOT NULL,
	status text NOT NULL,
	execution_output text
);

CREATE TABLE result (
	id serial PRIMARY KEY,
	stdout text NOT NULL,
	description text
);

CREATE TABLE result_status (
	id serial PRIMARY KEY REFERENCES result,
	result text NOT NULL,
	stage text NOT NULL,
	type text NOT NULL,
	stderr text
);

CREATE TABLE tests (
	id serial PRIMARY KEY REFERENCES result,
	success boolean NOT NULL DEFAULT FALSE
);

CREATE TABLE test_result (
	id serial PRIMARY KEY,
	tests_id integer REFERENCES tests,
	name text NOT NULL,
	success boolean NOT NULL DEFAULT FALSE,
	description text
);
