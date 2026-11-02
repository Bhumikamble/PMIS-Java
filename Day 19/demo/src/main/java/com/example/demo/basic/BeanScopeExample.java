package com.example.demo.basic;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class BeanScopeExample {

    public static void main(String[] args) {

        // Start a context using the bean definitions in ScopeConfiguration.
        try (AnnotationConfigApplicationContext context =
                     new AnnotationConfigApplicationContext(ScopeConfiguration.class)) {

            // Singleton scope: both requests return the same object.
            SingletonBean firstSingleton =
                    context.getBean(SingletonBean.class);

            SingletonBean secondSingleton =
                    context.getBean(SingletonBean.class);

            // Prototype scope: each request returns a different object.
            PrototypeBean firstPrototype =
                    context.getBean(PrototypeBean.class);

            PrototypeBean secondPrototype =
                    context.getBean(PrototypeBean.class);

            // Compare object identity.
            System.out.println("Singleton objects are the same: "
                    + (firstSingleton == secondSingleton));

            System.out.println("Prototype objects are the same: "
                    + (firstPrototype == secondPrototype));

            System.out.println("First Singleton: " + firstSingleton);
            System.out.println("Second Singleton: " + secondSingleton);

            System.out.println("First Prototype: " + firstPrototype);
            System.out.println("Second Prototype: " + secondPrototype);
        }
    }
}