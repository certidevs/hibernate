# Hibernate Caching

Hibernate Cache puede ser muy útil para obtener un rendimiento rápido de la aplicación si se usa correctamente. La idea detrás del caché es reducir la cantidad de consultas a la base de datos, lo que reduce el tiempo de rendimiento de la aplicación. Hibernate viene con diferentes tipos de caché:

## Caché 1L

Caché de primer nivel: la caché de primer nivel de Hibernate está asociada con el objeto Sesión.

La caché de primer nivel de Hibernate está habilitado de forma predeterminada y no hay forma de
deshabilitarlo.

Hibernate proporciona métodos (detach, evict, load, refresh, clear) a través de los cuales podemos
eliminar objetos seleccionados del caché o borrar el caché por completo.

Cualquier objeto almacenado en caché en una sesión no será visible para otras sesiones y cuando
se cierre la sesión, todos los objetos almacenados en caché también se perderán.


## Caché 2L

Caché de segundo nivel: la caché de segundo nivel de Hibernate está deshabilitada de forma predeterminada, pero podemos habilitarla a través de la configuración.

EHCache e Infinispan brindan implementación para el caché de segundo nivel de Hibernate.


* READ_ONLY: esta estrategia de almacenamiento en caché debe usarse para objetos persistentes
  que siempre leerán pero nunca se actualizarán. Es bueno para leer y almacenar en caché la configuración de la aplicación y otros datos estáticos que nunca se actualizan. Esta es la estrategia más simple con el mejor rendimiento porque no hay sobrecarga para verificar si el objeto está actualizado en la base de datos o no.

* READ_WRITE: es bueno para objetos persistentes que la aplicación puede actualizar. Sin embargo,
  si los datos se actualizan a través de otros backend u otras aplicaciones, entonces no hay forma
  de que Hibernate lo sepa y los datos pueden estar obsoletos. Entonces, mientras usa esta
  estrategia, es necesario usar la API de Hibernate para actualizar los datos.

* NONSTRICT_READ_WRITE: si la aplicación solo necesita actualizar datos ocasionalmente y no se
  requiere un aislamiento estricto de transacciones, una memoria caché de lectura y escritura no restringida podría ser apropiada.

* TRANSACTIONAL: la estrategia de caché transaccional brinda soporte para proveedores de caché
  totalmente transaccional como JBoss TreeCache. Tal caché solo se puede usar en un entorno JTA y debe especificar hibernate.transaction.manager_lookup_class.

Hibernate integra el módulo JCache para proporcionar una API de caché estándar. JCache es una API de caché estándar que se puede usar para implementar diferentes proveedores de caché como EhCache, Infinispan, Hazelcast, etc.

https://jcp.org/aboutJava/communityprocess/implementations/jsr107/index.html

## Query Cache

Caché de consultas: Hibernate también puede almacenar en caché el conjunto de resultados de una
consulta.

Hibernate Query Cache no almacena en caché el estado de las entidades reales en el caché;
almacena en caché solo valores de identificador y resultados de tipo de valor.

Por lo tanto, siempre debe usarse junto con el caché de segundo nivel.

