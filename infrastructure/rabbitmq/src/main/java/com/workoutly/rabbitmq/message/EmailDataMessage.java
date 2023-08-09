package com.workoutly.rabbitmq.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailDataMessage implements Serializable {
    @JsonProperty("recipient")
    private String recipient;

    @JsonProperty("subject")
    private String subject;

    @JsonProperty("sender")
    private String sender;

    @JsonProperty("content")
    private String content;

    private EmailDataMessage(Builder builder) {
        setRecipient(builder.recipient);
        setSubject(builder.subject);
        setSender(builder.sender);
        setContent(builder.content);
    }
    public static Builder create() {
        return new Builder();
    }


    public static final class Builder {
        private String recipient;
        private String subject;
        private String sender;
        private String content;

        private Builder() {
        }


        public Builder recipient(String val) {
            recipient = val;
            return this;
        }

        public Builder subject(String val) {
            subject = val;
            return this;
        }

        public Builder sender(String val) {
            sender = val;
            return this;
        }

        public Builder content(String val) {
            content = val;
            return this;
        }

        public EmailDataMessage build() {
            return new EmailDataMessage(this);
        }
    }
}