/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ClasesDAO;

import Modelos.ClienteM;
import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author FranciscoJavierJimenezMuñoz
 */
public class ClienteDAO {

    private Connection conexion;

    public ClienteDAO(Connection conexion) {
        this.conexion = conexion;
    }

    public void insertarCliente(ClienteM cliente) throws SQLException {
        String sql = "INSERT INTO Clientes (Nombre, Fecha_nacimiento, Telefono, DNI, Puntos) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, cliente.getNombre());
            ps.setDate(2, cliente.getFechaNacimiento());
            ps.setString(3, cliente.getTelefono());
            ps.setString(4, cliente.getDni());
            ps.setInt(5, cliente.getPuntos());
            ps.executeUpdate();
        }
    }

    public ArrayList<ClienteM> obtenerTodos() throws SQLException {
        ArrayList<ClienteM> lista = new ArrayList<>();
        String sql = "SELECT * FROM Clientes";
        try (Statement st = conexion.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                ClienteM c = new ClienteM();
                c.setIdClientes(rs.getInt("idClientes"));
                c.setNombre(rs.getString("Nombre"));
                c.setFechaNacimiento(rs.getDate("Fecha_nacimiento"));
                c.setTelefono(rs.getString("Telefono"));
                c.setDni(rs.getString("DNI"));
                c.setPuntos(rs.getInt("Puntos"));
                lista.add(c);
            }
        }
        return lista;
    }

    public void eliminarCliente(int id) throws SQLException {
        String sql = "DELETE FROM Clientes WHERE idClientes = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
    
    public ClienteM buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM Clientes WHERE idClientes = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ClienteM c = new ClienteM();
                    c.setIdClientes(rs.getInt("idClientes"));
                    c.setNombre(rs.getString("Nombre"));
                    c.setFechaNacimiento(rs.getDate("Fecha_nacimiento"));
                    c.setTelefono(rs.getString("Telefono"));
                    c.setDni(rs.getString("DNI"));
                    c.setPuntos(rs.getInt("Puntos"));
                    return c;
                }
            }
        }
        return null;
    }

    public void actualizarCliente(ClienteM cliente) throws SQLException {
        String sql = "UPDATE Clientes SET Nombre = ?, Fecha_nacimiento = ?, Telefono = ?, DNI = ?, Puntos = ? WHERE idClientes = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, cliente.getNombre());
            ps.setDate(2, cliente.getFechaNacimiento());
            ps.setString(3, cliente.getTelefono());
            ps.setString(4, cliente.getDni());
            ps.setInt(5, cliente.getPuntos());
            ps.setInt(6, cliente.getIdClientes());
            ps.executeUpdate();
        }
    }
}
