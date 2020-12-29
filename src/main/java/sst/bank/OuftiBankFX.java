package sst.bank;

import com.google.common.eventbus.DeadEvent;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lombok.extern.log4j.Log4j;
import sst.bank.activities.LifeCycleInterface;
import sst.bank.controllers.MainController;
import sst.bank.events.BeneficiaryChangeEvent;
import sst.bank.events.CategoryChangeEvent;

import java.io.IOException;
import java.net.URL;

@Log4j
public class OuftiBankFX extends Application {
    public static final String EURO_1_JPG = "euro1.jpg";
    private static final String VIEWS_MAIN_FXML = "/Main.fxml";
    public static final EventBus eventBus = new EventBus();
    private Stage primaryStage;

    public static void main(String[] args) {
        log.info("+----------------------------------------------------------------------------------------------+");
        log.info("|----O-U-F-T-I----B-A-N-K----------------------------------------------------------------------|");
        log.info("+----------------------------------------------------------------------------------------------+");

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        eventBus.register(this);

        LifeCycleInterface.runCompleteLifeCyle();

        // set title
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Oufti Bank");

        initRootLayout();
        primaryStage.setMaximized(true);
        Scene scene = primaryStage.getScene();
        scene.getStylesheets().clear();
        scene.getStylesheets().add(String.valueOf(OuftiBankFX.class.getResource("/bankFX.css")));

        String icon = EURO_1_JPG;
        try {
            URL url = getClass().getResource(icon);
            if (url != null) {
                primaryStage.getIcons().add(new Image(url.openStream()));
            } else {
                log.error("Cannot load icon <" + icon + ">");
                primaryStage.getIcons().add(new Image(EURO_1_JPG));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Initializes the root layout.
     */
    private void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader;
            loader = new FXMLLoader();
            URL resource = OuftiBankFX.class.getResource(VIEWS_MAIN_FXML);
            loader.setLocation(resource);
            AnchorPane rootLayout = loader.load();

            MainController controller = loader.getController();
            controller.setOwner(this);

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            log.fatal("Cannot load " + VIEWS_MAIN_FXML + " : ", e);
            OuftiBankFX.eventBus.post(e);
        }
    }

    @Subscribe
    public void handleEvent(CategoryChangeEvent e) {
        log.info("" + e.getCategory() + " has changed.");
        LifeCycleInterface.saveCategories();
    }

    @Subscribe
    public void handleEvent(BeneficiaryChangeEvent e) {
        log.info("" + e.getBeneficiary() + " has changed.");
        LifeCycleInterface.saveBeneficiaries();
    }

    @Subscribe
    public void deadEvents(DeadEvent e) {
        log.info("Event " + e.getEvent() + " not subscribed...");
    }

    @Subscribe
    public void fatalError(Exception e) {
        log.fatal("FATAL error detected", e);
        System.exit(-1);
    }
}
