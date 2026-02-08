package ClasesDAO;

import com.mycompany.mercadollars.ConexionBD;
import java.sql.*;


/**
 *
 * @author Carlos Duarte Ruiz
 */

public class VentasDAO {


    private static final String SQL_INSERT = 
        "INSERT INTO Ventas (idVentas, Clientes_idClientes, Productos_idProductos, Usuario_idEmpleados, Fecha_venta, Ticket) VALUES (?, ?, ?, ?, ?, ?)";

    public boolean realizarVenta(int idVenta, int idCliente, int idProducto, int idEmpleado, int ticket) {
        Connection conn = ConexionBD.abrirConexion();
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement(SQL_INSERT);

            ps.setInt(1, idVenta);
            ps.setInt(2, idCliente);
            ps.setInt(3, idProducto);
            ps.setInt(4, idEmpleado);
            ps.setTimestamp(5, new Timestamp(System.currentTimeMillis())); // Fecha actual
            ps.setInt(6, ticket);

            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.err.println("Error de SQL: " + e.getMessage());
            return false;
        } finally {
            try { if (ps != null) ps.close(); } catch (Exception e) {}
            try { if (conn != null) conn.close(); } catch (Exception e) {}
            ConexionBD.cerrar(null, conn); // cerrar conexion a BD por seguridad
        }
    }
}