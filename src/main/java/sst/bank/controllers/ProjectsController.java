package sst.bank.controllers;

import com.google.common.eventbus.DeadEvent;
import com.google.common.eventbus.Subscribe;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import lombok.extern.log4j.Log4j;
import sst.bank.OuftiBankFX;
import sst.bank.events.ProjectChangeEvent;
import sst.bank.model.Project;
import sst.bank.model.container.BankContainer;

import java.util.stream.Collectors;

@Log4j
public class ProjectsController {
    @FXML
    private ListView<Project> projectsListView;
    @FXML
    private EditProjectController editProjectController;

    @FXML
    private void initialize() {
        log.debug("initialize...");

        OuftiBankFX.eventBus.register(this);

        ObservableList<Project> projects = FXCollections.observableArrayList();
        projects.addAll(BankContainer.me().projectsContainer().projects()
                .stream()
                .sorted()
                .collect(Collectors.toList()));

        projectsListView.getItems().setAll(projects);
        projectsListView.getSelectionModel().selectedItemProperty().addListener(
                (ov, oldVal, newVal) -> editProjectController.setData(newVal));
    }

    @Subscribe
    public void handleEvent(ProjectChangeEvent e) {
        ObservableList<Project> projects = FXCollections.observableArrayList();
        projects.addAll(BankContainer.me().projectsContainer().projects()
                .stream()
                .sorted()
                .collect(Collectors.toList()));
        projectsListView.getItems().setAll(projects);
    }

    @Subscribe
    public void deadEvents(DeadEvent e) {
        log.debug("Event " + e.getEvent() + " not subscribed...");
    }
}
