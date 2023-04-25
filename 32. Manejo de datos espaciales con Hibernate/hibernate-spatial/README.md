
# Hibernate Spatial

Hibernate Spatial se desarrolló originalmente como una extensión genérica de Hibernate para manejar datos geográficos

Desde 5.0, Hibernate Spatial ahora es parte del proyecto ORM de Hibernate y permite manejar datos geográficos de manera estandarizada.

Hibernate Spatial proporciona:

* Interfaz para almacenamiento de datos geográficos
* Funciones de consulta.


Es compatible con la mayoría de las funciones descritas por la especificación de funciones
simples de OGC.

Las bases de datos admitidas son Oracle 10g/11g, PostgreSQL/PostGIS, MySQL, Microsoft SQL Server, DB2, CockroachDB y H2/GeoDB.

Los tipos de datos espaciales no forman parte de la biblioteca estándar de Java y están ausentes
de la especificación JDBC.

Hibernate Spatial admite dos modelos de geometría diferentes: JTS y geolatte-geom. JTS es el estándar de facto. Geolatte-geom (también escrito por el desarrollador principal de Hibernate Spatial) es una biblioteca más reciente que admite muchas características especificadas en SQL/MM pero no disponibles en JTS (como compatibilidad con geometrías 4D y compatibilidad con formatos WKT/WKB extendidos) .

Geolatte-geom también implementa codificadores/decodificadores para los tipos nativos de la base de datos. Geolatte-geom tiene una buena interoperabilidad con JTS. Convertir una geometría de Geolatte en una geometría JTS, por ejemplo, no requiere copiar las coordenadas. También delega el procesamiento espacial a JTS.

Tanto con JTS o con Geolatte-geom, Hibernate asigna los tipos espaciales de la
base de datos al modelo de geometría de elección. Sin embargo, siempre utilizará Geolatte-geom
para decodificar los tipos nativos de la base de datos.

## Requisitos

Agregar dialecto Spatial a la configuración de Hibernate que añada soporte para tipos de datos espaciales.


## Tipos de datos

Algunos tipos de datos en Hibernate Spatial son:

* Point
* Polygon
* Geometry

## WKT

Well Known Text (WKT) es un formato de texto para representar geometrías.

https://www.wikiwand.com/en/Well-known_text

Visualizador WKT online: https://bogdartysh.github.io/energia/wkt/