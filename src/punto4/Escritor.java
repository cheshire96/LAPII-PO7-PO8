package punto4;

import java.sql.Connection;

public class Escritor implements Runnable{
	
	int numeroSentencia;
	String sentencia;
	private Connection conn;
//------------------------
//|		CONSTRUCTOR		 |
//------------------------
	public Escritor(int numeroSentencia,String sentencia) {	
		this.numeroSentencia= numeroSentencia;//numero de sentencia que va a ejecutar
		this.sentencia = sentencia;//sentencia que va a ejecutar
		try {
			//crea una nueva conexion a la base de datos para un hilo
            conn = Conexion.getConnection();
        } catch (InstantiationException | IllegalAccessException ex) {
        	String archivo="errores.txt";//El archivo donde se va a guardar todo pasa a ser errores.txt
			//Se escribe el numero de sentencia, la sentencia, y el error que se produjo en el archivo
        	Archivos.escribirArchivo(numeroSentencia, sentencia, archivo, ex.getMessage(), 0);
			String l="\n-------------------------------------------------------------------------------\n";
	        Archivos.escribirArchivo(numeroSentencia, sentencia, archivo, l, 1);
	        Archivos.escribirArchivo(numeroSentencia, sentencia, archivo, "\n", 1);
        }

	}

	
	public void run() {
		Archivos.getPrueba().obtenerResultado(this.conn, this.numeroSentencia, this.sentencia);
		
	}
	
}