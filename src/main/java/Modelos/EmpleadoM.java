/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos;

/**
 *
 * @author pablo
 */
public class EmpleadoM {

    // Atributos
    private int idEmpleados;
    private String nombre;
    private String apellidos;
    private String dni;
    private String contraseña;
    private String email;
    private String telefono;
    private String admin;

    // Constructor vacío
    public EmpleadoM() {
    }

    // Constructor con parámetros (sin ID para inserciones)
    public EmpleadoM(String nombre, String apellidos, String dni, String contraseña,
            String email, String telefono, String admin) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.dni = dni;
        this.contraseña = contraseña;
        this.email = email;
        this.telefono = telefono;
        this.admin = admin;
    }

    // Constructor completo (con ID para consultas)
    public EmpleadoM(int idEmpleados, String nombre, String apellidos, String dni,
            String contraseña, String email, String telefono, String admin) {
        this.idEmpleados = idEmpleados;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.dni = dni;
        this.contraseña = contraseña;
        this.email = email;
        this.telefono = telefono;
        this.admin = admin;
    }

    // Getters y Setters
    public int getIdEmpleados() {
        return idEmpleados;
    }

    public void setIdEmpleados(int idEmpleados) {
        this.idEmpleados = idEmpleados;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    // Método helper para verificar si es admin
    public boolean isAdmin() {
        return "SI".equals(admin);
    }

    // toString para debugging
    @Override
    public String toString() {
        return "EmpleadoM{"
                + "idEmpleados=" + idEmpleados
                + ", nombre='" + nombre + '\''
                + ", apellidos='" + apellidos + '\''
                + ", dni='" + dni + '\''
                + ", email='" + email + '\''
                + ", telefono='" + telefono + '\''
                + ", admin='" + admin + '\''
                + '}';
    }
}
