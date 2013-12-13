-- Use the following scripts to create a mysql database called sample with a user called samplemgr
-- Login to mysql  bash>mysql -u root -p
-- mysql>create database sample
-- mysql>grant all privileges on sample.* to 'samplemgr'@'%' identified by 's@mpl3';
-- mysql>grant all privileges on sample.* to 'samplemgr'@'localhost' identified by 's@mpl3';

-- Add the following to $TOMCAT_HOME/conf/context.xml

/*
 * 
 * 
 * 
    <Resource name="jdbc/SAMPLE" auth="Container"
             type="javax.sql.DataSource"
             username="samplemgr"
             password="s@mpl3"
             driverClassName="com.mysql.jdbc.Driver"
             url="jdbc:mysql://localhost/sample"
             maxActive="10" maxIdle="4"/>

*/
-- download and add latest mysql jdbc jar to $TOMCAT_HOME/lib 
-- ie: mysql-connector-java-3.1.12-bin.jar
-- Login to mysql as samplemgr (bash>mysql -u samplemgr -p ) and create the following table.

CREATE TABLE sample_autos (
 id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
 make varchar(32),
 model varchar(32),
 colour varchar(32),
 cost int
 );


