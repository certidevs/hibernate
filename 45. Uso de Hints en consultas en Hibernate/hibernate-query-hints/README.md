
# Query Hints

## Introduction

Una Query Hint es una opción de personalización que se puede aplicar a una consulta para modificar su comportamiento. 

Las Query Hints se pueden aplicar a una consulta de varias maneras:

1. A través de la anotación `@QueryHint` dentro de una consulta nombrada (named query)
2. A través del método setHint() de la interfaz jakarta.persistence.Query
3. A través de métodos de la interfaz org.hibernate.query.Query
    * setTimeout()
    * setFetchSize()
    * setCacheable() y setCacheRegion()
    * setCacheMode()
    * setFlushMode()
    * setLockMode()
    * setReadOnly()
    * setComment()
    * addQueryHint()