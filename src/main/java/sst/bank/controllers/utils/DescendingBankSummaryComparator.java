package sst.bank.controllers.utils;

import sst.bank.model.BankSummary;

import java.util.Comparator;

public class DescendingBankSummaryComparator implements Comparator<BankSummary> {

    @Override
    public int compare(BankSummary o1, BankSummary o2) {
        return o2.getStartDate().compareTo(o1.getStartDate());
    }
}
