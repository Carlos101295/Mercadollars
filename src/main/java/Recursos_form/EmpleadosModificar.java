/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Recursos_form;

import java.awt.Color;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import ClasesDAO.UsuarioDAO;
import Modelos.EmpleadoM;

/**
 *
 * @author ÓscarMaqueda
 */
public class EmpleadosModificar extends javax.swing.JPanel {

    // DAO
    private UsuarioDAO usuarioDAO;

    // Empleado actual cargado
    private EmpleadoM empleadoActual;
    
    // ========== MÉTODOS DE FUNCIONALIDAD ==========
    
    private void buscarEmpleado() {
        String dni = txtDniSearch.getText().trim();

        // Buscar en la base de datos
        empleadoActual = usuarioDAO.buscarPorDNI(dni);

        if (empleadoActual != null) {
            // Llenar los campos con los datos del empleado
            txtNombre.setText(empleadoActual.getNombre());
            txtApellidos.setText(empleadoActual.getApellidos() != null ? empleadoActual.getApellidos() : "");
            txtDni.setText(empleadoActual.getDni());
            txtCorreo.setText(empleadoActual.getEmail());
            txtTelefono.setText(empleadoActual.getTelefono() != null ? empleadoActual.getTelefono() : "");

            // Habilitar campos para edición
            habilitarCampos();

            JOptionPane.showMessageDialog(this,
                    "Empleado encontrado: " + empleadoActual.getNombre(),
                    "Búsqueda Exitosa",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this,
                    "No se encontró ningún empleado con el DNI: " + dni,
                    "Empleado No Encontrado",
                    JOptionPane.ERROR_MESSAGE);
            limpiarCamposEmpleado();
            deshabilitarCampos();
        }
    }

    private void modificarEmpleado() {
        if (empleadoActual == null) {
            JOptionPane.showMessageDialog(this,
                    "Primero debe buscar un empleado",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Obtener datos del formulario
        String nombre = txtNombre.getText().trim();
        String apellidos = txtApellidos.getText().trim();
        String correo = txtCorreo.getText().trim();
        String telefono = txtTelefono.getText().trim();

        // Validaciones
        if (nombre.isEmpty() || correo.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Nombre y Correo son obligatorios",
                    "Campos Obligatorios",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Validar formato de email
        if (!validarEmail(correo)) {
            JOptionPane.showMessageDialog(this,
                    "El formato del correo electrónico no es válido",
                    "Email Inválido",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Actualizar objeto empleado
        empleadoActual.setNombre(nombre);
        empleadoActual.setApellidos(apellidos.isEmpty() ? null : apellidos);
        empleadoActual.setEmail(correo);
        empleadoActual.setTelefono(telefono.isEmpty() ? null : telefono);

        // Si se marcó cambiar contraseña
        if (chkCambiarContrasenia.isSelected()) {
            String nuevaContrasenia = new String(pwdContrasenia.getPassword());
            
            if (nuevaContrasenia.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Por favor, ingrese la nueva contraseña",
                        "Contraseña Requerida",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            if (nuevaContrasenia.length() < 5) {
                JOptionPane.showMessageDialog(this,
                        "La contraseña debe tener al menos 5 caracteres",
                        "Contraseña Débil",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            empleadoActual.setContraseña(nuevaContrasenia);
        }

        // Modificar en la base de datos
        boolean modificado = usuarioDAO.modificarEmpleado(empleadoActual);

        if (modificado) {
            JOptionPane.showMessageDialog(this,
                    "Empleado modificado exitosamente",
                    "Modificación Exitosa",
                    JOptionPane.INFORMATION_MESSAGE);
            
            // Limpiar checkbox y contraseña
            chkCambiarContrasenia.setSelected(false);
            pwdContrasenia.setText("");
            pwdContrasenia.setEnabled(false);
        } else {
            JOptionPane.showMessageDialog(this,
                    "Error al modificar el empleado.\nPor favor, intente nuevamente.",
                    "Error de Modificación",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void darDeBajaEmpleado() {
        if (empleadoActual == null) {
            JOptionPane.showMessageDialog(this,
                    "Primero debe buscar un empleado",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Confirmar eliminación
        int confirmacion = JOptionPane.showConfirmDialog(this,
                "¿Está seguro que desea dar de baja al empleado?\n\n" +
                "Nombre: " + empleadoActual.getNombre() + " " + 
                (empleadoActual.getApellidos() != null ? empleadoActual.getApellidos() : "") + "\n" +
                "DNI: " + empleadoActual.getDni() + "\n\n" +
                "Esta acción NO se puede deshacer.",
                "Confirmar Baja de Empleado",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (confirmacion == JOptionPane.YES_OPTION) {
            boolean eliminado = usuarioDAO.eliminarEmpleado(empleadoActual.getDni());

            if (eliminado) {
                JOptionPane.showMessageDialog(this,
                        "Empleado dado de baja exitosamente",
                        "Baja Exitosa",
                        JOptionPane.INFORMATION_MESSAGE);
                
                // Limpiar todo el formulario y deshabilitar campos
                limpiarFormularioCompleto();
                deshabilitarCampos();
                empleadoActual = null;
            } else {
                JOptionPane.showMessageDialog(this,
                        "Error al dar de baja el empleado.\nPor favor, intente nuevamente.",
                        "Error de Baja",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // ========== MÉTODOS AUXILIARES ==========

    private void habilitarCampos() {
        txtNombre.setEditable(true);
        txtApellidos.setEditable(true);
        txtCorreo.setEditable(true);
        txtTelefono.setEditable(true);
        chkCambiarContrasenia.setEnabled(true);
        btnGuardar1.setEnabled(true); // Botón Modificar
        btnGuardar.setEnabled(true);  // Botón Dar de baja
        
        // El DNI no se puede modificar
        txtDni.setEditable(false);
    }

    private void deshabilitarCampos() {
        txtNombre.setEditable(false);
        txtApellidos.setEditable(false);
        txtDni.setEditable(false);
        txtCorreo.setEditable(false);
        txtTelefono.setEditable(false);
        chkCambiarContrasenia.setEnabled(false);
        chkCambiarContrasenia.setSelected(false);
        pwdContrasenia.setEnabled(false);
        btnGuardar1.setEnabled(false); // Botón Modificar
        btnGuardar.setEnabled(false);  // Botón Dar de baja
    }

    private void limpiarCamposEmpleado() {
        txtNombre.setText("");
        txtApellidos.setText("");
        txtDni.setText("");
        txtCorreo.setText("");
        txtTelefono.setText("");
        pwdContrasenia.setText("");
        chkCambiarContrasenia.setSelected(false);
    }

    private void limpiarFormularioCompleto() {
        txtDniSearch.setForeground(Color.GRAY);
        txtDniSearch.setText("Inserte DNI a buscar");
        limpiarCamposEmpleado();
    }

    // Método para validar formato de email
    private boolean validarEmail(String email) {
        return email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    }

    /**
     * Creates new form EmpleadosModificar
     */
    public EmpleadosModificar() {
        usuarioDAO = new UsuarioDAO();
        empleadoActual = null;
        initComponents();
        // Deshabilitar campos hasta que se busque un empleado
        deshabilitarCampos();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        txtDniSearch = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        lblNombre = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        lblCorreo = new javax.swing.JLabel();
        txtCorreo = new javax.swing.JTextField();
        lblApellidos = new javax.swing.JLabel();
        txtApellidos = new javax.swing.JTextField();
        lblTelefono = new javax.swing.JLabel();
        txtTelefono = new javax.swing.JTextField();
        lblDni = new javax.swing.JLabel();
        txtDni = new javax.swing.JTextField();
        lblContrasenia = new javax.swing.JLabel();
        pwdContrasenia = new javax.swing.JPasswordField();
        btnGuardar1 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        chkCambiarContrasenia = new javax.swing.JCheckBox();

        setBackground(new java.awt.Color(255, 255, 255));
        setLayout(new java.awt.GridBagLayout());

        txtDniSearch.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        txtDniSearch.setForeground(new java.awt.Color(128, 128, 128));
        txtDniSearch.setText("Inserte DNI a buscar");
        txtDniSearch.setPreferredSize(new java.awt.Dimension(64, 40));
        txtDniSearch.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDniSearchFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDniSearchFocusLost(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 25, 25);
        add(txtDniSearch, gridBagConstraints);

        btnBuscar.setBackground(new java.awt.Color(0, 102, 102));
        btnBuscar.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        btnBuscar.setForeground(new java.awt.Color(255, 255, 255));
        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 40;
        gridBagConstraints.ipady = 10;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 25, 0);
        add(btnBuscar, gridBagConstraints);

        btnGuardar.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        btnGuardar.setForeground(new java.awt.Color(51, 153, 0));
        btnGuardar.setText("Dar de baja");
        btnGuardar.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 153, 0), 1, true));
        btnGuardar.setPreferredSize(new java.awt.Dimension(400, 39));
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.ipadx = 40;
        gridBagConstraints.ipady = 10;
        add(btnGuardar, gridBagConstraints);

        lblNombre.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        lblNombre.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lblNombre.setText("Nombre:");
        lblNombre.setPreferredSize(new java.awt.Dimension(125, 40));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 25, 0);
        add(lblNombre, gridBagConstraints);

        txtNombre.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        txtNombre.setPreferredSize(new java.awt.Dimension(300, 40));
        txtNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 25, 25, 0);
        add(txtNombre, gridBagConstraints);

        lblCorreo.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        lblCorreo.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lblCorreo.setText("Correo:");
        lblCorreo.setPreferredSize(new java.awt.Dimension(125, 40));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 25, 25, 0);
        add(lblCorreo, gridBagConstraints);

        txtCorreo.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        txtCorreo.setPreferredSize(new java.awt.Dimension(300, 40));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 25, 25, 0);
        add(txtCorreo, gridBagConstraints);

        lblApellidos.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        lblApellidos.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lblApellidos.setText("Apellidos:");
        lblApellidos.setPreferredSize(new java.awt.Dimension(125, 40));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 25, 0);
        add(lblApellidos, gridBagConstraints);

        txtApellidos.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        txtApellidos.setPreferredSize(new java.awt.Dimension(300, 40));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 25, 25, 0);
        add(txtApellidos, gridBagConstraints);

        lblTelefono.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        lblTelefono.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lblTelefono.setText("Teléfono:");
        lblTelefono.setPreferredSize(new java.awt.Dimension(125, 40));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 25, 25, 0);
        add(lblTelefono, gridBagConstraints);

        txtTelefono.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        txtTelefono.setPreferredSize(new java.awt.Dimension(300, 40));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 25, 25, 0);
        add(txtTelefono, gridBagConstraints);

        lblDni.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        lblDni.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lblDni.setText("DNI:");
        lblDni.setPreferredSize(new java.awt.Dimension(125, 40));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 25, 0);
        add(lblDni, gridBagConstraints);

        txtDni.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        txtDni.setPreferredSize(new java.awt.Dimension(300, 40));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 25, 25, 0);
        add(txtDni, gridBagConstraints);

        lblContrasenia.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        lblContrasenia.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lblContrasenia.setText("Contraseña:");
        lblContrasenia.setPreferredSize(new java.awt.Dimension(125, 40));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 25, 25, 0);
        add(lblContrasenia, gridBagConstraints);

        pwdContrasenia.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        pwdContrasenia.setEnabled(false);
        pwdContrasenia.setPreferredSize(new java.awt.Dimension(300, 40));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 25, 25, 0);
        add(pwdContrasenia, gridBagConstraints);

        btnGuardar1.setBackground(new java.awt.Color(0, 102, 102));
        btnGuardar1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        btnGuardar1.setForeground(new java.awt.Color(255, 255, 255));
        btnGuardar1.setText("Modificar");
        btnGuardar1.setPreferredSize(new java.awt.Dimension(400, 39));
        btnGuardar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardar1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.ipadx = 40;
        gridBagConstraints.ipady = 10;
        gridBagConstraints.insets = new java.awt.Insets(25, 0, 25, 0);
        add(btnGuardar1, gridBagConstraints);

        jPanel1.setBackground(new java.awt.Color(0, 102, 102));
        jPanel1.setPreferredSize(new java.awt.Dimension(940, 1));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 25, 0);
        add(jPanel1, gridBagConstraints);

        chkCambiarContrasenia.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        chkCambiarContrasenia.setText("Cambiar Contraseña:");
        chkCambiarContrasenia.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        chkCambiarContrasenia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkCambiarContraseniaActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 25, 25, 0);
        add(chkCambiarContrasenia, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void chkCambiarContraseniaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkCambiarContraseniaActionPerformed
        if (chkCambiarContrasenia.isSelected()) {
            pwdContrasenia.setEnabled(true);
        } else {
            pwdContrasenia.setEnabled(false);
            pwdContrasenia.setText("");
        }
    }//GEN-LAST:event_chkCambiarContraseniaActionPerformed

    private void txtDniSearchFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDniSearchFocusLost
        if (txtDniSearch.getText().isEmpty()) {
            txtDniSearch.setForeground(Color.GRAY);
            txtDniSearch.setText("Inserte DNI a buscar");
        }
    }//GEN-LAST:event_txtDniSearchFocusLost

    private void txtDniSearchFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDniSearchFocusGained
        if (txtDniSearch.getText().equals("Inserte DNI a buscar")) {
            txtDniSearch.setText("");
            txtDniSearch.setForeground(Color.BLACK);
        }
    }//GEN-LAST:event_txtDniSearchFocusGained

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        Pattern dniPattern = Pattern.compile("[0-9]{7,8}[A-Z a-z]"); //PATRON PARA EL DNI

        if (txtDniSearch.getText().equals("Inserte DNI a buscar")) {
            JOptionPane.showMessageDialog(this, "Porfavor introduzca algo para realizar su busqueda", "Falta dni", JOptionPane.INFORMATION_MESSAGE);
        } else if (!dniPattern.matcher(txtDniSearch.getText()).matches()) {
            JOptionPane.showMessageDialog(this, "Dni mal introducido, intentelo denuevo", "Dni missmatch", JOptionPane.ERROR_MESSAGE);
        } else {
            buscarEmpleado();
        }
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void txtNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreActionPerformed

    private void btnGuardar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardar1ActionPerformed
        modificarEmpleado();
    }//GEN-LAST:event_btnGuardar1ActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        darDeBajaEmpleado();
    }//GEN-LAST:event_btnGuardarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnGuardar1;
    private javax.swing.JCheckBox chkCambiarContrasenia;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblApellidos;
    private javax.swing.JLabel lblContrasenia;
    private javax.swing.JLabel lblCorreo;
    private javax.swing.JLabel lblDni;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblTelefono;
    private javax.swing.JPasswordField pwdContrasenia;
    private javax.swing.JTextField txtApellidos;
    private javax.swing.JTextField txtCorreo;
    private javax.swing.JTextField txtDni;
    private javax.swing.JTextField txtDniSearch;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtTelefono;
    // End of variables declaration//GEN-END:variables
}
