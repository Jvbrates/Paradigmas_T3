package infra.dados.dao.database;

import infra.dados.ExceptionInfraDados;

import java.sql.*;

public class connectionDB {
	private static Connection conn = null;
	
	public static Connection getConn(){
		
		if(conn == null) {
			
			try {
			Class.forName("org.postgresql.Driver");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			
			try {
				conn = DriverManager.getConnection("jdbc:postgresql://localhost/t3_paradigmas", "postgres", "");

			} catch (org.postgresql.util.PSQLException e){
				throw new ExceptionInfraDados("Não foi possível se conectar ao banco de dados");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		

	}

		return conn;
	}
}
