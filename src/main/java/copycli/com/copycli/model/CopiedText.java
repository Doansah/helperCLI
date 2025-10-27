package copycli.com.copycli.model;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CopiedText {
    private String content;
    private LocalDateTime createdAt;

    public CopiedText(String text, LocalDateTime createdAt) {
        this.content = content;
        this.createdAt = createdAt;

    }


    public String getContent() {
        return this.content;
    }
    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }
}