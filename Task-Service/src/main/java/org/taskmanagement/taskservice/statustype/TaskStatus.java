package org.taskmanagement.taskservice.statustype;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum TaskStatus {
    ACTIVE("active"),
    COMPLETED("completed"),
    CANCELED("canceled");
    private final String value;
    TaskStatus(String value) {
        this.value = value;
    }
    @JsonCreator
    public static TaskStatus fromString(String value) {
//        return TaskStatus.valueOf(value.toLowerCase());
        for(TaskStatus status : TaskStatus.values()){
            if(status.value.equalsIgnoreCase(value)){
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid TaskStatus value " + value);
    }
}
