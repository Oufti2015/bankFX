package sst.bank.controllers.utils;

import sst.bank.model.Category;

import java.util.Comparator;

public class CategoryComparator implements Comparator<Category> {

    @Override
    public int compare(Category o1, Category o2) {
        return o1.getFxName().compareTo(o2.getFxName());
    }

}
