
# Hibernate transactions

Una transacción es una unidad de trabajo que se realiza sobre una base de datos. Se trata de una
o más sentencias SQL que se ejecutan de forma conjunta. Si alguna de las sentencias falla, todas
las sentencias se deshacen y se vuelve al estado anterior a la transacción.

Las transacciones ayudan a las bases de datos a satisfacer las propiedades ACID:

* Atomicidad (Atomicity): todas las sentencias se ejecutan o ninguna.
* Consistencia (Consistency): la base de datos siempre se encuentra en un estado consistente.
* Aislamiento (Isolation): las transacciones se ejecutan de forma aislada, una tras otra.
* Durabilidad (Durability): los cambios realizados por una transacción se mantienen en la base de
  datos.

Hibernate proporciona la interfaz ``org.hibernate.Transaction`` para manejar las transacciones. La
interfaz Transaction proporciona los siguientes métodos:

* begin(): inicia una transacción.
* commit(): confirma los cambios realizados en la base de datos.
* rollback(): deshace los cambios realizados en la base de datos.
* isActive(): comprueba si la transacción está activa.
* getLocalStatus(): devuelve el estado de la transacción.
* setTimeout(): establece el tiempo de espera máximo para la transacción.
* getTimeout(): devuelve el tiempo de espera máximo para la transacción.

## Tipos de transacciones

### JDBC

* Java Database Connectivity. Es el modo por defecto, para aplicaciones que no están
  basadas en Jakarta Persistence.
* Maneja las transacciones utilizando ``java.sql.Connection``.
* La aplicación es responsable de gestionar las transacciones.
* Las transacciones son locales, afectan a un único contexto de persistencia, y no se pueden propagar a otros contextos de persistencia.

En Hibernate:

```java
StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
		.applySetting(AvailableSettings.TRANSACTION_COORDINATOR_STRATEGY, "jdbc")
		.build();
```

En JPA usando persistence.xml:

```xml
<persistence-unit transaction-type="RESOURCE_LOCAL">
    ...
</persistence-unit>
```

### JTA

Java Transaction API.

Permite a las aplicaciones realizar transacciones distribuidas.

* JTA especifica las interfaces estándar de Java entre un administrador de transacciones y las
  partes involucradas en un sistema de transacciones distribuidas.

* El contenedor/servidor de aplicaciones es responsable de gestionar las transacciones, por
  ejemplo Payara Server, WildFly, GlassFish, etc.

* Las transacciones son globales, afectan a varios contextos de persistencia, y se pueden propagar
  a otros contextos de persistencia.


```java
StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
		.applySetting(AvailableSettings.TRANSACTION_COORDINATOR_STRATEGY, "jta")
		.build();
```

En JPA usando persistence.xml:

```xml
<persistence-unit transaction-type="JTA">
    ...
</persistence-unit>
```

Hay dos modos:

* CMT (Container Managed Transaction): el contenedor/servidor de aplicaciones es responsable de
  gestionar las transacciones.
* BMT (Bean Managed Transaction): la aplicación es responsable de gestionar las transacciones.
  El desarrollador es responsable de iniciar y terminar las transacciones. UserTransaction es quien
  interactúa con JTA TransactionManager.

## Hibernate Transaction API

Hibernate proporciona una API para gestionar transacciones. Esta API es independiente del
motor de base de datos.

La API Transaction de Hibernate proporciona una manera uniforme de gestionar transacciones en
Hibernate independientemente del entorno que se use JDBC o JTA CMT o BMT.

