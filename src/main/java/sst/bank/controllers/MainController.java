package sst.bank.controllers;

import javafx.fxml.FXML;
import lombok.extern.log4j.Log4j;
import sst.bank.OuftiBankFX;
import sst.bank.model.container.BankContainer;

@Log4j
public class MainController {
    OuftiBankFX owner = null;
    @FXML
    private OperationsByYearController byYearTabController;
    @FXML
    private OperationsByMonthController byMonthTabController;
    @FXML
    private OperationsByCategoryController byCategoryTabController;
    @FXML
    private CategoriesController categoriesTabController;
    @FXML
    private BeneficiariesController beneficiariesTabController;

    public MainController() {
	super();
	log.debug("OuftiBankController...");
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
	log.debug("initialize...");
	log.debug("byYearTabController  = " + byYearTabController);
	log.debug("byMonthTabController = " + byMonthTabController);
	log.debug("byCategoryController = " + byCategoryTabController);

	byMonthTabController.setTitle("By Month");
	byCategoryTabController.setTitle("By Category");
	byYearTabController.setListViewData(BankContainer.me().operationsByYear());
	byMonthTabController.setListViewData(BankContainer.me().operationsByMonth());
	byCategoryTabController.setTreeViewData(BankContainer.me().getCategories());
    }

    @FXML
    public void update() {
	log.debug("Update...");
    }

    public void setOwner(OuftiBankFX ouftiBankFX) {
	this.owner = ouftiBankFX;
    }
}
