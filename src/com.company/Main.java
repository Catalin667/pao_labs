package com.company;

import com.company.services.Services;

import java.lang.reflect.InvocationTargetException;


public class Main {
    public static void main (String[] args) throws InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        Services myService2 = Services.createServices();

    }
}