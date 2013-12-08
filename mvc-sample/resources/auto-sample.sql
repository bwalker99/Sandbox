
--  grant all privileges on sample.* to 'samplemgr'@'%' identified by 's@mpl3';
-- grant all privileges on sample.* to 'samplemgr'@'localhost' identified by 's@mpl3';


 CREATE TABLE sample_autos (
 id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
 make varchar(32),
 model varchar(32),
 colour varchar(32),
 cost int
 );


