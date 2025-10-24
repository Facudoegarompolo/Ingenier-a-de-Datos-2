package com.example;

import org.bson.Document;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.mongoDB.MongoDBCRUD;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

@Component
public class Main implements CommandLineRunner {

    private final MongoDatabase mongoDatabase;
    private final MongoDBCRUD mongoDBCRUD;

    private static boolean ejecutado = false; // evita múltiples ejecuciones

    public Main(MongoDatabase mongoDatabase, MongoDBCRUD mongoDBCRUD) {
        this.mongoDatabase = mongoDatabase;
        this.mongoDBCRUD = mongoDBCRUD;
    }

    @Override
    public void run(String... args) {
        if (ejecutado) return;
        ejecutado = true;

        System.out.println("✅ Conectado a MongoDB: " + mongoDatabase.getName());

        String coleccion = "user";
        MongoCollection<Document> collection = mongoDatabase.getCollection(coleccion);

        // Insertar documento de ejemplo con mail si no existe
        Document ejemploMail = new Document("nombre", "Ejemplo")
                                .append("mail", "ejemplo@mail.com")
                                .append("edad", 25);

        if (collection.find(new Document("mail", "ejemplo@mail.com")).first() == null) {
            collection.insertOne(ejemploMail);
            System.out.println("📄 Documento de ejemplo insertado con mail.");
        }

        // Listar colecciones
        System.out.println("📂 Colecciones disponibles:");
        mongoDatabase.listCollectionNames().forEach(System.out::println);

        // Listar documentos de la colección
        System.out.println("📄 Documentos en la colección '" + coleccion + "':");
        collection.find().forEach(doc -> System.out.println(doc.toJson()));

        // Buscar usuario por mail usando MongoDBCRUD
        mongoDBCRUD.buscarUsuarioPorMail(collection, "ejemplo@mail.com");
    }
}
