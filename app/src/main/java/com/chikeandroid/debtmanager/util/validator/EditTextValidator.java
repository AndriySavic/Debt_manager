package com.chikeandroid.debtmanager.util.validator;

import android.widget.EditText;



public abstract class EditTextValidator {

    protected EditText mEditText;
    protected String mErrorMessage;

    public EditTextValidator(EditText editText) {
        mEditText = editText;
    }

    public EditText getEditText() {
        return mEditText;
    }

    public void setEditText(EditText editText) {
        mEditText = editText;
    }

    public String getErrorMessage() {
        return mErrorMessage;
    }

    public abstract boolean isValid();

    public EditTextValidator setErrorMessage(String errorMessage) {
        mErrorMessage = errorMessage;
        return this;
    }


}
