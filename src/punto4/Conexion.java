package punto4;

import java.sql.*;

public class Conexion {
	      
	    private static Connection connection = null;

	    public static Connection getConnection() throws InstantiationException, IllegalAccessException, ConexionException {
	        if (connection != null)
	            return connection;
	        else {
	            try {
	                Class.forName("org.postgresql.Driver").newInstance();
	                String url = "jdbc:postgresql://localhost:5432/ventas";
	                String user = "postgres";
	                String password = "123456";
	                connection = DriverManager.getConnection(url, user, password);
	            } catch (ClassNotFoundException | SQLException e) {
	            	throw new ConexionException("No se pudo conectar a la base de datos", e);
	            }
	            return connection;
	        }

	    }
	}