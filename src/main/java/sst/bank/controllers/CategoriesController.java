package sst.bank.controllers;

import com.google.common.eventbus.DeadEvent;
import com.google.common.eventbus.Subscribe;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import lombok.extern.log4j.Log4j2;
import sst.bank.OuftiBankFX;
import sst.bank.controllers.utils.CategoryComparator;
import sst.bank.events.CategoryChangeEvent;
import sst.bank.model.Category;
import sst.bank.model.container.BankContainer;

import java.util.stream.Collectors;

@Log4j2
public class CategoriesController {
    @FXML
    private ListView<Category> categoryListView;
    @FXML
    private EditCategoryController editCategoryController;

    @FXML
    private void initialize() {
        log.debug("initialize...");
        OuftiBankFX.eventBus.register(this);

        ObservableList<Category> categories = FXCollections.observableArrayList();
        for (Category category : BankContainer.me().getCategories()
                .stream()
                .sorted(new CategoryComparator())
                .collect(Collectors.toList())) {
            categories.add(category);
        }

        categoryListView.getItems().setAll(categories);
        categoryListView.getSelectionModel().selectedItemProperty().addListener(
                (ov, oldVal, newVal) -> editCategoryController.setData(newVal));
    }

    @Subscribe
    public void handleEvent(CategoryChangeEvent e) {
        ObservableList<Category> categories = FXCollections.observableArrayList();
        categories.addAll(BankContainer.me().getCategories()
                .stream()
                .sorted(new CategoryComparator())
                .collect(Collectors.toList()));
        categoryListView.getItems().setAll(categories);
    }

    @Subscribe
    public void deadEvents(DeadEvent e) {
        log.debug("Event " + e.getEvent() + " not subscribed...");
    }
}
