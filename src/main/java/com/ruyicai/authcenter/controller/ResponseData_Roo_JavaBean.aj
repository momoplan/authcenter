// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.ruyicai.authcenter.controller;

import java.lang.Object;
import java.lang.String;

privileged aspect ResponseData_Roo_JavaBean {
    
    public String ResponseData.getErrorCode() {
        return this.errorCode;
    }
    
    public void ResponseData.setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
    
    public Object ResponseData.getValue() {
        return this.value;
    }
    
    public void ResponseData.setValue(Object value) {
        this.value = value;
    }
    
}
