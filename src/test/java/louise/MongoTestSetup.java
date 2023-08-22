package louise;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public abstract class MongoTestSetup {
    private static final MongoDBContainer MONGO_DB_CONTAINER;

    static {
        MONGO_DB_CONTAINER = new MongoDBContainer("mongo:6.0")
                .withExposedPorts(27017);
        MONGO_DB_CONTAINER.start();
    }
    @DynamicPropertySource
    static void mongoDbProperties(DynamicPropertyRegistry registry) {
        MONGO_DB_CONTAINER.start();
        registry.add("spring.data.mongodb.uri", MONGO_DB_CONTAINER::getReplicaSetUrl);
        registry.add("spring.data.mongodb.port", MONGO_DB_CONTAINER::getExposedPorts);
        registry.add("spring.data.mongodb.host", MONGO_DB_CONTAINER::getHost);
    }
}
