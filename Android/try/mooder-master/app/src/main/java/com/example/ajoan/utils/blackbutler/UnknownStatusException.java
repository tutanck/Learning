package com.example.ajoan.utils.blackbutler;

public class UnknownStatusException extends Exception {
    UnknownStatusException() {
        super("BlackButler : Unknown response's status. Supported status are -1 for issues and 0 for replies");
    }
}