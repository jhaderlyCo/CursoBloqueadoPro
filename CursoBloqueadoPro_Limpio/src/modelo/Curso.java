package modelo;
public final class Curso {
    public final int id, ciclo, creditos;
    public final String nombre;
    public Curso(int id, String nombre, int ciclo, int creditos) {
        this.id = id; this.nombre = nombre; this.ciclo = ciclo; this.creditos = creditos;
    }
    @Override public String toString() { return nombre + " (" + creditos + " cr.)"; }
}
