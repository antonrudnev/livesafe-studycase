# Testcase for the interview with LiveSafe

Task is described in **problem.txt** file.

Chosen stack of tecnologies for the solution: 
1. **Postgresql** as the persistence layer 
2. **Slick** as the object relational mapping
3. **Play** as the web framework for restful services 

**Slick and Play allows to implement data access and communication with the service in asynchronous way that is critical to meet the requirement of potential scalability to many nodes.**

### Prerequisites 
1. java 8
2. sbt

### Database configuration
1. Install **postgresql**
2. Configure **pg_hba.conf** to allow remote connections to the database server based on credentials (md5 method)
3. Start **postgresql** server 
4. Execute queries from **/sql** folder against target database in the order specified by prefixes
5. Configure application connection parameters in **/conf/application.conf** file

To start the application go to the project folder and execute **sbt run**.
Open browser at **localhost:9000/tips** and see the json response with the content of tip table.

Use any tool you like (curl, postman,...) to test all endpoints of the service:


**GET     /tips** returns all tips

**GET     /tips/:id** returns the tip by id

**POST    /tips** creates new tip based on provided json 

**PUT     /tips** updates existing tip based on provided json

**DELETE  /tips/:id** deletes tip by id


**GET     /tips/:id/comments** returns all comments associated with tip

**GET     /comments/:id** returns the comment by id

**POST    /comments** creates comment based on provided json

**DELETE  /comments/:id** deletes comment by id
