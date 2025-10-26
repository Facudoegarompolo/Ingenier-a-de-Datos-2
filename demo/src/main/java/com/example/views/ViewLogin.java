package com.example.views;

import java.util.Optional;
import java.util.Scanner;

import com.example.model.User;
import com.example.mongoDB.MongoDBCRUD;

public class ViewLogin {

    private final MongoDBCRUD mongoDBCRUD;

    public ViewLogin(MongoDBCRUD mongoDBCRUD) {
        this.mongoDBCRUD = mongoDBCRUD;
    }

    /**
     * Muestra el menú principal y gestiona el login.
     * Si el login es exitoso, devuelve el usuario completo.
     * Si falla, devuelve null.
     */
    public User mostrarMenuPrincipal() {
        Scanner sc = new Scanner(System.in);
        User usuarioEnSesion = null;
        boolean salir = false;

        while (!salir && usuarioEnSesion == null) {
            System.out.println("\n==== MENÚ PRINCIPAL =====");
            System.out.println("1) Iniciar sesión");
            System.out.println("2) Salir");
            System.out.print("Seleccione una opción: ");
            String opcion = sc.nextLine().trim();

            switch (opcion) {
                case "1":
                    usuarioEnSesion = intentarLogin(sc);
                    break;
                case "2":
                    salir = true;
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opción inválida. Intente nuevamente.");
            }
        }

        if (usuarioEnSesion != null) {
            System.out.println("\n✅ Bienvenido, " + usuarioEnSesion.getNombre() +
                    " (" + usuarioEnSesion.getMail() + ") — Tipo: " + usuarioEnSesion.getTipoUsuario());
        }

        return usuarioEnSesion;
    }

    private User intentarLogin(Scanner sc) {
        System.out.println("\n===== INICIO DE SESIÓN =====");
        System.out.print("Ingrese su mail: ");
        String mail = sc.nextLine().trim();
        System.out.print("Ingrese su contraseña: ");
        String password = sc.nextLine().trim();

        Optional<User> maybeUser = mongoDBCRUD.buscarUsuarioPorMailYPassword(mail, password);
        if (maybeUser.isPresent()) {
            return maybeUser.get();
        } else {
            System.out.println("Credenciales incorrectas. Intente nuevamente.");
            return null;
        }
    }
}
