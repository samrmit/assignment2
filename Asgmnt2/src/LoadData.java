import java.io.*;
import java.util.*;

import org.hsqldb.Server;

import java.sql.*;

public class LoadData {
	
	public static String[][] peopleArray = new String[10][6];
	public static String[][] relationsArray = new String[14][3];

	public static void loadInitData() {

		File peopleFile = new File("people.txt");
		File relationsFile = new File("relations.txt");

		

		// if file doesn'r exists check the DB
		if (peopleFile.exists()) {

			try {

				BufferedReader inputPeople = new BufferedReader(new FileReader(peopleFile));

				String nextPpl = inputPeople.readLine();
				int i=0;
				while (nextPpl != null) {
					String[] ar = nextPpl.split(",");
					 peopleArray[i] = ar;
					nextPpl = inputPeople.readLine();
					i++;
				}
				inputPeople.close();


			} catch (IOException e) {
				System.err.println("Person File Reading Error!" + e.getMessage());
				System.exit(0);
			}

		} else {

			Server hsqlServer = null;
			ResultSet rs = null;			
			Connection con = null;
			
			hsqlServer = new Server();   
			hsqlServer.setLogWriter(null);   
			hsqlServer.setSilent(true);   
			hsqlServer.setDatabaseName(0, "TestDB");   
			hsqlServer.setDatabasePath(0, "file:MYDB");
			
			//default port 9001    hsqlServer.getPort()
			//hsqlServer.checkRunning(false);	
			hsqlServer.start();
 

			//System.out.print(hsqlServer.getPort());
			//System.out.print(hsqlServer.getProductName());

			
			try {
				//Registering the HSQLDB JDBC driver
				Class.forName("org.hsqldb.jdbc.JDBCDriver");
				//Creating the connection with HSQLDB
				con = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/testdb", "SA", "");
				if (con!= null){
					//System.out.println("Connection created successfully");

					con.prepareStatement("drop table persons if exists;").execute();
					con.prepareStatement("create table persons (name varchar(100) not null, image varchar(100),status varchar(100),sex varchar(1), age integer, state varchar(3));").execute();
					
					//inserting initial values to table to demonstrate retrieve from DB 
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
					
					con.commit();
					
					rs = con.prepareStatement("select name, image, status, sex, age, state from persons;").executeQuery();
					
					         int i =0;
					         while(rs.next()){
					        	 String[] ar = {rs.getString("name"),rs.getString("image"),rs.getString("status"),rs.getString("sex"),rs.getString("age"),rs.getString("state")};
					        	 peopleArray[i] = ar;
					        	 i++;
					         }
 
				con.close();
				
				}else{
					System.out.println("Problem with creating connection");
					
				}
			
			}  catch (Exception e) {
				e.printStackTrace(System.out);
			}
			

		}
		
				
				try {
					BufferedReader inputRel = new BufferedReader(new FileReader(relationsFile));

					String nextRel = inputRel.readLine();
					
					int i =0;

					while (nextRel != null) {

						String[] arRel = nextRel.split(",");
						nextRel = inputRel.readLine();
						relationsArray[i] = arRel;

						i++;

					}
					inputRel.close();
					
					//System.out.println(Arrays.deepToString(relationsArray));
					
				} catch (IOException e) {
					System.err.println("Relations File Reading Error!" + e.getMessage());
					System.exit(0);
				}
		


	}
}
