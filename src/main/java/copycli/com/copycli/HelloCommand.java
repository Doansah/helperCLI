package copycli.com.copycli;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class HelloCommand {

    @ShellMethod(value = "my-first-command-using-spring-shell", key = "hello")
    public String sayHello() {
        return "Hello from Spring Shell!";
    }
    // Eventually Copied Text

    // Method which returns the last 5 copied texts

    @ShellMethod(value = "Copy text to clipboard", key = "copy")
    public String copyText(String text) {
        try {
            setClipboardContent(text);
            return "✅ Text copied to clipboard: " + text;
        } catch (Exception e) {
            return "❌ Error copying to clipboard: " + e.getMessage();
        }
    }

    @ShellMethod(value = "Get current clipboard content", key = "get-clip")
    public String getClipboard() {
        try {
            String content = getClipboardContent();
            if (content.isEmpty()) {
                return "📋 Clipboard is empty";
            }
            return "📋 Clipboard content: " + content;
        } catch (Exception e) {
            return "❌ Error reading clipboard: " + e.getMessage();
        }
    }

    // Helper method to get clipboard content
    private String getClipboardContent() {
        try {
            // Check if we're in a headless environment
            if (java.awt.GraphicsEnvironment.isHeadless()) {
                throw new RuntimeException("Clipboard not available in headless mode");
            }
            
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            if (!clipboard.isDataFlavorAvailable(DataFlavor.stringFlavor)) {
                return "";
            }
            return (String) clipboard.getData(DataFlavor.stringFlavor);
        } catch (UnsupportedFlavorException | IOException | SecurityException e) {
            throw new RuntimeException("Failed to read clipboard: " + e.getMessage());
        }
    }

    // Helper method to set clipboard content
    private void setClipboardContent(String text) {
        try {
            // Check if we're in a headless environment
            if (java.awt.GraphicsEnvironment.isHeadless()) {
                throw new RuntimeException("Clipboard not available in headless mode");
            }
            
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            StringSelection selection = new StringSelection(text);
            clipboard.setContents(selection, null);
        } catch (SecurityException e) {
            throw new RuntimeException("Permission denied accessing clipboard: " + e.getMessage());
        }
    }


}