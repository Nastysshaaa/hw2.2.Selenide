import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class AppCardDeliveryTest {
    int days = 3;
    ChooseDate chooseData = new ChooseDate();

    @Test
    void shouldPositivePathTest() {

        open("http://localhost:9999/");
        $("[data-test-id='city'] input").setValue("Екатеринбург");
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(chooseData.generateDate(days));
        $("[data-test-id='name'] input").setValue("Иванов Иван");
        $("[data-test-id='phone'] input").setValue("+79116742005");
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Забронировать")).click();
        $(".notification__content").shouldBe(Condition.text("Встреча успешно забронирована на " + ChooseDate.generateDate(days)), Duration.ofSeconds(15)).shouldBe(Condition.visible);
    }

    @Test
    void shouldNegativePathTestWithoutFieldName(){
        open("http://localhost:9999/");
        $("[data-test-id='city'] input").setValue("Екатеринбург");
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(chooseData.generateDate(days));
        $("[data-test-id='phone'] input").setValue("+78526524009");
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Забронировать")).click();
        $x("//span[@data-test-id='name' and contains(@class, 'input_invalid')]//span[contains(text(), 'Поле')]").should(Condition.appear);
    }
}