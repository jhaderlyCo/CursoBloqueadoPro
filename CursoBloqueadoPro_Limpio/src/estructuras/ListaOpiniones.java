package estructuras;
public final class ListaOpiniones {
    public static final class Opinion {
        public final String curso, docente, autor, comentario;
        public final int claridad, exigencia;
        Opinion(String curso, String docente, String autor, String comentario, int claridad, int exigencia) {
            this.curso = curso; this.docente = docente; this.autor = autor; this.comentario = comentario;
            this.claridad = claridad; this.exigencia = exigencia;
        }
    }
    private static final class Nodo {
        Opinion dato; Nodo siguiente;
        Nodo(Opinion dato) { this.dato = dato; }
    }
    private Nodo cabeza, cola;
    public void agregar(String curso, String docente, String autor, String comentario, int claridad, int exigencia) {
        Nodo n = new Nodo(new Opinion(curso, docente, autor, comentario, claridad, exigencia));
        if (cabeza == null) cabeza = n; else cola.siguiente = n;
        cola = n;
    }
    public String resumenParaCurso(String curso) {
        int c = 0, sumaClaridad = 0, sumaExigencia = 0;
        StringBuilder detalles = new StringBuilder();
        for (Nodo n = cabeza; n != null; n = n.siguiente) {
            if (n.dato.curso.equals(curso)) {
                c++;
                sumaClaridad += n.dato.claridad;
                sumaExigencia += n.dato.exigencia;
                detalles.append("• ").append(n.dato.docente).append(" — ")
                        .append(n.dato.comentario).append("\n");
            }
        }
        if (c == 0) return "Sin comentarios registrados todavía.";
        return "Opiniones de estudiantes: " + c + "\n" +
                "Claridad promedio: " + String.format("%.1f", (double) sumaClaridad / c) + "/5\n" +
                "Exigencia promedio: " + String.format("%.1f", (double) sumaExigencia / c) + "/5\n" +
                detalles;
    }
}
