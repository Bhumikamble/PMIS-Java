package com.example.demo.basic;

public class BeanMethodExample {

    public static void main(String[] args) {
        BeanConfiguration beanConfiguration = new BeanConfiguration();
        BasicBean basicBean = beanConfiguration.basicBean();
        System.out.println(basicBean.greet());
    }
}
