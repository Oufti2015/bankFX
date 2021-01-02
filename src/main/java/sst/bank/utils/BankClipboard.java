package sst.bank.utils;

import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class BankClipboard {
    private BankClipboard() {
    }

    public static void toClipboard(String text) {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent content = new ClipboardContent();
        content.putString(text);
        clipboard.setContent(content);
        log.debug(String.format("[%s] copied to clipboard.%n", text));
    }

    public static String fromClipboard() {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        return clipboard.getString();
    }
}
