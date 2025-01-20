package base;

import io.restassured.RestAssured;
import io.restassured.config.EncoderConfig;
import org.testng.annotations.BeforeMethod;

public class BaseTestSetup {

    @BeforeMethod
    public void setup() {
        EncoderConfig encoderConfig = RestAssured.config().getEncoderConfig()
                .appendDefaultContentCharsetToContentTypeIfUndefined(false);

        RestAssured.config = RestAssured.config().encoderConfig(encoderConfig);
    }
}
