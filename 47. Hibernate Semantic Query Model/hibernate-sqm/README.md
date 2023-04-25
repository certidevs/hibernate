
# Hibernate Semantic Query Model

Modelo de consulta semántica o Semantic Query Model (SQM).

Hibernate 6 proporciona un analizador de consultas de entidad completamente nuevo que puede
producir un modelo canónico, el modelo de consulta semántica, tanto desde JPQL como desde la API
de Criteria.

Al unificar el modelo de consulta de entidades, las consultas de Criteria ahora se pueden mejorar
con características que no son compatibles con la especificación Jakarta, como tablas
derivadas, Common Table Expression (CTE) y funciones ventana.

Traducción de consultas en Hibernate 3, 4 y 5:

* JPQL/HQL -> SQL
* Criteria -> JPQL/HQL -> SQL

Traducción de consultas en Hibernate 6:

* JPQL/HQL -> SQM -> SQL
* Criteria -> SQM -> SQL

## Habilitar logs

En logback.xml:

```xml
<logger name="org.hibernate.orm.query.sqm.ast" level="debug"/>
```

## Clases

Paquete: org.hibernate.query.sqm

* SqmSelectStatement
* SqmInsertSelectStatement

## SQM

SQM API permite el uso de características avanzadas como:

* LATERAL JOIN
* CTE
* Window functions

Algunas de estas funcionalidades solo se podían lograr con frameworks como Blaze Persistence.

## Funciones ventana

* Las funciones agregadas agregan datos de un conjunto de filas en una sola fila.

* Las funciones ventana permiten realizar cálculos sobre un conjunto de filas relacionadas con la fila actual.

* no agrupan los datos en un único resultado.

* Permiten realizar cálculos sin perder detalle.

* Los datos permanecen separados en filas

* Crean agregaciones en las propias filas sin reducir a un único resultado

Funciones de agregación: toman múltiples filas y las convierten en menos filas pero con los datos agregados

Funciones ventana: compara la fila actual con todas las demás filas de una misma agrupación.

Sintaxis:

* OVER ()
* PARTITION BY()
* ORDER BY()

Funciones especiales:

* ROW_NUMBER()
* RANK()
* DENSE_RANK()
* FIRST_VALUE()
* LAST_VALUE(): ROWS BETWEEN UNBOUNDED PRECEDING AND UNBOUNDED FOLLOWING
* LAG()
* LEAD()