package com.fourimpact.TaskManagementWithDbPersistence.Enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum TaskPriority {
    LOW,
    MEDIUM,
    HIGH;

    @JsonCreator
    public static TaskPriority fromString(String value) {
        return TaskPriority.valueOf(value.toUpperCase().replace(" ", "_"));
    }
}
