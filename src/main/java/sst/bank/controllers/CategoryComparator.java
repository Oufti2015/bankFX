package sst.bank.controllers;

import java.util.Comparator;

import sst.bank.model.Category;

public class CategoryComparator implements Comparator<Category> {

    @Override
    public int compare(Category o1, Category o2) {
	return o1.getFxName().compareTo(o2.getFxName());
    }

}
