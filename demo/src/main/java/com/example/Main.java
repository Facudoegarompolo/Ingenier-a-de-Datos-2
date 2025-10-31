package com.example;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.model.User;
import com.example.mongoDB.MongoDBCRUD;
import com.example.views.MenuAdmin;
import com.example.views.MenuUser;
import com.example.views.ViewLogin;

@Component
public class Main implements CommandLineRunner {

    private final MongoDBCRUD mongoDBCRUD;
    private static boolean ejecutado = false;

    public Main(MongoDBCRUD mongoDBCRUD) {
        this.mongoDBCRUD = mongoDBCRUD;
    }

    @Override
    public void run(String... args) {
        if (ejecutado) return;
        ejecutado = true;

        ViewLogin view = new ViewLogin(mongoDBCRUD);
        User usuario = view.mostrarMenuPrincipal();

        if (usuario != null) {
            if (usuario.getTipoUsuario().equalsIgnoreCase("admin")) {
                System.out.println("Bienvenido, Administrador " + usuario.getNombre());
                MenuAdmin Menu = new MenuAdmin(usuario);
                Menu.mostrarMenu();
            } else {
                System.out.println("Bienvenido, Usuario " + usuario.getNombre());
                MenuUser Menu = new MenuUser(usuario);
                Menu.mostrarMenu();
                }
        }

    }
}
