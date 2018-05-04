package ch.globaz.tmmas.authentificationservice.application;

/**
 * Created by seb on .
 * <p>
 * ${VERSION}
 */

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.support.ErrorMessage;

import java.util.ArrayList;

import static java.util.Arrays.asList;

@Data
@NoArgsConstructor
public class RestErrorList extends ArrayList<ErrorMessage> {


    private HttpStatus status;

    public RestErrorList(HttpStatus status, ErrorMessage... errors) {
        this(status.value(), errors);
    }

    public RestErrorList(int status, ErrorMessage... errors) {
        super();
        this.status = HttpStatus.valueOf(status);
        addAll(asList(errors));
    }

}
