package com.example.tenant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Component
public class TenantRoutingDatasource extends AbstractRoutingDataSource {

    @Autowired
    private TenantIdentifierResolver tenantResolver;

    @Override
    protected Object determineCurrentLookupKey() {
        return tenantResolver.resolveCurrentTenantIdentifier();
    }

    public TenantRoutingDatasource() {
        DataSource tenant1 = DataSourceBuilder.create()
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .url("jdbc:mysql://localhost:3306/tenant1")
                .username("root")
                .password("admin")
                .build();
        DataSource tenant2 = DataSourceBuilder.create()
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .url("jdbc:mysql://localhost:3306/tenant2")
                .username("root")
                .password("admin")
                .build();
        DataSource tenant3 = DataSourceBuilder.create()
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .url("jdbc:mysql://localhost:3306/tenant3")
                .username("root")
                .password("admin")
                .build();
        setDefaultTargetDataSource(tenant1);
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put("tenant1", tenant1);
        targetDataSources.put("tenant2", tenant2);
        targetDataSources.put("tenant3", tenant3);
        setTargetDataSources(targetDataSources);
    }
}
