package copycli.com.copycli;

import java.io.File;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;


/* DOC2PDF
1) access downlaods folder
2) find file to be converted
3) convert file to pdf 
4) Write to a folder with new converted file 
*/


@ShellComponent
public class ConverterCommand {

    
@ShellMethod(value = "list-downloads", key= "list-all-downloads")
public String listAllFilesInDownloads() {  // Changed from private to public!
    String userHome = System.getProperty("user.home");
    String downloadsPath = userHome + File.separator + "Downloads";
    
    File downloadsFolder = new File(downloadsPath);
    StringBuilder resultStr = new StringBuilder();
    
    // Check if folder exists and is accessible
    if (!downloadsFolder.exists()) {
        return "❌ Downloads folder doesn't exist at: " + downloadsPath;
    }
    
    if (!downloadsFolder.isDirectory()) {
        return "❌ Downloads path is not a directory: " + downloadsPath;
    }
     
    if (!downloadsFolder.canRead()) {
        return "❌ Permission denied - cannot read Downloads folder: " + downloadsPath;
    }
    
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
    
    
}