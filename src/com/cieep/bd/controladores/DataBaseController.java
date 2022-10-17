package com.cieep.bd.controladores;

import com.cieep.bd.Constantes;
import com.cieep.bd.modelos.Animal;

import java.sql.*;

public class DataBaseController {
    //Esta clase debe dar las herramientas para gestionar la base de datos
    //Atributos privados e inamovibles con los datos necesarios de la base de datos #1
    private final String SERVER = "jdbc:mysql://localhost/granja";
    private final String USER = "root";
    private final String PASSWORD = "toor";

    public DataBaseController() throws SQLException {
        inicializaTablas();
    }

    //Función de conexión para la base de datos, necesitando los datos escritos previamente. #2
    public Connection obtenerConexion() throws SQLException {
        return DriverManager.getConnection(SERVER, USER, PASSWORD);
    }

    //Función que crea una tabla en la base de datos "granja". Cuando se conecta, crea la orden en una query
    // (orden a ejecutar) y, una vez se conecta y no da error, se ejecuta la query y se crea la tabla. #3
    private void inicializaTablas() throws SQLException {
        Connection connection = obtenerConexion();
        String query = "create table if not exists "+Constantes.TABLA_ANIMALES+"(\n" +
                "    "+Constantes.ID_ANIMAL+" int primary key ,\n" +
                "    "+Constantes.TIPO+" varchar(40) not null ,\n" +
                "    "+ Constantes.NOMBRE+" varchar(40) not null ,\n" +
                "    "+Constantes.COLOR+" varchar(10) ,\n" +
                "    "+Constantes.EDAD+" int not null ,\n" +
                "    "+Constantes.NUM_ENFERMEDADES+" int not null\n" +
                ");";
        Statement stm = connection.createStatement();
        stm.execute(query);
    }

    public int insertarAnimal(Animal animal, Connection connection) throws SQLException {
        String query = "insert into "+Constantes.TABLA_ANIMALES+" values (?,?,?,?,?,?);";
        PreparedStatement pstm = connection.prepareStatement(query);
        pstm.setInt(1, animal.getIdAnimal());
        pstm.setString(2, animal.getTipo());
        pstm.setString(3, animal.getNombre());
        pstm.setString(4, animal.getColor());
        pstm.setInt(5, animal.getEdad());
        pstm.setInt(6, animal.getNumEnfermedades());

        return pstm.executeUpdate();
    }

    public Animal getAnimal(int idAnimal, Connection connection) throws SQLException {
        String query = "select * from "+Constantes.TABLA_ANIMALES+" where "+Constantes.TABLA_ANIMALES+" = ?";
        PreparedStatement pstm = connection.prepareStatement(query);
        pstm.setInt(1, idAnimal);
        ResultSet rs = pstm.executeQuery();
        if (rs.first()){
            return new Animal(
                    rs.getInt(Constantes.ID_ANIMAL),
                    rs.getString(Constantes.TIPO),
                    rs.getString(Constantes.NOMBRE),
                    rs.getString(Constantes.COLOR),
                    rs.getInt(Constantes.EDAD),
                    rs.getInt(Constantes.NUM_ENFERMEDADES)
            );
        }else {
            return null;
        }

    }
}
