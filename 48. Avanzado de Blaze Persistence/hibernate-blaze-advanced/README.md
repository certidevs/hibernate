
# Common Table Expressions (CTE)

1. Es un resultado temporal tomado de una sentencia SQL
2. Es otra manera de crear tablas temporales para consultar datos en lugar de utilizar subqueries en una cláusula FROM. Las CTE son una alternativa a las subqueries
3. A diferencia de las subqueries, las CTE pueden ser referenciadas múltiples veces desde múltiples partes de una misma sentencia SQL.
4. Mejoran la legibilidad del código
5. El ciclo de vida de una CTE es el mismo que el de una sentencia SQL,

7. Se utiliza para simplificar el uso de JOIN
8. Se utilizan en conjunción con funciones ventana
   PostgreSQL las introduce desde 2018 con la versión 8
6. Desde Postgres 12 hay NOT MATERIALIZED (por defecto) y MATERIALIZED

Gracias a las CTE se puede realizar agregación multinivel

Hay 2 tipos:
* no recursiva
* recursiva

## Sintaxis CTE
Sintaxis:

```SQL
WITH cte_name (column_list) AS (
    CTE_query_definition
)
statement;
```

CTE puede:

* Contener SELECT, INSERT, UPDATE, DELETE
* Se pueden utilizar más de una CTE en una misma sentencia, separando las CTE por comas.


## Recursivas

Poner un LIMIT cuando se está testeando y no se está seguro de si la condición de parada está bien:

```SQL
WITH RECURSIVE t(n) AS (
    SELECT 1
  UNION ALL
    SELECT n+1 FROM t
)
SELECT n FROM t LIMIT 100;
```