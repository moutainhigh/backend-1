package com.fb.im.connect;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class SpringNettyApplication implements CommandLineRunner {

    public static void main(String[] args) {

        SpringApplication.run(SpringNettyApplication.class, args);
    }

    public void run(String... args) throws Exception {
        System.out.println("start netty application");
        while (true) {

        }
    }
}

