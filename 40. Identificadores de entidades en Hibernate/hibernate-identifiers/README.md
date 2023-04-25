
# Identificadores de entidades en Hibernate

* ``@Id``: Identificador de la entidad.
* ``@GeneratedValue``: Genera el valor del identificador.

## Tipos de generación de identificadores

* ``GenerationType.AUTO``: Hibernate elige el tipo de generación de identificadores.

* ``GenerationType.IDENTITY``: El identificador se genera en la base de datos usando métodos
  auto-incrementales.

* ``GenerationType.SEQUENCE``: El identificador se genera en la base de datos a partir de una
  secuencia. Permite a Hibernate utilizar JDBC batching y optimizar el rendimiento, diferir la
  inserción de entidades, etc. No están soportados por todas las bases de datos, por ejemplo,
  MySQL no tiene Database Sequences, PostgreSQL sí.

* ``GenerationType.TABLE``: El identificador se genera en la base de datos a partir de una tabla.
  Genera un lock en la tabla de hibernate_sequences, por lo que tiene mucho overhead.

* ``GenerationType.UUID``: El identificador se genera en la base de datos a partir de un UUID.

## Ejemplo

```java
@Entity
public class Persona {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
}
```

