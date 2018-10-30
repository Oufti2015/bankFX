package sst.bank.controllers;

import com.google.common.eventbus.DeadEvent;
import com.google.common.eventbus.Subscribe;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import lombok.extern.log4j.Log4j;
import org.junit.Assert;
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

        Assert.assertNotNull(projectsListView);
        Assert.assertNotNull(editProjectController);
        ObservableList<Project> projects = FXCollections.observableArrayList();
        for (Project project : BankContainer.me().projectsContainer().projects()
                .stream()
                .sorted()
                .collect(Collectors.toList())) {
            projects.add(project);
        }

        projectsListView.getItems().setAll(projects);
        projectsListView.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Project>() {
                    @Override
                    public void changed(ObservableValue<? extends Project> ov,
                                        Project old_val, Project new_val) {
                        editProjectController.setData(new_val);
                    }
                });
    }

    @Subscribe
    public void handleEvent(ProjectChangeEvent e) {
        ObservableList<Project> projects = FXCollections.observableArrayList();
        for (Project project : BankContainer.me().projectsContainer().projects()
                .stream()
                .sorted()
                .collect(Collectors.toList())) {
            projects.add(project);
        }
        projectsListView.getItems().setAll(projects);
    }

    @Subscribe
    public void deadEvents(DeadEvent e) {
        log.debug("Event " + e.getEvent() + " not subscribed...");
    }
}
