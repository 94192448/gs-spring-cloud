package webmvc;

/**
 * The class description.
 *
 * @author yangzq80@gmail.com
 * @date 2019-12-09
 * @see
 * @since 1.0.0
 */
import java.util.Date;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@EnableAutoConfiguration
@RestController
public class Backend {

    @RequestMapping("/api")
    public String printDate() {
        return new Date().toString();
    }

    public static void main(String[] args) {
        SpringApplication.run(Backend.class,
                "--spring.application.name=backend",
                "--server.port=9000"
        );
    }
}
