package com.fourimpact.TaskManagementWithDbPersistence.Enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum TaskStatus {
    TODO,
    IN_PROGRESS,
    DONE;

    @JsonCreator
    public static TaskStatus fromString(String value) {
        return TaskStatus.valueOf(value.toUpperCase().replace(" ", "_"));
    }
}