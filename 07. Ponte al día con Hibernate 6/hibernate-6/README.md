
# Hibernate 6.1


## Versión java

* Hibernate 5: 8, 11, 17, 18
* Hibernate 6: 11, 17, 18

## Uso Jakarta Persistence

* Hibernate 5: 2.2, 3.0
* Hibernate 6: 3.0, 3.1

javax.persistence -> jakarta.persistence


## Mejoras de rendimiento JDBC


* ResultSet de JDBC es una interfaz para ejecutar sentencias contra la base de datos
* Utiliza dos formas para extraer los datos
    * read by name
    * read by position
* read by name es más lento que read by position
* En Hibernate 6 se utiliza read by position con el fin de mejorar el rendimiento

## Contexto de persistencia (Session)

Se deprecan métodos en Session 	
* save()
* saveOrUpdate()
* update()
* load()
* replicate()
* createQuery(String)
* delete()
* ...
* Se recomienda usar los métodos de EntityManager

Se pueden identificar porque llevan la anotación `@Deprecated(since = "6.0")`

## ANTLR

Hibernate Query Language (HQL) se traduce usando ANTLR. 

ANTLR 2 a ANTLR 4.

## SQM

* Integración con SQM:

* SQM significa Semantic Query Model, y es el nuevo analizador de consultas de entidades que aborda tanto JPQL como Criteria API.
* El nuevo analizador es mucho más flexible y proporciona una mejor traducción SQL de consultas de entidad.
* Desde la perspectiva del usuario, SQM proporciona más funciones como funciones de ventana, CTE (expresiones de tabla comunes), uniones laterales, etc.
* SQM también proporciona un mejor rendimiento para Criteria API debido a que se traduce directamente a SQL en lugar de usar HQL como intermediario.


## Se elimina la API Criteria de Hibernate


* Se elimina Criteria API legacy de Hibernate (org.hibernate.Criteria)
* En su lugar se utiliza Criteria API de Jakarta (jakarta.persistence.criteria.CriteriaQuery)


## Separación de Queries para lectura / escritura

Hibernate separa las queries de lectura de las queries para insert, update, delete:

* SelectionQuery
* MutationQuery

## Mejoras en el uso de la API java.time

java.time aparece en java 8

Hibernate 6 mejora el soporte de ZonedDateTime y OffsetDateTime introduciendo nuevas opciones de mapeo:

* @TimeZoneStorage(TimeZoneStorageType.NATIVE)
* @TimeZoneStorage(TimeZoneStorageType.NORMALIZE)
* @TimeZoneStorage(TimeZoneStorageType.NORMALIZE_UTC)
* @TimeZoneStorage(TimeZoneStorageType.COLUMN)
* @TimeZoneStorage(TimeZoneStorageType.AUTO)

## Generación de identificadores

Hibernate 6.0 agrega una nueva anotación @IdGeneratorType que permite una forma mejor
y segura de definir generadores personalizados para usar en la generación de identificadores.

* @IdGeneratorType

A mayores, Jakarta Persistence 3.1 incorpora una nueva estrategia de generación de ids para UUID.


## Tipos SQL

* Parámetros de configuración para los tipos de SQL preferidos:

Hibernate 6 presenta 4 parámetros de configuración que puede usar para configurar el tipo de JDBC que Hibernate usará para asignar atributos de tipo boolean, UUID, Duration e Instant:

* hibernate.type.preferred_boolean_jdbc_type
* hibernate.type.preferred_uuid_jdbc_type
* hibernate.type.preferred_duration_jdbc_type
* hibernate.type.preferred_instant_jdbc_type

Ejemplo de uso:

```xml
<property name="hibernate.type.preferred_uuid_jdbc_type" value="CHAR" />
```

## Mejoras en Result Transformers:

La interfaz ResultTransformer se depreca en Hibernate 5.

En Hibernate 6 aparecen dos nuevas interfaces:

* TupleTransformer
* ListTransformer


## Nuevas estrategias de nombrado

4 implicit naming strategies for database sequences:

```xml
<property name="hibernate.id.db_structure_naming_strategy" value="standard" />
<property name="hibernate.id.db_structure_naming_strategy" value="legacy" />
<property name="hibernate.id.db_structure_naming_strategy" value="single" />
<property name="hibernate.id.db_structure_naming_strategy" value="com.example.MyCustomStrategy" />
```

La última opción requiere crear una nueva estrategia:

```java
 MyCustomStrategy implements ImplicitDatabaseObjectNamingStrategy
```


## Orden en listas

Por defecto, Hibernate maneja java.util.List como una Bag.

Después de haberlo obtenido de la base de datos, contiene los elementos en un orden indefinido.

Este es el caso cuando se usa @ElementCollection o asociaciones Many.

Hibernate 6 proporciona 2 formas de modificar este comportamiento:

* @OrderColumn

```xml
<property name="hibernate.mapping.default_list_semantics" value="LIST" />
```


## Select en JPQL

Se recomienda usar select en todas las consultas JPQL, dado que en hibernate 6 cuando se hace join ya no trae todas las entidades como sí ocurría en Hibernate 5.


Ejemplo:

```java
List<Object[]> results = em.createQuery("FROM Company c JOIN c.employees e").getResultList();

// query que se genera en Hibernate 5: 
SELECT c, e FROM Company c JOIN c.employees e

// query que se genera en Hibernate 6:
SELECT c FROM Company c JOIN c.employees

// Por tanto es mejor especificar SELECT:

List<Object[]> results = em.createQuery("SELECT c, e FROM Company c JOIN c.employees e").getResultList();
```

## Auto-desduplicado

En Hibernate 5, cuando se hacía un join fetch de asociaciones de tipo many era necesario especificar `distinct` en el select a fin de evitar que se duplicara la entidad padre, ejemplo:


```java
      select distinct c from Company c
      join fetch c.computers
      where c.id = :id
```

Hibernate 6 ya lo hace automáticamente, por lo que no se necesita usar `distinct`:

```java
      select c from Company c
      join fetch c.computers
      where c.id = :id
```

## @Incubating

Algunas de las mejoras expuestas son @Incubating lo que quiere decir es que podrían sufrir cambios a futuro por lo que conviene revisarlas.

## Otras mejoras

* https://docs.jboss.org/hibernate/orm/6.0/migration-guide/migration-guide.html
* https://in.relation.to/2022/03/31/orm-60-final/
* https://hibernate.org/orm/releases/
* https://github.com/hibernate/hibernate-orm/wiki/Roadmap
* https://docs.jboss.org/hibernate/orm/6.1/migration-guide/migration-guide.html
* https://docs.jboss.org/hibernate/orm/6.0/migration-guide/migration-guide.html
* https://in.relation.to/2022/03/31/orm-60-final/