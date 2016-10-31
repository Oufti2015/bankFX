package sst.bank.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import sst.bank.model.Category.CategoryType;

public class EditCategoryController {
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
}
