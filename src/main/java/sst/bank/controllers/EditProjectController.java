package sst.bank.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import lombok.extern.log4j.Log4j2;
import sst.bank.OuftiBankFX;
import sst.bank.events.ProjectChangeEvent;
import sst.bank.model.BankSummary;
import sst.bank.model.Operation;
import sst.bank.model.Project;

@Log4j2
public class EditProjectController {
    private Project project;
    @FXML
    private TextField idTextField;
    @FXML
    private TextField nameTextField;
    @FXML
    private DatePicker startDatePicker;
    @FXML
    private DatePicker endDatePicker;
    @FXML
    private OperationsListController operationsByProjectController;
    @FXML
    private Button okButton;
    @FXML
    private Button cancelButton;

    @FXML
    private void initialize() {
        log.debug("initialize...");

        setEditable(false);

        okButton.setOnAction(e -> {
            project.setName(nameTextField.getText());
            project.setId(idTextField.getText());
            project.setStartDate(startDatePicker.getValue());
            project.setEndDate(endDatePicker.getValue());

            OuftiBankFX.eventBus.post(new ProjectChangeEvent(project));
            setEditable(false);
        });

        cancelButton.setOnAction(e -> setProjectInfo());
    }

    private void setEditable(boolean b) {
        nameTextField.setDisable(!b);
        idTextField.setDisable(!b);
        okButton.setDisable(!b);
        cancelButton.setDisable(!b);
    }

    public void setData(Project project) {
        this.project = project;

        if (project != null) {
            setProjectInfo();
            setEditable(true);
        }
    }

    private void setProjectInfo() {
        nameTextField.setText(project.getName());
        idTextField.setText(project.getId());
        startDatePicker.setValue(project.getStartDate());
        endDatePicker.setValue(project.getEndDate());

        ObservableList<Operation> operations = FXCollections.observableArrayList();
        for (Operation operation : project.getOperations()) {
            operations.add(operation);
        }

        operationsByProjectController.setData(new BankSummary(operations));
    }
}
