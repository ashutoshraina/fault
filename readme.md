Start by cloning the following repos
1)git clone git@github.com:ashutoshraina/fault.git 
2)git clone git@github.com:ashutoshraina/tault.git 
3)git clone git@github.com:ashutoshraina/tingu.git

Build and Publish locally tingu. 
This has no material impact on the demo since we are not using the `CowardTracer`. This can be done using gradle.

`./gradlew clean build publish`

Start the Zipkin Server
`docker run -d -p 9411:9411 openzipkin/zipkin`

The fault service passes additional httpheaders `X-B3-FaultType` and `X-B3-Service` when it makes a request to the tault service.
This additional metadata is passed using the instrumented OKHTTP Library. 

Start fault and tault from their respective directories using gradle.

`./gradlew bootRun`

Next, we will hit the chaining endpoint in the fault service.

```
$ curl localhost:8080/chaining
Hello from Taulty!with delay%     
```

The tault service will read the request and inject an artificial delay.
This shows how an already instrumented system can leverage injection and extraction facilities of open tracing for fault injection.

You can also look at the traces at http://localhost:9411



