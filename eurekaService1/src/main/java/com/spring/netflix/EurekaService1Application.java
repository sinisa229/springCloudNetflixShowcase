package com.spring.netflix;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@EnableCircuitBreaker
public class EurekaService1Application {
    public static void main(String[] args) {
        SpringApplication.run(EurekaService1Application.class, args);
    }
}

@RestController
class Service1Controller {

    @Autowired
    private HelloProvider helloProvider;

    @RequestMapping("/")
    public @ResponseBody Service1Pojo getSomePojo() {
        return new Service1Pojo(helloProvider.provideHello());
    }

}

@Component
class HelloProvider {

    @Autowired
    private HelloClient helloClient;

    @HystrixCommand(fallbackMethod = "defaultHello")
    public String provideHello() {
        try {
            return helloClient.getHello().getProp();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public String defaultHello() {
        return "Hello World";
    }

}

@FeignClient("SERVICE-2")
interface HelloClient {
    @RequestMapping(method = RequestMethod.GET, value = "/")
    Service1Pojo getHello();
}


class Service1Pojo {
    private String prop;

    public Service1Pojo() {
        super();
    }

    public Service1Pojo(String prop) {
        this.prop = prop;
    }

    public String getProp() {
        return prop;
    }

    public void setProp(String prop) {
        this.prop = prop;
    }
}
