
# Jakarta Persistence API 3.1

Jakarta Persistence API

https://projects.eclipse.org/projects/ee4j.jpa/governance

Jakarta 3.0 no añade funcionalidades nuevas con respecto a JPA 2.2.

Jakarta 3.1 incorpora nuevas mejoras.

Cambios en las nomenclaturas:

* javax.persistence -> jakarta.persistence
* http://xmlns.jcp.org/xml/ns/persistence -> https://jakarta.ee/xml/ns/persistence
* http://xmlns.jcp.org/xml/ns/persistence/orm -> https://jakarta.ee/xml/ns/persistence/orm

## Versiones java compatibles

* Jakarta 3.0 : Java 8 o superior
* Jakarta 3.0 : Java 11 o superior

## Implementaciones de Jakarta Persistence API

Jakarta Persistence API 3.0:

* EclipseLink: 3.0.0
* Hibernate: 5.5, 6.x

Jakarta Persistence API 3.1: 

* EclipseLink: 4.0.0
* Hibernate: 6.x

## Unidad de persistencia

La unidad de persistencia es un archivo XML que contiene la configuración de la conexión a la base de datos y las entidades que se van a persistir.

Se llama persistence.xml y se coloca en la carpeta META-INF.

Ejemplo versión 3.0:

```xml
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<persistence xmlns="https://jakarta.ee/xml/ns/persistence"
             xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
             xsi:schemaLocation="https://jakarta.ee/xml/ns/persistence https://jakarta.ee/xml/ns/persistence/persistence_3_0.xsd"
             version="3.0">
    <persistence-unit name="default">
        <exclude-unlisted-classes>false</exclude-unlisted-classes>
        <properties>
            <property name="jakarta.persistence.jdbc.driver" value="com.mysql.cj.jdbc.Driver" />
            <property name="jakarta.persistence.jdbc.url" value="jdbc:mysql://localhost:3306/hibernate1" />
            <property name="jakarta.persistence.jdbc.user" value="root" />
            <property name="jakarta.persistence.jdbc.password" value="admin" />
            <property name="jakarta.persistence.schema-generation.database.action" value="drop-and-create" />
            <property name="hibernate.show_sql" value="true" />
            <property name="hibernate.format_sql" value="true" />
        </properties>
    </persistence-unit>
</persistence>

```

Ejemplo unidad de persistencia JPA 2.2:

```xml
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<persistence xmlns="http://xmlns.jcp.org/xml/ns/persistence"
             xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
             xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/persistence http://xmlns.jcp.org/xml/ns/persistence/persistence_2_2.xsd"
             version="2.2">
    <persistence-unit name="default">
        <exclude-unlisted-classes>false</exclude-unlisted-classes>
        <properties>
            <property name="javax.persistence.jdbc.driver" value="com.mysql.cj.jdbc.Driver" />
            <property name="javax.persistence.jdbc.url" value="jdbc:mysql://localhost:3306/hibernate1" />
            <property name="javax.persistence.jdbc.user" value="root" />
            <property name="javax.persistence.jdbc.password" value="admin" />
            <property name="javax.persistence.schema-generation.database.action" value="drop-and-create" />
            <property name="hibernate.show_sql" value="true" />
            <property name="hibernate.format_sql" value="true" />
        </properties>
    </persistence-unit>
</persistence>
```

## UUID

* UUID ahora se considera un tipo básico para clave primaria

```java
@Id
@Column(name = "id", nullable = false)
@GeneratedValue(strategy = GenerationType.UUID)
private UUID id;
```

## Mejoras JPQL

Jakarta Persistence Query Language (JPQL) incorpora nuevas funciones.

### Funciones numéricas

* Nuevas funciones numéricas:
    * JPQL: CEILING, EXP, FLOOR, LN, POWER, ROUND, SIGN
    * Criteria API: ceiling(), exp(), floor(), ln(), power(), round(), sign()

### Funciones sobre fechas

* Nuevas funciones para fechas:
    * JPQL: LOCAL DATE, LOCAL DATETIME, LOCAL TIME
    * Criteria API: localDate(), localDateTime(), localTime()

Relativas a la API java.time

### Funciones de cadena

* EXTRACT (JPQL) y extract() (Criteria API) para extraer partes de una fecha