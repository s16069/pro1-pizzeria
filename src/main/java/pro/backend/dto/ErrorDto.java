package pro.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@NoArgsConstructor
@AllArgsConstructor
public class ErrorDto {

    public ErrorDto(HttpStatus httpStatus, String message) {
        this(httpStatus.value(), httpStatus.getReasonPhrase(), message);
    }

    @Getter
    private int status;

    @Getter
    private String error;

    @Getter
    private String message;
}
