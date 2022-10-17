package com.cieep;

import com.cieep.bd.controladores.DataBaseController;
import com.cieep.bd.modelos.Animal;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try {
            DataBaseController dataBaseController = new DataBaseController();
            Animal animal = new Animal(1, "Marino", "Nemo", "Naranja" ,1, 1);
            Connection connection = dataBaseController.obtenerConexion();
            dataBaseController.insertarAnimal(animal, connection);
        } catch (SQLException e) {
            System.out.println(e.getLocalizedMessage());
        }
    }
}