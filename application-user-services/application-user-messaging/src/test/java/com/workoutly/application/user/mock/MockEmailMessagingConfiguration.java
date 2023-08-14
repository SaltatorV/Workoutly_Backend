package com.workoutly.application.user.mock;

import com.workoutly.application.user.configuration.EmailMessagingConfiguration;

public class MockEmailMessagingConfiguration extends EmailMessagingConfiguration {
    @Override
    public String getSender() {
        return "sender";
    }

    @Override
    public String getCreateSubject() {
        return "create-subject/%s";
    }

    @Override
    public String getCreateContent() {
        return "create-content";
    }

    @Override
    public String getActivateSubject() {
        return "activate-subject";
    }

    @Override
    public String getActivateContent() {
        return "activate-content";
    }

    @Override
    public String getUpdateSubject() {
        return "update-subject";
    }
}
