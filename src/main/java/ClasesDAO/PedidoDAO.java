/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ClasesDAO;

/**
 *
 * @author FranciscoJavierJimenezMuñoz
 */
import Modelos.PedidoM;
import java.sql.*;
import java.util.ArrayList;

public class PedidoDAO {

    private Connection conexion;

    public PedidoDAO(Connection conexion) {
        this.conexion = conexion;
    }

    public PedidoM buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM Pedido WHERE idPedido = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    PedidoM p = new PedidoM();
                    p.setIdPedido(rs.getInt("idPedido"));
                    p.setCantidad(rs.getInt("Cantidad"));
                    p.setIdProveedor(rs.getInt("Proveedores_idProveedores"));
                    p.setIdEmpleado(rs.getInt("Usuario_idEmpleados"));
                    return p;
                }
            }
        }
        return null;
    }

    public void actualizarPedido(PedidoM pedido) throws SQLException {
        String sql = "UPDATE Pedido SET Cantidad = ?, Proveedores_idProveedores = ?, Usuario_idEmpleados = ? WHERE idPedido = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, pedido.getCantidad());
            ps.setInt(2, pedido.getIdProveedor());
            ps.setInt(3, pedido.getIdEmpleado());
            ps.setInt(4, pedido.getIdPedido());
            ps.executeUpdate();
        }
    }
    
    public void insertarPedido(PedidoM pedido) throws SQLException {
        String sql = "INSERT INTO Pedido (Cantidad, Proveedores_idProveedores, Usuario_idEmpleados) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, pedido.getCantidad());
            ps.setInt(2, pedido.getIdProveedor());
            ps.setInt(3, pedido.getIdEmpleado());
            ps.executeUpdate();
        }
    }

    public ArrayList<PedidoM> obtenerTodos() throws SQLException {
        ArrayList<PedidoM> lista = new ArrayList<>();
        String sql = "SELECT * FROM Pedido";
        try (Statement st = conexion.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                PedidoM p = new PedidoM();
                p.setIdPedido(rs.getInt("idPedido"));
                p.setCantidad(rs.getInt("Cantidad"));
                p.setIdProveedor(rs.getInt("Proveedores_idProveedores"));
                p.setIdEmpleado(rs.getInt("Usuario_idEmpleados"));
                lista.add(p);
            }
        }
        return lista;
    }

    public void eliminarPedido(int id) throws SQLException {
        String sql = "DELETE FROM Pedido WHERE idPedido = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}
