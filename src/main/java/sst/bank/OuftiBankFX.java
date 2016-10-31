package sst.bank;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

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
import sst.bank.config.BankConfiguration;
import sst.bank.controllers.MainController;
import sst.bank.events.CategoryChangeEvent;

@Log4j
public class OuftiBankFX extends Application {
    private static final String VIEWS_MAIN_FXML = "views/Main.fxml";

    public static final String ICON = BankConfiguration.PATH + File.separator + "euro1.jpg";

    public final static EventBus eventBus = new EventBus();

    private Stage primaryStage;
    private AnchorPane rootLayout;

    public static void main(String[] args) {
	log.info("+----------------------------------------------------------------------------------------------+");
	log.info("|----O-U-F-T-I----B-A-N-K----------------------------------------------------------------------|");
	log.info("+----------------------------------------------------------------------------------------------+");

	launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
	eventBus.register(this);
	LifeCycleInterface.runReadOnlyLifeCyle();

	// set title
	this.primaryStage = primaryStage;
	primaryStage.setTitle("Oufti Bank");

	// scene.getStylesheets().add(OuftiBankFX.class.getResource("sigillo.css").toExternalForm());

	// init(grid);

	initRootLayout();
	primaryStage.setMaximized(true);
	// primaryStage.setFullScreen(true);
	// Scene scene = primaryStage.getScene();
	// File f = new File("bankFX2.css");
	// scene.getStylesheets().clear();
	// scene.getStylesheets().add("file:///" +
	// f.getAbsolutePath().replace("\\", "/"));

	InputStream resourceAsStream = OuftiBankFX.class.getResourceAsStream(ICON);
	if (resourceAsStream != null) {
	    primaryStage.getIcons().add(new Image(resourceAsStream));
	} else {
	    log.error("Cannot load icon <" + ICON + ">");
	    primaryStage.getIcons().add(new Image("euro3.png"));
	}
    }

    /**
     * Initializes the root layout.
     */
    private void initRootLayout() {
	try {
	    // Load root layout from fxml file.
	    FXMLLoader loader = null;
	    loader = new FXMLLoader();
	    loader.setLocation(OuftiBankFX.class.getResource(VIEWS_MAIN_FXML));
	    rootLayout = (AnchorPane) loader.load();

	    MainController controller = loader.getController();
	    controller.setOwner(this);

	    // Show the scene containing the root layout.
	    Scene scene = new Scene(rootLayout);
	    primaryStage.setScene(scene);
	    primaryStage.show();
	} catch (IOException e) {
	    log.fatal("Cannot load " + VIEWS_MAIN_FXML + " : ", e);
	}
    }

    @Subscribe
    public void handleEvent(CategoryChangeEvent e) {
	log.debug("" + e.getCategory() + " has changed.");
	LifeCycleInterface.saveCategories();
    }

    @Subscribe
    public void deadEvents(DeadEvent e) {
	log.debug("Event " + e.getEvent() + " not subscribed...");
    }
}
