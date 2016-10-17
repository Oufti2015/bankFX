package sst.bank;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sst.bank.activities.a.config.CategoriesLoader;
import sst.bank.activities.a.config.Configurator;
import sst.bank.activities.b.loading.OperationsLoader;
import sst.bank.activities.e.grouping.OperationsGrouper;
import sst.bank.controllers.OuftiBankController;

public class OuftiBankFX extends Application {
    private Stage primaryStage;
    private AnchorPane rootLayout;

    public static void main(String[] args) {
	launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
	new CategoriesLoader().run();
	new Configurator().run();
	new OperationsLoader().run();
	new OperationsGrouper().run();

	// set title
	this.primaryStage = primaryStage;
	primaryStage.setTitle("Oufti Bank");

	// scene.getStylesheets().add(OuftiBankFX.class.getResource("sigillo.css").toExternalForm());

	// init(grid);

	initRootLayout();
	primaryStage.setMaximized(true);
	// primaryStage.setFullScreen(true);
    }

    /**
     * Initializes the root layout.
     */
    private void initRootLayout() {
	try {
	    // Load root layout from fxml file.
	    FXMLLoader loader = new FXMLLoader();
	    loader.setLocation(OuftiBankFX.class.getResource("views/Main.fxml"));
	    rootLayout = (AnchorPane) loader.load();

	    OuftiBankController controller = loader.getController();
	    controller.setOwner(this);

	    // Show the scene containing the root layout.
	    Scene scene = new Scene(rootLayout);
	    primaryStage.setScene(scene);
	    primaryStage.show();
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }
}
