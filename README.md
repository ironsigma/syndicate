# Syndicate

In order to run the application the follwoing need to be setup.

For Tomcat update the `context.xml` to have the following:

Define the location of the configuration files

```xml
    <Environment name="config/syndicate" 
                type="java.lang.String"
                value="file:///Users/m075878/Servers/config/syndicate.properties" />
```

Define a data source

```xml
	<Resource name="jdbc/syndicate/datasource"
		auth="Container"
		type="javax.sql.DataSource"
		maxActive="8"
		maxIdle="2"
		username="SA"
		driverClassName="org.hsqldb.jdbc.JDBCDriver"
		url="jdbc:hsqldb:file:///Users/m075878/Servers/config/syndicate-db" />
```

Define the data source dialect

```xml
    <Environment name="jdbc/syndicate/dialect" 
                type="java.lang.String"
                value="org.hibernate.dialect.HSQLDialect" />
```
