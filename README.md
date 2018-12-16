RPL-Server
==========

## Installation

Follow the instructions in *installation.md*. A Vagrantfile is provided if you want to set up everything in a VM (simply run `vagrant up`, it will automatically run the setup script).


## Compilation
With *sudo*:

API server:
`mvn install -PwebApp`

Result monitor:
`mvn package -Pmonitor` (to be run alongside API server)

Daemon worker:
`mvn package -PwebApp` & `docker build -t rpl rpl-runner/`


## Run / Deploy

Previously clone in `~/repo/` and install as above.

#### Master (API server)
Customize `deploy-master.sh` and run it with sudo to prepare API deployment. Alternatively, compile as above and
Then run (in different terminals of the same system):
```bash
$ sudo ./start-wildfly.sh
$ sudo ./start-monitor.sh
```
#### Daemon worker
Set master's hostname in `rpl.config` or as env var `RPL_MASTER_HOST`.
Compile as above then run:
```bash
$ sudo ./start-daemon.sh
```


## Test
Run Daemon
`sudo java -jar rpl-daemon/target/rpl-daemon-0.0.1.jar`

Create Activity
`curl -X post -H "Content-Type: application/json" -d '{"name": "Print hello", "language": "PYTHON", "points": 10, "testType": "INPUT", "input": "", "output": "hello"}' http://localhost:8080/rpl-server-api/courses/1/activities`

Create Submission
`curl -X post -H "Content-Type: application/json" -d '{"code": "print \"hello\""}' http://localhost:8080/rpl-server-api/activities/1/submission`

Get Activity
`curl http://localhost:8080/rpl-server-api/activities/1`

Get Submission
`curl http://localhost:8080/rpl-server-api/submissions/1`
