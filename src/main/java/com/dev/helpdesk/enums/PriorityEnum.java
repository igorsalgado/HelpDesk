package com.dev.helpdesk.enums;

public enum PriorityEnum {
    HIGH,
    NORMAL,
    LOW;

    public static PriorityEnum getPriority(String priority){
        switch(priority){
            case "HIGH": return HIGH;
            case "NORMAL": return NORMAL;
            case "LOW": return LOW;
            default: return LOW;
        }
    }
}
