package copycli.com.copycli;

import java.io.File;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;


/* DOC2PDF
1) access downlaods folder
2) find file to be converted
3) convert file to pdf 
4) Write to a folder with new converted file 
*/


@ShellComponent
public class ConverterCommand {

@ShellMethod(value = "list-N-files in downloads", key= "list d")
public String listNFilesInDownloads(@ShellOption(defaultValue = "5") int amount) {
    String userHome = System.getProperty("user.home");
    String downloadsPath = userHome + File.separator + "Downloads";
    
    File downloadsFolder = new File(downloadsPath);
    StringBuilder resultStr = new StringBuilder();
    
    
    isFileReadable(downloadsFolder);
    
    // Get file list - this can return null!
    File[] files = downloadsFolder.listFiles();

    if (files == null) {
        return "❌ Unable to list files in Downloads: " + downloadsFolder.getAbsolutePath();
    }

    int count = 0;
    for (File file : files) {
        if (count >= amount) break;
        if (file.isFile()) {
            resultStr.append("📄 ").append(file.getName()).append("\n");
            count++;
        } else if (file.isDirectory()) {
            resultStr.append("📁 ").append(file.getName()).append(" (folder)\n");
            count++;
        }
    }
    
    return resultStr.toString();


}
    
@ShellMethod(value = "list-downloads", key= "list-all-downloads")
public String listAllFilesInDownloads() {  // Changed from private to public!
    String userHome = System.getProperty("user.home");
    String downloadsPath = userHome + File.separator + "Downloads";
    
    File downloadsFolder = new File(downloadsPath);
    StringBuilder resultStr = new StringBuilder();
    
    
    isFileReadable(downloadsFolder);
    
    // Get file list - this can return null!
    File[] files = downloadsFolder.listFiles();
    
    resultStr.append("📁 Found ").append(files.length).append(" items in Downloads:\n\n");
    
    // Safe loop - files is guaranteed not null now
    for (File file : files) {
        if (file.isFile()) {
            resultStr.append("📄 ").append(file.getName()).append("\n");
        } else if (file.isDirectory()) {
            resultStr.append("📁 ").append(file.getName()).append(" (folder)\n");
        }
    }
    
    return resultStr.toString();
}

    /*
    @ShellMethod(value = "convert docs to pdfs", key = "doc2pdf")
    private String doc2pdf(String docFileName) {

    }
    */
    
    
public String isFileReadable(File file) {
    
     if (!file.exists()) {
        return "❌  folder doesn't exist at: " + file.getName();
    }
    
    if (!file.isDirectory()) {
        return "❌ Downloads path is not a directory: " + file.getName();
    }
     
    if (!file.canRead()) {
        return "❌ Permission denied - cannot read Downloads folder: " + file.getName();
    }

    return " Downloads folder is present and readable: " + file.getAbsolutePath();
}


}