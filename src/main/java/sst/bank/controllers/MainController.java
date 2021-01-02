package sst.bank.controllers;

import javafx.fxml.FXML;
import lombok.extern.log4j.Log4j2;
import sst.bank.OuftiBankFX;
import sst.bank.model.container.BankContainer;
import sst.bank.utils.StringConstants;

@Log4j2
public class MainController {
    OuftiBankFX owner = null;
    @FXML
    private OperationsController byYearTabController;
    @FXML
    private OperationsController byMonthTabController;
    @FXML
    private OperationsByCategoryController byCategoryTabController;
    @FXML
    private CategoriesController categoriesTabController;
    @FXML
    private BeneficiariesController beneficiariesTabController;
    @FXML
    private ProjectsController projectsTabController;

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

        byCategoryTabController.setTitle(StringConstants.BY_CATEGORY);
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
