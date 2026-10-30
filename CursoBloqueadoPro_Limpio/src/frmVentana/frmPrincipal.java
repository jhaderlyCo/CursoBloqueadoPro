package frmVentana;

import servicio.Orientador;
import modelo.Estudiante;

import javax.swing.*;

public class frmPrincipal extends JFrame {
    private JPanel panelPrincipal;
    private JTextField txtCodigo;
    private JButton btnConsultar;
    private JTextArea txtCursos;
    private JTextArea txtComentarios;
    private JLabel lblEstado;
    private JLabel lblNombre;
    private JScrollPane scrollCursos;
    private JScrollPane scrollComentarios;
    private final Orientador orientador = new Orientador();

    public frmPrincipal() {
        setContentPane(panelPrincipal);
        setTitle("CursoBloqueado Pro | Consulta académica");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(980, 680);
        setLocationRelativeTo(null);

        txtCursos.setEditable(false);
        txtComentarios.setEditable(false);
        txtCursos.setLineWrap(true);
        txtCursos.setWrapStyleWord(true);
        txtComentarios.setLineWrap(true);
        txtComentarios.setWrapStyleWord(true);

        // Fuente de ancho fijo para que los cursos, créditos y estados queden alineados.
        java.awt.Font fuenteResultados = new java.awt.Font("Monospaced", java.awt.Font.PLAIN, 12);
        txtCursos.setFont(fuenteResultados);
        txtComentarios.setFont(fuenteResultados);
        txtCursos.setLineWrap(false);
        txtComentarios.setLineWrap(false);
        txtCursos.setBackground(java.awt.Color.WHITE);
        txtComentarios.setBackground(java.awt.Color.WHITE);
        txtCodigo.setText("2025107002");
        btnConsultar.addActionListener(e -> consultar());
        txtCodigo.addActionListener(e -> consultar());

        consultar();
    }

    private void consultar() {
        String codigo = txtCodigo.getText().trim();
        if (codigo.isEmpty()) {
            lblEstado.setText("Ingresa un código de estudiante.");
            lblNombre.setText("Estudiante no encontrado");
            txtCursos.setText("");
            txtComentarios.setText("");
            return;
        }
        Estudiante e = orientador.buscarEstudiante(codigo);
        if (e == null) {
            lblEstado.setText("No existe el código ingresado en esta demo.");
            lblNombre.setText("Estudiante no encontrado");
            txtCursos.setText("Códigos de prueba: 2025107001, 2025107002, 2025107003, 2025107004, 2025107005");
            txtComentarios.setText("Usa uno de los códigos de prueba para ver la simulación.");
            return;
        }
        lblNombre.setText(e.nombre + "  |  Código: " + e.codigo);
        txtCursos.setText(orientador.cursosHabilitados(e));
        txtCursos.setCaretPosition(0);
        txtComentarios.setText(orientador.recomendacion(e));
        txtComentarios.setCaretPosition(0);
        lblEstado.setText("Consulta realizada correctamente.");
    }
}
