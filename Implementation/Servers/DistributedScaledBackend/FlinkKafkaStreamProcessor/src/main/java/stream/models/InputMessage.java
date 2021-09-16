package stream.models;

import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.annotation.JsonSerialize;

// import java.time.LocalDateTime;

@JsonSerialize
public class InputMessage {
    String sender;
    String recipient;
//    LocalDateTime sentAt;
    String message;
}
