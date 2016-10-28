package sst.bank.controllers.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javafx.util.StringConverter;

public class LocalDateStringConverter extends StringConverter<LocalDate> {
    private static DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd-MMM-yyyy");

    @Override
    public String toString(LocalDate date) {
	return date.format(fmt);
    }

    @Override
    public LocalDate fromString(String string) {
	return null;
    }
}
