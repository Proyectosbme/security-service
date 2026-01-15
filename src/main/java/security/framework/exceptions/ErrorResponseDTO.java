package security.framework.exceptions;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorResponseDTO {

    private int status;
    private String error;
    private String message;
    private LocalDateTime timestamp;
    private String path;
    private List<String> details;

    public ErrorResponseDTO(int status, String error, String message, String path) {
        this.status = status;
        this.error = error;
        this.message = message;
        this.timestamp = LocalDateTime.now();
        this.path = path;
    }

    public ErrorResponseDTO(int status, String error, String message, String path, List<String> details) {
        this.status = status;
        this.error = error;
        this.message = message;
        this.timestamp = LocalDateTime.now();
        this.path = path;
        this.details = details;
    }
}
