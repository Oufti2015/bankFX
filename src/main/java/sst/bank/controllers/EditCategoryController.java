package sst.bank.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import lombok.extern.log4j.Log4j2;
import sst.bank.OuftiBankFX;
import sst.bank.events.CategoryChangeEvent;
import sst.bank.model.Budget.BudgetFrequencyType;
import sst.bank.model.Budget.BudgetType;
import sst.bank.model.Category;
import sst.bank.model.Category.CategoryType;

import java.math.BigDecimal;

@Log4j2
public class EditCategoryController {
    private Category category;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField htmlLabelTextField;
    @FXML
    private TextField fxNameTextField;
    @FXML
    private TextField styleTextField;
    @FXML
    private ComboBox<CategoryType> typeComboBox;
    @FXML
    private CheckBox visaCheckBox;
    @FXML
    private CheckBox defaultCheckBox;
    @FXML
    private TextField budgetTextField;
    @FXML
    private Button okButton;
    @FXML
    private Button cancelButton;
    @FXML
    private ComboBox<BudgetType> budgetTypeComboBox;
    @FXML
    private ComboBox<BudgetFrequencyType> budgetFreqTypeComboBox;

    @FXML
    private void initialize() {
        log.debug("initialize...");

        setEditable(false);

        typeComboBox.getItems().setAll(CategoryType.values());
        budgetTypeComboBox.getItems().setAll(BudgetType.values());
        budgetFreqTypeComboBox.getItems().setAll(BudgetFrequencyType.values());

        okButton.setOnAction(e -> {
            category.setName(nameTextField.getText());
            category.setLabel(htmlLabelTextField.getText());
            category.setFxName(fxNameTextField.getText());
            category.setStyle(styleTextField.getText());
            category.getBudget().setAmount(new BigDecimal(budgetTextField.getText()));
            category.getBudget().setControlledAmount(new BigDecimal(budgetTextField.getText()));
            category.setType(typeComboBox.getValue());
            category.setVisa(visaCheckBox.isSelected());
            category.setDefaultCategory(defaultCheckBox.isSelected());
            category.getBudget().setBudgetType(budgetTypeComboBox.getValue());
            category.getBudget().setBudgetFrequencyType(budgetFreqTypeComboBox.getValue());

            OuftiBankFX.eventBus.post(new CategoryChangeEvent(category));
            setEditable(false);
        });

        cancelButton.setOnAction(e -> setCategoryInfo());
    }

    private void setEditable(boolean b) {
        budgetTextField.setDisable(!b);
        nameTextField.setDisable(!b);
        htmlLabelTextField.setDisable(!b);
        fxNameTextField.setDisable(!b);
        styleTextField.setDisable(!b);
        typeComboBox.setDisable(!b);
        visaCheckBox.setDisable(!b);
        defaultCheckBox.setDisable(!b);
        okButton.setDisable(!b);
        cancelButton.setDisable(!b);
        budgetTypeComboBox.setDisable(!b);
        budgetFreqTypeComboBox.setDisable(!b);
    }

    public void setData(Category category) {
        this.category = category;

        if (category != null) {
            setCategoryInfo();
            setEditable(true);
        }
    }

    private void setCategoryInfo() {
        nameTextField.setText(category.getName());
        htmlLabelTextField.setText(category.getLabel());
        fxNameTextField.setText(category.getFxName());
        styleTextField.setText(category.getStyle());
        budgetTextField.setText("" + category.getBudget().getAmount());
        typeComboBox.getSelectionModel().select(category.getType());
        visaCheckBox.setSelected(category.isVisa());
        defaultCheckBox.setSelected(category.isDefaultCategory());
        budgetTypeComboBox.getSelectionModel().select(category.getBudget().getBudgetType());
        budgetFreqTypeComboBox.getSelectionModel().select(category.getBudget().getBudgetFrequencyType());
    }
}
