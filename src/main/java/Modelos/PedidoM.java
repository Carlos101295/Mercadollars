/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos;

/**
 *
 * @author Fran
 */
public class PedidoM {

    private int idPedido;
    private int cantidad;
    private int idProveedor;
    private int idEmpleado;

    // Constructor vacío
    public PedidoM() {
    }

    // Constructor para inserciones (el ID suele ser auto-incremental)
    public PedidoM(int cantidad, int idProveedor, int idEmpleado) {
        this.cantidad = cantidad;
        this.idProveedor = idProveedor;
        this.idEmpleado = idEmpleado;
    }

    // Getters y Setters
    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }
}
