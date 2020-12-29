package sst.bank.controllers.utils;

import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateStringConverter extends StringConverter<LocalDate> {
    private static final DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd MMM yyyy");

    @Override
    public String toString(LocalDate date) {
        return date.format(fmt);
    }

    @Override
    public LocalDate fromString(String string) {
        return null;
    }
}
