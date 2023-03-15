# DropBookmarks

A Dropwizard 4.0.0 project exposing REST API to store bookmarks. The 
application is using Maven build management, MSSQL Server as a DBMS and database migrations with liquibase.

Database settings are in the local_properties.yml file. To switch to another RDBMS one 
should modify the driver and connection URL. By default it is necessary to create 
DropBookmarks database and populate it using migrations. The liquibase settings are in the
liquibase.properties file, they should also be changed if database is changed.
  
## How to start the DropBookmarks application

0. Check out project using `git clone https://github.com/andy489/DropBookmarks.git`
1. Create a key store in the project's folder using Java 8 *keytool* 
`keytool -genkeypair -keyalg RSA -dname "CN=localhost" -keystore dropbookmarks.keystore  -keypass 123456 -storepass 123456`
2. Run `mvn clean package` to build the application. Database will be populated automatically.
3. Run application with arguments `server .\src\main\resources\local_properties.yml`:

    `java -jar target/DropBookmarks-1.0-SNAPSHOT.jar server .\src\main\resources\local_properties.yml`
5. To check that your application is running, enter URL `http://localhost:8081` in the browser 

## How to try the DropBookmarks application
  
The API is secured with Basic Authentication. One can find a user *pesho*
whose password is *1234* (all users have the same password, other populated users are: maria, gosho, andy and foo) 
along with several bookmarks in the database after executing the migrations.

To get the list of all bookmarks stored by *pesho* enter

~~~~
curl.exe -k https://localhost:8443/bookmark/my  -u pesho:1234
~~~~

To all bookmarks of *pesho*, which contain the infix *okta* in their name

~~~~
curl.exe -w "\n" -k https://localhost:8443/bookmark/search?infix=okta -u pesho:1234
~~~~

To add a bookmark it is necessary to key in 

~~~~
curl.exe -X POST -w "\n" -k https://localhost:8443/bookmark -u pesho:1234 -H "Content-Type: application/json" --data '{\"name\":\"Git\", \"url\":\"http://github.com\", \"description\":\"A lot of great projects\"}'
~~~~

To modify a bookmark the API offers PUT method

~~~~
curl.exe -X PUT -w "\n" -k https://localhost:8443/bookmark/2 -u pesho:1234 -H "Content-Type: application/json" -d '{\"url\":\"https://github.com/andy489/Linux_Shell\"}'
~~~~

To delete a bookmark use 

~~~~
curl.exe -X DELETE -w "\n" -k https://localhost:8443/bookmark/1 -u pesho:1234
~~~~

To register userr
~~~~
curl.exe -X POST -w "\n" -k http://localhost:8080/user -H "Content-Type: application/json" -d'{\"username\" : \"newuser\",\"fullName\" : \"New User\",\"email\": \"new@gmail.com\",\"password\": \"1234\",\"confirmPassword\": \"1234\"}'
~~~~

Other functionalities are exposed in the Postman exported collection.

Project src tree
```
.
├───src
│   ├───main
│   │   ├───java
│   │   │   └───com
│   │   │       └───pros
│   │   │           └───bookmarks
│   │   │               │   DropBookmarksApplication.java
│   │   │               ├───auth
│   │   │               │       DBAuthenticator.java
│   │   │               ├───configuration
│   │   │               │       DropBookmarksConfiguration.java
│   │   │               ├───dao
│   │   │               │       BookmarkEntityDAO.java
│   │   │               │       UserEntityDAO.java
│   │   │               ├───model
│   │   │               │   ├───dto
│   │   │               │   │       BookmarkDto.java
│   │   │               │   │       UserDto.java
│   │   │               │   ├───entity
│   │   │               │   │       BaseEntity.java
│   │   │               │   │       BookmarkEntity.java
│   │   │               │   │       UserEntity.java
│   │   │               │   ├───mapper
│   │   │               │   │       MapStructMapper.java
│   │   │               │   ├───validation
│   │   │               │   │       FieldMatch.java
│   │   │               │   │       FieldMatchValidator.java
│   │   │               │   │       UniqueEmail.java
│   │   │               │   │       UniqueEmailValidator.java
│   │   │               │   │       UniqueUsername.java
│   │   │               │   │       UniqueUsernameValidator.java
│   │   │               │   └───view
│   │   │               │           BookmarkFullView.java
│   │   │               │           BookmarkView.java
│   │   │               │           UserView.java
│   │   │               ├───resources
│   │   │               │       BookmarkResource.java
│   │   │               │       UserResource.java
│   │   │               └───service
│   │   │                       BookmarkService.java
│   │   │                       UserService.java
│   │   └───resources
│   │       │   liquibase.properties
│   │       │   local_properties.yml
│   │       │   migrations.xml
│   │       └───db.changelog
│   │               change-log-1.0.xml
│   │               change-log-1.1.xml
│   │               change-log-1.2.xml
│   └───test
└───target
```