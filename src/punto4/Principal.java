package punto4;

import java.io.*;
import java.util.ArrayList;

public class Principal {
//-----------------	
//|		MAIN	  |
//-----------------
	public static void main(String[] args) {
		String ruta="sentencias.txt";//ruta donde se encuentra el archivo
		ArrayList<String> sentencias =obtenerSentencia(ruta);//lee las sentencias del archivo
		for(int i=0;i<sentencias.size();i++){
			//Por cada sentencia SQL se lanza un nuevo hilo para su ejecucion
			new Thread (new Escritor(i+1,sentencias.get(i))).start();
		}
	}

//-------------------------------
//|		LEER LAS SENTENCIAS 	|
//-------------------------------
//LEE LAS SENTENCIAS LINEA POR LINEA DEL ARCHIVO
	public static ArrayList<String> obtenerSentencia(String ruta){
		ArrayList<String> consultas= new ArrayList<String>();
		File archivo = new File(ruta);//archivo que se pasa como argumento
		try {
			BufferedReader fich = new BufferedReader(new FileReader(archivo));
			//Usamos la clase BufferReadeader para tener acceso a un metodo propio (readLine()) y asi mediante un contador contar las lineas.
		   int consulta = 0;//numero de consulta realizada
			String linea;
			try {
				//En este caso la condicion final del while corresponde a null, para indicar el final de linea
				while((linea = fich.readLine()) != null){
					//quita la linea vacia
					if(!linea.equals("")){
						
						consultas.add(consulta, linea);//guarda la consulta que es en un arreglo
						consulta++;//
					}
				}
				fich.close();   
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return consultas;
	}
}
