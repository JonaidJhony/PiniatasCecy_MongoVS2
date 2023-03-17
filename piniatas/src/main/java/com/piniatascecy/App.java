package com.piniatascecy;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import java.util.ArrayList;
import java.util.Scanner;

import org.bson.Document;


public class App {
    public static void main(String[]args) {
        
        MongoClientURI uri = crearConexion();
        
        try(MongoClient mongoClient = new MongoClient(uri))
        {
            Scanner scan = new Scanner(System.in);
            MongoDatabase database = mongoClient.getDatabase("Piniatas");
            
            //INSERTAR PIÑATA
            //System.out.println("Ingrese el nombre de la piñata");
            //String ingresarPiniata = scan.nextLine();
            //System.out.println("Ingrese el codigo de la piñata");
            //String ingresarCodigo = scan.nextLine();
            //insertarPiniata(database, "piniata", ingresarPiniata, ingresarCodigo);
            //mostrarColeccion(database, "piniata");

            
            //ACTUALIZAR PIÑATA
            mostrarColeccion(database, "piniata");
            System.out.println("Nombre de la piñata que se va a actualizar");
            String actualizaPiniata = scan.nextLine();
            System.out.println("Ingrese el nuevo código de la piniata");
            String actualizaCodigo= scan.nextLine();
            actualizarPiniata(database, "piniata", actualizaPiniata, actualizaCodigo);
            mostrarColeccion(database, "piniata");

           
            //BUSCAR POR NOMBRE
            //System.out.println("Nombre de la piñata que está buscando");
            //mostrarColeccion(database, "piniata");
            //String buscarPiniata = scan.nextLine();
            //buscarPorNombre(database, "piniata", buscarPiniata);

           
            //ELIMINAR PIÑATA
            //mostrarColeccion(database, "piniata");
            //System.out.println("Nombre de la piñata que se va a borrar");
            //String eliminaPiniata = scan.nextLine();
            //borrarPiniata(database, "piniata", eliminaPiniata);
            //mostrarColeccion(database, "piniata");

        } catch (Exception e)
        {   
            System.out.println(e);
        }
    }
    
    // MUESTRA TODOS LOS DOCUMENTOS DE LA COLECCION USUARIOS
    public static void mostrarColeccion(MongoDatabase database, String coleccion) {
        MongoCollection<Document> colec = database.getCollection(coleccion);
        
        MongoCursor<Document> document = colec.find().iterator();
        
         while(document.hasNext()){
                ArrayList<Object> veri = new ArrayList(document.next().values());
                for (int i = 0; i < veri.size(); i++)
                {
                System.out.println(veri.get(i));    
                }
            }
    }
    
    // METODO PARA CREAR LA CONEXION A MONGODB
    public static MongoClientURI crearConexion() {
        System.out.println("PRUEBA CONEXION MONGODB");
        
        MongoClientURI uri = new MongoClientURI("mongodb+srv://jonaidjhony09:haruka09@piniatas.dbdeuem.mongodb.net/?retryWrites=true&w=majority");
        
        return uri;
    }
    
    // METODO PARA INSERTAR UN DOCUMENTO (REGISTRO)
    public static void insertarPiniata(MongoDatabase database, String coleccion, String nombre, String codigo) {
        MongoCollection<Document> collection = database.getCollection(coleccion);
        
        // CREA EL DOCUMENTO(REGISTRO) E INSERTA LA INFORMACION RECIBIDA
        
        Document documento = new Document();
        documento.put("nombre", nombre);
        documento.put("codigo", codigo);
        collection.insertOne(documento);
        
    }
               
    // MUESTRA TODOS LOS DOCUMENTOS DE LA COLECCION USUARIOS QUE COINCIDAN CON EL NOMBRE
    public static void buscarPorNombre(MongoDatabase database, String coleccion, String nombre) {
        MongoCollection<Document> collection = database.getCollection(coleccion);
        
        // CREAMOS LA CONSULTA CON EL CAMPO NOMBRE
        Document consulta = new Document();
        consulta.put("nombre", nombre);
        
        // BUSCA Y MUESTRA TODOS LOS DOCUMENTOS QUE COINCIDAN CON LA CONSULTA
        MongoCursor<Document> cursor = collection.find(consulta).iterator();
        while(cursor.hasNext()) {
            System.out.println("-- " + cursor.next().get("codigo"));
        }
    }
    
    // METODO PARA ACTUALIZAR UN DOCUMENTO (REGISTRO)
    public static void actualizarPiniata(MongoDatabase database, String coleccion, String nombre, String codigo) {
        MongoCollection<Document> collection = database.getCollection(coleccion);
        
        // SENTENCIA CON LA INFORMACION A REMPLAZAR
        Document actualizarPiniata = new Document();
        actualizarPiniata.append("$set", new Document().append("codigo", codigo));
        
        // BUSCA EL DOCUMENTO EN LA COLECCION
        Document buscarPorNombre = new Document();
        buscarPorNombre.append("nombre", nombre);
        
        // REALIZA EL UPDATE
        collection.updateOne(buscarPorNombre, actualizarPiniata);
    }
    
    // METODO PARA ELIMINAR UN DOCUMENTO (REGISTRO)
    public static void borrarPiniata(MongoDatabase database, String coleccion, String nombre) {
        MongoCollection<Document> collection = database.getCollection(coleccion);
        
        collection.deleteOne(new Document().append("nombre", nombre));
    }
}
