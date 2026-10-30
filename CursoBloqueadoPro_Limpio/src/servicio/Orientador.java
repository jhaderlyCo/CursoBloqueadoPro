package servicio;

import estructuras.ArbolEstudiantes;
import estructuras.GrafoCursos;
import estructuras.ListaOpiniones;
import modelo.Curso;
import modelo.Estudiante;

public final class Orientador {
    public static final int LIMITE_CREDITOS=24;
    public final GrafoCursos grafo;
    public final ListaOpiniones opiniones=new ListaOpiniones();
    private final ArbolEstudiantes estudiantes=new ArbolEstudiantes();

    public Orientador() {
        Curso[] cursos={
            new Curso(0,"Matemática I",1,4),
            new Curso(1,"Fundamentos de Programación",1,4),
            new Curso(2,"Comunicación",1,3),
            new Curso(3,"Matemática II",2,4),
            new Curso(4,"Programación Orientada a Objetos",2,4),
            new Curso(5,"Estadística",2,3),
            new Curso(6,"Estructuras de Datos I",3,4),
            new Curso(7,"Base de Datos I",3,4),
            new Curso(8,"Algoritmos II",4,4),
            new Curso(9,"Base de Datos II",4,4),
            new Curso(10,"Ingeniería de Software",5,4),
            new Curso(11,"Proyecto Integrador",6,4),
            // Siete cursos de un ciclo de ejemplo (3+4+3+3+3+3+3 = 22).
            new Curso(12,"Cálculo Vectorial",3,3),
            new Curso(13,"Física II",3,4),
            new Curso(14,"Álgebra Lineal",3,3),
            new Curso(15,"Estructuras Discretas I",3,3),
            new Curso(16,"Inglés Técnico",3,3),
            new Curso(17,"Análisis de Datos",3,3),
            new Curso(18,"Laboratorio de Programación",3,3)
        };
        grafo=new GrafoCursos(cursos);
        int[][] deps={{0,3},{0,5},{1,4},{4,6},{4,7},{6,8},{7,9},{6,10},{8,11},{9,11},{10,11},
            {3,12},{3,14},{0,15},{5,17},{17,11},{4,18},{1,18}};
        for(int[] d:deps)grafo.agregarDependencia(d[0],d[1]);

        int[] siete={12,13,14,15,16,17,18};
        // Código 2025107002: desaprobó Estadística (3 cr); 7 cursos previstos suman 22 cr.
        estudiantes.insertar(new Estudiante("2025107002","María Flores",flags(cursos.length,0,1,2,3,4),flags(cursos.length,5),siete));
        estudiantes.insertar(new Estudiante("2025107001","Kevin Quispe",flags(cursos.length,0,1,2,3,4,5),flags(cursos.length),siete));
        estudiantes.insertar(new Estudiante("2025107003","Luis Apaza",flags(cursos.length,0,1,2,3,4),flags(cursos.length,5),siete));
        estudiantes.insertar(new Estudiante("2025107004","Ana Choque",flags(cursos.length,0,1,2,3,4,5,6),flags(cursos.length),siete));
        estudiantes.insertar(new Estudiante("2025107005","Piero Condori",flags(cursos.length,0,1,2),flags(cursos.length,4),siete));

        opiniones.agregar("Estadística","Docente A","Estudiante 1","Es recomendable repetirlo pronto para habilitar Análisis de Datos.",4,3);
        opiniones.agregar("Cálculo Vectorial","Docente B","Estudiante 2","Conviene practicar constantemente los ejercicios del curso.",5,4);
        opiniones.agregar("Álgebra Lineal","Docente C","Estudiante 3","Llevarlo al día ayuda a entender temas posteriores.",4,4);
        opiniones.agregar("Programación Orientada a Objetos","Docente D","Estudiante 4","Repetirlo pronto evita retrasos en cursos que dependen de programación.",4,4);
    }

    private static boolean[] flags(int cantidad,int... ids){boolean[] b=new boolean[cantidad];for(int id:ids)b[id]=true;return b;}
    public Estudiante buscarEstudiante(String codigo){return estudiantes.buscar(codigo);}

    private static final String LINEA = "────────────────────────────────────────────";
    private static String fila(Curso c, String estado) {
        return String.format("%-31s %2d cr.  %s\n", c.nombre, c.creditos, estado);
    }

    public String cursosHabilitados(Estudiante e) {
        Curso[] cursos = grafo.cursos();
        StringBuilder sb = new StringBuilder();
        int planeados = 0, repeticion = 0, habilitados = 0, bloqueados = 0;

        sb.append("  CURSOS DEL SIGUIENTE CICLO\n").append(LINEA).append("\n");
        for (int id : e.cursosPlaneados) {
            Curso c = cursos[id];
            planeados += c.creditos;
            boolean puede = grafo.puedeLlevar(id, e.aprobados);
            sb.append(fila(c, puede ? "HABILITADO" : "BLOQUEADO"));
            if (puede) habilitados++;
            else {
                bloqueados++;
                sb.append("    Requisito pendiente: ")
                  .append(grafo.requisitosPendientes(id, e.aprobados)).append("\n");
            }
        }
        sb.append(LINEA).append("\n")
          .append("  Cursos: ").append(e.cursosPlaneados.length)
          .append("  |  Habilitados: ").append(habilitados)
          .append("  |  Bloqueados: ").append(bloqueados).append("\n")
          .append("  Créditos del ciclo: ").append(planeados).append("\n\n");

        sb.append("  CURSOS DESAPROBADOS PARA REPETIR\n").append(LINEA).append("\n");
        int cantidad = 0;
        for (Curso c : cursos) {
            if (e.desaprobados[c.id] && !e.aprobados[c.id]) {
                sb.append(fila(c, "REPETIR"));
                repeticion += c.creditos;
                cantidad++;
            }
        }
        if (cantidad == 0) sb.append("  Ningún curso desaprobado.\n");

        sb.append("\n  RESUMEN DE CRÉDITOS\n").append(LINEA).append("\n")
          .append("  Ciclo propuesto:        ").append(planeados).append(" cr.\n")
          .append("  Cursos para repetir:    ").append(repeticion).append(" cr.\n")
          .append("  Total solicitado:       ").append(planeados + repeticion).append(" cr.\n")
          .append("  Máximo de matrícula:    ").append(LIMITE_CREDITOS).append(" cr.\n")
          .append("  Para cursos nuevos:     ").append(Math.max(0, LIMITE_CREDITOS - repeticion)).append(" cr.\n");
        if (planeados + repeticion > LIMITE_CREDITOS)
            sb.append("\n  ATENCIÓN: Exceso de ").append(planeados + repeticion - LIMITE_CREDITOS).append(" crédito(s).\n");
        else sb.append("\n  El total cabe en el límite de créditos.\n");
        return sb.toString();
    }

    public String recomendacion(Estudiante e) {
        Curso[] cursos = grafo.cursos();
        StringBuilder sb = new StringBuilder();
        StringBuilder elegidos = new StringBuilder();
        StringBuilder postergados = new StringBuilder();
        StringBuilder bloqueados = new StringBuilder();
        int usados = 0, nRepetidos = 0, nElegidos = 0;

        sb.append("  1. CURSOS QUE DEBES REPETIR\n").append(LINEA).append("\n");
        for (Curso c : cursos) {
            if (e.desaprobados[c.id] && !e.aprobados[c.id]) {
                usados += c.creditos;
                nRepetidos++;
                sb.append(fila(c, "PRIORIDAD"));
                sb.append("    Impacto: ").append(grafo.impacto(c.id))
                  .append(" curso(s) posteriores.\n");
            }
        }
        if (nRepetidos == 0) sb.append("  Ninguno.\n");
        sb.append("\n  2. MATRÍCULA RECOMENDADA\n").append(LINEA).append("\n");
        if (usados > LIMITE_CREDITOS) {
            sb.append("  Los cursos a repetir superan el límite.\n")
              .append("  Consulta con tu universidad.\n");
            return sb.toString();
        }
        int[] orden = e.cursosPlaneados.clone();
        for (int i = 0; i < orden.length; i++)
            for (int j = i + 1; j < orden.length; j++)
                if (grafo.impacto(orden[j]) > grafo.impacto(orden[i])) {
                    int aux = orden[i]; orden[i] = orden[j]; orden[j] = aux;
                }
        for (int id : orden) {
            Curso c = cursos[id];
            if (!grafo.puedeLlevar(id, e.aprobados)) {
                bloqueados.append(fila(c, "BLOQUEADO"))
                          .append("    Falta: ").append(grafo.requisitosPendientes(id, e.aprobados)).append("\n");
            } else if (usados + c.creditos <= LIMITE_CREDITOS) {
                usados += c.creditos; nElegidos++;
                elegidos.append(fila(c, "LLEVAR"));
            } else postergados.append(fila(c, "POSTERGAR"));
        }
        sb.append(elegidos.length() == 0 ? "  No hay cursos nuevos seleccionados.\n" : elegidos)
          .append("\n  Créditos matriculados: ").append(usados).append(" / ").append(LIMITE_CREDITOS)
          .append("  (incluye repetidos)\n")
          .append("  Cursos nuevos elegidos: ").append(nElegidos).append("\n");
        sb.append("\n  3. CURSOS QUE NO PUEDES LLEVAR\n").append(LINEA).append("\n")
          .append(bloqueados.length() == 0 ? "  Ninguno bloqueado por requisitos.\n" : bloqueados);
        sb.append("\n  4. CURSOS A POSTERGAR POR CRÉDITOS\n").append(LINEA).append("\n")
          .append(postergados.length() == 0 ? "  Ninguno adicional por falta de créditos.\n" : postergados);
        sb.append("\n  5. OPINIONES DE ESTUDIANTES\n").append(LINEA).append("\n");
        int opinionesMostradas = 0;
        for (Curso c : cursos) {
            if (e.desaprobados[c.id] && !e.aprobados[c.id]) {
                sb.append("  ").append(c.nombre).append("\n")
                  .append(opiniones.resumenParaCurso(c.nombre)).append("\n\n");
                opinionesMostradas++;
            }
        }
        if (opinionesMostradas == 0) {
            for (int id : e.cursosPlaneados) {
                String opinion = opiniones.resumenParaCurso(cursos[id].nombre);
                if (!"Sin comentarios registrados todavía.".equals(opinion)) {
                    sb.append("  ").append(cursos[id].nombre).append("\n")
                      .append(opinion).append("\n\n");
                    opinionesMostradas++;
                }
            }
        }
        if (opinionesMostradas == 0) sb.append("  Aún no hay opiniones para estos cursos.\n");
        sb.append("\n").append(LINEA).append("\n")
          .append("  Criterio: repetir primero; luego priorizar\n")
          .append("  cursos habilitados con mayor impacto DFS.\n")
          .append("  Datos ficticios para fines académicos.\n");
        return sb.toString();
    }
}
