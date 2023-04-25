
# Hibernate naming strategies



1. Primero determina el nombre lógico de una entidad o atributo.
    * Se puede establecer explícitamente el nombre lógico utilizando las anotaciones @Table y @Column.
    * Si no se especifica, Hibernate utilizará una de sus estrategias de nomenclatura implícitas.

2. Asigna el nombre lógico a un nombre físico. Por defecto, Hibernate usa el nombre lógico
   como nombre físico.
    * Se puede implementar una PhysicalNamingStrategy que asigne el nombre lógico a uno
      físico que siga una nomenclatura personalizada.
    * Desde Hibernate 5.5.4, se puede activar CamelCaseToUnderscoresNamingStrategy.

## 1. Logical naming strategy

Hace referencia al nombre de la entidad o atributo que se utiliza en el código Java/Hibernate.

### Explícita

* @Table
* @Column


### Implícita

Por defecto Hibernate utiliza la estrategia definida por JPA.

```xml
<property name="hibernate.implicit_naming_strategy" value="jpa" />
```


## 2. Physical naming strategy

Hace referencia al nombre de la tabla o columna que se utiliza en la base de datos.

Por defecto, Hibernate utiliza el nombre lógico como nombre físico.

Hibernate permite personalizar el nombre físico cambiando la estrategia.

```xml
<property name="hibernate.physical_naming_strategy" value="org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy"/>
```

