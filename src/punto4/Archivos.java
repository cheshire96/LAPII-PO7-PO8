package punto4;

import java.io.*;
import java.sql.*;

public class Archivos {
	
	private static Archivos instancia=new Archivos();
	
//---------------------------
//|		OBJETO MONITOR		|
//---------------------------
	public static Archivos getPrueba () {
		return Archivos.instancia;
	}
	
//-----------------------------------
//|		OBTENER EL RESULTADO	    |
//-----------------------------------
	//OBTENER RESULTADO: realiza la consulta a la base de datos y devuelve su resultado
	public synchronized void obtenerResultado(Connection conn,int numeroSentencia,String sentencia){
		int contador=0;
		int j=0;
		PreparedStatement preparedStatement;
		String columnas="";
		String resultado="";
		String archivo="resultados.txt";
		try {
			preparedStatement = conn.prepareStatement( sentencia );
			ResultSet rs = preparedStatement.executeQuery();//Se realiza la consulta SQL a la base de datos

	        while(rs.next()){//Recorre los resultados obtenidos de la consulta SQL
	        	
	        	//Rrecorre todas las columnas de la tupla resultante
	        	for (int i=1;i<=rs.getMetaData().getColumnCount();i++){
					
					if(j==0){//Si es la primera vez que va a escribir un resultado
						//Recorre un bucle para obtener todos los nombres de las columnas
						for (int k=1;k<=rs.getMetaData().getColumnCount();k++){
							columnas=columnas+"\t"+rs.getMetaData().getColumnName(k);//concatena los nombres de las columnas
						}
						//Escribe los nombres de las columnas en el archivo
						escribirArchivo(numeroSentencia, sentencia, archivo, columnas, contador);
						contador++;
					}
						resultado=resultado+"\t"+rs.getString(i);//concatena los datos de los distintos campos pertenecientes a una misma tupla
					j++;
	        	}
	        	//Escribe en el archivo el numero de sentencia, la sentencia ejecutada y el resultado
	        	escribirArchivo(numeroSentencia, sentencia, archivo, "\n", contador);
	        	escribirArchivo(numeroSentencia, sentencia, archivo, resultado, contador);
	        	resultado="";//vuelve a vaciar el resultado
			}
	        String l="\n-------------------------------------------------------------------------------\n";
	        escribirArchivo(numeroSentencia, sentencia, archivo, l, contador);
	        escribirArchivo(numeroSentencia, sentencia, archivo, "\n", contador);
	    
		} catch (SQLException e) {//Si ocurre un error
			archivo="errores.txt";//El archivo donde se va a guardar todo pasa a ser errores.txt
			//Se escribe el numero de sentencia, la sentencia, y el error que se produjo en el archivo
			escribirArchivo(numeroSentencia, sentencia, archivo, e.getErrorCode()+": "+e.getMessage(), 0);
			String l="\n-------------------------------------------------------------------------------\n";
	        escribirArchivo(numeroSentencia, sentencia, archivo, l, 1);
	        escribirArchivo(numeroSentencia, sentencia, archivo, "\n", 1);
		}
        		
	}

	
//-----------------------------------
//|		ESCRIBIR EN EL ARCHIVO	    |
//-----------------------------------
//Escribe en el archivo el numero de consulta, la sentencia, y el resultado de la sentencia	
	public static void escribirArchivo(int numeroSentencia,String sentencia,String archivo, String resultado, int contador){
			
		try {
			
			//Archivo donde se debe escribir el numero de consulta, la sentencia que se dio, y el resultado de esa sentencia
			File copia = new File(archivo);
			FileWriter f= new FileWriter(copia, true);	    
				
			if(contador==0){//Si es la primera vez que va a escribir una linea
				//Escribe en el archivo que consulta es
				if(numeroSentencia<10){
					f.write("Consulta 0"+numeroSentencia);
				}else{
					f.write("Consulta "+numeroSentencia);
				}
				//Escribe en el archivo la sentencia que se ejecuto
				f.write("\n"+sentencia+"\n");
			}
			//Se escribe el resultado en el archivo
			f.write(resultado);
			//Se cierra el archivo
		    f.close();
			   
		} catch (IOException e) {
			archivo="errores.txt";//El archivo donde se va a guardar todo pasa a ser errores.txt
			//Se escribe el numero de sentencia, la sentencia, y el error que se produjo en el archivo
			escribirArchivo(numeroSentencia, sentencia, archivo, e.getMessage(), 0);
			String l="\n-------------------------------------------------------------------------------\n";
	        escribirArchivo(numeroSentencia, sentencia, archivo, l, 1);
	        escribirArchivo(numeroSentencia, sentencia, archivo, "\n", 1);
		    
		}
	}
				
}
