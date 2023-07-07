# Trabajo obligatorio BD1 2023
Leandro Alfonso - Joel Alayón

## Sobre el trabajo

### Informe
El informe se puede encontrar en la ruta <b>src/main/resources/Informe.docx</b>

### Planficacion
Para ayudar a orientarnos, y asistir a la hora de ver relaciones entre datos y qué tablas debíamos crear,
creamos un diagrama en draw.io, similar a un MER, pero simplificado y sin seguir reglas
estrictas. El mismo se encuentra en <b>src/main/resources/MER DB.drawio</b>

### Ejecutar el programa
 - Se debe tener levantado un contenedor con una base de datos MySQL (imagen disponible en <b>src/main/resources/docker-compose.yml</b>)
 - Corroborar credenciales, para que conincidan con las utilizadas en Main
 - Correr el programa, ejecutando el archivo Main (<b>src/main/java/com/github/alfonsoleandro/Main.java</b>).

### Verificar resultados
Abrir archivo de resultados (<b>src/main/resources/procesado/resultados_queries.txt</b>)

### Estructura
 - Los archivos .csv se encuentran en <b>src/main/resources/archivos</b>
 - Las queries de creación de tablas se encuentran en <b>src/main/resources/sql/create_tables.sql</b>
 - Las queries de alteración de tablas (donde agregamos las foreing keys, lo cual decidimos hace posterior a la
creación de las tablas, para evitar crear las tablas en un orden dado, con motivo de que no se rompan las relaciones,
o se creen relaciones con tablas que no existen) se encuentran en
   <b>src/main/resources/sql/alter_tables.sql</b>
 - Las queries de inserción de datos se encuentran en <b>src/main/java/com/github/alfonsoleandro/DataInsertionHelper.java</b>
 - Las queries de consultas se encuentran en <b>src/main/java/com/github/alfonsoleandro/QueryHelper.java</b>

