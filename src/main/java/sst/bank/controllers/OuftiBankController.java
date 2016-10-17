package sst.bank.controllers;

import javafx.fxml.FXML;
import sst.bank.OuftiBankFX;
import sst.bank.model.container.BankContainer;

public class OuftiBankController {
    OuftiBankFX owner = null;
    @FXML
    private BankSummaryController byMonthTabController;
    @FXML
    private OperationsByCategoriesController byCategoryTabController;

    public OuftiBankController() {
	super();
	System.out.println("OuftiBankController...");
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
	System.out.println("initialize...");
	System.out.println("byMonthTabController = " + byMonthTabController);
	System.out.println("byCategoryController = " + byCategoryTabController);

	byMonthTabController.setTitle("By Month");
	byCategoryTabController.setTitle("By Category");
	byMonthTabController.setTreeViewData(BankContainer.me().operationsByMonth());
	byCategoryTabController.setTreeViewData(BankContainer.me().getCategories());
    }

    @FXML
    public void update() {
	System.out.println("Update...");
    }

    public void setOwner(OuftiBankFX ouftiBankFX) {
	this.owner = ouftiBankFX;
    }
}
