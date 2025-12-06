package vn.duynv.secutityone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SecutityOneApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecutityOneApplication.class, args);
    }

}
