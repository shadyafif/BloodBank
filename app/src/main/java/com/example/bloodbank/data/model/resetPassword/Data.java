
package com.example.bloodbank.data.model.resetPassword;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("pin_code_for_test")
    @Expose
    private int pinCodeForTest;

    public int getPinCodeForTest() {
        return pinCodeForTest;
    }

    public void setPinCodeForTest(int pinCodeForTest) {
        this.pinCodeForTest = pinCodeForTest;
    }

}
