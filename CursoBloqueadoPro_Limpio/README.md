# CursoBloqueado Pro — siete cursos = 22 créditos

Se conserva el archivo `src/frmVentana/frmPrincipal.form` sin cambios.

## Créditos

- Siete cursos de ejemplo: **3 + 4 + 3 + 3 + 3 + 3 + 3 = 22 créditos**.
- Límite de matrícula de ejemplo: **24 créditos** (no se confunde con la suma de los cursos).
- Caso `2025107002`: Estadística desaprobada, **3 créditos** para repetir.
- Si intenta matricular siete cursos (22) y repetir Estadística (3): **25 créditos**, excede en **1 crédito** el límite de 24. Además, **Análisis de Datos** está bloqueado por su prerrequisito Estadística.

Los datos son ficticios y el sistema distingue la falta de créditos del bloqueo por prerrequisitos.

## Uso en IntelliJ IDEA
1. Abre la carpeta del proyecto.
2. Configura JDK 17+ y marca `src` como Sources Root.
3. En Settings > Editor > GUI Designer: Generate GUI into Binary class files.
4. Build > Rebuild Project y ejecuta `Main.java`.

## Estructuras implementadas
- Grafo dirigido y DFS: `GrafoCursos.java`.
- Árbol binario de búsqueda: `ArbolEstudiantes.java`.
- Lista enlazada: `ListaOpiniones.java`.

## Códigos de demostración
2025107001, 2025107002, 2025107003, 2025107004, 2025107005.

Los paneles muestran una fila por curso y secciones separadas para créditos, bloqueos y opiniones.
