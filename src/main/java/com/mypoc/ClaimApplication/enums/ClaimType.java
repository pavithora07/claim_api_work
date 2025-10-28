package com.mypoc.ClaimApplication.enums;


import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlType;

@XmlType(name = "ClaimType")
@XmlEnum
public enum ClaimType {
    H,  // HCFA
    U;  // UB

    public String value() {
        return name();
    }

    public static ClaimType fromValue(String v) {
        return valueOf(v);
    }
}
