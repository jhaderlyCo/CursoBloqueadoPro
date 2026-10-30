package modelo;
public final class Estudiante {
    public final String codigo, nombre;
    public final boolean[] aprobados, desaprobados;
    public final int[] cursosPlaneados;
    public Estudiante(String codigo, String nombre, boolean[] aprobados, boolean[] desaprobados, int[] cursosPlaneados) {
        this.codigo=codigo; this.nombre=nombre; this.aprobados=aprobados;
        this.desaprobados=desaprobados; this.cursosPlaneados=cursosPlaneados;
    }
}
