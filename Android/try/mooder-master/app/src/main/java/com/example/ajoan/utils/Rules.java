package com.example.ajoan.utils;

/**
 * Created by Joan on 01/04/2017.
 */

public interface Rules {

    String USERNAME_RULE="((?=.*[a-z])^[a-zA-Z](\\w{2,}))";

    String USERNMAIL_RULE=".+@.+"; //todo change

    String PASS_RULE = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,})";

    String NON_EMPTY_RULE = ".+";

}
