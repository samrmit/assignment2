import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;

import org.hsqldb.Server;

public class DBTest {
	
	public static void main(String[] args) {
		Server hsqlServer = null;
		Connection connection = null;
		ResultSet rs = null;

		
		Connection con = null;
		
		hsqlServer = new Server();   
		hsqlServer.setLogWriter(null);   
		hsqlServer.setSilent(true);   
		hsqlServer.setDatabaseName(0, "TestDB");   
		hsqlServer.setDatabasePath(0, "file:MYDB");      
		hsqlServer.start();
		try {
			//Registering the HSQLDB JDBC driver
			Class.forName("org.hsqldb.jdbc.JDBCDriver");
			//Creating the connection with HSQLDB
			con = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/testdb", "SA", "");
			if (con!= null){
				System.out.println("Connection created successfully");

				con.prepareStatement("drop table persons if exists;").execute();
				con.prepareStatement("create table persons (name varchar(100) not null, image varchar(100),status varchar(100),sex varchar(1), age integer, state varchar(3));").execute();
				
				con.prepareStatement("insert into persons (name, image, status, sex, age, state)"	+ "values ('Alice', 'alice.jpg','Accountant at RMIT','F',44,'VIC');").execute();
				con.prepareStatement("insert into persons (name, image, status, sex, age, state)"	+ "values ('James', 'jms.png','Lawyer','M',48,'VIC');").execute();
				con.prepareStatement("insert into persons (name, image, status, sex, age, state)"	+ "values ('Mike', 'mk.jpg','Student','M',10,'VIC');").execute();
				
				con.prepareStatement("insert into persons (name, image, status, sex, age, state)"	+ "values ('Ronald', 'ron.gif','Carpenter','M',28,'NSW');").execute();
				
				con.prepareStatement("insert into persons (name, image, status, sex, age, state)"	+ "values ('Ben', 'ben.png','School Teacher','M',36,'WA');").execute();
				con.prepareStatement("insert into persons (name, image, status, sex, age, state)"	+ "values ('Marie', '','','F',34,'WA');").execute();
				con.prepareStatement("insert into persons (name, image, status, sex, age, state)"	+ "values ('Steve', 'st.jpg','','M',12,'WA');").execute();	
				
				con.prepareStatement("insert into persons (name, image, status, sex, age, state)"	+ "values ('Sam', 'jsm.jpg','','F',44,'VIC');").execute();
				con.prepareStatement("insert into persons (name, image, status, sex, age, state)"	+ "values ('Anne', 'an.jpg','','F',37,'VIC');").execute();
				con.prepareStatement("insert into persons (name, image, status, sex, age, state)"	+ "values ('Marcus', '','','M',2,'VIC');").execute();
				
				rs = con.prepareStatement("select * from persons;").executeQuery();
				
				         
				         while(rs.next()){
				        	 System.out.println(rs.getString("state") );
				         }
				
				
				//rs.next();
				//System.out.println(rs.getString(1)+"---"+rs.getString(2)+"---"+rs.getString(3)+"---"+rs.getString(4)+"---"+rs.getInt(5)+"---"+rs.getString(6) );
				con.commit();
			
			}else{
				System.out.println("Problem with creating connection");
			}
		
		}  catch (Exception e) {
			e.printStackTrace(System.out);
		}
		
		
		
		System.exit(0);
		
		
		hsqlServer = new Server();
		//hsqlServer.setPort(544);
		hsqlServer.setLogWriter(null);
		hsqlServer.setSilent(true);
		hsqlServer.setDatabaseName(0, "TestDB");
		hsqlServer.setDatabasePath(0, "file:MYDB");
		System.out.print(hsqlServer.getPort());

		//hsqlServer.getPro
		//hsqlServer.start();
		
		
		// making a connection
		try {
		Class.forName("org.hsqldb.jdbcDriver");
		connection = DriverManager.getConnection("jdbc:hsqldb:TestDB://127.0.0.1:544/webappdb", "sa", "123");
		jdbc:hsqldb:file:///c:/hsqldb/mydb
		connection.prepareStatement("drop table barcodes if	exists;").execute();
		connection.prepareStatement("create table barcodes (id integer, barcode varchar(20) not null);").execute();
		connection.prepareStatement("insert into barcodes (id, barcode)"	+ "values (1, '12345577');").execute();
		
		// // query from the db
		rs = connection.prepareStatement("select id,barcode from barcodes;").executeQuery();
		rs.next();
		//System.out.println(String.format("ID: %1d, Name:%1s", rs.getInt(1), rs.getString(2)));
		connection.commit();
		} catch (SQLException e2) {
		e2.printStackTrace();
		} catch (ClassNotFoundException e2) {
		e2.printStackTrace();
		}
		// end of stub code for in/out stub
		}

}
