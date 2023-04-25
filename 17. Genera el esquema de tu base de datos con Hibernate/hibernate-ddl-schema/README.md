

Importar archivos SQL en el caso de JPA: 

```xml

  <persistence-unit name="my-persistence-unit" transaction-type="JTA">
    …
     
    <properties>
      <property name="javax.persistence.schema-generation.database.action" value="drop-and-create"/>
       
      <property name="javax.persistence.schema-generation.create-source" value="script"/>
      <property name="javax.persistence.schema-generation.create-script-source" value="META-INF/create.sql"/>
       
      <property name="javax.persistence.schema-generation.drop-source" value="script"/>
      <property name="javax.persistence.schema-generation.drop-script-source" value="META-INF/drop.sql"/>
       
      <property name="javax.persistence.sql-load-script-source" value="META-INF/data.sql"/>
    </properties>
```