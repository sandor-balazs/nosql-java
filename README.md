# Using NoSQL Databases from Java
Comparison of some of the most widely used NoSQL databases and their usage
from Java.

## Applications
The applications try to demonstrate the use cases where NoSQL databases
can be considered as an alternative for traditional relational database
management systems.

The applications are built using Spring Boot and AngularJS, the initial project
structure was generated using [JHipster][1].

### Building and running the applications

Generally:
```shell
% cd [cassandra|mongodb|mysql|oracle]
% npm install && bower install
% mvn
```
After installing npm and bower dependencies, Maven will build the application
and run it with an embedded web server using Spring Boot and Tomcat. The
application can be tested by opening up a browser and hitting
<http://localhost:8080>.

### Prerequisites
The databases have to be started before running the applications. For
databases other than Cassandra the database objects are created
automatically during application launch. In the case of Oracle and MySQL, an
initial schema has to be created but database objects (tables, indexes, etc.)
are created automatically by [Liquibase][2].

#### Cassandra
When using Cassandra, the below files containing CQL statements
have to be executed manually from the CQL interactive terminal cqlsh before
running the application:
```shell
% cd cassandra/src/main/resources/config/cql
% cqlsh
cqlsh> USE ontime;
cqlsh> SOURCE 'create-keyspace.cql'
cqlsh> SOURCE 'create-tables-auth.cql'
cqlsh> SOURCE 'create-tables-domain.cql'
```
Cassandra should be running on the default 9042 port.

#### MongoDB
[Mongeez][3] manages the database initialization.
MongoDB should be running on the default 27177 port.

#### MySQL
The initial schema should be created:
```sql
CREATE SCHEMA ontime;
```
In the application-dev.yml file of the src/resources/config folder the
database user's password should be added.
MySQL should be running on the default 3306 port.

#### Oracle XE database
For Oracle XE database, the initial schema can be created using the following
commands (where \<\<SECRET\>\> has to be substituted with an appropriate
password):
```sql
CREATE USER ontime IDENTIFIED BY <<SECRET>>
  DEFAULT TABLESPACE "USERS"
  TEMPORARY TABLESPACE "TEMP";
GRANT "CONNECT" TO ontime;
GRANT "RESOURCE" TO ontime;
```
The user's password should be added to the
src/resources/config/application-dev.yml file.

When testing the Oracle application the test URL was changed to
<http://localhost:8081> as Oracle XE Database Home Page is running on port 8080.
Oracle database itself should be running on the default 1521 port.

## Technologies used
Databases:
* MongoDB
* Cassandra
* Ehcache
* MySQL
* Oracle XE

Server-side:
* Spring Boot
* Java 8

Client-side:
* AngularJS

## Generating the documentation
```shell
cd doc
make all
```

[1]: https://jhipster.github.io/
[2]: http://www.liquibase.org/
[3]: https://github.com/mongeez/mongeez
