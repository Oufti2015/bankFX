package sst.bank.controllers;

import java.util.Comparator;

import sst.bank.model.BankSummary;

public class DescendingBankSummaryComparator implements Comparator<BankSummary> {

    @Override
    public int compare(BankSummary o1, BankSummary o2) {
	return o2.getStartDate().compareTo(o1.getStartDate());
    }
}
