# Spring Data JDBC Tutorial: How to simplify data access in Spring Boot
Tutorial tomado del canal de youtube de [Dan Vega](https://www.youtube.com/watch?v=l_T0nQNbFiM)

# Ver Base de datos H2
- En el archivo de application.properties, debemos habilitar la consola de h2.
- Acceder por navegador mediante la siguiente url
```
http://localhost:8080/h2-console
```
- Debemos agregar los siguientes campos (El valor del JDBC URL lo podemos ver en la consola del IDE)
```
JDBC URL: jdbc:h2:mem:blog
User Name: sa
Password: <no hay password>
```
