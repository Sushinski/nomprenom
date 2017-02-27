package com.nomprenom2.utils;

public class ActionEvent {

    private final boolean isSuccessfull;
    private final String message;
    //можно передавать дополнительную информацию

    public ActionEvent(boolean isSuccessefull, String message) {
        this.isSuccessfull = isSuccessefull;
        this.message = message;
    }

    public boolean isSUccessefull() {
        return isSuccessfull;
    }

    public String getMessage() {
        return message;
    }
}
