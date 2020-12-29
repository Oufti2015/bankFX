package sst.bank.utils;

import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;

public class BankClipboard {
    public static void toClipboard(String text) {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent content = new ClipboardContent();
        content.putString(text);
        clipboard.setContent(content);
        System.out.printf("[%s] copied to clipboard.%n", text);

    }

    public static String fromClipboard() {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        return clipboard.getString();
    }
}
