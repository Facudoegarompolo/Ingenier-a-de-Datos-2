package com.example.mongoDB;

import org.bson.Document;
import org.springframework.stereotype.Component;

import com.mongodb.client.MongoCollection;

@Component
public class MongoDBCRUD {

    // Método de búsqueda por mail
    public void buscarUsuarioPorMail(MongoCollection<Document> collection, String mail) {
        System.out.println("🔍 Resultados de búsqueda para mail '" + mail + "':");
        collection.find(new Document("mail", mail))
                  .forEach(doc -> System.out.println(doc.toJson()));
    }
}
