xdubbo
======

To `mvn clean install` this project, you must have jdk8 installed.

## Features

1. Expose spring managed beans as http endpoints , especially useful for testing dubbo services.
2. A `Service` can be marked as exposable by `ServiceFilter`.
3. The endpoint url can be customised by `UrlGenerator`.

## Limitations

1. ~~Can expose bean methods with zero or one arg.~~ Multi args are supported now.
2. Does not support bean method overloading unless a custom `UrlGenerator` is provided to ensure each overloaded method having a different url from each other.
3. Method arguments are sent from request body (As with `@RequestBody`)

## Usage

See the `test` module.

[Download Postman test data](https://www.getpostman.com/collections/b87a452f355a01bf8a10)

To integrate with your existing spring mvc project , just add `life.yuan.xdubbo` to your `<component-scan/>`

## Test

```
git clone https://github.com/Youmoo/xdubbo.git
cd xdubbo
mvn clean install -DskipTests -Dskip
cd test
mvn spring-boot:run
```

## Explanation

The `Service` below will exposed as:

 1. '/service/find'
 2. 'service/update'
 3. 'service/findAll'
 
```java
@Component
public class Service {

    public User find(Integer id) {
        User user = new User();
        user.setId(id);
        user.setUsername("youmoo");
        return user;
    }

    public User update(User user) {
        return user;
    }

    String findAll() {
        return "findAll";
    }

}
```

## License

XDubbo is released under version 2.0 of the [Apache License][].

[Apache License]: http://www.apache.org/licenses/LICENSE-2.0