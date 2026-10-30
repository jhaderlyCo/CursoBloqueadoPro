package estructuras;
import modelo.Estudiante;
/** ABB manual para buscar por código; no usa TreeMap ni librerías de estructuras. */
public final class ArbolEstudiantes {
    private static final class Nodo {
        Estudiante dato; Nodo izquierdo, derecho;
        Nodo(Estudiante e) {dato=e;}
    }
    private Nodo raiz;
    public void insertar(Estudiante e) {raiz=insertar(raiz,e);}
    private Nodo insertar(Nodo n,Estudiante e) {
        if(n==null) return new Nodo(e);
        int cmp=e.codigo.compareTo(n.dato.codigo);
        if(cmp<0)n.izquierdo=insertar(n.izquierdo,e);
        else if(cmp>0)n.derecho=insertar(n.derecho,e);
        else n.dato=e;
        return n;
    }
    public Estudiante buscar(String codigo) {
        Nodo n=raiz;
        while(n!=null) {
            int cmp=codigo.compareTo(n.dato.codigo);
            if(cmp==0)return n.dato;
            n=cmp<0?n.izquierdo:n.derecho;
        }
        return null;
    }
}
