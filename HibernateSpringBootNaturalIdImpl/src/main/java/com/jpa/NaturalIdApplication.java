package com.jpa;

import java.util.Optional;
import java.util.logging.Logger;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = NaturalRepositoryImpl.class)
public class NaturalIdApplication {

    private static final Logger logger = Logger.getLogger(NaturalIdApplication.class.getName());

    private final ProductRepository productRepository;

    public NaturalIdApplication(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(NaturalIdApplication.class, args);
    }

    @Bean
    public ApplicationRunner init() {
        return args -> {

            // persist two products
            Product tshirt = new Product();
            tshirt.setName("T-Shirt");
            tshirt.setCode("014-tshirt-2019");
            // tshirt.setSku(1L);

            Product socks = new Product();
            socks.setName("Socks");
            socks.setCode("012-socks-2018");
            // socks.setSku(2L);

            productRepository.save(tshirt);
            productRepository.save(socks);

            Optional<Product> p1 = productRepository.findById(tshirt.getId());                 // find by ID
            Optional<Product> p2 = productRepository.findBySimpleNaturalId(tshirt.getCode()); // find by natural ID

            if (p1.isPresent() && p2.isPresent()) {
                System.out.println("p1: " + p1.get());
                System.out.println("p2: " + p2.get());
            } else {
                System.out.println("Not found!");
            }
        };
    }
}
