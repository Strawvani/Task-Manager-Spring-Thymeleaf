package com.fourimpact.TaskManagementWithDbPersistence.Enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum TaskStatus {
    IN_PROGRESS("In Progress"),
    TODO("To Do"),
    DONE("Done");

    private final String displayName;

    TaskStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}