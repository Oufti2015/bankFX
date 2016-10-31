package sst.bank.controllers;

import java.math.BigDecimal;

import org.junit.Assert;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import lombok.extern.log4j.Log4j;
import sst.bank.model.Category;
import sst.bank.model.Category.CategoryType;

@Log4j
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
    private void initialize() {
	log.info("initialize...");
	Assert.assertNotNull(budgetTextField);
	Assert.assertNotNull(nameTextField);
	Assert.assertNotNull(htmlLabelTextField);
	Assert.assertNotNull(fxNameTextField);
	Assert.assertNotNull(styleTextField);
	Assert.assertNotNull(typeComboBox);
	Assert.assertNotNull(visaCheckBox);
	Assert.assertNotNull(defaultCheckBox);
	Assert.assertNotNull(okButton);
	Assert.assertNotNull(cancelButton);

	typeComboBox.getItems().setAll(CategoryType.values());

	okButton.setOnAction(new EventHandler<ActionEvent>() {
	    @Override
	    public void handle(ActionEvent e) {
		category.setName(nameTextField.getText());
		category.setLabel(htmlLabelTextField.getText());
		category.setFxName(fxNameTextField.getText());
		category.setStyle(styleTextField.getText());
		category.getBudget().setAmount(new BigDecimal(budgetTextField.getText()));
		category.setType(typeComboBox.getValue());
		category.setVisa(visaCheckBox.isSelected());
		category.setDefaultCategory(defaultCheckBox.isSelected());
	    }
	});

	cancelButton.setOnAction(new EventHandler<ActionEvent>() {
	    @Override
	    public void handle(ActionEvent e) {
		setCategoryInfo();
	    }
	});
    }

    public void setData(Category category) {
	this.category = category;

	setCategoryInfo();
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
    }
}
