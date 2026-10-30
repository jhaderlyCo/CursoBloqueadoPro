package estructuras;
import modelo.Curso;
public final class GrafoCursos {
    private static final class Arista {
        int destino; Arista siguiente;
        Arista(int destino, Arista siguiente) { this.destino = destino; this.siguiente = siguiente; }
    }
    private final Curso[] cursos;
    private final Arista[] ady;
    public GrafoCursos(Curso[] cursos) {
        this.cursos = cursos;
        this.ady = new Arista[cursos.length];
    }
    public void agregarDependencia(int prerequisito, int posterior) {
        for (Arista a = ady[prerequisito]; a != null; a = a.siguiente) if (a.destino == posterior) return;
        ady[prerequisito] = new Arista(posterior, ady[prerequisito]);
    }
    public Curso[] cursos() { return cursos.clone(); }
    public boolean puedeLlevar(int id, boolean[] aprobados) {
        if (aprobados[id]) return false;
        for (int i = 0; i < cursos.length; i++) {
            for (Arista a = ady[i]; a != null; a = a.siguiente) {
                if (a.destino == id && !aprobados[i]) return false;
            }
        }
        return true;
    }
    public String requisitosPendientes(int id, boolean[] aprobados) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < cursos.length; i++) {
            for (Arista a = ady[i]; a != null; a = a.siguiente) {
                if (a.destino == id && !aprobados[i]) {
                    if (sb.length() > 0) sb.append(", ");
                    sb.append(cursos[i].nombre);
                }
            }
        }
        return sb.toString();
    }
    public boolean[] bloqueadosSiPostergas(int id) {
        boolean[] visitado = new boolean[cursos.length];
        dfs(id, visitado);
        visitado[id] = false;
        return visitado;
    }
    private void dfs(int origen, boolean[] visitado) {
        for (Arista a = ady[origen]; a != null; a = a.siguiente) {
            if (!visitado[a.destino]) {
                visitado[a.destino] = true;
                dfs(a.destino, visitado);
            }
        }
    }
    public int impacto(int id) {
        int total = 0;
        boolean[] b = bloqueadosSiPostergas(id);
        for (boolean v : b) if (v) total++;
        return total;
    }
}
