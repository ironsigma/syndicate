# Syndicate

In order to run the application the following needs to be setup.

For Tomcat update the `config/Catalina/localhost/syndicate.xml` to have the following:

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

Create a `logback.xml` configuration file and point to it using `-Dlogback.configurationFile="/config/logback.xml"`

```xml
<?xml version="1.0" encoding="UTF-8"?>
<configuration>
    <appender name="file" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <file>/Users/m075878/Servers/config/tomcat7.log</file>
        <append>true</append>

        <rollingPolicy class="ch.qos.logback.core.rolling.FixedWindowRollingPolicy">
            <fileNamePattern>tomcat7.%i.log.zip</fileNamePattern>
            <minIndex>1</minIndex>
            <maxIndex>3</maxIndex>
        </rollingPolicy>

        <triggeringPolicy class="ch.qos.logback.core.rolling.SizeBasedTriggeringPolicy">
            <maxFileSize>5MB</maxFileSize>
        </triggeringPolicy>
        <encoder>
            <pattern>%date [%thread] %-5level %logger - %msg%n</pattern>
        </encoder>
    </appender>

    <root level="warn">
        <appender-ref ref="file" />
    </root>
</configuration>
```