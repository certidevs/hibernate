package com.example;

import com.example.model.Employee;
import com.example.repository.EmployeeRepository;
import com.example.tenant.TenantIdentifierResolver;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MultitenantTest {

    public static final String TENANT_1 = "tenant1";
    public static final String TENANT_2 = "tenant2";
    public static final String TENANT_3 = "tenant3";

    @Autowired
    TenantIdentifierResolver tenantResolver;

    @Autowired
    private EmployeeRepository repo;

    @Test
    void contextLoads() {

        // tenant1
        var employee = new Employee("emp1");
        repo.save(employee);


        // tenant 2
        tenantResolver.setCurrentTenant(TENANT_2);
        employee = new Employee("emp2");
        repo.save(employee);


        // tenant3
        tenantResolver.setCurrentTenant(TENANT_3);
        employee = new Employee("emp3");
        repo.save(employee);

        // tenant1
        tenantResolver.setCurrentTenant(TENANT_1);
        employee = new Employee("emp4");
        repo.save(employee);
    }

}
