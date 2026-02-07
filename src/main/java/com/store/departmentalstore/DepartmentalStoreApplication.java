
package com.store.departmentalstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class DepartmentalStoreApplication {
    public static void main(String[] args) {
        SpringApplication.run(DepartmentalStoreApplication.class, args);
    }
}
