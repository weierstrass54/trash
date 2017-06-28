package ru.opentech.api.shop.component.hibernate;

import org.hibernate.boot.model.TypeContributions;
import org.hibernate.dialect.PostgreSQL94Dialect;
import org.hibernate.service.ServiceRegistry;

public class CustomPostgreSqlDialect extends PostgreSQL94Dialect {

    @Override
    public void contributeTypes( TypeContributions typeContributions, ServiceRegistry serviceRegistry ) {
        super.contributeTypes( typeContributions, serviceRegistry );
        typeContributions.contributeType( ArrayType.INSTANCE );
    }

}
