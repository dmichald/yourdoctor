package doctor;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

@SpringBootTest
@ComponentScan("doctor.models.*")
@ComponentScan("doctor.repository.*")
class DoctorApplicationTests {

    @Test
    void contextLoads() {
    }

}
