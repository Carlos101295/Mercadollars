/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ClasesDAO;

/**
 *
 * @author MEDAC
 */
import Modelos.EmpleadoM;
import com.mycompany.mercadollars.ConexionBD;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {


    // 1. REGISTRAR un nuevo empleado (Admin por defecto "NO")
    public boolean registrarEmpleado(EmpleadoM empleado) {
        Connection conn = ConexionBD.abrirConexion();
        String sql = "INSERT INTO Usuario (Nombre, Apellidos, DNI, Contraseña, Email, Telefono, Admin) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, empleado.getNombre());
            pstmt.setString(2, empleado.getApellidos());
            pstmt.setString(3, empleado.getDni());
            pstmt.setString(4, empleado.getContraseña());
            pstmt.setString(5, empleado.getEmail());
            pstmt.setString(6, empleado.getTelefono());
            // Si no se especifica, se asigna "NO" por defecto
            pstmt.setString(7, empleado.getAdmin() != null ? empleado.getAdmin() : "NO");

            int filasAfectadas = pstmt.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }finally{
            ConexionBD.cerrar(null, conn);
        }
        
    }

    // 1b. REGISTRAR empleado simple (sin especificar Admin, será "NO" automáticamente)
    public boolean registrarEmpleadoSimple(String nombre, String apellidos, String dni,
            String contraseña, String email, String telefono) {
        Connection conn = ConexionBD.abrirConexion();
        String sql = "INSERT INTO Usuario (Nombre, Apellidos, DNI, Contraseña, Email, Telefono) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nombre);
            pstmt.setString(2, apellidos);
            pstmt.setString(3, dni);
            pstmt.setString(4, contraseña);
            pstmt.setString(5, email);
            pstmt.setString(6, telefono);
            // No se especifica Admin, la BD asigna "NO" por defecto

            int filasAfectadas = pstmt.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }finally{
            ConexionBD.cerrar(null, conn);
        }
    }

    // 2. BUSCAR empleado por DNI
    public EmpleadoM buscarPorDNI(String dni) {
        Connection conn = ConexionBD.abrirConexion();
        String sql = "SELECT * FROM Usuario WHERE DNI = ?";
        EmpleadoM empleado = null;

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, dni);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                empleado = new EmpleadoM(
                        rs.getInt("idEmpleados"),
                        rs.getString("Nombre"),
                        rs.getString("Apellidos"),
                        rs.getString("DNI"),
                        rs.getString("Contraseña"),
                        rs.getString("Email"),
                        rs.getString("Telefono"),
                        rs.getString("Admin")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            ConexionBD.cerrar(null, conn);
        }

        return empleado;
    }

    // 3. MODIFICAR empleado
    public boolean modificarEmpleado(EmpleadoM empleado) {
        Connection conn = ConexionBD.abrirConexion();
        String sql = "UPDATE Usuario SET Nombre = ?, Apellidos = ?, "
                + "Email = ?, Telefono = ?, Contraseña = ? WHERE DNI = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, empleado.getNombre());
            pstmt.setString(2, empleado.getApellidos());
            pstmt.setString(3, empleado.getEmail());
            pstmt.setString(4, empleado.getTelefono());
            pstmt.setString(5, empleado.getContraseña());
            pstmt.setString(6, empleado.getDni());

            int filasAfectadas = pstmt.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }finally{
            ConexionBD.cerrar(null, conn);
        }
    }

    // 4. DAR DE BAJA empleado (eliminación física)
    public boolean eliminarEmpleado(String dni) {
        Connection conn = ConexionBD.abrirConexion();
        String sql = "DELETE FROM Usuario WHERE DNI = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, dni);

            int filasAfectadas = pstmt.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }finally{
            ConexionBD.cerrar(null, conn);
        }
    }

    // 5. VALIDAR credenciales para login
    public EmpleadoM validarLogin(String email, String contraseña) {
        Connection conn = ConexionBD.abrirConexion();
        String sql = "SELECT * FROM Usuario WHERE Email = ? AND Contraseña = ?";
        EmpleadoM empleado = null;

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);
            pstmt.setString(2, contraseña);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                empleado = new EmpleadoM(
                        rs.getInt("idEmpleados"),
                        rs.getString("Nombre"),
                        rs.getString("Apellidos"),
                        rs.getString("DNI"),
                        rs.getString("Contraseña"),
                        rs.getString("Email"),
                        rs.getString("Telefono"),
                        rs.getString("Admin")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            ConexionBD.cerrar(null, conn);
        }

        return empleado;
    }

    // 6. VERIFICAR si existe un DNI
    public boolean existeDNI(String dni) {
        Connection conn = ConexionBD.abrirConexion();
        String sql = "SELECT COUNT(*) FROM Usuario WHERE DNI = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, dni);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            ConexionBD.cerrar(null, conn);
        }

        return false;
    }

    // 7. VERIFICAR si existe un Email
    public boolean existeEmail(String email) {
        Connection conn = ConexionBD.abrirConexion();
        String sql = "SELECT COUNT(*) FROM Usuario WHERE Email = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            ConexionBD.cerrar(null, conn);
        }

        return false;
    }

    // 8. LISTAR todos los empleados
    public List<EmpleadoM> listarTodos() {
        Connection conn = ConexionBD.abrirConexion();
        String sql = "SELECT * FROM Usuario ORDER BY Nombre";
        List<EmpleadoM> empleados = new ArrayList<>();

        try (PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                EmpleadoM empleado = new EmpleadoM(
                        rs.getInt("idEmpleados"),
                        rs.getString("Nombre"),
                        rs.getString("Apellidos"),
                        rs.getString("DNI"),
                        rs.getString("Contraseña"),
                        rs.getString("Email"),
                        rs.getString("Telefono"),
                        rs.getString("Admin")
                );
                empleados.add(empleado);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            ConexionBD.cerrar(null, conn);
        }

        return empleados;
    }

    // 9. CAMBIAR contraseña
    public boolean cambiarContraseña(String dni, String nuevaContraseña) {
        Connection conn = ConexionBD.abrirConexion();
        String sql = "UPDATE Usuario SET Contraseña = ? WHERE DNI = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nuevaContraseña);
            pstmt.setString(2, dni);

            int filasAfectadas = pstmt.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }finally{
            ConexionBD.cerrar(null, conn);
        }
    }
}
