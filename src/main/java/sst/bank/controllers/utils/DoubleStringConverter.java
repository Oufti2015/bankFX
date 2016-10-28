package sst.bank.controllers.utils;

import java.text.NumberFormat;

import javafx.util.StringConverter;

public class DoubleStringConverter extends StringConverter<Double> {

    private final NumberFormat nf = NumberFormat.getNumberInstance();

    {
	nf.setMaximumFractionDigits(2);
	nf.setMinimumFractionDigits(2);
    }

    @Override
    public String toString(Double value) {
	return nf.format(value) + " €";
    }

    @Override
    public Double fromString(String string) {
	// Don't need this, unless table is editable, see
	// DoubleStringConverter if needed
	return null;
    }
}
