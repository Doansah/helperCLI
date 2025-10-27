package copycli.com.copycli;

import java.util.ArrayList;

import lombok.Data;

@Data
public class CopyStack {
    ArrayList<CopiedText> copiedTextList;

    public ArrayList<CopiedText> getCopiedTextList() {
        return this.copiedTextList;
    }

    public String build(CopyStack copyStack, int length) {
        StringBuilder resultString = new StringBuilder();
        
        ArrayList<CopiedText> list = (copyStack != null) ? copyStack.getCopiedTextList() : this.copiedTextList;
        if (list == null || list.isEmpty()) {
            return "";
        }

        int count = 0;
        for (CopiedText copiedText : list) {
            if (length > 0 && count++ >= length) {
                break;
            }
            String dateCreated = copiedText.getCreatedAt().toString();
            String content = copiedText.getContent();
            String separator = "\n ===========================================";
            String modifiedText = "Sample Content: " + content + "\n" + dateCreated + separator;

    }
    return resultString.toString();
}
    
    
    
    
    
    // ensures the copystack does not get too large
    public void validateLength() {
        // example maximum size; adjust as needed
        final int MAX_SIZE = 100;
        if (copiedTextList != null && copiedTextList.size() > MAX_SIZE) {
            resize();
        }
    }

    // resize (ie. we dont want infinte copies on the stack)
    public CopyStack resize() {
        final int MAX_SIZE = 100;
        if (copiedTextList == null) {
            return this;
        }
        while (copiedTextList.size() > MAX_SIZE) {
            // remove oldest or last entry depending on desired behavior
            copiedTextList.remove(copiedTextList.size() - 1);
        }
        return this;
    }

     
    }
    


    

