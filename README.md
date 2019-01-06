# msg-logger-thorntail-demo

A simple JAX-RS + Datasource thorntail app, with a simple REST-API endpint to consume message and get them.

Build
```console
$ mvn package 

$ docker build . -t msg-logger-thorntail-demo  
```

Run 
```console
$ docker run --rm -it -p 8080:8080 msg-logger-thorntail-demo  

```console
# persist a message to postgres db
$ curl "http://localhost:8080/messages/send?username=rgo&message=hacking"
```
```console
# get all messages
$ curl -s http://localhost:8080/messages
{rgo: hacking}
```

To tweak the db location, invoke the conatiner extra arguments, it will be picked up due to microprofile-config api
```console
$ docker run \
    -it \
    --link 
    msg-logger-thorntail-demo \
    -Dthorntail.datasources.data-sources.ExampleDS.connection-url=jdbc:postgresql://postgres:5432/testdb
```

