package com.sg.bankaccountback.model;

import com.sun.javafx.beans.IDProperty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Balance {
    @Id
    @GeneratedValue
    private Long id;
    private float amount;
}

