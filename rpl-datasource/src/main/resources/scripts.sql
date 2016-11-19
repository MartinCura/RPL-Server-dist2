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
	'PYTHON',
	10,
	1,
	'INPUT',
	'',
	'hello',
	'testcode'
	);


CREATE TABLE result (
	id serial PRIMARY KEY,
	stdout text
);

insert into result (
	stdout
	) values (
	'test'
	);

CREATE TABLE result_status (
	id serial PRIMARY KEY REFERENCES result,
	result text,
	stage text,
	type text,
	stderr text
);

insert into result_status (
	result,
	stage,
	type,
	stderr
	) values (
	'test',
	'test',
	'test',
	'test'
	);

CREATE TABLE tests (
	id serial PRIMARY KEY REFERENCES result,
	success boolean NOT NULL DEFAULT FALSE
);

insert into tests (
	id
	) values (
	1
	);

CREATE TABLE test_result (
	id serial PRIMARY KEY,
	tests_id integer REFERENCES tests,
	name text NOT NULL,
	success boolean NOT NULL DEFAULT FALSE,
	description text
);

insert into test_result (
	tests_id,
	name,
	description
	) values (
	1,
	'test',
	'test'
	);

CREATE TABLE activity_submission (
	id serial PRIMARY KEY,
	submission_date date NOT NULL,
	activity_id integer REFERENCES activity,
	code text NOT NULL,
	status text NOT NULL,
	execution_output text,
	result_id integer REFERENCES result
);
