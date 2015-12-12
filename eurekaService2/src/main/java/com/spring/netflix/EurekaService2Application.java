package com.spring.netflix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableEurekaClient
public class EurekaService2Application {
    public static void main(String[] args) {
        SpringApplication.run(EurekaService2Application.class, args);
    }
}

@RestController
class Service2Controller {

    @RequestMapping("/")
    public @ResponseBody Service2Pojo getSomePojo() {
        System.out.println("Received request");
        return new Service2Pojo("Hello from the other side!");
    }

}

class Service2Pojo {
    private String prop;

    public Service2Pojo(String prop) {
        this.prop = prop;
    }

    public String getProp() {
        return prop;
    }

    public void setProp(String prop) {
        this.prop = prop;
    }
}
