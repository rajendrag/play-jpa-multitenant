Play-JPA-Multitenant
===================

A very simple PlayFramework application, to demonstrate how to build a multi tenant (schema/db per tenant) application using JPA(Hibernate).

Assumptions:
-----------
- There will be a common database which will have the "common to all tenants" information. It also has a table com_tenant which will provide information about all the tenants.
- There will be one separate database/schema per tenant (All these databases will have exact same structure like same table names and column names etc)
- Uses **Hibernate** as the JPA provider.
- Using Hibernate dynamic data source routing.
- tenant id will be available in Http.Context.
- Common DB evolutions are inside evolutions/commondb and tenant databases evolutions are inside evolutions/application.


It also handles the db migrations (evolutions) properly. The scripts in **evolutions/application** will be applied on all the tenant databases

Important Classes
-----------------
- ```IRequestHandler``` Play's RequestHandler's implementation, Decides and sets the current tenant for that request in Http.Context
- ```TenantIdResolver``` Provides the tenant id which will be used to switch to the proper data source
- ```MultiTenantConnectionProviderImpl``` Hibernates's ConnecitonProvider, switches the DataSource dynamically based on the tenant id provided by the resolver.
- ```MultiTenantEvolutionsExecutor``` Addon to Play's evolutions module to execute scripts from **evolutions/appliction** on to all tenant databases.

Install Activator
------------------
Install latest version of activator from https://www.typesafe.com/activator/download unzip and add Activator to your PATH to have the command available in your terminal.

Create DBs
-----------
Execute sql/create_database.sql in mysql.

Setup
-----------
```
git clone https://github.com/rajendrag/play-jpa-multitenant.git
cd play-jpa-multitenant
activator "run" (or to debug activator -jvm-debug 9999 run)
```

This will start the application on port 9000 in auto reload mode, so that you can see your changes instantly

Testing
-------------
```
http://localhost:9000/api/units
```
