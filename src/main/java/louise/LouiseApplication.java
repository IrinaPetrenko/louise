package louise;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class LouiseApplication {

    public static void main(String[] args) {
        SpringApplication.run(LouiseApplication.class, args);
    }

}
