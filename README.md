# Database Interface
### Introduction
This project uses Scala to create a database interface and application, which can then be built and run with Docker. `src/main/scala/DatabaseInterface.scala` provides functions that users can call to insert, update, and delete data via SQL (via Slick) in two database tables: `SUPPLIERS` and `COFFEES`. As a demo, `src/main/scala/DatabaseInterfaceDemo.scala` demonstrates how users can interact with the tables through the interface, e.g., insert suppliers, insert coffees, update coffee sales, delete suppliers, and delete coffees.
### Run the Database Interface Demo
To build the Docker image, run following command:   
```
$ docker build -t database-interface:1.0 .
```  
To run the Docker image inside of a container, run the following command:  
```
$ docker run database-interface:1.0
```
While running, the demo prints updates, e.g., 
```
[info] Success: Inserted 1 row (name: Acme, Inc.) to Suppliers table
[info] Success: Inserted 1 row (name: The High Ground) to Suppliers table
[info] Success: Inserted 1 row (name: Palo Alto Coffee) to Suppliers table
[info] Success: Inserted 1 row (name: Superior Coffee) to Suppliers table
[info] Success: Inserted 1 row (name: Espresso) to Coffees table
[info] Success: Inserted 1 row (name: Colombian_Decaf) to Coffees table
[info] Success: Inserted 1 row (name: Colombian) to Coffees table
[info] Success: Inserted 1 row (name: French_Roast) to Coffees table
[info] Success: Inserted 1 row (name: French_Roast_Decaf) to Coffees table
[info] Success: Updated 1 row from Coffees table (updated sales of Espresso to 1)
[info] Success: Updated 1 row from Coffees table (updated sales of Colombian to 2)
[info] Success: Deleted 1 row (name: French_Roast_Decaf) from Coffees table
[info] Success: Deleted 1 row (id: 49) from Suppliers table
```
Finally, the demo prints the newest version of the tables in the terminal.
