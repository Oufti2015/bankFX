package sst.bank.controllers;

import java.util.Collection;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import sst.bank.model.Category;

public class OperationsByCategoriesController {
    @FXML
    private Label byCatLabel;
    @FXML
    private Label fromCatLabel;
    @FXML
    private ListView<Category> categoriesListView;

    public void setTitle(String title) {
	this.byCatLabel.setText(title);
    }

    public void setTreeViewData(Collection<Category> list) {
	ObservableList<Category> categories = FXCollections.observableArrayList();
	for (Category category : list) {
	    categories.add(category);
	}
	categoriesListView.setItems(categories);
	categoriesListView.setEditable(true);
	categoriesListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
	categoriesListView.getSelectionModel().selectedItemProperty().addListener(
		new ChangeListener<Category>() {
		    @Override
		    public void changed(ObservableValue<? extends Category> ov,
					Category old_val, Category new_val) {
			// System.out.println("" + old_val + "-" + new_val);
		    }
		});
    }
}