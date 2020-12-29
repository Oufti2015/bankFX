package sst.bank.controllers.utils;

import javafx.util.StringConverter;
import sst.bank.config.BankConfiguration;

import java.text.NumberFormat;

public class DoubleStringConverter extends StringConverter<Double> {

    private final NumberFormat nf = NumberFormat.getNumberInstance();

    public DoubleStringConverter() {
        nf.setMaximumFractionDigits(2);
        nf.setMinimumFractionDigits(2);
    }

    @Override
    public String toString(Double value) {
        return nf.format(value) + " " + BankConfiguration.EURO_CHAR;
    }

    @Override
    public Double fromString(String string) {
        // Don't need this, unless table is editable, see
        // DoubleStringConverter if needed
        return null;
    }
}
