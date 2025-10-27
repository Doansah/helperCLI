package copycli.com.copycli.command;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class HelloCommand {

    // Clipboard history storage (in-memory)
    private final List<ClipboardEntry> clipboardHistory = new ArrayList<>();
    private final int MAX_HISTORY_SIZE = 10;
    
    // Inner class to store clipboard entries with timestamp
    private static class ClipboardEntry {
        private final String content;
        private final LocalDateTime timestamp;
        
        public ClipboardEntry(String content) {
            this.content = content;
            this.timestamp = LocalDateTime.now();
        }
        
        public String getContent() { return content; }
        public LocalDateTime getTimestamp() { return timestamp; }
    }

    @ShellMethod(value = "my-first-command-using-spring-shell", key = "hello")
    public String sayHello() {
        return "Hello from Spring Shell!";
    }
    // Eventually Copied Text

    // Method which returns the last 5 copied texts

    @ShellMethod(value = "Copy text to clipboard", key = "copy")
    public String copyText(String... words) {
        if (words.length == 0) {
            return "❌ Please provide text to copy. Usage: copy your text here";
        }
        
        String text = String.join(" ", words);
        try {
            setClipboardContent(text);
            addToHistory(text);
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

    @ShellMethod(value = "Show clipboard history (last 10 entries)", key = "history")
    public String showHistory() {
        if (clipboardHistory.isEmpty()) {
            return "📋 No clipboard history available";
        }
        
        StringBuilder result = new StringBuilder("📋 Clipboard History:\n");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd HH:mm:ss");
        
        for (int i = 0; i < clipboardHistory.size(); i++) {
            ClipboardEntry entry = clipboardHistory.get(i);
            String preview = entry.getContent().length() > 50 
                ? entry.getContent().substring(0, 50) + "..." 
                : entry.getContent();
            
            result.append(String.format("%d. [%s] %s\n", 
                i + 1, 
                entry.getTimestamp().format(formatter), 
                preview));
        }
        
        return result.toString();
    }
    
    @ShellMethod(value = "Get clipboard entry by number from history", key = "get")
    public String getFromHistory(int index) {
        if (clipboardHistory.isEmpty()) {
            return "❌ No clipboard history available";
        }
        
        if (index < 1 || index > clipboardHistory.size()) {
            return "❌ Invalid index. Use 'history' to see available entries (1-" + clipboardHistory.size() + ")";
        }
        
        ClipboardEntry entry = clipboardHistory.get(index - 1);
        try {
            setClipboardContent(entry.getContent());
            return "✅ Copied to clipboard: " + entry.getContent();
        } catch (Exception e) {
            return "❌ Error copying to clipboard: " + e.getMessage();
        }
    }
    
    @ShellMethod(value = "Clear clipboard history", key = "clear-history")
    public String clearHistory() {
        clipboardHistory.clear();
        return "✅ Clipboard history cleared";
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

    // Helper method to add entries to history
    private void addToHistory(String text) {
        // Don't add duplicates if it's the same as the last entry
        if (!clipboardHistory.isEmpty() && clipboardHistory.get(0).getContent().equals(text)) {
            return;
        }
        
        // Add to the beginning of the list (most recent first)
        clipboardHistory.add(0, new ClipboardEntry(text));
        
        // Keep only the last MAX_HISTORY_SIZE entries
        if (clipboardHistory.size() > MAX_HISTORY_SIZE) {
            clipboardHistory.remove(clipboardHistory.size() - 1);
        }
    }

}