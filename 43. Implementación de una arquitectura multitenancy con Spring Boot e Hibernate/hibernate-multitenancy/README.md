
# Hibernate multitenant

Una arquitectura multitenant permite que varios clientes o "tenants" usen un solo recurso o, una sola instancia de base de datos. El objetivo es aislar la información que necesita cada tenant de la base de datos compartida.

* Base de datos separada: una instancia de base de datos física separada por tenant

* Esquema separado: un esquema por tenant en la misma instancia de base de datos física

* Datos particionados (discriminador): los datos de cada tenant se dividen por un valor discriminador en una nueva columna