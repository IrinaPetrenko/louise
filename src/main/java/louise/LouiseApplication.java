package louise;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
@EnableMongoRepositories
public class LouiseApplication {

    public static void main(String[] args) {
        SpringApplication.run(LouiseApplication.class, args);
    }

}
