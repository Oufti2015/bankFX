package sst.bank.controllers;

import java.util.stream.Collectors;

import org.junit.Assert;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import lombok.extern.log4j.Log4j;
import sst.bank.controllers.utils.CategoryComparator;
import sst.bank.model.Category;
import sst.bank.model.container.BankContainer;

@Log4j
public class CategoriesController {
    @FXML
    private ListView<Category> categoryListView;
    @FXML
    private EditCategoryController editCategoryController;

    @FXML
    private void initialize() {
	log.info("initialize...");
	Assert.assertNotNull(categoryListView);
	Assert.assertNotNull(editCategoryController);
	ObservableList<Category> categories = FXCollections.observableArrayList();
	for (Category category : BankContainer.me().getCategories()
		.stream()
		.sorted(new CategoryComparator())
		.collect(Collectors.toList())) {
	    categories.add(category);
	}

	categoryListView.getItems().setAll(categories);
	categoryListView.getSelectionModel().selectedItemProperty().addListener(
		new ChangeListener<Category>() {
		    @Override
		    public void changed(ObservableValue<? extends Category> ov,
					Category old_val, Category new_val) {
			log.debug("" + old_val + "-" + new_val);
			editCategoryController.setData(new_val);
		    }
		});
    }
}
