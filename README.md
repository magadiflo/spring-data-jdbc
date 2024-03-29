# [Spring Data JDBC](https://www.youtube.com/playlist?list=PLbuI9mmWSoUFGL6B_NxB9IoGqyDq-vEen)

Tomado del canal de youtube **Spring Boot TUTORIAL**

---

## [JDBC vs JPA: Pros y Contras](https://www.youtube.com/watch?v=XuLUnTlAWmw&t=10s)

Antes de comenzar de lleno con `Spring Data JDBC` es importante hacer una comparación entre las distintas tecnologías
que nos permiten **manejar la persistencia de datos** en nuestra aplicación de **Spring Boot**, tales como:
`JDBCTemplate, Spring Data JPA, Spring Data JDBC`.

### JDBC

`Java Data Base Connectivity API`, es una implementación estándar para conectarnos a bases de datos relacionales. Todo
lo que se comunica con base de datos cuando hablamos de java, pasa por `JDBC`.

El patrón que se sigue cuando vamos a cualquier tecnología, no solo la basada en java, es el siguiente:

1. Abres una conexión.
2. Abres un cursor.
3. Envías una consulta.
4. Obtienes un conjunto de resultados (resultSet) sobre el que iteras.
5. Cierras el resultSet.
6. Cierras el cursor.
7. Finalmente, cierras la conexión.

Ahora, tal vez nos toque implementar muchas consultas, supongamos unas 20 y **desafortunadamente nos olvidamos de cerrar
una de ellas**, eso podría traernos problemas.

El administrar las conexiones, abrirlas, cerrarlas, etc., no es una tarea difícil pero es tediosa y de eso se debría
encargar el framework, es ahí donde nace `JdbcTemplate`.

### Jdbc Template

Como desarrolladores de código de aplicación, nuestro enfoque debe ser escribir la consulta, obtener los resultados y
olvidarnos de la tarea de administrar las conexiones. En base a esa necesidad es que nació `JdbcTemplate`.

`Jdbc Template` es una plantilla que básicamente nos permite concentrarnos en **enviar la consulta y consumir los
resultados**, todo lo demás, como cerrar conexiones, cerrar los resultSets son administradas por el kit de herramientas.

### Hibernate

Supongamos que creamos unas 200 consultas SQL, se vuelve abrumador poder escribirlas unas tras otras, porque por
ejemplo, estamos repitiendo los atributos de un objeto constantemente, entonces nos preguntamos:
**¿Por qué java no puede inferir o mapear el tipo de objeto que uso?** digamos que esa fue la razón por la que se generó
el proyecto de `Hibernate` (precursor o iniciador de JPA).

Otra razón por la que surgió hibernate es que las bases de datos como: **Oracle, MySQL, PostgreSQL, SQL Server, etc.,**
si bien es cierto usan el estándar SQL, todas estas bases de datos tienen sintaxis propias adicionales como por ejemplo,
**MySQL = AUTO_INCREMENT, SQLServer = Identity, Oracle = Secuencias, etc.** Entoces hibernate dijo, que si nosotros
mapeamos nuestros objetos java con Hibernate, éste se encargaría de **UNIFICAR** el acceso de tal forma que no debemos
preocuparnos por la sintaxis adicional de cada base de datos.

Hibernate se volvió tan popular que lo estandarizaron y crearon `JPA (Java Persistence API)`. **JPA como especificación
e Hibernate como implementación**, pero ahora no solo existe hibernate como implementación, hay muchos más como
`Eclipse Link`, etc., pero continuaremos hablando de JPA en general.

### Spring Data JPA

Se dieron cuenta que solo utilizando `Hibernate` simplemente estábamos cambiando nuestro conocimiento de `SQL` por el
conocimiento de `JPA`, es decir ya no hacíamos consultas `SQL` sino consulta `JPQL`, al final habría un esfuerzo mayor,
pues estaríamos requiriendo tener ambos conocimientos. Pero sabemos que `JPA` es bastante poderoso, y a veces ese poder
necesita un poco de ayuda, es ahí que nace `Spring Data JPA`.

`Spring Data JPA` nos ayuda con consultas simples y evita la necesidad de trabajar demasiado con el `Entity Manager`,
pero si queremos trabajar con el `Entity Manager`, en `Spring Data JPA` hay un entity manager que podemos inyectar
en los servicios y luego hacer las consultas por nosotros mismos, pero para la mayoría de las consultas simples,
`Spring Data JPA` simplifica el proceso para ponerlo en funcionamiento, como por ejemplo nos proporciona
los `Query Methods` (consultas generadas a partir del nombre del método).

### Spring Data JDBC

`Spring Data JDBC` es una forma de alejarnos del borde del abismo de `JPA`, proporciona a los desarrolladores una
alternativa más sencilla a JPA sin dejar de seguir el paradigma de Spring Data.

`Spring data JDBC` simplifica la implementación de repositorios basados en JDBC. Spring data JDBC añade soporte mejorado
para las capas de acceso a datos tradicionales basadas en JDBC. Para mantenerlo simple, Spring data JDBC no ofrece
características como caché, lazy loading, write behind, y muchas otras características de JPA.

En este proyecto trabajaremos precisamente con `Spring Data JDBC` y profundizaremos más en sus detalles.

---

## [Qué es Spring Data JDBC](https://spring.io/projects/spring-data-jdbc)

Spring Data JDBC, parte de la familia más grande Spring Data, `facilita la implementación de repositorios basados en
JDBC`. Este módulo trata sobre el soporte mejorado para las capas de acceso a datos basadas en JDBC. Facilita la
creación de aplicaciones impulsadas por Spring que utilizan tecnologías de acceso a datos.

`Spring Data JDBC` se aloja junto con `Spring Data R2DBC` en el módulo `Spring Data Relational`, que proporciona
infraestructura para el acceso directo a bases de datos SQL.

Spring Data JDBC pretende ser conceptualmente fácil. Para lograr esto, `NO ofrece almacenamiento en caché, lazy loading,
escritura detrás o muchas otras características de JPA`. Esto hace que `Spring Data JDBC` sea un ORM simple, limitado y
obstinado.

> Fuente: [**Baeldung**](https://www.baeldung.com/spring-data-jdbc-intro)
>
> `Spring Data JDBC` es un marco de persistencia que no es tan complejo como `Spring Data JPA`. **No proporciona
> caché, carga diferida, escritura posterior ni muchas otras características de JPA**. Sin embargo, tiene su propio ORM
> y **proporciona la mayoría de las funciones que usamos con Spring Data JPA, como entidades mapeadas, repositorios,
> anotaciones de consultas y JdbcTemplate.**
>
> Una cosa importante a tener en cuenta es que Spring Data JDBC no ofrece generación de esquemas. Como resultado,
> **somos responsables de crear explícitamente el esquema.**

### El Aggregate Root

Los repositorios de Spring Data están inspirados en el repositorio como se describe en el libro Domain Driven Design de
Eric Evans. Una consecuencia de esto es que `debe tener un repositorio por Aggregate Root`. El **Aggregate Root** es
otro concepto del mismo libro y **describe una entidad que controla el ciclo de vida de otras entidades que juntas son
un Agregado**. `Un Aggregate` es un subconjunto de su modelo que es consistente entre las llamadas de método a su
Aggregate Root.

### Características de Spring Data JDBC

- Operaciones CRUD para agregados simples con NamingStrategy personalizable.
- Soporte para anotaciones @Query.
- Soporte para consultas MyBatis.
- Eventos.
- Configuración de repositorio basada en JavaConfig introduciendo @EnableJdbcRepositories.

## Dependencias del proyecto

Iniciamos el proyecto con las siguientes dependencias:

````xml
<!--Versión de Spring Boot: 3.1.2-->
<!--Versión de Java: 17-->
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jdbc</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>

    <dependency>
        <groupId>com.mysql</groupId>
        <artifactId>mysql-connector-j</artifactId>
        <scope>runtime</scope>
    </dependency>
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <optional>true</optional>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>
</dependencies>
````

## Configuración de acceso a datos con Spring Data JDBC

Primero necesitamos realizar algunas configuraciones en el **application.properties**:

````properties
# DataSource
spring.datasource.url=jdbc:mysql://localhost:3306/db_spring_data_jdbc?serverTimezone=America/Lima
spring.datasource.username=root
spring.datasource.password=magadiflo
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
# Initialize a Database Using Basic SQL Scripts: schema.sql y data.sql
spring.sql.init.mode=always
# Logging JdbcTemplate Queries
logging.level.org.springframework.jdbc.core.JdbcTemplate=DEBUG
logging.level.org.springframework.jdbc.core.StatementCreatorUtils=TRACE
````

A continuación explico los **tres apartados que definí en el application.properties:**

### (1) DataSource

Corresponde a las configuraciones que siempre hemos realizado para definir la base de datos con el que trabajaremos, en
este caso, definimos la url de MySql, su username, password y su dirver class name.

### (2) Initialize a Database Using Basic SQL Scripts: schema.sql y data.sql

[Fuente: Spring Boot](https://docs.spring.io/spring-boot/docs/current/reference/html/howto.html#howto.data-initialization.using-basic-sql-scripts)  
Para cualquier inicialización basada en scripts, es decir, insertar datos a través de `data.sql` o crear un esquema a
través de un `schema.sql`, debemos establecer la propiedad `spring.sql.init.mode=always`. **Para bases de datos
embebidas como H2, esto se establece en siempre de forma predeterminada.**

### (3) Logging JdbcTemplate Queries

[Fuente: Spring Data JDBC](https://docs.spring.io/spring-data/jdbc/docs/current/reference/html/#jdbc.logging)  
**Spring Data JDBC realiza poco o ningún registro (logging) por sí solo**. En cambio, la mecánica de `JdbcTemplate`
proporciona registro (logging) para emitir declaraciones SQL. Por lo tanto, **si desea inspeccionar qué declaraciones
SQL se ejecutan, active el registro para NamedParameterJdbcTemplate o MyBatis de Spring.**

`Spring Data JDBC` se basa en la funcionalidad proporcionada por `JdbcTemplate` para interactuar con la base de datos,
pero abstrae aún más la capa de acceso a datos proporcionando un enfoque más orientado a objetos.

Por lo tanto, **como requerimos tener un registro en consola de las sentencias SQL ejecutadas utilizaremos las
configuraciones proporcionadas por JdbcTemplate.**

[Logging JdbcTemplate Queries](https://www.baeldung.com/sql-logging-spring-boot#loggingJdbcTemplate)  
**Para configurar el registro (logging) de instrucciones cuando se usa JdbcTemplate**, necesitamos las siguientes
propiedades:

````properties
logging.level.org.springframework.jdbc.core.JdbcTemplate=DEBUG
logging.level.org.springframework.jdbc.core.StatementCreatorUtils=TRACE
````

Es similar a la configuración de registro (logging) JPA, **la primera línea es para registrar instrucciones** y la
segunda es para registrar parámetros de instrucciones preparadas.

## Creación de los scripts SQL schema.sql y data.sql

Adelantándonos un poco en la creación del proyecto es que crearemos los scripts SQL de la base de datos.

### Script DDL schema.sql

> Lenguaje de Definición de Datos (DDL) se utiliza para definir y gestionar la estructura de la base de datos. Implica
> la creación, modificación y eliminación de objetos en la base de datos, como tablas, índices, vistas y restricciones:
> **CREATE TABLE, ALTER TABLE, DROP TABLE, CREATE INDEX, CREATE VIEW, ETC**.

En nuestro script `schema.sql` definimos la creación de la tabla usuarios con el que trabajaremos inicialmente en este
proyecto:

````sql
DROP TABLE IF EXISTS users;

CREATE TABLE users(
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(256) NOT NULL,
    account_non_expired BIT,
    account_non_locked BIT,
    credentials_non_expired BIT,
    enabled BIT,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    email_address VARCHAR(100),
    birthdate DATE
);
````

### Script DML data.sql

> Lenguaje de Manipulación de Datos (DML), se utiliza para interactuar con los datos almacenados en la base de datos.
> Los comandos DML permiten la inserción, actualización y eliminación de registros en las tablas, así como la
> recuperación de datos mediante consultas: **SELECT, INSERT INTO, UPDATE, DELETE FROM**.

En nuestro script `data.sql` definimos los datos que insertaremos en la tabla usuarios:

````sql
INSERT INTO users(username, password, account_non_expired, account_non_locked, credentials_non_expired, enabled, first_name, last_name, email_address, birthdate) VALUES('admin', '123456', true, true, true, true, 'Martín', 'Díaz', 'martin@gmail.com', '2000-01-15');
INSERT INTO users(username, password, account_non_expired, account_non_locked, credentials_non_expired, enabled, first_name, last_name, email_address, birthdate) VALUES('user', '123456', true, true, true, true, 'Clara', 'Díaz', 'clara@gmail.com', '1998-07-28');
INSERT INTO users(username, password, account_non_expired, account_non_locked, credentials_non_expired, enabled, first_name, last_name, email_address, birthdate) VALUES('karen', '123456', true, true, true, true, 'Karen', 'Díaz', 'karen@gmail.com', '2000-06-03');
````

Cuando levantemos el proyecto veremos generado la tabla **users** en nuestra base de datos de MySQL de la siguiente
manera:

![tabla users](./assets/tabla_users.png)

Los datos también estarán poblados en la tabla users:

![datos tabla users](./assets/datos-tabla-users.png)

## Creación de entidad, repositorio, servicio y controlador

### Entity User

Iniciaremos con la entidad **User** y a continuación con las demás capas necesarias para su funcionamiento:

````java
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Data
@Builder
@Table(name = "users")
public class User {
    @Id
    private Integer id;
    private String username;
    private String password;
    @Column(value = "account_non_expired")
    private Boolean accountNonExpired;
    private Boolean accountNonLocked;
    private Boolean credentialsNonExpired;
    private Boolean enabled;
    private String firstName;
    private String lastName;
    private String emailAddress;
    private LocalDate birthdate;
}
````

Como observamos en el código anterior, la definición de la entidad es similar a la que trabajamos con JPA, pero en este
código no existe la anotación **@Entity** tampoco **@GeneratedValue**, etc. pero mantenemos otras anotaciones que no
son de JPA sino del propio spring.

> Notar que estoy utilizando **lombok** para hacer el código más limpio y enfocarnos en la forma en la que trabaja
> Spring Data Jdbc.

### Repository IUserRepository

Crearemos ahora el repositorio `IUserRepository` asociado al entity User:

````java
import com.magadiflo.spring.data.jdbc.app.entities.User;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;

public interface IUserRepository extends ListCrudRepository<User, Integer>,
        ListPagingAndSortingRepository<User, Integer> {
}
````

`ListCrudRepository<,>`, interfaz para operaciones CRUD genéricas en un repositorio para un tipo específico. **Esta es
una extensión de la Lista de retorno de CrudRepository en lugar de Iterable** cuando corresponda.

`ListPagingAndSortingRepository<,>`, fragmento del repositorio para proporcionar métodos para **recuperar entidades
utilizando la abstracción de paginación y clasificación**. Esta es una **extensión de PagingAndSortingRepository que
devuelve List en lugar de Iterable cuando corresponda.**

**NOTA**

> Al igual que sucedía cuando trabajábamos con Spring Data JPA, aquí tampoco necesitamos utilizar el estereotipo
> `@Repository` en la interfaz `IUserRepository`, porque estamos extendiendo de una interfaz de Spring quien, pues hará
> la implementación del repositorio en tiempo de ejecución y creará un @Bean registrándolo en el contenedor de Spring.

### Service UserService

Crearemos ahora la clase de servicio:

````java
import com.magadiflo.spring.data.jdbc.app.entities.User;
import com.magadiflo.spring.data.jdbc.app.repositories.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {
    private final IUserRepository userRepository;

    @Transactional(readOnly = true)
    public List<User> getUsers() {
        return this.userRepository.findAll();
    }
}
````

Como notamos en el código anterior, a partir de este punto, el código se vuelve similar o exacto a cómo veníamos
trabajando en proyectos utilizando Spring Data JPA y esto es porque aquí estamos en otra capa **la de servicio**,
mientras que **la diferencia notable en usar Spring Data JPA vs Spring Data JDBC está en la capa de acceso a datos:
repositorios y entities.**

### Controller UserController

Finalmente crearemos el controlador:

````java
import com.magadiflo.spring.data.jdbc.app.entities.User;
import com.magadiflo.spring.data.jdbc.app.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/users")
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok(this.userService.getUsers());
    }
}
````

## Accediendo a los datos con Spring Data JDBC

Hasta este punto ya podemos probar la aplicación y ver el funcionamiento de nuestro api rest, donde **utilizamos Spring
Data JDBC para poder interactuar con la base de datos MySQL:**

````bash
curl -v http://localhost:8080/api/v1/users | jq

--- Resultado
>
< HTTP/1.1 200
< Content-Type: application/json
< Transfer-Encoding: chunked
< Date: Wed, 23 Aug 2023 23:04:47 GMT
<
[
  {
    "id": 1,
    "username": "admin",
    "password": "123456",
    "accountNonExpired": true,
    "accountNonLocked": true,
    "credentialsNonExpired": true,
    "enabled": true,
    "firstName": "Martín",
    "lastName": "Díaz",
    "emailAddress": "martin@gmail.com",
    "birthdate": "2000-01-15"
  },
  {
    "id": 2,
    "username": "user",
    "password": "123456",
    "accountNonExpired": true,
    "accountNonLocked": true,
    "credentialsNonExpired": true,
    "enabled": true,
    "firstName": "Clara",
    "lastName": "Díaz",
    "emailAddress": "clara@gmail.com",
    "birthdate": "1998-07-28"
  },
  {
    "id": 3,
    "username": "karen",
    "password": "123456",
    "accountNonExpired": true,
    "accountNonLocked": true,
    "credentialsNonExpired": true,
    "enabled": true,
    "firstName": "Karen",
    "lastName": "Díaz",
    "emailAddress": "karen@gmail.com",
    "birthdate": "2000-06-03"
  }
]
````

Revisando la consola del IDE IntelliJ IDEA muestro parte del log:

````bash
 INFO 6376 --- [nio-8080-exec-2] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring DispatcherServlet 'dispatcherServlet'
 INFO 6376 --- [nio-8080-exec-2] o.s.web.servlet.DispatcherServlet        : Initializing Servlet 'dispatcherServlet'
 INFO 6376 --- [nio-8080-exec-2] o.s.web.servlet.DispatcherServlet        : Completed initialization in 2 ms
DEBUG 6376 --- [nio-8080-exec-2] o.s.jdbc.core.JdbcTemplate               : Executing prepared SQL query
DEBUG 6376 --- [nio-8080-exec-2] o.s.jdbc.core.JdbcTemplate               : Executing prepared SQL statement [SELECT `users`.`id` AS `id`, `users`.`enabled` AS `enabled`, `users`.`username` AS `username`, `users`.`last_name` AS `last_name`, `users`.`password` AS `password`, `users`.`first_name` AS `first_name`, `users`.`birthdate` AS `birthdate`, `users`.`email_address` AS `email_address`, `users`.`account_non_locked` AS `account_non_locked`, `users`.`account_non_expired` AS `account_non_expired`, `users`.`credentials_non_expired` AS `credentials_non_expired` FROM `users`]
````

---

## Relaciones con Spring Data JDBC

Spring Data JDBC adopta un enfoque diferente para las relaciones. Principalmente que **no hay carga diferida**, por
lo que si alguna vez no desea una relación en una Entidad, déjela fuera de la clase. Esto proviene del concepto de que
**en Domain Driven Design nuestras entidades que estamos recuperando son raíces agregadas (aggregate roots)**, por lo
que, por diseño, los agregados deberían retirar otras clases.

Recordemos que **Spring Data Jdbc no puede crear tablas de bases de datos**, tenemos que hacerlo nosotros mismos, de
manera manual, tal como lo hicimos en el apartado anterior creando la tabla de **users**. En este apartado crearemos
más tablas para poder ejemplificar las relaciones `One-to-One y One-to-Many` de las entidades mapeadas a las tablas
de la base de datos.

## Relación One-to-One

Modificamos los archivos `schema.sql` y `data.sql` para agregar la creación de las dos tablas y poblarlas:

````sql
-- Para la relación One-to-One: owners y addresses
DROP TABLE IF EXISTS addresses;
DROP TABLE IF EXISTS owners;

CREATE TABLE owners(
    id INT AUTO_INCREMENT PRIMARY KEY,
    full_name VARCHAR(100) NOT NULL,
    email VARCHAR(255) NOT NULL,
    username VARCHAR(100) NOT NULL
);

CREATE TABLE addresses(
    id INT AUTO_INCREMENT PRIMARY KEY,
    owner_id INT NOT NULL,
    address_line TEXT NOT NULL,
    CONSTRAINT uq_owner_id UNIQUE(owner_id),
    CONSTRAINT fk_owners_addresses FOREIGN KEY(owner_id) REFERENCES owners(id)
);
````

Es importante notar aquí, que como estamos viendo las relaciones `One-to-One`, es que agregamos la restricción de
columna **UNIQUE** a la columna `owner_id` (foreign key) en el esquema anterior.

````sql
-- Para la relación One-to-One: owners y addresses
INSERT INTO owners(id, full_name, email, username) VALUES(10, 'Martín Díaz', 'martin@gmail.com', 'martin');
INSERT INTO owners(id, full_name, email, username) VALUES(20, 'Karen Caldas', 'karen@gmail.com', 'karen');

INSERT INTO addresses(id, owner_id, address_line) VALUES(1, 10, 'Av. José Olaya, Chimbote');
INSERT INTO addresses(id, owner_id, address_line) VALUES(2, 20, 'Pichari, La Convención, Cuzco');
````

Creamos las entidades `Owner` y `Address`:

````java
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Builder
@Table(name = "owners")
public class Owner {
    @Id
    private Integer id;
    private String fullName;
    private String email;
    private String username;

    @MappedCollection(idColumn = "owner_id")
    private Address address;
}
````

En la entidad `Owner` hemos definido un atributo `address` del tipo de la entidad `Address`, de esta manera ambas
entidades están siendo asociadas a nivel de clases. Ahora, a nivel de base de datos le decimos a Spring Data Jdbc que la
tabla `addresses` tiene una columna llamada `owner_id` (foreign key), por eso agregamos la anotación
`@MappedCollection(idColumn = "owner_id")` sobre el atributo `Address`.

**NOTA:**
[Fuente: docs.spring](https://docs.spring.io/spring-data/jdbc/docs/2.4.14/reference/html/#jdbc.entity-persistence.custom-column-name)

> Para relaciones de `One-to-One` y `One-to-Many` utilizamos la misma anotación `@MappedCollection`. Es decir,
> La anotación `@MappedCollection` se puede utilizar en un tipo de referencia **(relación uno a uno)** o en Sets, Lists
> y Maps **(relación uno a muchos).**
>
> El elemento **idColumn de la anotación** proporciona un nombre personalizado para la columna **foreign key** que hace
> referencia a la columna de identificación en la otra tabla.

````java
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Builder
@Table(name = "addresses")
public class Address {
    @Id
    private Integer id;
    private String addressLine;
}
````

Como la entidad `Owner` es una entidad padre o raíz, mientras que la entidad `Address` es una entidad que en la base de
datos tiene una clave foránea a la entidad `owners`, entonces podríamos decir que es una entidad hija, en ese sentido
es que **solo necesitamos crear una interfaz de repositorio para la entidad padre**, ya que este traerá consigo las
entidades relacionadas:

````java
public interface IOwnerRepository extends ListCrudRepository<Owner, Integer> {
}
````

Creamos nuestra clase de servicio:

````java

@RequiredArgsConstructor
@Service
public class OwnerService {
    private final IOwnerRepository ownerRepository;

    @Transactional(readOnly = true)
    public List<Owner> getAllOwners() {
        return this.ownerRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Owner> getOwner(Integer id) {
        return this.ownerRepository.findById(id);
    }
}
````

Ahora creamos nuestra clase de controlador:

````java

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/owners")
public class OwnerController {
    private final OwnerService ownerService;

    @GetMapping
    public ResponseEntity<List<Owner>> getOwners() {
        return ResponseEntity.ok(this.ownerService.getAllOwners());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Owner> getOwner(@PathVariable Integer id) {
        return this.ownerService.getOwner(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
````

Ejecutando la aplicación:

````bash
curl -v http://localhost:8080/api/v1/owners | jq

--- Respuesta
>
< HTTP/1.1 200
< Content-Type: application/json
<
[
  {
    "id": 10,
    "fullName": "Martín Díaz",
    "email": "martin@gmail.com",
    "username": "martin",
    "address": {
      "id": 1,
      "addressLine": "Av. José Olaya, Chimbote"
    }
  },
  {
    "id": 20,
    "fullName": "Karen Caldas",
    "email": "karen@gmail.com",
    "username": "karen",
    "address": {
      "id": 2,
      "addressLine": "Pichari, La Convención, Cuzco"
    }
  }
]
````

La consulta mostrada en consola fue la siguiente:

````roomsql
SELECT `owners`.`id` AS `id`, `owners`.`email` AS `email`, `owners`.`full_name` AS `full_name`, `owners`.`username` AS `username`, 
    `address`.`id` AS `address_id`, `address`.`address_line` AS `address_address_line` 
FROM `owners` 
    LEFT OUTER JOIN `addresses` `address` ON `address`.`owner_id` = `owners`.`id`
````

Buscando un Owner por su id:

````bash
curl -v http://localhost:8080/api/v1/owners/20 | jq

--- Respuesta
>
< HTTP/1.1 200
< Content-Type: application/json
<
{
  "id": 20,
  "fullName": "Karen Caldas",
  "email": "karen@gmail.com",
  "username": "karen",
  "address": {
    "id": 2,
    "addressLine": "Pichari, La Convención, Cuzco"
  }
}
````

Para este último request, estamos observando no solo la sentencia SQL generada (gracias a la configuración en el
application.properties del JdbcTemplate), sino también el parámetro que se le pasó a la sentencia (gracias a la
configuración en el application.properties del StatementCreatorUtils).

````bash
DEBUG 3192 --- [nio-8080-exec-5] o.s.jdbc.core.JdbcTemplate               : Executing prepared SQL query
DEBUG 3192 --- [nio-8080-exec-5] o.s.jdbc.core.JdbcTemplate               : Executing prepared SQL statement [SELECT `owners`.`id` AS `id`, `owners`.`email` AS `email`, `owners`.`full_name` AS `full_name`, `owners`.`username` AS `username`, `address`.`id` AS `address_id`, `address`.`address_line` AS `address_address_line` FROM `owners` LEFT OUTER JOIN `addresses` `address` ON `address`.`owner_id` = `owners`.`id` WHERE `owners`.`id` = ?]
TRACE 3192 --- [nio-8080-exec-5] o.s.jdbc.core.StatementCreatorUtils      : Setting SQL statement parameter value: column index 1, parameter value [20], value class [java.lang.Integer], SQL type 4
````

Si organizamos mejor la consulta que obtuvimos en consola veremos lo siguiente:

````roomsql
SELECT `owners`.`id` AS `id`, `owners`.`email` AS `email`, `owners`.`full_name` AS `full_name`, `owners`.`username` AS `username`, 
    `address`.`id` AS `address_id`, `address`.`address_line` AS `address_address_line` 
FROM `owners` 
    LEFT OUTER JOIN `addresses` `address` ON `address`.`owner_id` = `owners`.`id` 
WHERE `owners`.`id` = ?
````

## Relación One-to-Many

Modificamos los archivos `schema.sql` y `data.sql` para agregar la creación de las dos tablas y poblarlas:

````sql
-- Para la relación One-to-Many: tasks y comments
DROP TABLE IF EXISTS comments;
DROP TABLE IF EXISTS tasks;

CREATE TABLE tasks(
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    content TEXT NOT NULL,
    published_on DATETIME NOT NULL,
    updated_on DATETIME
);

CREATE TABLE comments(
    id INT AUTO_INCREMENT PRIMARY KEY,
    task_id INT NOT NULL,
    name VARCHAR(100) NOT NULL,
    content TEXT NOT NULL,
    published_on DATETIME NOT NULL,
    updated_on DATETIME,
    CONSTRAINT fk_tasks_comments FOREIGN KEY(task_id) REFERENCES tasks(id)
);
````

````sql
-- Para la relación One-to-Many: tasks y comments
INSERT INTO tasks(id, title, content, published_on, updated_on) VALUES(10, 'Proyecto envío email', 'Este proyecto enviará emails', now(), now());
INSERT INTO tasks(id, title, content, published_on, updated_on) VALUES(20, 'Renovación jardinería', 'Este proyecto renovará el jardín', now(), now());
INSERT INTO tasks(id, title, content, published_on, updated_on) VALUES(30, 'Pintado fachada', 'Trabajamos para remodelar fachada', now(), now());
INSERT INTO tasks(id, title, content, published_on, updated_on) VALUES(40, 'Compra mercado', 'Compras del mes', now(), now());

INSERT INTO comments(id, task_id, name, content, published_on, updated_on) VALUES(1, 10, 'Desarrollador Senior', 'Me uno al proyecto', now(), now());
INSERT INTO comments(id, task_id, name, content, published_on, updated_on) VALUES(2, 10, 'Desarrollador Junior', 'Quiero participar', now(), now());
INSERT INTO comments(id, task_id, name, content, published_on, updated_on) VALUES(3, 20, 'vecino', 'Excelente decisión', now(), now());
INSERT INTO comments(id, task_id, name, content, published_on, updated_on) VALUES(4, 30, 'Karen', 'Colores suaves sería genial', now(), now());
````

Creamos las entidades `Task` y `Comment`:

````java
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@Table(name = "tasks")
public class Task {
    @Id
    private Integer id;
    private String title;
    private String content;
    private LocalDateTime publishedOn;
    private LocalDateTime updatedOn;

    @MappedCollection(idColumn = "task_id")
    private Set<Comment> comments = new HashSet<>();

    public void addComment(Comment comment) {
        this.comments.add(comment);
    }

    public void removeComment(Comment comment) {
        this.comments.remove(comment);
    }
}
````

Al igual que en la relación de `Onte-to-One`, aquí también usaremos la anotación `@MappedCollection`, pero en este caso
anotado sobre un atributo de colección como `Set`. A nivel de base de datos, lo definido en `idColumn = "task_id"`
representa la **clave foránea en la tabla comments que hace referencia al id de la tabla tasks**.

````java
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.Objects;

@Data
@Builder
@Table(name = "comments")
public class Comment {
    @Id
    private Integer id;
    private String name;
    private String content;
    private LocalDateTime publishedOn;
    private LocalDateTime updatedOn;
}
````

Creando el repositorio para la entidad `Task`:

````java
public interface ITaskRepository extends ListCrudRepository<Task, Integer> {
}
````

Creamos nuestra clase de servicio:

````java

@RequiredArgsConstructor
@Service
public class TaskService {
    private final ITaskRepository taskRepository;

    @Transactional(readOnly = true)
    public List<Task> findAllTasks() {
        return this.taskRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Task> getTask(Integer id) {
        return this.taskRepository.findById(id);
    }
}
````

Ahora creamos nuestra clase de controlador:

````java

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/tasks")
public class TaskController {
    private final TaskService taskService;

    @GetMapping
    public ResponseEntity<List<Task>> findAllTasks() {
        return ResponseEntity.ok(this.taskService.findAllTasks());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Task> getTask(@PathVariable Integer id) {
        return this.taskService.getTask(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
````

Ejecutando la aplicación:

````bash
curl -v http://localhost:8080/api/v1/tasks | jq

--- Respuesta
>
< HTTP/1.1 200
< Content-Type: application/json
<
[
  {
    "id": 10,
    "title": "Proyecto envío email",
    "content": "Este proyecto enviará emails",
    "publishedOn": "2023-08-24T12:35:25",
    "updatedOn": "2023-08-24T12:35:25",
    "comments": [
      {
        "id": 1,
        "name": "Desarrollador Senior",
        "content": "Me uno al proyecto",
        "publishedOn": "2023-08-24T12:35:25",
        "updatedOn": "2023-08-24T12:35:25"
      },
      {
        "id": 2,
        "name": "Desarrollador Junior",
        "content": "Quiero participar",
        "publishedOn": "2023-08-24T12:35:25",
        "updatedOn": "2023-08-24T12:35:25"
      }
    ]
  },
  {
    "id": 20,
    "title": "Renovación jardinería",
    "content": "Este proyecto renovará el jardín",
    "publishedOn": "2023-08-24T12:35:25",
    "updatedOn": "2023-08-24T12:35:25",
    "comments": [
      {
        "id": 3,
        "name": "vecino",
        "content": "Excelente decisión",
        "publishedOn": "2023-08-24T12:35:25",
        "updatedOn": "2023-08-24T12:35:25"
      }
    ]
  },
  {
    "id": 30,
    "title": "Pintado fachada",
    "content": "Trabajamos para remodelar fachada",
    "publishedOn": "2023-08-24T12:35:25",
    "updatedOn": "2023-08-24T12:35:25",
    "comments": [
      {
        "id": 4,
        "name": "Karen",
        "content": "Colores suaves sería genial",
        "publishedOn": "2023-08-24T12:35:25",
        "updatedOn": "2023-08-24T12:35:25"
      }
    ]
  },
  {
    "id": 40,
    "title": "Compra mercado",
    "content": "Compras del mes",
    "publishedOn": "2023-08-24T12:35:25",
    "updatedOn": "2023-08-24T12:35:25",
    "comments": []
  }
]
````

Las consultas mostradas en consola fueron las siguientes:

**Consulta general:**

````roomsql
SELECT `tasks`.`id` AS `id`, `tasks`.`title` AS `title`, `tasks`.`content` AS `content`, `tasks`.`updated_on` AS `updated_on`, `tasks`.`published_on` AS `published_on` 
FROM `tasks`
````

**Obteniendo comments para cada task. En el ejemplo siguiente solo se muestra para el task con id 10:**

````roomsql
SELECT `comments`.`id` AS `id`, `comments`.`name` AS `name`, `comments`.`content` AS `content`, `comments`.`updated_on` AS `updated_on`, `comments`.`published_on` AS `published_on` 
FROM `comments` WHERE `comments`.`task_id` = ?

--Setting SQL statement parameter value: column index 1, parameter value [10], value class [java.lang.Integer], SQL type 4
````

Buscando un Task por su id:

````bash
curl -v http://localhost:8080/api/v1/tasks/20 | jq

--- Respuesta
>
< HTTP/1.1 200
< Content-Type: application/json
<
{
  "id": 20,
  "title": "Renovación jardinería",
  "content": "Este proyecto renovará el jardín",
  "publishedOn": "2023-08-24T12:35:25",
  "updatedOn": "2023-08-24T12:35:25",
  "comments": [
    {
      "id": 3,
      "name": "vecino",
      "content": "Excelente decisión",
      "publishedOn": "2023-08-24T12:35:25",
      "updatedOn": "2023-08-24T12:35:25"
    }
  ]
}
````

La consulta mostrada en consola fue la siguiente:

**Primero busca el task con id = 20:**

````roomsql
SELECT `tasks`.`id` AS `id`, `tasks`.`title` AS `title`, `tasks`.`content` AS `content`, `tasks`.`updated_on` AS `updated_on`, `tasks`.`published_on` AS `published_on` 
FROM `tasks` WHERE `tasks`.`id` = ?
--Setting SQL statement parameter value: column index 1, parameter value [20], value class [java.lang.Integer], SQL type 4
````

**Luego, busca todos los comments con task_id = 20:**

````roomsql
SELECT `comments`.`id` AS `id`, `comments`.`name` AS `name`, `comments`.`content` AS `content`, `comments`.`updated_on` AS `updated_on`, `comments`.`published_on` AS `published_on` 
FROM `comments` 
WHERE `comments`.`task_id` = ?
--Setting SQL statement parameter value: column index 1, parameter value [20], value class [java.lang.Integer], SQL type 4
````

## Agregados y Agregado raíz (Aggregates and Aggregate Root)

[Fuente: Docs.spring](https://docs.spring.io/spring-data/jdbc/docs/2.4.14/reference/html/#jdbc.domain-driven-design)  
[Fuente: deviq](https://deviq.com/domain-driven-design/aggregate-pattern)  
[Fuente: koalite](https://blog.koalite.com/2015/03/como-elegir-aggregate-roots/)  
[Fuente: José Cuéllar](https://josecuellar.net/domain-driven-design-modules-aggregates/)

Todos los módulos de Spring Data están inspirados en los conceptos de **"repositorio", "agregado" y "raíz agregada"** de
**Domain Driven Design.**

Un **aggregate** es una colección de una o más entidades relacionadas (y posiblemente objetos de valor). Cada agregado
tiene una sola entidad raíz, denominada **aggregate root**. El aggregate root **es responsable de controlar el acceso a
todos los miembros de su agregado.** Es perfectamente **aceptable tener agregados de una sola entidad**, en cuyo caso
esa entidad es en sí misma la raíz de su agregado. Además de controlar el acceso, la raíz agregada también es
responsable de garantizar la consistencia del agregado. Es por eso que es importante asegurarse de que la raíz agregada
no exponga directamente a sus hijos, sino que controle el acceso a sí misma.

Una característica que deben cumplir los **aggregate root** es que son las únicas entidades que retornan los
repositorios.

**Los aggregates proveen un agrupamiento lógico de Entidades y Objetos-Valor.** El **aggregate root actúa de punto de
entrada para ese conjunto,** encargándose de las normas y restricciones que deban cumplir las colecciones de hijos.

Un agregado es un grupo de clases que mantienen un invariante de forma conjunta. La raíz de ese agregado es el objeto
«padre» que nos permite interactuar con el grupo de clases.

La existencia de la agregación se da en una sola dirección, si la raíz es eliminada, todo los objetos agregados a ella
también deberán ser eliminadas, no tienen sentido de existencia si la raíz deja de existir.

Si trabajamos con **agregados**, deberíamos seguir ciertas **reglas innegociables:**

- Todas **las interacciones con clases que forman parte de un agregado deberíamos hacerlas a través del
  aggregate root.** Esto es necesario para que podamos garantizar los invariantes.
- **Solo podemos recuperar de la base de datos (a través de repositorios o como más te guste) aggregate roots.** Nunca
  recuperaremos entidades internas al agregado.
- **Si necesitamos mantener una relaciones entre entidades de diferentes agregados, siempre será hacia el aggregate
  root,** nunca hacia clases internas del agregado.

Los **agregados** pueden relacionarse entre sí **únicamente mediante los identificadores de su entidad raíz:**

![referencia-otros-agregados.png](./assets/referencia-otros-agregados.png)

**Debemos evitar las propiedades asociativas** conteniendo la instancia de la entidad root del otro agregado.
**Realizaremos dicha referencia mediante su identificador almacenado en un value object.**

### Ejemplo de aggregates de un eCommerce

Como ejemplo, considere un dominio de comercio electrónico que tiene conceptos para **Orders (Order)**, que tienen
múltiples **items de order (OrderItem)**, cada uno de los cuales se refiere a una cierta cantidad de **productos
(Producto)** que se compran. Agregar y eliminar items de un Order debe estar controlado por el Order - partes de la
aplicación no deberían poder llegar y crear un **OrderItem** individual como parte de un **Order** sin pasar por el
**Order.** Eliminar un Order debería eliminar todos los items asociados con él. Por lo tanto, el **Order tiene
sentido como raíz agregada** para el grupo Order - OrderItem.

![order-aggregate](./assets/order-aggregate.png)

En el ejemplo anterior, el agregado de Order se compone de varias entidades. Sin embargo, el acceso a la entidad
OrderItem lo gestiona el agregado Order, que es el **aggregate root.**

**¿Qué pasa con el producto?** Cada **OrderItem** representa (entre otras cosas) una cantidad de un producto.
**¿Tiene sentido que OrderItem tenga una propiedad de navegación para Producto?** Si es así, eso complicaría el agregado
de Order, ya que idealmente debería poder atravesar todas sus propiedades de navegación cuando persista.
A modo de prueba, **¿tiene sentido eliminar el "Producto A" si se elimina un Order de ese producto?** No, si bien este
order puede no incluir el "Producto A", otro order puede incluirlo. Por lo tanto, **el Producto no pertenece al agregado
del Order. Es probable que el Producto deba ser su propia raíz agregada**, en cuyo caso la recuperación de instancias
del producto se puede realizar mediante un Repositorio. **Todo lo que se requiere para hacerlo es su ID.** En
consecuencia, si **OrderItem solo hace referencia a Producto por Id**, es suficiente.

![product-aggregate](./assets/product-aggregate.png)

En el ejemplo anterior, `el agregado Producto no tiene ninguna otra entidad. Este es un ejemplo de un agregado de una
sola entidad.`

## Relaciones entre agregados (aggregates)

Hasta este punto tenemos 5 entidades de las cuales, para este apartado nos interesan 4, la entidad **User** que
trabajamos inicialmente lo veremos más adelante, por lo tanto, centrémonos en las entidades:
`Owner, Address, Task y Comment`.

Hasta este punto **tenemos dos agregados**, cada uno con su **agregado raíz** y lo que queremos hacer ahora es
establecer la relación de **One-to-Many** entre los **agregados raíz de cada agregado**, tal como se ve en la imagen
siguiente:

![agregados-trabajados](./assets/agregados-trabajados.png)

Entonces, como queremos establecer la relación de `One-to-Many` entre las tablas `owners y tasks`, debemos ir al esquema
de la base de datos y modificar la tabla **tasks** para que contenga la **foreign key** `owner_id` que hace referencia
a la **primary key (id)** de la tabla **owners**:

````sql
-- Relación One-to-Many: owners y tasks
CREATE TABLE tasks(
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    content TEXT NOT NULL,
    published_on DATETIME NOT NULL,
    updated_on DATETIME,
    owner_id INT,
    CONSTRAINT fk_owners_tasks FOREIGN KEY (owner_id) REFERENCES owners(id)
);
````

Con la modificación anterior, también debemos modificar los datos del archivo `data.sql` agregando el nuevo campo
**owner_id**:

````sql
-- Relación One-to-Many: owners y tasks
INSERT INTO tasks(id, title, content, published_on, updated_on, owner_id) VALUES(10, 'Proyecto envío email', 'Este proyecto enviará emails', now(), now(), 20);
INSERT INTO tasks(id, title, content, published_on, updated_on, owner_id) VALUES(20, 'Renovación jardinería', 'Este proyecto renovará el jardín', now(), now(), 10);
INSERT INTO tasks(id, title, content, published_on, updated_on, owner_id) VALUES(30, 'Pintado fachada', 'Trabajamos para remodelar fachada', now(), now(), 10);
INSERT INTO tasks(id, title, content, published_on, updated_on, owner_id) VALUES(40, 'Compra mercado', 'Compras del mes', now(), now(), 10);
````

Ahora vamos a la clase de entidad **Task** donde crearemos una referencia agregada de tipo **Owner**:

````java

@Data
@Builder
@Table(name = "tasks")
public class Task {
    /* other properties */
    @MappedCollection(idColumn = "task_id")
    private Set<Comment> comments = new HashSet<>();

    @Column(value = "owner_id")
    private AggregateReference<Owner, Integer> owner;
    /* other code */
}
````

Observamos en el código anterior que estamos usando la interfaz `AggregateReference`, **esta interfaz nos permite
agregar una referencia a la raíz agregada de un agregado diferente.** En otras palabras, al **aggregate root Task**
estamos agregando la referencia del otro **aggregate root Owner**. Nótese además, que estamos usando la anotación
`@Column(value = "owner_id")` sobre la interfaz `AggregateReference` esto es porque necesitamos decirle a Spring
Data Jdbc que en la tabla `tasks` de la base de datos está la **foreign key** llamada `owner_id`.

### Probando la aplicación

Vemos hasta este punto el comportamiento de la aplicación llamando a los endpoints del **OwnerController** quien trabaja
con la entidad **Owner** como un `aggregate root`:

````bash
curl -v http://localhost:8080/api/v1/owners | jq

--- Respuesta
>
< HTTP/1.1 200
< Content-Type: application/json
<
[
  {
    "id": 10,
    "fullName": "Martín Díaz",
    "email": "martin@gmail.com",
    "username": "martin",
    "address": {
      "id": 1,
      "addressLine": "Av. José Olaya, Chimbote"
    }
  },
  {
    "id": 20,
    "fullName": "Karen Caldas",
    "email": "karen@gmail.com",
    "username": "karen",
    "address": {
      "id": 2,
      "addressLine": "Pichari, La Convención, Cuzco"
    }
  }
]
````

Resultado en consola:

````roomsql
SELECT `owners`.`id` AS `id`, `owners`.`email` AS `email`, `owners`.`full_name` AS `full_name`, `owners`.`username` AS `username`, 
        `address`.`id` AS `address_id`, `address`.`address_line` AS `address_address_line` 
FROM `owners` 
    LEFT OUTER JOIN `addresses` `address` ON `address`.`owner_id` = `owners`.`id`
````

**Como vemos en el resultado, no hay cambios, es decir los resultados son iguales a lo que obtuvimos en secciones
anteriores, no vemos que los cambios que hicimos en el código hayan afectado el resultado.**

Ahora, hagamos lo mismo llamando a los endpoints del **TaskController** quien trabaja con la entidad **Task** como
un `aggregate root`:

````bash
curl -v http://localhost:8080/api/v1/tasks | jq

--- Resultado
>
< HTTP/1.1 200
< Content-Type: application/json
<
[
  {
    "id": 10,
    "title": "Proyecto envío email",
    "content": "Este proyecto enviará emails",
    "publishedOn": "2023-08-24T22:41:44",
    "updatedOn": "2023-08-24T22:41:44",
    "comments": [
      {
        "id": 1,
        "name": "Desarrollador Senior",
        "content": "Me uno al proyecto",
        "publishedOn": "2023-08-24T22:41:44",
        "updatedOn": "2023-08-24T22:41:44"
      },
      {
        "id": 2,
        "name": "Desarrollador Junior",
        "content": "Quiero participar",
        "publishedOn": "2023-08-24T22:41:44",
        "updatedOn": "2023-08-24T22:41:44"
      }
    ],
    "owner": {
      "id": 20
    }
  },
  {...}
]
````

Resultados en consola:

````roomsql
SELECT `tasks`.`id` AS `id`, `tasks`.`owner_id` AS `owner_id`, `tasks`.`title` AS `title`, `tasks`.`content` AS `content`, `tasks`.`updated_on` AS `updated_on`, `tasks`.`published_on` AS `published_on` 
FROM `tasks`
````

````roomsql
SELECT `comments`.`id` AS `id`, `comments`.`name` AS `name`, `comments`.`content` AS `content`, `comments`.`updated_on` AS `updated_on`, `comments`.`published_on` AS `published_on` 
FROM `comments` 
WHERE `comments`.`task_id` = ?
--Setting SQL statement parameter value: column index 1, parameter value [10], value class [java.lang.Integer], SQL type 4

-- Otras consultas similares a la anterior se omitieron para no hacerlo repetitivo.
````

En el resultado anterior, al consultar por el endpoint de **tasks** vemos que a la respuesta que inicialmente teníamos
como información del **task** y de los **comments** ahora se le suma el `owner con su id`, esto es porque
agregamos `AggregateReference<Owner, Integer> owner` a la clase **Task**.

El resultado anterior puede ser suficiente en algunos casos, pero generalmente queremos más información sobre el
**Owner** o los **Task**, para remediar ese caso podemos crear **DTOs** usando los **record** de java.

### Agregando Información del Owner en Task (One to One)

Iniciemos desde el **aggregate root Task**, como vimos, el resultado nos arroja información del **task**, de los
**comments** y a eso se le suma ahora el **owner id**, pero no solo queremos ver el **owner id**, sino información
detallada del **Owner**, para eso es necesario ver cómo tenemos actualmente nuestra base de datos.

![base-de-datos-agregados](./assets/base-de-datos-agregados.png)

Si nos fijamos en la relación de **One-to-Many** entre **owners** y **tasks** (lo que en las clases java serían los
aggregates root Owner y Task), veremos que `un owner tiene muchos tasks y muchos tasks le pertenecen a un owner`. Ahora
si analizamos la siguiente situación, del conjunto de tasks que le pertenecen a un owner, seleccionamos uno de ellos,
veremos que ese task le sigue perteneciendo al mismo owner.

Ahora, si nos vamos a nivel de clases java, cuando consultemos el `aggregate root Task` lo que haremos será mostrar la
información que nos retorna task **(task + comments + owner id)** pero ahora le agregaremos información
del owner al que pertenece ese task y eso es usando un record al que le llamaremos `TaskDetails`:

````java
public record TaskDetails(Task task, Owner owner) { // Task 1 ---- 1 Owner
}
````

Nos vamos al controller **TaskController** (en este punto no necesitamos hacer cambios en el repositorio ni
el servicio de Task, al menos no para hacer la consulta desde los endpoints de **TaskController**) y agregamos un
endpoint para ver un **Task** en específico incluyendo sus detalles adicionales como el tener los detalles del
**Owner** al que pertenece:

````java

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/tasks")
public class TaskController {

    private final TaskService taskService;
    private final OwnerService ownerService;

    /* omitted code */

    @GetMapping(path = "/{id}/details")
    public ResponseEntity<TaskDetails> getTaskDetails(@PathVariable Integer id) {
        return this.taskService.getTask(id) // (1)
                .map(taskDB -> this.ownerService.getOwner(taskDB.getOwner().getId()) // (2)
                        .map(ownerDB -> ResponseEntity.ok(new TaskDetails(taskDB, ownerDB))) // (3)
                        .orElseGet(() -> ResponseEntity.notFound().build())
                )
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
````

En el código anterior, lo que hacemos es **(1)** obtener un task específico de la base de datos, luego a partir
de ese task, para ser más precisos a partir del **(2)** `owner id` obtener información de su Owner para finalmente
retornar el **record TaskDetails** incluyendo el Task y su Owner encontrado.

````bash
curl -v http://localhost:8080/api/v1/tasks/10/details | jq

--- Respuesta
>
< HTTP/1.1 200
< Content-Type: application/json
<
{
  "task": {
    "id": 10,
    "title": "Proyecto envío email",
    "content": "Este proyecto enviará emails",
    "publishedOn": "2023-08-25T11:45:22",
    "updatedOn": "2023-08-25T11:45:22",
    "comments": [
      {
        "id": 1,
        "name": "Desarrollador Senior",
        "content": "Me uno al proyecto",
        "publishedOn": "2023-08-25T11:45:22",
        "updatedOn": "2023-08-25T11:45:22"
      },
      {
        "id": 2,
        "name": "Desarrollador Junior",
        "content": "Quiero participar",
        "publishedOn": "2023-08-25T11:45:22",
        "updatedOn": "2023-08-25T11:45:22"
      }
    ],
    "owner": {
      "id": 20
    }
  },
  "owner": {
    "id": 20,
    "fullName": "Karen Caldas",
    "email": "karen@gmail.com",
    "username": "karen",
    "address": {
      "id": 2,
      "addressLine": "Pichari, La Convención, Cuzco"
    }
  }
}
````

Consulta SQL obtenido en consola:

````roomsql
SELECT `tasks`.`id` AS `id`, `tasks`.`owner_id` AS `owner_id`, `tasks`.`title` AS `title`, `tasks`.`content` AS `content`, `tasks`.`updated_on` AS `updated_on`, `tasks`.`published_on` AS `published_on` 
FROM `tasks` 
WHERE `tasks`.`id` = ?
--parameter value [10], value class [java.lang.Integer], SQL type 4
````

````roomsql
SELECT `comments`.`id` AS `id`, `comments`.`name` AS `name`, `comments`.`content` AS `content`, `comments`.`updated_on` AS `updated_on`, `comments`.`published_on` AS `published_on` 
FROM `comments` 
WHERE `comments`.`task_id` = ?
--parameter value [10], value class [java.lang.Integer], SQL type 4
````

````roomsql
SELECT `owners`.`id` AS `id`, `owners`.`email` AS `email`, `owners`.`full_name` AS `full_name`, `owners`.`username` AS `username`, 
    `address`.`id` AS `address_id`, `address`.`address_line` AS `address_address_line` 
FROM `owners` 
    LEFT OUTER JOIN `addresses` `address` ON `address`.`owner_id` = `owners`.`id` 
WHERE `owners`.`id` = ?
--parameter value [20], value class [java.lang.Integer], SQL type 4
````

### Agregando Información al Owner de sus Tasks (One-to-Many)

Ahora, enfoquémonos en el **aggregate root Owner**, recordemos que el resultado que nos arrojaba al consultar por el
endpoint de **OwnerController** era información del **Owner** y su **Address** nada más. Entonces, lo que haremos ahora
será consultar por un **Owner** y que nos retorne (además de su información y su address) información sobre la lista
de tasks que tiene.

Recordemos nuevamente la base de datos donde `Un Owner tiene Muchos Tasks`:

![base-de-datos-agregados](./assets/base-de-datos-agregados.png)

Por lo tanto, cuando consultemos por un Owner en específico, consultaremos luego por la lista de tasks que tiene
ese owner y los colocaremos en un **DTO record de java**, dando por cumplido la relación de **uno a muchos**:

````java
public record OwnerDetails(Owner owner, List<Task> tasks) { //Owner 1 ---- * Task
}
````

Ahora sí se hace necesario agregar un método personalizado en la interfaz **ITaskRepository**, ya que necesitamos
obtener los Task que le pertenecen aun Owner en específico:

````java
public interface ITaskRepository extends ListCrudRepository<Task, Integer> {
    List<Task> findAllByOwner(Integer id);
}
````

También modificamos la clase de servicio para implementar el método anterior:

````java

@RequiredArgsConstructor
@Service
public class TaskService {
    /* omitted code */

    @Transactional(readOnly = true)
    public List<Task> findAllByOwner(Integer id) {
        return this.taskRepository.findAllByOwner(id);
    }
}
````

Finalmente, modificamos el controlador **OwnerController** para implementar el endpoint por donde realizaremos la
consulta del owner y sus detalles (tasks):

````java

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/owners")
public class OwnerController {

    private final OwnerService ownerService;
    private final TaskService taskService;

    /* omitted code */

    @GetMapping(path = "/{id}/details")
    public ResponseEntity<OwnerDetails> getOwnerDetails(@PathVariable Integer id) {
        return this.ownerService.getOwner(id) // (1)
                .map(ownerDB -> {
                    List<Task> tasks = this.taskService.findAllByOwner(id); // (2)
                    return ResponseEntity.ok(new OwnerDetails(ownerDB, tasks)); // (3)
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
````

El código anterior **(1)** consulta a la base de datos para obtener un Owner en específico, luego a partir del id del
Owner **(2)** consultamos por los tasks que tiene en la base de datos, finalmente **(3)** retornamos el **record**
conteniendo el **Owner** solicitado junto a su lista de **Task".

````bash
curl -v http://localhost:8080/api/v1/owners/20/details | jq

--- Respuesta
>
< HTTP/1.1 200
< Content-Type: application/json
<
{
  "owner": {
    "id": 20,
    "fullName": "Karen Caldas",
    "email": "karen@gmail.com",
    "username": "karen",
    "address": {
      "id": 2,
      "addressLine": "Pichari, La Convención, Cuzco"
    }
  },
  "tasks": [
    {
      "id": 10,
      "title": "Proyecto envío email",
      "content": "Este proyecto enviará emails",
      "publishedOn": "2023-08-25T12:17:46",
      "updatedOn": "2023-08-25T12:17:46",
      "comments": [
        {
          "id": 1,
          "name": "Desarrollador Senior",
          "content": "Me uno al proyecto",
          "publishedOn": "2023-08-25T12:17:46",
          "updatedOn": "2023-08-25T12:17:46"
        },
        {
          "id": 2,
          "name": "Desarrollador Junior",
          "content": "Quiero participar",
          "publishedOn": "2023-08-25T12:17:46",
          "updatedOn": "2023-08-25T12:17:46"
        }
      ],
      "owner": {
        "id": 20
      }
    }
  ]
}
````

````roomsql
SELECT `owners`.`id` AS `id`, `owners`.`email` AS `email`, `owners`.`full_name` AS `full_name`, `owners`.`username` AS `username`, 
        `address`.`id` AS `address_id`, `address`.`address_line` AS `address_address_line` 
FROM `owners` 
    LEFT OUTER JOIN `addresses` `address` ON `address`.`owner_id` = `owners`.`id` 
WHERE `owners`.`id` = ?
--parameter value [20], value class [java.lang.Integer], SQL type 4
````

````roomsql
SELECT `tasks`.`id` AS `id`, `tasks`.`owner_id` AS `owner_id`, `tasks`.`title` AS `title`, `tasks`.`content` AS `content`, `tasks`.`updated_on` AS `updated_on`, `tasks`.`published_on` AS `published_on` FROM 
`tasks` 
WHERE `tasks`.`owner_id` = ?
--parameter value [20], value class [java.lang.Integer], SQL type 4
````

````roomsql
SELECT `comments`.`id` AS `id`, `comments`.`name` AS `name`, `comments`.`content` AS `content`, `comments`.`updated_on` AS `updated_on`, `comments`.`published_on` AS `published_on` 
FROM `comments` 
WHERE `comments`.`task_id` = ?
--parameter value [10], value class [java.lang.Integer], SQL type 4
````

## Relación entre agregados (Many-to-Many)

Recordemos que al inicio trabajamos con la entity **User** a nivel de clase java y **users** como tabla de base de datos
para ver aspectos generales de Spring Data Jdbc. En este apartado tocaremos nuevamente dicha entidad y su relación con
la entidad **Authority** que crearemos más adelante.

Veamos ahora las tablas de base de datos con las que trabajaremos en este apartado. **Un usuario puede tener muchos
authorities y a un authority pueden estar asignados muchos usuarios**, claramente nos encontramos en una relación de
`Many-to-Many` a nivel de base de datos y ante esa relación lo que se hace es crear una tabla intermedia que contenga
los identificadores de las otras tablas.

![users-authorities-db](./assets/users-authorities-db.png)

Ya teniendo nuestro diagrama sobre el que trabajaremos, lo que sigue es modificar el esquema de la base de datos
agregando las nuevas tablas para relacionarlas con **users**:

````sql
DROP TABLE IF EXISTS users_authorities;
DROP TABLE IF EXISTS authorities;
DROP TABLE IF EXISTS users;

CREATE TABLE users(
  /**
   * tabla ya creada desde el inicio 
  */
);

CREATE TABLE authorities(
    id INT AUTO_INCREMENT PRIMARY KEY,
    authority VARCHAR(50)
);

CREATE TABLE users_authorities(
    user_id INT NOT NULL,
    authority_id INT NOT NULL,
    CONSTRAINT pk_users_authorities PRIMARY KEY (user_id, authority_id),
    CONSTRAINT fk_users__users_authorities FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT fk_authorities__users_authorities FOREIGN KEY (authority_id) REFERENCES authorities(id)
);
````

En nuestro script **data.sql** agregamos datos en las nuevas tablas creadas. Es necesario definir
explícitamente el identificador de los **users** y los **authorities** para poder usarlos en la tabla intermedia:

````sql
INSERT INTO users(id, username, password, account_non_expired, account_non_locked, credentials_non_expired, enabled, first_name, last_name, email_address, birthdate) VALUES(2, 'admin', '123456', true, true, true, true, 'Martín', 'Díaz', 'martin@gmail.com', '2000-01-15');
INSERT INTO users(id, username, password, account_non_expired, account_non_locked, credentials_non_expired, enabled, first_name, last_name, email_address, birthdate) VALUES(4, 'user', '123456', true, true, true, true, 'Clara', 'Díaz', 'clara@gmail.com', '1998-07-28');
INSERT INTO users(id, username, password, account_non_expired, account_non_locked, credentials_non_expired, enabled, first_name, last_name, email_address, birthdate) VALUES(6, 'karen', '123456', true, true, true, true, 'Karen', 'Díaz', 'karen@gmail.com', '2000-06-03');
INSERT INTO authorities(id, authority) VALUES(3, 'ROLE_USER');
INSERT INTO authorities(id, authority) VALUES(5, 'ROLE_ADMIN');
INSERT INTO authorities(id, authority) VALUES(7, 'ROLE_DEVELOPER');
INSERT INTO users_authorities(user_id, authority_id) VALUES(2, 3);
INSERT INTO users_authorities(user_id, authority_id) VALUES(2, 5);
INSERT INTO users_authorities(user_id, authority_id) VALUES(2, 7);
INSERT INTO users_authorities(user_id, authority_id) VALUES(4, 3);
INSERT INTO users_authorities(user_id, authority_id) VALUES(4, 5);
INSERT INTO users_authorities(user_id, authority_id) VALUES(6, 7);
````

### [Mapeando entidades](https://javabydeveloper.com/spring-data-jdbc-many-to-many-example/#google_vignette)

En **Spring Data JDBC, para crear entidades, la clase debe seguir el diseño agregado de Domain Driven Design (DDD)**.
`Una entidad solo puede formar parte de un agregado`, es decir una entidad no puede ser parte de varios agregados
diferentes al mismo tiempo.

**Spring Data JDBC no admite relaciones de muchos a muchos entre entidades (clases java)**, porque en la relación de
muchos a muchos **se necesitarían dos raíces de agregados (aggregate roots)** en la asignación de entidades, lo cual
**no es compatible con el enfoque de un solo agregado por entidad** en Spring Data JDBC.

Para complementar la idea anterior con ejemplos, hemos venido trabajando hasta este punto con los siguientes agregados:

- **Primer Agregado:** Owner(aggregate root) y Address - **Relación de One-to-One**
- **Segundo Agregado:** Task(aggregate root) y Comment - **Relación de One-to-Many**

Ahora, **"supongamos"** que las nuevas entidades que trabajaremos también formen un agregado, entonces sería:

- **Tercer Agregado:** User(aggregate root), Authority(aggregate root), UserAuthority (tabla intermedia) - **Relación de
  Many-to-Many**

**Los dos primeros agregados son correctos**, pero **el tercer agregado que supusimos es totalmente incorrecto** y eso
es porque en una relación de `Many-to-Many` cada entidad de la relación es un **aggregate root**, por lo tanto, en
nuestro caso tenemos dos **aggregate roots: User y Authority**, adicionalmente tenemos una entidad **UserAuthority**
que mapea la tabla intermedia de la base de datos **users_authorities**, entonces en este **"supuesto" tercer agregado**
tendríamos tres entidades, de las cuales la entidad **UserAuthority** se convertiría en una especie de entidad de unión
que **necesitaría dos raíces de agregados: uno relacionado con User y la otra con Authority**. Sin embargo, el enfoque
que se toma en Spring Data Jdbc es que cada agregado debe tener un único **aggregate root** y eso no se estaría
cumpliendo con este tipo de relación.

Pero **podemos lograr el mapeo de entidades de muchos a muchos utilizando referencias de identificación.** Veamos
como lograr eso, pero antes recordemos cómo tenemos nuestras tablas en la base de datos:

![users_authorities-mysql](./assets/users_authorities-mysql.png)

La primera decisión que debemos tomar es **¿dónde agregar una relación?**, si va a ser un `Authority que conoce
la lista de Users` o el `User conoce la lista de Authorities`.

En mi caso `tiene más sentido agregar una colección de Authorities en la entidad User`, para eso necesitamos crear
una nueva clase que modele la referencia a los authorities, esta clase se llamará `AuthorityRef` y `representará la
tabla intermedia en la base de datos.`

Esta clase `AuthorityRef` tendrá solo un campo y será `authorityId (authority_id en la tabla de la BD)`, lo que
significa que es una identificación de Authority al que hace referencia el User.

Ahora sí, comencemos creando la entidad **Authority** que solo tendrá los atributos correspondientes a la tabla
**authorities** de la base de datos:

````java

@Data
@Builder
@Table(name = "authorities")
public class Authority {
    @Id
    private Integer id;
    private String authority;
}
````

La entidad **AuthorityRef** es solo para contener identificadores de entidad de **Authority**:

````java

@Data
@Table(name = "users_authorities")
public class AuthorityRef {
    @Column(value = "authority_id")
    private Integer authorityId;
}
````

Finalmente, creamos la entidad **User** quien tendrá la colección de los **AuthorityRef**. Esta clase **AuthorityRef**
está mapeada a la tabla intermedia **users_authorities**. Además, en la clase definimos solo el atributo del
identificador del **Authority** a través del atributo **authority_id**, mientras que
el atributo **user_id** es usado como una especie de **FK** que permite referenciar a la entidad **User**.

Para comprender mejor lo anterior, muestro la imagen que hice a mano:

![many-to-many-ref](./assets/many-to-many-ref.png)

Como no podemos mapear directamente una relación de muchos a muchos en Spring Data Jdbc es que nos recomiendan
realizar la estrategia de la tabla de referencia **AuthorityRef**. Ahora, según cómo yo lo veo, y es el origen del
diagrama anterior, es que **"debemos imaginarnos una relación One-to-Many entre la entidad User (quien será nuestra
clase principal o padre) y la entidad AuthorityRef quien tendrá la 'Foreing Key' de User, es decir tendrá el user_id,
además del campo authority_id"**, entonces cuando creamos la relación entre **User** y **AuthorityRef**, será tal como
lo hicimos en apartados superiores con la relación **One-to-Many**. La entidad padre **User** tendrá la colección
de la entidad hija **AuthorityRef** y a esa colección lo anotamos con **@MappedCollection(idColumn = "user_id")**
indicándole el **user_id**, es decir la **Fk** en la tabla **AuthorityRef** que hace referencia a **User**:

````java

@Data
@Builder
@Table(name = "users")
public class User {
    @Id
    private Integer id;
    private String username;
    private String password;

    // Con el @Column podemos definirle un nombre al que se asociará este atributo de la clase con un atributo de la BD, 
    // aunque por defecto si el atributo de la clase está en camelCase se asociará en la DB a uno con guiones bajos.
    @Column(value = "account_non_expired")
    private Boolean accountNonExpired;

    private Boolean accountNonLocked;
    private Boolean credentialsNonExpired;
    private Boolean enabled;
    private String firstName;
    private String lastName;
    private String emailAddress;
    private LocalDate birthdate;

    @MappedCollection(idColumn = "user_id")
    private Set<AuthorityRef> authorities = new HashSet<>();
}
````

Como siguiente paso creamos la interfaz para la entidad **Authority** donde definimos un método personalizado con la
anotación `@Query`, ese método busca todos los **authorities** que pertenecen a un usuario:

````java
public interface IAuthorityRepository extends ListCrudRepository<Authority, Integer> {
    @Query(value = """
            SELECT a.*
            FROM authorities AS a
                JOIN users_authorities AS ua ON(a.id = ua.authority_id)
            WHERE ua.user_id = :id
            """)
    List<Authority> findAllAuthoritiesByUserId(@Param("id") Integer id);
}
````

A la interfaz **IUserRepository** le agregamos un método personalizado que nos devolverá todos los **users** que
tienen asignado un **authority** determinado:

````java
public interface IUserRepository extends ListCrudRepository<User, Integer>,
        ListPagingAndSortingRepository<User, Integer> {
    @Query(value = """
            SELECT u.*
            FROM users AS u
                JOIN users_authorities AS ua ON(u.id = ua.user_id)
            WHERE ua.authority_id = :id
            """)
    List<User> findAllUsersByAuthority(@Param("id") Integer id);
}
````

Creamos los DTO's que serán los objetos expuestos por los endpoints, además con esos objetos estableceremos la
asociación de muchos a muchos, donde:

- Un **user** tiene Muchos **authorities**

  ````java
  public record UserDetails(User user, List<Authority> authorities) {
  }
  ````

- Un **authority** tiene muchos **users**:
  ````java
  public record AuthorityDetails(Authority authority, List<User> users) {
  }
  ````

Creamos el servicio **AuthorityService** donde definimos un método que nos retornará un **AuthorityDetails**:

````java

@RequiredArgsConstructor
@Service
public class AuthorityService {

    private final IAuthorityRepository authorityRepository;
    private final IUserRepository userRepository;

    public Optional<AuthorityDetails> getAuthorityDetails(Integer id) {
        return this.authorityRepository.findById(id)
                .map(authorityDB -> {
                    List<User> usersDB = this.userRepository.findAllUsersByAuthority(id);
                    return new AuthorityDetails(authorityDB, usersDB);
                });
    }
}
````

Anteriormente, ya habíamos creado nuestra clase de servicio **UserService**, lo que haremos será agregarle un método
que nos retornará un **UserDetails**:

````java

@RequiredArgsConstructor
@Service
public class UserService {

    private final IUserRepository userRepository;
    private final IAuthorityRepository authorityRepository;

    /* list method */

    @Transactional(readOnly = true)
    public Optional<UserDetails> getUserDetails(Integer id) {
        return this.userRepository.findById(id)
                .map(userDB -> {
                    List<Authority> authoritiesDB = this.authorityRepository.findAllAuthoritiesByUserId(id);
                    return new UserDetails(userDB, authoritiesDB);
                });
    }
}
````

Finalmente creamos el controlador **AuthorityController**:

````java

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/authorities")
public class AuthorityController {

    private final AuthorityService authorityService;

    @GetMapping(path = "/{id}/details")
    public ResponseEntity<AuthorityDetails> getAuthorityDetails(@PathVariable Integer id) {
        return this.authorityService.getAuthorityDetails(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
````

Y ahora el controlador **UserController** le agregamos un método adicional:

````java

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/users")
public class UserController {
    private final UserService userService;

    /* endpoint list */

    @GetMapping(path = "/{id}/details")
    public ResponseEntity<UserDetails> getUserDetails(@PathVariable Integer id) {
        return this.userService.getUserDetails(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
````

Consultamos por los **detalles de un usuario**:

````bash
curl -v http://localhost:8080/api/v1/users/4/details | jq

--- Respuesta
>
< HTTP/1.1 200
< Content-Type: application/json
<
{
  "user": {
    "id": 4,
    "username": "user",
    "password": "123456",
    "accountNonExpired": true,
    "accountNonLocked": true,
    "credentialsNonExpired": true,
    "enabled": true,
    "firstName": "Clara",
    "lastName": "Díaz",
    "emailAddress": "clara@gmail.com",
    "birthdate": "1998-07-28",
    "authorities": [
      {
        "authorityId": 3
      },
      {
        "authorityId": 5
      }
    ]
  },
  "authorities": [
    {
      "id": 3,
      "authority": "ROLE_USER"
    },
    {
      "id": 5,
      "authority": "ROLE_ADMIN"
    }
  ]
}
````

En el resultado observamos que nos retorna información del usuario y además los authorities que tiene dicho usuario.
Observemos también que el objeto **user** nos retorna como parte de su información un arreglo de **authorities** que
presenta solo los identificadores del **Authority** y eso ocurre precisamente porque usamos la estrategia de la
clase de Referencia para poder trabajar de algún modo la relación de `Many-to-Many`.

Las consultas generadas:

````roomsql
SELECT `users`.`id` AS `id`, `users`.`enabled` AS `enabled`, `users`.`last_name` AS `last_name`, `users`.`username` AS `username`, `users`.`password` AS `password`, `users`.`birthdate` AS `birthdate`, `users`.`first_name` AS `first_name`, `users`.`email_address` AS `email_address`, `users`.`account_non_locked` AS `account_non_locked`, `users`.`account_non_expired` AS `account_non_expired`, `users`.`credentials_non_expired` AS `credentials_non_expired` 
FROM `users` 
WHERE `users`.`id` = ?
--parameter value [4], value class [java.lang.Integer]
````

````roomsql
SELECT `users_authorities`.`authority_id` AS `authority_id` 
FROM `users_authorities` 
WHERE `users_authorities`.`user_id` = ?
--parameter value [4], value class [java.lang.Integer]
````

````roomsql
SELECT a.*
FROM authorities AS a
    JOIN users_authorities AS ua ON(a.id = ua.authority_id)
WHERE ua.user_id = ?
--parameter value [4], value class [java.lang.Integer]
````

Consultamos por los **detalles de un authority**:

````bash
curl -v http://localhost:8080/api/v1/authorities/3/details | jq

--- Respuesta
>
< HTTP/1.1 200
< Content-Type: application/json
<
{
  "authority": {
    "id": 3,
    "authority": "ROLE_USER"
  },
  "users": [
    {
      "id": 2,
      "username": "admin",
      "password": "123456",
      "accountNonExpired": true,
      "accountNonLocked": true,
      "credentialsNonExpired": true,
      "enabled": true,
      "firstName": "Martín",
      "lastName": "Díaz",
      "emailAddress": "martin@gmail.com",
      "birthdate": "2000-01-15",
      "authorities": [
        {
          "authorityId": 3
        },
        {
          "authorityId": 5
        },
        {
          "authorityId": 7
        }
      ]
    },
    {
      "id": 4,
      "username": "user",
      "password": "123456",
      "accountNonExpired": true,
      "accountNonLocked": true,
      "credentialsNonExpired": true,
      "enabled": true,
      "firstName": "Clara",
      "lastName": "Díaz",
      "emailAddress": "clara@gmail.com",
      "birthdate": "1998-07-28",
      "authorities": [
        {
          "authorityId": 3
        },
        {
          "authorityId": 5
        }
      ]
    }
  ]
}
````

En la consulta anterior vemos todos los usuarios que tiene el `authority con id = 3`. Es preciso notar nuevamente que
la información de los usuarios viene con un arreglo de authorities conteniendo solo el identificador. Como ya lo
mencioné anteriormente esto se debe a la estrategia que usamos para poder solventar la relación de muchos a muchos
utilizando una clase de referencia **AuthorityRef**.

Las consultas generadas:

````roomsql
SELECT `authorities`.`id` AS `id`, `authorities`.`authority` AS `authority` 
FROM `authorities` 
WHERE `authorities`.`id` = ?
--parameter value [3], value class [java.lang.Integer]
````

````roomsql
SELECT u.*
FROM users AS u
    JOIN users_authorities AS ua ON(u.id = ua.user_id)
WHERE ua.authority_id = ?
--parameter value [3], value class [java.lang.Integer]
````

````roomsql
SELECT `users_authorities`.`authority_id` AS `authority_id` 
FROM `users_authorities` 
WHERE `users_authorities`.`user_id` = ?
--parameter value [2], value class [java.lang.Integer]
````

````roomsql
SELECT `users_authorities`.`authority_id` AS `authority_id` 
FROM `users_authorities` 
WHERE `users_authorities`.`user_id` = ?
--parameter value [4], value class [java.lang.Integer]
````

Listo, con eso habríamos finalizado la relación de muchos a muchos usando Spring Data Jdbc. Ahora, podemos evitar de que
nos mande información de la entidad **AuthorityRef**, ya que no nos sirve de mucho, al menos en la parte de la recepción
de la información pues solo nos envía el **authorityId** de los authorities, pero sí es muy importante para poder
emular la relación de muchos a muchos. Entonces, para no mostrar dicha información podemos usar la anotación
`@JsonIgnore` de esa manera dicha información ya no se serializará.

````java

@Data
@Builder
@Table(name = "users")
public class User {
    @Id
    private Integer id;
    private String username;
    private String password;
    @Column(value = "account_non_expired")
    private Boolean accountNonExpired;
    private Boolean accountNonLocked;
    private Boolean credentialsNonExpired;
    private Boolean enabled;
    private String firstName;
    private String lastName;
    private String emailAddress;
    private LocalDate birthdate;

    @JsonIgnore //<-- Agregando la anotación que evita que esta propiedad authorities se envíe al endpoint
    @MappedCollection(idColumn = "user_id")
    private Set<AuthorityRef> authorities = new HashSet<>();
}
````

Consultando por un usuario:

````bash
curl -v http://localhost:8080/api/v1/users/4/details | jq

--- Respuesta
>
< HTTP/1.1 200
< Content-Type: application/json
<
{
  "user": {
    "id": 4,
    "username": "user",
    "password": "123456",
    "accountNonExpired": true,
    "accountNonLocked": true,
    "credentialsNonExpired": true,
    "enabled": true,
    "firstName": "Clara",
    "lastName": "Díaz",
    "emailAddress": "clara@gmail.com",
    "birthdate": "1998-07-28"
  },
  "authorities": [
    {
      "id": 3,
      "authority": "ROLE_USER"
    },
    {
      "id": 5,
      "authority": "ROLE_ADMIN"
    }
  ]
}
````

Consultando por un authority:

````bash
curl -v http://localhost:8080/api/v1/authorities/3/details | jq

--- Respuesta
>
< HTTP/1.1 200
< Content-Type: application/json
<
{
  "authority": {
    "id": 3,
    "authority": "ROLE_USER"
  },
  "users": [
    {
      "id": 2,
      "username": "admin",
      "password": "123456",
      "accountNonExpired": true,
      "accountNonLocked": true,
      "credentialsNonExpired": true,
      "enabled": true,
      "firstName": "Martín",
      "lastName": "Díaz",
      "emailAddress": "martin@gmail.com",
      "birthdate": "2000-01-15"
    },
    {
      "id": 4,
      "username": "user",
      "password": "123456",
      "accountNonExpired": true,
      "accountNonLocked": true,
      "credentialsNonExpired": true,
      "enabled": true,
      "firstName": "Clara",
      "lastName": "Díaz",
      "emailAddress": "clara@gmail.com",
      "birthdate": "1998-07-28"
    }
  ]
}
````

Listo, ahora sí vemos las respuestas más limpias que antes, pues con la anotación `@JsonIgnore` ignoramos la propiedad
sobre la que se anota evitando enviarnos información que anteriormente nos envíaba, me refiero a información como la
que se muestra a continuación:

````bash
"authorities": [
      {
        "authorityId": 3
      },
      {
        "authorityId": 5
      }
]
````

---

# Sección: Spring Data JDBC - CRUD

---

## CRUD API de una sola entidad

Crearemos un CRUD API completo de la entidad **Tutorial**, para eso comenzamos creando su tabla correspondiente en la
base de datos y poblándola con datos de prueba:

````sql
DROP TABLE IF EXISTS tutorials;

CREATE TABLE tutorials(
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(100) NOT NULL
);
````

````sql
INSERT INTO tutorials(title) VALUES('Spring Data JDBC');
INSERT INTO tutorials(title) VALUES('Spring Data JPA');
INSERT INTO tutorials(title) VALUES('Spring Boot 3');
INSERT INTO tutorials(title) VALUES('Microservicios con Eureka');
INSERT INTO tutorials(title) VALUES('Curso de Docker');
````

Creamos la interfaz para la entidad Tutorial:

````java
public interface ITutorialRepository extends ListCrudRepository<Tutorial, Integer> {

}
````

Creamos el servicio implementando el crud:

````java

@RequiredArgsConstructor
@Service
public class TutorialService {

    private final ITutorialRepository tutorialRepository;

    @Transactional(readOnly = true)
    public List<Tutorial> getAllTutorials() {
        return this.tutorialRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Tutorial> getTutorial(Integer id) {
        return this.tutorialRepository.findById(id);
    }

    @Transactional
    public Tutorial createTutorial(Tutorial tutorial) {
        return this.tutorialRepository.save(tutorial);
    }

    @Transactional
    public List<Tutorial> createListTutorials(List<Tutorial> tutorials) {
        return this.tutorialRepository.saveAll(tutorials);
    }

    @Transactional
    public Optional<Tutorial> updateTutorial(Integer id, Tutorial tutorial) {
        return this.tutorialRepository.findById(id)
                .map(tutorialDB -> {
                    tutorialDB.setTitle(tutorial.getTitle());
                    return this.tutorialRepository.save(tutorialDB);
                });
    }

    @Transactional
    public Optional<Tutorial> patchTutorial(Integer id, Tutorial tutorial) {
        return this.tutorialRepository.findById(id)
                .map(tutorialDB -> {
                    tutorialDB.setTitle(tutorial.getTitle());
                    return this.tutorialRepository.save(tutorialDB);
                });
    }

    @Transactional
    public Optional<Boolean> deleteTutorial(Integer id) {
        return this.tutorialRepository.findById(id)
                .map(tutorialDB -> {
                    this.tutorialRepository.deleteById(id);
                    return true;
                });
    }

    @Transactional
    public void deleteAllTutorials() {
        this.tutorialRepository.deleteAll();
    }
}
````

Finalmente, implementamos la clase de controlador:

````java

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/tutorials")
public class TutorialController {

    private final TutorialService tutorialService;

    @GetMapping
    public ResponseEntity<List<Tutorial>> getTutorials() {
        return ResponseEntity.ok(this.tutorialService.getAllTutorials());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Tutorial> getTutorial(@PathVariable Integer id) {
        return this.tutorialService.getTutorial(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Tutorial> createTutorial(@RequestBody Tutorial tutorial) {
        Tutorial tutorialDB = this.tutorialService.createTutorial(tutorial);
        URI uriTutorial = URI.create("/api/v1/tutorials/" + tutorialDB.getId());
        return ResponseEntity.created(uriTutorial).body(tutorialDB);
    }

    @PostMapping(path = "/all")
    public ResponseEntity<List<Tutorial>> createListTutorials(@RequestBody List<Tutorial> tutorials) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(this.tutorialService.createListTutorials(tutorials));
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<Tutorial> updateTutorial(@PathVariable Integer id, @RequestBody Tutorial tutorial) {
        return this.tutorialService.updateTutorial(id, tutorial)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PatchMapping(path = "/{id}")
    public ResponseEntity<Tutorial> patchTutorial(@PathVariable Integer id, @RequestBody Tutorial tutorial) {
        return this.tutorialService.patchTutorial(id, tutorial)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteTutorial(@PathVariable Integer id) {
        return this.tutorialService.deleteTutorial(id)
                .map(wasDeleted -> new ResponseEntity<Void>(HttpStatus.NO_CONTENT))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAllTutorials() {
        this.tutorialService.deleteAllTutorials();
        return ResponseEntity.noContent().build();
    }
}
````

Realizamos las peticiones desde nuestro cliente curl:

- Lista todos los tutoriales:

````bash
curl -v http://localhost:8080/api/v1/tutorials | jq

--- Respuesta
< HTTP/1.1 200
<
[
  {
    "id": 1,
    "title": "Spring Data JDBC"
  },
  {
    "id": 2,
    "title": "Spring Data JPA"
  },
  {
    "id": 3,
    "title": "Spring Boot 3"
  },
  {
    "id": 4,
    "title": "Microservicios con Eureka"
  },
  {
    "id": 5,
    "title": "Curso de Docker"
  }
]
````

````roomsql
SELECT `tutorials`.`id` AS `id`, `tutorials`.`title` AS `title` 
FROM `tutorials`
````

- Ver un tutorial en específico:

````bash
 curl -v http://localhost:8080/api/v1/tutorials/4 | jq
 
--- Respuesta
>
< HTTP/1.1 200
<
{
  "id": 4,
  "title": "Microservicios con Eureka"
}
````

````roomsql
SELECT `tutorials`.`id` AS `id`, `tutorials`.`title` AS `title` 
FROM `tutorials` 
WHERE `tutorials`.`id` = ?
--Setting SQL statement parameter value: column index 1, parameter value [4], value class [java.lang.Integer], SQL type 4
````

- Guardar un tutorial

````bash
curl -v -X POST -H "Content-Type: application/json" -d "{\"title\": \"Curso de Angular\"}" http://localhost:8080/api/v1/tutorials | jq

--- Respuesta
>
< HTTP/1.1 201
< Location: /api/v1/tutorials/6
<
{
  "id": 6,
  "title": "Curso de Angular"
}
````

````roomsql
INSERT INTO `tutorials` (`title`) VALUES (?)
--Setting SQL statement parameter value: column index 1, parameter value [Curso de Angular], value class [java.lang.String], SQL type 12
````

- Guardar una lista de tutoriales

````bash
curl -v -X POST -H "Content-Type: application/json" -d "[{\"title\": \"Curso de Angular\"}, {\"title\": \"Curso de TypeScript\"}, {\"title\": \"Kubernetes\"}]" http://localhost:8080/api/v1/tutorials/all | jq

--- Respuesta
< HTTP/1.1 201
< Content-Type: application/json
<
[
  {
    "id": 6,
    "title": "Curso de Angular"
  },
  {
    "id": 7,
    "title": "Curso de TypeScript"
  },
  {
    "id": 8,
    "title": "Kubernetes"
  }
]
````

````roomsql
INSERT INTO `tutorials` (`title`) VALUES (?)
-- Setting SQL statement parameter value: column index 1, parameter value [Curso de Angular], value class [java.lang.String], SQL type 12
-- Setting SQL statement parameter value: column index 1, parameter value [Curso de Angular], value class [java.lang.String], SQL type 12
-- Setting SQL statement parameter value: column index 1, parameter value [Curso de TypeScript], value class [java.lang.String], SQL type 12
--  Setting SQL statement parameter value: column index 1, parameter value [Kubernetes], value class [java.lang.String], SQL type 12
````

- Actualizar todos los datos de un tutorial

````bash
curl -v -X PUT -H "Content-Type: application/json" -d "{\"title\": \"Curso SASS\"}" http://localhost:8080/api/v1/tutorials/7 | jq

--- Respuesta
>
} [23 bytes data]
< HTTP/1.1 200
<
{
  "id": 7,
  "title": "Curso SASS"
}
````

````roomsql
SELECT `tutorials`.`id` AS `id`, `tutorials`.`title` AS `title` 
FROM `tutorials` 
WHERE `tutorials`.`id` = ?
-- Setting SQL statement parameter value: column index 1, parameter value [7], value class [java.lang.Integer], SQL type 4


````

````roomsql
UPDATE `tutorials` 
    SET `title` = ? 
WHERE `tutorials`.`id` = ?
-- Setting SQL statement parameter value: column index 1, parameter value [Curso SASS], value class [java.lang.String], SQL type 12
-- Setting SQL statement parameter value: column index 2, parameter value [7], value class [java.lang.Integer], SQL type 4
````

- Actualizar algunos datos de un tutorial

````bash
curl -v -X PATCH -H "Content-Type: application/json" -d "{\"title\": \"Angular v16 Signals\"}" http://localhost:8080/api/v1/tutorials/6 | jq

--- Respuesta
>
< HTTP/1.1 200
<
{
  "id": 6,
  "title": "Angular v16 Signals"
}
````

````roomsql
SELECT `tutorials`.`id` AS `id`, `tutorials`.`title` AS `title` 
FROM `tutorials` 
WHERE `tutorials`.`id` = ?
-- Setting SQL statement parameter value: column index 1, parameter value [6], value class [java.lang.Integer], SQL type 4
````

````roomsql
UPDATE `tutorials` 
    SET `title` = ? 
WHERE `tutorials`.`id` = ?
-- Setting SQL statement parameter value: column index 1, parameter value [Angular v16 Signals], value class [java.lang.String], SQL type 12
-- Setting SQL statement parameter value: column index 2, parameter value [6], value class [java.lang.Integer], SQL type 4
````

- Elimina un tutorial

````bash
curl -v -X DELETE http://localhost:8080/api/v1/tutorials/6 | jq

--- Respuesta
>
< HTTP/1.1 204
````

````roomsql
SELECT `tutorials`.`id` AS `id`, `tutorials`.`title` AS `title` 
FROM `tutorials` 
WHERE `tutorials`.`id` = ?
-- Setting SQL statement parameter value: column index 1, parameter value [6], value class [java.lang.Integer], SQL type 4
````

````roomsql
DELETE FROM `tutorials` 
WHERE `tutorials`.`id` = ?
-- Setting SQL statement parameter value: column index 1, parameter value [6], value class [java.lang.Integer], SQL type 4
````

- Elimina todos los tutoriales

````bash
curl -v -X DELETE http://localhost:8080/api/v1/tutorials | jq

--- Respuesta
>
< HTTP/1.1 204
<
````

````roomsql
DELETE FROM `tutorials`
````

## CRUD API Relación One-to-One entre owners y addresses

En este apartado trabajaremos sobre las tablas `owners` y `addresses` de la base de datos que están relacionados de
`One-to-One`. Recordemos que anteriormente ya habíamos trabajado estas tablas quienes están mapeadas a las entidades
`Owner` y `Address` respectivamente. Veamos cómo tenemos las tablas a nivel de base de datos:

![owners y addresses relación one to one](./assets/owners-addresses-one-to-one.png)

Como observamos no solo tenemos la relación de `One-to-One` entre las tablas `owners` y `addresses`, también está la
tabla `tasks` relacionándose con la tabla `owners`, aunque en este apartado no trabajamos con la tabla `tasks` es
importante considerarlo en el diagrama, solo para tener una idea visual, ya que puede influir cuando se haga
eliminaciones de los `ownres` de esa manera sabremos por qué podría estar fallando la eliminación cuando nos lance un
error de **constraint**.

A continuación se muestra la implementación completa de la clase de servicio **OwnerService**. Recordemos que
anteriormente ya habíamos trabajado con estas entidades, pero solo para la lectura de los registros:

````java

@RequiredArgsConstructor
@Service
public class OwnerService {
    private final IOwnerRepository ownerRepository;

    @Transactional(readOnly = true)
    public List<Owner> getAllOwners() {
        return this.ownerRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Owner> getOwner(Integer id) {
        return this.ownerRepository.findById(id);
    }

    @Transactional
    public Owner createOwner(Owner owner) {
        return this.ownerRepository.save(owner);
    }

    @Transactional
    public List<Owner> createListOwners(List<Owner> owner) {
        return this.ownerRepository.saveAll(owner);
    }

    @Transactional
    public Optional<Owner> updateOwner(Integer id, Owner owner) {
        return this.ownerRepository.findById(id)
                .map(ownerDB -> {
                    ownerDB.setFullName(owner.getFullName());
                    ownerDB.setEmail(owner.getEmail());
                    ownerDB.setUsername(owner.getUsername());
                    ownerDB.setAddress(owner.getAddress());
                    return this.ownerRepository.save(ownerDB);
                });
    }

    @Transactional
    public Optional<Owner> patchOwner(Integer id, Owner owner) {
        return this.ownerRepository.findById(id)
                .map(ownerDB -> {
                    ownerDB.setEmail(owner.getEmail());
                    return this.ownerRepository.save(ownerDB);
                });
    }

    @Transactional
    public Optional<Boolean> deleteOwner(Integer id) {
        return this.ownerRepository.findById(id)
                .map(ownerDB -> {
                    this.ownerRepository.deleteById(id);
                    return true;
                });
    }

    @Transactional
    public void deleteAllOwners() {
        this.ownerRepository.deleteAll();
    }
}
````

De la misma manera, se muestra a continuación la implementación completa del controlador **OwnerController**:

````java

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/owners")
public class OwnerController {

    private final OwnerService ownerService;
    private final TaskService taskService;

    @GetMapping
    public ResponseEntity<List<Owner>> getOwners() {
        return ResponseEntity.ok(this.ownerService.getAllOwners());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Owner> getOwner(@PathVariable Integer id) {
        return this.ownerService.getOwner(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping(path = "/{id}/details")
    public ResponseEntity<OwnerDetails> getOwnerDetails(@PathVariable Integer id) {
        return this.ownerService.getOwner(id)
                .map(ownerDB -> {
                    List<Task> tasks = this.taskService.findAllByOwner(id);
                    return ResponseEntity.ok(new OwnerDetails(ownerDB, tasks));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Owner> createOwner(@RequestBody Owner owner) {
        Owner ownerDB = this.ownerService.createOwner(owner);
        URI uriOwner = URI.create("/api/v1/owners/" + ownerDB.getId());
        return ResponseEntity.created(uriOwner).body(ownerDB);
    }

    @PostMapping(path = "/all")
    public ResponseEntity<List<Owner>> createListOwners(@RequestBody List<Owner> owners) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(this.ownerService.createListOwners(owners));
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<Owner> updateOwner(@PathVariable Integer id, @RequestBody Owner owner) {
        return this.ownerService.updateOwner(id, owner)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PatchMapping(path = "/{id}")
    public ResponseEntity<Owner> patchOwner(@PathVariable Integer id, @RequestBody Owner owner) {
        return this.ownerService.patchOwner(id, owner)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteOwner(@PathVariable Integer id) {
        return this.ownerService.deleteOwner(id)
                .map(wasDeleted -> new ResponseEntity<Void>(HttpStatus.NO_CONTENT))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAllOwners() {
        this.ownerService.deleteAllOwners();
        return ResponseEntity.noContent().build();
    }

}
````

Probamos los endpoints implementados:

- Crear un owner:

````bash
curl -v -X POST -H "Content-Type: application/json" -d "{\"fullName\": \"Rosa Maria\", \"email\":\"rosa@gmail.com\", \"username\":\"rosita\", \"address\": {\"addressLine\": \"Cajamarca City\"}}" http://localhost:8080/api/v1/owners | jq

>
< HTTP/1.1 201
< Location: /api/v1/owners/21
< Content-Type: application/json
<
{
  "id": 21,
  "fullName": "Rosa Maria",
  "email": "rosa@gmail.com",
  "username": "rosita",
  "address": {
    "id": 3,
    "addressLine": "Cajamarca City"
  }
}
````

````roomsql
INSERT INTO `owners` (`email`, `full_name`, `username`) VALUES (?, ?, ?)
--column index 1, parameter value [rosa@gmail.com], value class [java.lang.String]
--column index 2, parameter value [Rosa Maria], value class [java.lang.String]
--column index 3, parameter value [rosita], value class [java.lang.String]
````

````roomsql
INSERT INTO `addresses` (`address_line`, `owner_id`) VALUES (?, ?)
--column index 1, parameter value [Cajamarca City], value class [java.lang.String]
--column index 2, parameter value [21], value class [java.lang.Integer]
````

- Crear una lista de owners:

````bash
curl -v -X POST -H "Content-Type: application/json" -d "[{\"fullName\": \"Rosa Maria\", \"email\":\"rosa@gmail.com\", \"username\":\"rosita\", \"address\": {\"addressLine\": \"Cajamarca City\"}}, {\"fullName\": \"Rosa Milagros\", \"email\":\"rosilla@gmail.com\", \"username\":\"rosi\", \"address\": {\"addressLine\": \"Chimbote\"}}]" http://localhost:8080/api/v1/owners/all | jq

>
< HTTP/1.1 201
< Content-Type: application/json
<
[
  {
    "id": 22,
    "fullName": "Rosa Maria",
    "email": "rosa@gmail.com",
    "username": "rosita",
    "address": {
      "id": 4,
      "addressLine": "Cajamarca City"
    }
  },
  {
    "id": 23,
    "fullName": "Rosa Milagros",
    "email": "rosilla@gmail.com",
    "username": "rosi",
    "address": {
      "id": 5,
      "addressLine": "Chimbote"
    }
  }
]
````

- Actualizar un owner:

````bash
curl -v -X PUT -H "Content-Type: application/json" -d "{\"fullName\": \"Vicky\", \"email\":\"vicky@gmail.com\", \"username\":\"vicky\", \"address\": {\"addressLine\": \"Espana\"}}" http://localhost:8080/api/v1/owners/22 | jq

>
< HTTP/1.1 200
< Content-Type: application/json
<
{
  "id": 22,
  "fullName": "Vicky",
  "email": "vicky@gmail.com",
  "username": "vicky",
  "address": {
    "id": 6,
    "addressLine": "Espana"
  }
}
````

- Actualizar parcialmente un dato del owner:

````bash
curl -v -X PATCH -H "Content-Type: application/json" -d "{\"email\":\"vicky_es@gmail.com\"}" http://localhost:8080/api/v1/owners/22 | jq

>
< HTTP/1.1 200
< Content-Type: application/json
<
{
  "id": 22,
  "fullName": "Vicky",
  "email": "vicky_es@gmail.com",
  "username": "vicky",
  "address": {
    "id": 6,
    "addressLine": "Espana"
  }
}
````

- Eliminar un owner

````bash
curl -v -X DELETE http://localhost:8080/api/v1/owners/22 | jq

>
< HTTP/1.1 204
````

````roomsql
SELECT `owners`.`id` AS `id`, `owners`.`email` AS `email`, `owners`.`full_name` AS `full_name`, `owners`.`username` AS `username`, `address`.`id` AS `address_id`, `address`.`address_line` AS `address_address_line` 
FROM `owners` 
    LEFT OUTER JOIN `addresses` `address` ON `address`.`owner_id` = `owners`.`id` 
WHERE `owners`.`id` = ?
--column index 1, parameter value [22], value class [java.lang.Integer]
````

````sql
SELECT `owners`.`id` FROM `owners` WHERE `owners`.`id` = ? FOR UPDATE
column index 1, parameter value [22], value class [java.lang.Integer]
````

````roomsql
DELETE FROM `addresses` WHERE `addresses`.`owner_id` = ?
--column index 1, parameter value [22], value class [java.lang.Integer]
````

````roomsql
DELETE FROM `owners` WHERE `owners`.`id` = ?
--column index 1, parameter value [22], value class [java.lang.Integer]
````

- Eliminar todos los owners

````bash
curl -v -X DELETE http://localhost:8080/api/v1/owners | jq

>
< HTTP/1.1 500
<
{
  "timestamp": "2023-08-28T18:25:30.663+00:00",
  "status": 500,
  "error": "Internal Server Error",
  "path": "/api/v1/owners"
}
````

````bash
java.sql.SQLIntegrityConstraintViolationException: 
Cannot delete or update a parent row: 
a foreign key constraint fails (`db_spring_data_jdbc`.`tasks`, 
  CONSTRAINT `fk_owners_tasks` FOREIGN KEY (`owner_id`) REFERENCES `owners` (`id`))
````

Como observamos, la eliminación de todos los owners nos está fallando, eso ocurre porque en la tercera tabla relacionada
con `owners`, me refiero a la tabla `tasks` tiene referencias (FK) a esa tabla, por lo tanto, ocurre el error de
constraint, no podemos eliminar registros de la tabla `owners` mientras la tabla `tasks` las tenga referenciadas.
Pero en sí, el endpoint funcionaría si no hubiera referencias de owners en tasks.

## CRUD API Relación One-to-Many entre owners y tasks

En este apartado veremos cómo crear registros en la base de datos cuando dos entidades son **aggregate roots** y tienen
una relación de `One-to-Many`. Recordemos un poco, tenemos la entidad `Owner` y `Task`, ambos son `aggregate roots` de
sus respectivos agregados y lo que hicimos para poder relacionar ambos agregados en una relación del tipo `One-to-Many`,
fue utilizar la interfaz `AggregateReference` dentro de `Task`, luego cuando consultábamos directamente por la lista
de tasks o un task en específico, lo que obteníamos era información del **task**, información del **comment** y ahora
información del **owner id**, por ejemplo, algo similar a cómo se muestra a continuación:

````json
{
  "id": 30,
  "title": "Pintado fachada",
  "content": "Trabajamos para remodelar fachada",
  "publishedOn": "2023-08-28T19:58:39",
  "updatedOn": "2023-08-28T19:58:39",
  "comments": [
    {
      "id": 4,
      "name": "Karen",
      "content": "Colores suaves sería genial",
      "publishedOn": "2023-08-28T19:58:39",
      "updatedOn": "2023-08-28T19:58:39"
    }
  ],
  "owner": {
    "id": 10
  }
}
````

Recordemos también que decíamos que obtener el **owner id** era suficiente en algunos casos, pero luego nosotros
creamos DTO's para poder obtener la información completa de ambos agregados.

### Creando un Task

Entonces, **¿Cómo podemos guardar un registro de un Task si está relacionado con un Owner?** lo primero que debemos
hacer es crear un DTO llamado **TaskCreate** con el que enviaremos información desde el cliente y luego utilizando la
clase de servicio crearemos un objeto Task para poder persistirlo, veamos cómo:

````java
public record TaskCreate(Integer ownerId, String title, String content) {
}
````

El record anterior contiene información que será pasada desde el cliente, aunque falta información como el
**publishedOn**, **updatedOn**, **comments**. No nos preocupemos por esa información, ya que será completada en la
clase de servicio. Con respecto a cómo agregar **comments**, eso lo veremos más adelante.

````java

@RequiredArgsConstructor
@Service
public class TaskService {

    /* other methods*/

    @Transactional
    public Task createTask(TaskCreate taskCreate) {
        Task newTask = Task.builder()
                .title(taskCreate.title())
                .content(taskCreate.content())
                .publishedOn(LocalDateTime.now())
                .owner(AggregateReference.to(taskCreate.ownerId())) // (1)
                .build();
        return this.taskRepository.save(newTask);
    }
}
````

Es importante observar **(1)** cómo creamos un `AggregateReference` el cual es pasado al atributo `owner` de la clase
Task, ese atributo hace referencia a la llave foránea `owner_id`.

````java

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/tasks")
public class TaskController {

    /* other code */

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody TaskCreate taskCreate) {
        Task taskDB = this.taskService.createTask(taskCreate);
        URI uriTask = URI.create("/api/v1/tasks/" + taskDB.getId());
        return ResponseEntity.created(uriTask).body(taskDB);
    }

}
````

Listo, hasta este punto probaremos crear el registro para un Task:

````bash
curl -v -X POST -H "Content-Type: application/json" -d "{\"title\": \"Crear maceteros\", \"content\": \"Tarea por completar\", \"ownerId\": 20}" http://localhost:8080/api/v1/tasks | jq


>
< HTTP/1.1 201
< Location: /api/v1/tasks/41
<
{
  "id": 41,
  "title": "Crear maceteros",
  "content": "Tarea por completar",
  "publishedOn": "2023-08-28T20:17:09.2533282",
  "updatedOn": null,
  "comments": null,
  "owner": {
    "id": 20
  }
}
````

Las consultas generadas en consola:

````roomsql
INSERT INTO `tasks` (`content`, `owner_id`, `published_on`, `title`, `updated_on`) VALUES (?, ?, ?, ?, ?)
--column index 1, parameter value [Tarea por completar], value class [java.lang.String]
--column index 2, parameter value [20], value class [java.lang.Integer]
--column index 3, parameter value [2023-08-28T20:17:09.253328200], value class [java.time.LocalDateTime]
--column index 4, parameter value [Crear maceteros], value class [java.lang.String]
--column index 5, parameter value [null], value class [null]
````

El registro fue exitoso, acabamos de registrar un `Task` para el `Owner` con id = 20.

### Registrando los comments para un Task

Como observamos en el resultado anterior, cuando creamos un registro para un Task lo creamos sin comentarios. En este
apartado veremos cómo agregar comentarios a un Task utilizando los métodos que creamos desde un inicio en nuestra
entidad `Task`, esos métodos son los que a continuación muestro:

````java

@Data
@Builder
@Table(name = "tasks")
public class Task {
    /* omitted code */
    public void addComment(Comment comment) {
        this.comments.add(comment);
    }

    public void removeComment(Comment comment) {
        this.comments.remove(comment);
    }
}
````

El siguiente paso sería crear un DTO llamado `CommentUpdate`, cuya información será enviada desde el cliente:

````java
public record CommentUpdate(String name, String content) {
}
````

Ahora, en la clase de servicio creamos un método que nos permita agregar un `Comment` a un `Task`:

````java

@RequiredArgsConstructor
@Service
public class TaskService {
    /* omitted code*/

    @Transactional
    public Optional<Task> taskAddComment(Integer id, CommentUpdate commentUpdate) {
        return this.taskRepository.findById(id)
                .map(taskDB -> {
                    Comment newComment = Comment.builder()
                            .name(commentUpdate.name())
                            .content(commentUpdate.content())
                            .publishedOn(LocalDateTime.now())
                            .build();
                    taskDB.addComment(newComment);
                    return this.taskRepository.save(taskDB);
                });
    }
}
````

Lo mismo hacemos en el controlador, creamos un endpoint que será exclusivo para poder agregar comentarios a un task en
específico:

````java

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/tasks")
public class TaskController {

    /* omitted code */

    @PostMapping(path = "/{id}/comment")
    public ResponseEntity<Task> taskAddComment(@PathVariable Integer id, @RequestBody CommentUpdate comment) {
        return this.taskService.taskAddComment(id, comment)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}
````

Finalmente, probamos agregar comentario a un Task:

````bash
curl -v -X POST -H "Content-Type: application/json" -d "{\"name\": \"verduras\", \"content\": \"Solo comprar verduras\"}" http://localhost:8080/api/v1/tasks/40/comment | jq

>
< HTTP/1.1 200
< Content-Type: application/json
<
{
  "id": 40,
  "title": "Compra mercado",
  "content": "Compras del mes",
  "publishedOn": "2023-08-28T22:47:02",
  "updatedOn": "2023-08-28T22:47:02",
  "comments": [
    {
      "id": 5,
      "name": "verduras",
      "content": "Solo comprar verduras",
      "publishedOn": "2023-08-28T22:47:32.852101",
      "updatedOn": null
    }
  ],
  "owner": {
    "id": 10
  }
}
````

Volvemos a insertar otro comentario:

````bash
curl -v -X POST -H "Content-Type: application/json" -d "{\"name\": \"Fiesta infantil\", \"content\": \"Comprar golosinas\"}" http://localhost:8080/api/v1/tasks/40/comment | jq


< HTTP/1.1 200
< Content-Type: application/json
<
{
  "id": 40,
  "title": "Compra mercado",
  "content": "Compras del mes",
  "publishedOn": "2023-08-28T22:47:02",
  "updatedOn": "2023-08-28T22:47:02",
  "comments": [
    {
      "id": 5,
      "name": "verduras",
      "content": "Solo comprar verduras",
      "publishedOn": "2023-08-28T22:47:33",
      "updatedOn": null
    },
    {
      "id": 6,
      "name": "Fiesta infantil",
      "content": "Comprar golosinas",
      "publishedOn": "2023-08-28T22:54:09.1338213",
      "updatedOn": null
    }
  ],
  "owner": {
    "id": 10
  }
````

### Actualizando un Task

Anteriormente, vimos cómo crear un **Task**, para eso nos apoyamos en el **record** `TaskCreate`. En este apartado
implementaremos la actualización de un **Task** apoyándonos nuevamente de otro **record** `TaskUpdate`:

````java
public record TaskCreate(Integer ownerId, String title, String content) {
}
````

En nuestra clase de servicio creamos un método para actualizar el task:

````java

@RequiredArgsConstructor
@Service
public class TaskService {

    /* omitted code */

    @Transactional
    public Optional<Task> updateTask(Integer id, TaskUpdate taskUpdate) {
        return this.taskRepository.findById(id)
                .map(taskDB -> {

                    taskDB.setTitle(taskUpdate.title());
                    taskDB.setContent(taskUpdate.content());
                    taskDB.setUpdatedOn(LocalDateTime.now());
                    taskDB.setOwner(AggregateReference.to(taskUpdate.ownerId()));
                    taskDB.setComments(taskUpdate.comments());

                    return this.taskRepository.save(taskDB);
                });
    }
}
````

Finalmente, en nuestra clase de controlador implementamos el endpoint para actualizar el task:

````java

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/tasks")
public class TaskController {
    /* omitted code */

    @PutMapping(path = "/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Integer id, @RequestBody TaskUpdate taskUpdate) {
        return this.taskService.updateTask(id, taskUpdate)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
````

Probamos el endpoint actualizando un task existente en la base de datos:

````bash
curl -v -X PUT -H "Content-Type: application/json" -d "{\"title\": \"Mi nuevo Task\", \"content\": \"Probando actualizacion\", \"ownerId\": 10, \"comments\": [{\"name\": \"Comentario 01\", \"content\": \"Mi comentario 01\", \"publishedOn\": \"2023-08-28T23:18:20\", \"updatedOn\": \"2023-08-28T23:18:20\"}, {\"name\": \"Comentario 02\", \"content\": \"Mi comentario 02\", \"publishedOn\": \"2023-08-28T22:18:20\", \"updatedOn\": \"2023-08-28T22:18:20\"}]}" http://localhost:8080/api/v1/tasks/40 | jq

>
< HTTP/1.1 200
< Content-Type: application/json
<
{
  "id": 40,
  "title": "Mi nuevo Task",
  "content": "Probando actualizacion",
  "publishedOn": "2023-08-29T00:11:21",
  "updatedOn": "2023-08-29T00:15:15.3550109",
  "comments": [
    {
      "id": 5,
      "name": "Comentario 01",
      "content": "Mi comentario 01",
      "publishedOn": "2023-08-28T23:18:20",
      "updatedOn": "2023-08-28T23:18:20"
    },
    {
      "id": 6,
      "name": "Comentario 02",
      "content": "Mi comentario 02",
      "publishedOn": "2023-08-28T22:18:20",
      "updatedOn": "2023-08-28T22:18:20"
    }
  ],
  "owner": {
    "id": 10
  }
}
````

### Eliminando un Task

Eliminar un Task es más sencillo, primero empecemos creando el método en la clase de servicio:

````java

@RequiredArgsConstructor
@Service
public class TaskService {

    /* omitted code */

    @Transactional
    public Optional<Boolean> deleteTask(Integer id) {
        return this.taskRepository.findById(id)
                .map(taskDB -> {
                    this.taskRepository.deleteById(id);
                    return true;
                });
    }
}
````

Ahora implementemos el endpoint para eliminar un task:

````java

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/tasks")
public class TaskController {

    /* omitted code */

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Integer id) {
        return this.taskService.deleteTask(id)
                .map(wasDeleted -> new ResponseEntity<Void>(HttpStatus.NO_CONTENT))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
````

Levantamos el proyecto y probamos la funcionalidad de eliminar un task:

````bash
curl -v -X DELETE http://localhost:8080/api/v1/tasks/40 | jq

>
< HTTP/1.1 204
````

## CRUD API Relación Many-to-Many entre users y authorities

Recordemos cómo tenemos relacionados las tablas `users` y `authorities` en la base de datos, vemos que le agregamos una
tabla intermedia en la relación:

![user-authorities-db](./assets/users_authorities-mysql.png)

### Trabajando con la entidad Authority

Observemos cómo tenemos nuestra clase de entidad `Authority`:

````java

@Data
@Builder
@Table(name = "authorities")
public class Authority {
    @Id
    private Integer id;
    private String authority;
}
````

Como vemos, nuestra entidad `Authority` se presta para crearle directamente su CRUD sin problema alguno, así que
empezaremos con la clase de servicio implementando sus métodos:

````java

@RequiredArgsConstructor
@Service
public class AuthorityService {

    /* omitted properties */

    @Transactional(readOnly = true)
    public List<Authority> getAuthorities() {
        return this.authorityRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Authority> getAuthority(Integer id) {
        return this.authorityRepository.findById(id);
    }

    @Transactional
    public Authority createAuthority(Authority authority) {
        return this.authorityRepository.save(authority);
    }

    @Transactional
    public Optional<Authority> updateAuthority(Integer id, Authority authority) {
        return this.authorityRepository.findById(id)
                .map(authorityDB -> {
                    authorityDB.setAuthority(authority.getAuthority());
                    return this.authorityRepository.save(authorityDB);
                });
    }

    @Transactional
    public Optional<Boolean> deleteAuthority(Integer id) {
        return this.authorityRepository.findById(id)
                .map(authorityDB -> {
                    this.authorityRepository.deleteById(authorityDB.getId());
                    return true;
                });
    }

    /* omitted method getAuthorityDetails(...) */
}
````

Llegados a este punto toca implementar los enpoints del CRUD en la clase controladora:

````java

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/authorities")
public class AuthorityController {

    /* omitted property */

    @GetMapping
    public ResponseEntity<List<Authority>> getAllAuthorities() {
        return ResponseEntity.ok(this.authorityService.getAuthorities());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Authority> getAuthority(@PathVariable Integer id) {
        return this.authorityService.getAuthority(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Authority> createAuthority(@RequestBody Authority authority) {
        Authority authorityDB = this.authorityService.createAuthority(authority);
        URI uriAuthority = URI.create("/api/v1/authorities" + authorityDB.getId());
        return ResponseEntity.created(uriAuthority).body(authorityDB);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<Authority> updateAuthority(@PathVariable Integer id, @RequestBody Authority authority) {
        return this.authorityService.updateAuthority(id, authority)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteAuthority(@PathVariable Integer id) {
        return this.authorityService.deleteAuthority(id)
                .map(wasDeleted -> new ResponseEntity<Void>(HttpStatus.NO_CONTENT))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /* omitted endpoint /{id}/details */
}
````

Probando CRUD para la tabla `authorities`:

- Listando los authorities:

````bash
curl -v http://localhost:8080/api/v1/authorities | jq

>
< HTTP/1.1 200
< Content-Type: application/json
<
[
  {
    "id": 3,
    "authority": "ROLE_USER"
  },
  {
    "id": 5,
    "authority": "ROLE_ADMIN"
  },
  {
    "id": 7,
    "authority": "ROLE_DEVELOPER"
  }
]
````

````roomsql
SELECT `authorities`.`id` AS `id`, `authorities`.`authority` AS `authority` 
FROM `authorities`
````

- Ver un authority:

````bash
curl -v http://localhost:8080/api/v1/authorities/3 | jq

>
< HTTP/1.1 200
< Content-Type: application/json
<
{
  "id": 3,
  "authority": "ROLE_USER"
}
````

````roomsql
SELECT `authorities`.`id` AS `id`, `authorities`.`authority` AS `authority` 
FROM `authorities` 
WHERE `authorities`.`id` = ?
--column index 1, parameter value [3], value class [java.lang.Integer]
````

- Guardar un authority:

````bash
curl -v -X POST -H "Content-Type: application/json" -d "{\"authority\": \"ROLE_TESTER\"}" http://localhost:8080/api/v1/authorities | jq

>
< HTTP/1.1 201
< Location: /api/v1/authorities8
< Content-Type: application/json
<
{
  "id": 8,
  "authority": "ROLE_TESTER"
}
````

````roomsql
INSERT INTO `authorities` (`authority`) VALUES (?)
---column index 1, parameter value [ROLE_TESTER], value class [java.lang.String]
````

- Actualizar un authority:

````bash
curl -v -X PUT -H "Content-Type: application/json" -d "{\"authority\": \"ROLE_TESTER_UPDATE\"}" http://localhost:8080/api/v1/authorities/8 | jq

>
< HTTP/1.1 200
< Content-Type: application/json
<
{
  "id": 8,
  "authority": "ROLE_TESTER_UPDATE"
}
````

````roomsql
SELECT `authorities`.`id` AS `id`, `authorities`.`authority` AS `authority` 
FROM `authorities` 
WHERE `authorities`.`id` = ?
--column index 1, parameter value [8], value class [java.lang.Integer]
````

````roomsql
UPDATE `authorities` 
SET `authority` = ? 
WHERE `authorities`.`id` = ?
--column index 1, parameter value [ROLE_TESTER_UPDATE], value class [java.lang.String]
--column index 2, parameter value [8], value class [java.lang.Integer]
````

- Eliminar un authority:

````bash
curl -v -X DELETE http://localhost:8080/api/v1/authorities/8 | jq

>
< HTTP/1.1 204
````

````roomsql
SELECT `authorities`.`id` AS `id`, `authorities`.`authority` AS `authority` 
FROM `authorities` 
WHERE `authorities`.`id` = ?
--column index 1, parameter value [8], value class [java.lang.Integer]
````

````roomsql
DELETE FROM `authorities` WHERE `authorities`.`id` = ?
--column index 1, parameter value [8], value class [java.lang.Integer]
````

**IMPORTANTE**
> En este punto, si se intenta eliminar un Authority que está siendo usando por la relación nos arrojará un error de
> constraint: `Cannot delete or update a parent row: a foreign key constraint fails
> (db_spring_data_jdbc.users_authorities, CONSTRAINT fk_authorities__users_authorities FOREIGN KEY (authority_id)
> REFERENCES authorities (id))`

### Trabajando con la entidad User

Veamos cómo está conformado nuestra entidad `User`, observaremos que le agregamos dos métodos para poder agregar
y eliminar authorities:

````java

@Data
@Builder
@Table(name = "users")
public class User {
    @Id
    private Integer id;
    private String username;
    private String password;
    @Column(value = "account_non_expired")
    private Boolean accountNonExpired;
    private Boolean accountNonLocked;
    private Boolean credentialsNonExpired;
    private Boolean enabled;
    private String firstName;
    private String lastName;
    private String emailAddress;
    private LocalDate birthdate;

    @JsonIgnore
    @MappedCollection(idColumn = "user_id")
    private Set<AuthorityRef> authorities = new HashSet<>();

    public void addAuthority(Authority authority) {
        AuthorityRef authorityRef = new AuthorityRef();
        authorityRef.setAuthorityId(authority.getId());
        this.authorities.add(authorityRef);
    }

    public void removeAuthority(Authority authority) {
        AuthorityRef authorityRef = new AuthorityRef();
        authorityRef.setAuthorityId(authority.getId());
        this.authorities.remove(authorityRef);
    }
}
````

El siguiente paso es definir los métodos restantes en el servicio `UserService`:

````java

@RequiredArgsConstructor
@Service
public class UserService {

    /* omitted code */

    @Transactional(readOnly = true)
    public Optional<User> getUser(Integer id) {
        return this.userRepository.findById(id);
    }

    @Transactional
    public User createUser(User user) {
        return this.userRepository.save(user);
    }

    @Transactional
    public Optional<User> updateUser(Integer id, User user) {
        return this.userRepository.findById(id)
                .map(userDB -> {
                    userDB.setUsername(user.getUsername());
                    userDB.setPassword(user.getPassword());
                    userDB.setAccountNonExpired(user.getAccountNonExpired());
                    userDB.setAccountNonLocked(user.getAccountNonLocked());
                    userDB.setCredentialsNonExpired(user.getCredentialsNonExpired());
                    userDB.setEnabled(user.getEnabled());
                    userDB.setFirstName(user.getFirstName());
                    userDB.setLastName(user.getLastName());
                    userDB.setEmailAddress(user.getEmailAddress());
                    userDB.setBirthdate(user.getBirthdate());
                    return this.userRepository.save(userDB);
                });
    }

    @Transactional
    public Optional<Boolean> deleteUser(Integer id) {
        return this.userRepository.findById(id)
                .map(userDB -> {
                    this.userRepository.deleteById(id);
                    return true;
                });
    }

    @Transactional
    public Optional<UserDetails> addAuthority(Integer id, Authority authority) {
        return this.userRepository.findById(id)
                .map(userDB -> {
                    userDB.addAuthority(authority);
                    User userSaved = this.userRepository.save(userDB);
                    List<Authority> authorities = this.authorityRepository.findAllAuthoritiesByUserId(userDB.getId());
                    return new UserDetails(userSaved, authorities);
                });
    }

    @Transactional
    public Optional<UserDetails> removeAuthority(Integer id, Authority authority) {
        return this.userRepository.findById(id)
                .map(userDB -> {
                    userDB.removeAuthority(authority);
                    User userSaved = this.userRepository.save(userDB);
                    List<Authority> authorities = this.authorityRepository.findAllAuthoritiesByUserId(userDB.getId());
                    return new UserDetails(userSaved, authorities);
                });
    }

    /* omitted code */
}
````

Es importante observar que cuando agregamos o eliminamos un Authority a un User lo que nos retorna el método es un
`UserDetails` conteniendo tanto el `User` como una lista de `Authority`.

Finalmente, queda implementar el controlador `UserController`:

````java

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/users")
public class UserController {

    /* omitted code */

    @GetMapping(path = "/{id}")
    public ResponseEntity<User> getUser(@PathVariable Integer id) {
        return this.userService.getUser(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User userDB = this.userService.createUser(user);
        URI uriUser = URI.create("/api/v1/users" + userDB.getId());
        return ResponseEntity.created(uriUser).body(userDB);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Integer id, @RequestBody User user) {
        return this.userService.updateUser(id, user)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        return this.userService.deleteUser(id)
                .map(wasDeleted -> new ResponseEntity<Void>(HttpStatus.NO_CONTENT))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping(path = "/{id}/add-authority")
    public ResponseEntity<UserDetails> addAuthority(@PathVariable Integer id, @RequestBody Authority authority) {
        return this.userService.addAuthority(id, authority)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping(path = "/{id}/remove-authority")
    public ResponseEntity<UserDetails> removeAuthority(@PathVariable Integer id, @RequestBody Authority authority) {
        return this.userService.removeAuthority(id, authority)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /* omitted code */
}
````

- Listando los usuarios (Ya lo habíamos trabajado en apartados anteriores):

````bash
curl -v http://localhost:8080/api/v1/users | jq

>
< HTTP/1.1 200
< Content-Type: application/json
<
[
  {
    "id": 2,
    "username": "admin",
    "password": "123456",
    "accountNonExpired": true,
    "accountNonLocked": true,
    "credentialsNonExpired": true,
    "enabled": true,
    "firstName": "Martín",
    "lastName": "Díaz",
    "emailAddress": "martin@gmail.com",
    "birthdate": "2000-01-15"
  },
  {
    "id": 4,
    "username": "user",
    "password": "123456",
    "accountNonExpired": true,
    "accountNonLocked": true,
    "credentialsNonExpired": true,
    "enabled": true,
    "firstName": "Clara",
    "lastName": "Díaz",
    "emailAddress": "clara@gmail.com",
    "birthdate": "1998-07-28"
  },
  {
    "id": 6,
    "username": "karen",
    "password": "123456",
    "accountNonExpired": true,
    "accountNonLocked": true,
    "credentialsNonExpired": true,
    "enabled": true,
    "firstName": "Karen",
    "lastName": "Díaz",
    "emailAddress": "karen@gmail.com",
    "birthdate": "2000-06-03"
  }
]
````

- Ver un usuario:

````bash
curl -v http://localhost:8080/api/v1/users/4 | jq

>
< HTTP/1.1 200
< Content-Type: application/json
<
{
  "id": 4,
  "username": "user",
  "password": "123456",
  "accountNonExpired": true,
  "accountNonLocked": true,
  "credentialsNonExpired": true,
  "enabled": true,
  "firstName": "Clara",
  "lastName": "Díaz",
  "emailAddress": "clara@gmail.com",
  "birthdate": "1998-07-28"
}
````

````roomsql
SELECT `users`.`id` AS `id`, `users`.`enabled` AS `enabled`, `users`.`last_name` AS `last_name`, `users`.`username` AS `username`, `users`.`password` AS `password`, `users`.`birthdate` AS `birthdate`, `users`.`first_name` AS `first_name`, `users`.`email_address` AS `email_address`, `users`.`account_non_locked` AS `account_non_locked`, `users`.`account_non_expired` AS `account_non_expired`, `users`.`credentials_non_expired` AS `credentials_non_expired` 
FROM `users` 
WHERE `users`.`id` = ?
--column index 1, parameter value [4], value class [java.lang.Integer]
````

````roomsql
SELECT `users_authorities`.`authority_id` AS `authority_id` 
FROM `users_authorities` 
WHERE `users_authorities`.`user_id` = ?
--column index 1, parameter value [4], value class [java.lang.Integer]
````

- Crear un usuario:

````bash
curl -v -X POST -H "Content-Type: application/json" -d "{\"username\": \"nophy\", \"password\": \"123456\", \"accountNonExpired\": true, \"accountNonLocked\": true, \"credentialsNonExpired\": true, \"enabled\": true, \"firstName\": \"Nophy\", \"lastName\": \"Diaz\", \"emailAddress\": \"nophy@gmail.com\", \"birthdate\": \"2023-01-01\"}" http://localhost:8080/api/v1/users | jq

>
} [248 bytes data]
< HTTP/1.1 201
< Location: /api/v1/users7
<
{
  "id": 7,
  "username": "nophy",
  "password": "123456",
  "accountNonExpired": true,
  "accountNonLocked": true,
  "credentialsNonExpired": true,
  "enabled": true,
  "firstName": "Nophy",
  "lastName": "Diaz",
  "emailAddress": "nophy@gmail.com",
  "birthdate": "2023-01-01"
}
````

````roomsql
INSERT INTO `users` (`account_non_expired`, `account_non_locked`, `birthdate`, `credentials_non_expired`, `email_address`, `enabled`, `first_name`, `last_name`, `password`, `username`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
--column index 1, parameter value [true], value class [java.lang.Boolean], SQL type -7
--column index 2, parameter value [true], value class [java.lang.Boolean], SQL type -7
--column index 3, parameter value [2023-01-01 00:00:00.0], value class [java.sql.Timestamp], SQL type 93
--column index 4, parameter value [true], value class [java.lang.Boolean], SQL type -7
--column index 5, parameter value [nophy@gmail.com], value class [java.lang.String], SQL type 12
--column index 6, parameter value [true], value class [java.lang.Boolean], SQL type -7
--column index 7, parameter value [Nophy], value class [java.lang.String], SQL type 12
--column index 8, parameter value [Diaz], value class [java.lang.String], SQL type 12
--column index 9, parameter value [123456], value class [java.lang.String], SQL type 12
--column index 10, parameter value [nophy], value class [java.lang.String], SQL type 12
````

- Actualizar un usuario:

````bash
curl -v -X PUT -H "Content-Type: application/json" -d "{\"username\": \"nophy updated\", \"password\": \"123456\", \"accountNonExpired\": true, \"accountNonLocked\": true, \"credentialsNonExpired\": true, \"enabled\": true, \"firstName\": \"Nophy\", \"lastName\": \"Diaz\", \"emailAddress\": \"nophy@gmail.com\", \"birthdate\": \"2023-01-01\"}" http://localhost:8080/api/v1/users/7 | jq

>
< HTTP/1.1 200
< Content-Type: application/json
<
{
  "id": 7,
  "username": "nophy updated",
  "password": "123456",
  "accountNonExpired": true,
  "accountNonLocked": true,
  "credentialsNonExpired": true,
  "enabled": true,
  "firstName": "Nophy",
  "lastName": "Diaz",
  "emailAddress": "nophy@gmail.com",
  "birthdate": "2023-01-01"
}
````

- Agregar un authority a un user:

````bash
curl -v -X PUT -H "Content-Type: application/json" -d "{\"id\": 5, \"authority\": \"ROLE_ADMIN\"}" http://localhost:8080/api/v1/users/7/add-authority | jq

>
< HTTP/1.1 200
< Content-Type: application/json
<
{
  "user": {
    "id": 7,
    "username": "nophy updated",
    "password": "123456",
    "accountNonExpired": true,
    "accountNonLocked": true,
    "credentialsNonExpired": true,
    "enabled": true,
    "firstName": "Nophy",
    "lastName": "Diaz",
    "emailAddress": "nophy@gmail.com",
    "birthdate": "2023-01-01"
  },
  "authorities": [
    {
      "id": 5,
      "authority": "ROLE_ADMIN"
    }
  ]
}
````

````bash
curl -v -X PUT -H "Content-Type: application/json" -d "{\"id\": 3, \"authority\": \"ROLE_USER\"}" http://localhost:8080/api/v1/users/7/add-authority | jq

>
< HTTP/1.1 200
< Content-Type: application/json
<
{
  "user": {
    "id": 7,
    "username": "nophy updated",
    "password": "123456",
    "accountNonExpired": true,
    "accountNonLocked": true,
    "credentialsNonExpired": true,
    "enabled": true,
    "firstName": "Nophy",
    "lastName": "Diaz",
    "emailAddress": "nophy@gmail.com",
    "birthdate": "2023-01-01"
  },
  "authorities": [
    {
      "id": 3,
      "authority": "ROLE_USER"
    },
    {
      "id": 5,
      "authority": "ROLE_ADMIN"
    }
  ]
}
````

- Eliminar un authority de un user:

````bash
curl -v -X PUT -H "Content-Type: application/json" -d "{\"id\": 5, \"authority\": \"ROLE_ADMIN\"}" http://localhost:8080/api/v1/users/7/remove-authority | jq

>
< HTTP/1.1 200
< Content-Type: application/json
<
{
  "user": {
    "id": 7,
    "username": "nophy updated",
    "password": "123456",
    "accountNonExpired": true,
    "accountNonLocked": true,
    "credentialsNonExpired": true,
    "enabled": true,
    "firstName": "Nophy",
    "lastName": "Diaz",
    "emailAddress": "nophy@gmail.com",
    "birthdate": "2023-01-01"
  },
  "authorities": [
    {
      "id": 3,
      "authority": "ROLE_USER"
    }
  ]
}
````

- Eliminar un usuario:

````bash
curl -v -X DELETE http://localhost:8080/api/v1/users/7 | jq

>
< HTTP/1.1 204
````

````roomsql
SELECT `users`.`id` AS `id`, `users`.`enabled` AS `enabled`, `users`.`last_name` AS `last_name`, `users`.`username` AS `username`, `users`.`password` AS `password`, `users`.`birthdate` AS `birthdate`, `users`.`first_name` AS `first_name`, `users`.`email_address` AS `email_address`, `users`.`account_non_locked` AS `account_non_locked`, `users`.`account_non_expired` AS `account_non_expired`, `users`.`credentials_non_expired` AS `credentials_non_expired`
FROM `users` 
WHERE `users`.`id` = ?
--column index 1, parameter value [7], value class [java.lang.Integer], SQL type 4
````

````roomsql
SELECT `users_authorities`.`authority_id` AS `authority_id` 
FROM `users_authorities` 
WHERE `users_authorities`.`user_id` = ?
--column index 1, parameter value [7], value class [java.lang.Integer], SQL type 4
````

````sql
SELECT `users`.`id` 
FROM `users` 
WHERE `users`.`id` = ? FOR UPDATE
--column index 1, parameter value [7], value class [java.lang.Integer], SQL type 4
````

````roomsql
DELETE FROM `users_authorities` WHERE `users_authorities`.`user_id` = ?
--column index 1, parameter value [7], value class [java.lang.Integer], SQL type 4
````

````roomsql
DELETE FROM `users` WHERE `users`.`id` = ?
--column index 1, parameter value [7], value class [java.lang.Integer], SQL type 4
````

Observemos que la tercera consulta SQL generada contiene la cláusula `FOR UPDATE`, **¿qué hace?**:

> **FUENTE: ChatGPT**
>
> La cláusula `FOR UPDATE` se utiliza generalmente en consultas SQL dentro de transacciones en bases de datos
> relacionales. Su propósito principal es **bloquear las filas seleccionadas en la consulta para evitar que otras
> transacciones realicen cambios en esas filas hasta que la transacción actual se complete.**
>
> Al agregar `FOR UPDATE` al final de la consulta, estás indicando a la base de datos que las filas que cumplan con la
> condición de la consulta `(WHERE users.id = ?)` deben ser bloqueadas en modo de lectura y escritura hasta que se
> complete la transacción actual. Esto significa que otras transacciones que intenten modificar o bloquear las mismas
> filas deberán esperar hasta que la transacción actual termine.
>
> Esta cláusula es particularmente útil en escenarios donde necesitas asegurarte de que ciertas filas no sean
> modificadas por otras transacciones mientras realizas operaciones en ellas. Por ejemplo, si estás realizando una
> operación de actualización en base a los resultados de esta consulta, puedes usar `FOR UPDATE` para evitar que otros
> procesos modifiquen esas filas entre el momento en que seleccionas las filas y cuando las actualizas.

## Resumen de las tablas de base de datos trabajadas

![db_spring_data_jdbc_schema.png](./assets/db_spring_data_jdbc_schema.png)