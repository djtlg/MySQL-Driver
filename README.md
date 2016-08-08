MySQL-Driver
============
Introduction
Our project is to create a driver program for SQL database. Main aim of the project is the people who doesn’t have enough experience with SQL language and database program but wants to have a database for their purposes. This project is perfect for small companies or businesses where the number of user will not be too many. The program can do almost all the essential operations that the user might require. Our project it based on My SQL database software and uses JDBC the Java Database Connectivity (JDBC) to communicate with the database.

Assumptions
We assumed that the user who is interested in this project have enough knowledge to setup a My SQL database software and get it running or they already have an My SQL database up and running.

Features
As mentioned before the program is able to do almost all the basic operations that a user might require. It can,
* Connect to any MySQL database locally or on the cloud.
*	Provide log in credentials to the MySQL database and get authenticated.
*	Use multiple databases within the MySQL Host.
*	Add new database to the MySQL host.
*	Remove existing database from MySQL host.
*	List Existing databases on the MySQL host.
*	List tables in a database
*	Add new tables to the database 
*	Remove existing tables from the database
*	Add new columns to an existing table
*	Remove columns from existing tables
*	Alter the columns name or the data type it has
*	Enter new rows of data to an existing table 
*	Remove rows from an existing table
*	Update rows of an existing table.
It will also allow advanced users to type in their queries and display results accordingly.

Limitations
Currently our project only supports My SQL database software. Also since our program is working on the single thread it can have delays on the user interface while it is fetching data from the database. Also our program does not support advanced functions of the database software. I.e. clustering, table partitioning etc. Also currently updating the rows of an existing table and changing data type of columns within an existing table is under construction.
