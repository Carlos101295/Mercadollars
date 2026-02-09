/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ClasesDAO;

import Util.ConexionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author ÓscarMaqueda
 */
public class ProductoDAO {

    public void aniadirProducto(String id_producto, String nombre, String grupo, String stock, String ratio, String precioNoIva, String ivaAplicable, String precioFinal) {
        String sql = "INSERT INTO Productos (idProductos, Nombre, Grupo_tipo, Stock, Ratio_aviso, Precio_sin_IVA, IVA_aplicable, Precio_final) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        Connection conn = ConexionBD.abrirConexion();
        PreparedStatement ps = null;
        try {
            float precioSinIva = Float.parseFloat(precioNoIva);
            int iva = Integer.parseInt(ivaAplicable);
            ps = conn.prepareStatement(sql);
            ps.setInt(1, Integer.parseInt(id_producto));
            ps.setString(2, nombre);
            ps.setString(3, grupo);
            ps.setInt(4, Integer.parseInt(stock));
            ps.setInt(5, Integer.parseInt(ratio)); //RATIO AVISO
            ps.setFloat(6, precioSinIva);
            ps.setInt(7, iva);
            ps.setFloat(8, Float.parseFloat(precioFinal));
            ps.executeUpdate();

            System.out.println(id_producto + nombre + grupo + stock + precioNoIva + ivaAplicable + precioFinal);
            JOptionPane.showMessageDialog(null, "producto añadido correctamente", "OK", JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "error", JOptionPane.ERROR_MESSAGE);
        } finally {
            ConexionBD.cerrar(ps, conn);
        }

    }

    public ArrayList<String> buscarPorId(int id) {
        String sql = "SELECT idProductos, Nombre, Grupo_tipo, Stock, Ratio_aviso, Precio_sin_IVA, IVA_aplicable, Precio_final FROM Productos WHERE idProductos = ?";
        Connection conn = ConexionBD.abrirConexion();
        ResultSet rs = null;
        PreparedStatement ps = null;

        ArrayList<String> dump = new ArrayList<>();

        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                dump.add(String.valueOf(rs.getInt("idProductos")));
                dump.add(rs.getString("Nombre"));
                dump.add(rs.getString("Grupo_tipo"));
                dump.add(String.valueOf(rs.getInt("Stock")));
                dump.add(String.valueOf(rs.getInt("Ratio_aviso")));
                dump.add(String.valueOf(rs.getFloat("Precio_sin_IVA")));
                dump.add(String.valueOf(rs.getInt("IVA_aplicable")));
                dump.add(String.valueOf(rs.getFloat("Precio_final")));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "error", JOptionPane.ERROR_MESSAGE);
        } finally {
            ConexionBD.cerrar(rs, ps, conn);
            if (!dump.isEmpty()) {
                return dump;
            } else {
                return null;
            }
        }
    }

    public void eliminarProducto(int id) {
        String sql = "DELETE FROM Productos WHERE idProductos = ?";
        Connection conn = ConexionBD.abrirConexion();
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Se ha eliminado correctamente");

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "error", JOptionPane.ERROR_MESSAGE);
        } finally {
            ConexionBD.cerrar(ps, conn);
        }

    }

    public void actualizarProducto(String id_producto, String nombre, String grupo, String stock, String ratio, String precioNoIva, String ivaAplicable, String precioFinal) {
        String sql = "UPDATE Productos SET Nombre = ?, Grupo_tipo = ?, Stock = ?, Ratio_aviso = ?, Precio_sin_IVA = ?, IVA_aplicable = ?, Precio_final = ? WHERE idProductos = ?";
        Connection conn = ConexionBD.abrirConexion();
        PreparedStatement ps = null;
        
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, nombre);
            ps.setString(2, grupo);
            ps.setString(3, stock);
            ps.setString(4, ratio);
            ps.setString(5, precioNoIva);
            ps.setString(6, ivaAplicable);
            ps.setString(7, precioFinal);
            ps.setString(8, id_producto);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Se ha actualizado correctamente");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "error", JOptionPane.ERROR_MESSAGE);
        }finally{
             ConexionBD.cerrar(ps, conn);
        }
    }
}

