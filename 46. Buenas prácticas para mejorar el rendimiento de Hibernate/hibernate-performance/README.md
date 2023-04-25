
# Hibernate Performance 


## Desactivar logs

Desactivar logs en producción

```xml
<property name="hibernate.show_sql">false</property>
<property name="hibernate.format_sql">false</property>
<property name="hibernate.use_sql_comments">false</property>
```

## Fetch size

```xml
<property name="hibernate.jdbc.fetch_size">100</property>
```

Valores óptimos de fetch_size:

* A nivel global: 50 a 200
* A nivel de query particular: 200 - 2000

Variable en función la base de datos.

PostgreSQL haría fetch de todo, sería lo más óptimo pero si se cargan muchos registros puede ocurrir OutOfMemoryException.


## Usar Cachés

* Caché de primer nivel (por defecto activada)
* Caché de segundo nivel (por defecto desactivada, hay que activarla)
* Query Cache (por defecto desactivada, hay que activarla)


```xml
<property name="hibernate.cache.use_query_cache">true</property>
```

Para limpiar entidades cacheadas en el contexto de persistencia

```java
sessionFactory.getCache().evict...

session.detach(object);
```


## Query plan cache

Cada consulta JPQL o consulta de criterios se analiza en un árbol de sintaxis abstracta (AST)
antes de la ejecución para que Hibernate pueda generar la declaración SQL.

Dado que la compilación de consultas lleva tiempo, Hibernate proporciona un QueryPlanCache para un mejor rendimiento.

Hibernate primero verifica el caché del plan, y solo si no hay un plan disponible, genera un nuevo plan y almacena el plan de ejecución en el caché para referencia futura.


## Generación de claves primarias

```java
@Id
@GeneratedValue(strategy = GenerationType.SEQUENCE)
```

Utiliza una "database sequence" y está optimizado por PostgreSQL.

Hibernate usa un algoritmo optimizado por defecto para evitar declaraciones SELECT innecesarias.


## Cambiar FetchType a LAZY

Las asociaciones de tipo One son Eager por defecto, cambiar a Lazy.

```java
@OneToOne(fetch = FetchType.LAZY)

@ManyToOne(fetch = FetchType.LAZY)
```

## Evitar MultipleBagException

No traer varias colecciones con join fetch en la misma consulta. Es mejor hacer consultas 
separadas así se evita el producto cartesiano de las tablas.


## Evitar n+1 queries

Evitar los select automáticas que hace Hibernate para traer las asociaciones LAZY de las entidades.

Uso de:

* join fetch en consultas JPQL/HQL
* API Entity Graph
* fetch en API Criteria de JPA

## Uso de Query Hints

Las Query Hints permiten modificar el comportamiento de las consultas, permitiendo un control
más avanzado.

Query Hint read only:

```java
session.createQuery("select e FROM Employee e", Employee.class)
.setReadOnly(true)
.list()
.forEach(System.out::println);

session.createQuery("select e FROM Employee e", Employee.class)
.setHint(HibernateHints.HINT_READ_ONLY, true)
.list()
.forEach(System.out::println);
```

Query Hint timeout para interrumpir la consulta si tarda más de X segundos:

```java

```java
session.createQuery("select e FROM Employee e", Employee.class)
.setTimeout(10)
.list()
.forEach(System.out::println);
```

## Uso de DTOs

El uso de clases o records como DTO para las consultas permite una gran optimización ya que
hibernate no tiene que cargar entidades en el contexto de persistencia y se ahorran comprobaciones.

En los DTO se traen proyecciones, solo aquellas columnas que sean realmente necesarias.

## Uso de Set en lugar de List

Previo a la versión 5.0.8 de Hibernate hay un bug:

https://hibernate.atlassian.net/browse/HHH-5855

Las listas mantienen orden y permiten duplicados, los Sets no.

Es más eficiente usar Sets en lugar de Listas.


## Paginación de resultados

Obtener cantidades limitadas de datos, en lugar de recuperar todos los registros de una tabla se
pueden recuperar registros por páginas.

```java
session.createQuery("select e FROM Employee e", Employee.class)
.setFirstResult(0)
.setMaxResults(10)
.list()
.forEach(System.out::println);
```

## Estudiar estadísticas de Hibernate

```java
Statistics statistics = sessionFactory.getStatistics();
```

```xml
<property name="hibernate.generate_statistics">true</property>
```

## Slow Queries

Para buscar queries lentas:
```xml
<property name="hibernate.session.events.log.LOG_QUERIES_SLOWER_THAN_MS" value="20" />
```

## Optimizaciones en base de datos

### Uso de índices

1. Un índice es una estructura de datos separada que permite optimizar la recuperación de datos

2. Tienen el coste adicional de tener que realizar más operaciones de escritura para mantener los índices. Por tanto, no deberían crearse índices en todas las columnas.

3. Los índices son más útiles cuando se tienen que recuperar pocas filas.

4. ¿En qué tablas deberían evitarse los índices?:

* Tablas con pocos registros
* Tablas con operaciones masivas de INSERT/UPDATE
* Columnas con un gran porcentaje de valores nulos
* Columnas que se modifican con mucha frecuencia

### Query plan

Uso de EXPLAIN ANALYZE para ver si el Query Plan es el óptimo.

```sql
EXPLAIN ANALYZE SELECT * FROM credit_cards;

explain (format json) select * from measurement;
explain (format yaml) select * from measurement;
explain (format xml) select * from measurement;
explain (format text) select * from measurement;

```

### Vacuum

1. Cuando se borran grandes cantidades de datos en tablas, Postgresql lanza AUTOVACUUM
   automáticamente (al minuto de la operación)
2. Autovacuum está habilitado por defecto en postgresql.conf, no se recomienda desactivarlo
3. Autovacuum se ejecuta en background sin GUI, con una query podemos ver su actividad
4. Por defecto, Autovacuum se ejecuta a cada minuto


### Vistas materializadas

Vistas que se almacenan físicamente y se actualizan periódicamente a nivel de base de datos.

```sql
CREATE MATERIALIZED VIEW mv_employee AS
SELECT * FROM employee
WITH [NO] DATA;

REFRESH MATERIALIZED VIEW CONCURRENTLY mv_employee;
```

### Particionamiento de tablas

**Particionamiento de tablas en PostgreSQL**

Problema original. Tablas con millones de registros tardan en leerse y se corta la conexión.

1. Particionar es dividir una tabla lógica en tablas físicas más pequeñas y manejables

2. Particionamiento permite mejorar el rendimiento
    * postgres permite excluir particiones sin que se afecte a los datos que se están 
      leyendo/escribiendo

3. Las tablas particionadas mejoran el rendimiento de las consultas permitiendo al Query Optimizer escanear únicamente los datos necesarios para satisfacer una consulta en vez de escanear todo el contenido de una tabla.

Ejemplos:
* particionar por año
* particionar por categoría

**¿Cuándo utilizar particionamiento?**

* Cuando una tabla alcanza millones de registros
* Cuando una tabla puede ser dividida en partes más pequeñas, ejemplo: logs, auditoria
    * Esto permite que a la hora de borrar se pueda borrar una partición directamente.
* Cuando tras optimizar los índices seguimos teniendo slow queries
* Revisar las cláusulas where y realizar el particionamiento por aquellas columnas por las que más se filtra en las consultas con mayor frecuencia. Ejemplos:
    * Consultas por fecha: año, mes, semana, día
    * Consultas por categoría
    * Consultas por localización
* Cuando una tabla empieza a ocupar más de 1GB
* Datawarehousing, cuando queremos tener histórico pero no es importante para las consultas más frecuentes

* Añadir solo las particiones necesarias, ya que aumenta la carga de trabajo en cuanto a la gestión de tablas
* Evitar particionar demasiado, es posible terminar con millones de particiones.

**Formas de particionar tablas en PostgreSQL:**

* Range: por rango de fechas o ids
    * incluye en el límite inferior
    * excluye el límite superior
* List:
    * especifica explícitamente los valores clave que aparecerán en cada partición
* Hash
    * Especifica un módulo y un resto para cada partición