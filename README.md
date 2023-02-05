# Database Interface
### Introduction
This project uses Scala to create a database interface and application, which can then be built and run with Docker. `DatabaseInterface.scala` provides functions that users can to insert, update, and delete data in two sample database tables: `Suppliers` and `Coffees`. As an example, `DatabaseInterfaceDemo.scala` demonstrates how users can interact with the tables through the interface.
### Run the Database Interface Demo
To build the Docker image, run following command:   
```
$ docker build -t database-interface:1.0 .
```  
To run the Docker image inside of a container, run the following command:  
```
$ docker run database-interface:1.0
```
