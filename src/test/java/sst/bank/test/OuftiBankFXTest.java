package sst.bank.test;

import javafx.scene.control.Label;
import org.junit.Test;
import org.testfx.api.FxRobotException;

import static org.testfx.api.FxAssert.verifyThat;

public class OuftiBankFXTest extends TestBase {

    final String BY_LABEL = "#byLabel";

    @Test(expected = FxRobotException.class)
    public void testStart() {
        clickOn("#sector9");
    }

    @Test
    public void testByLabel() {

        verifyThat(BY_LABEL,
                (Label label) -> {
                    String text = label.getText();
                    return text.equals("By Category");
                });
    }
}
