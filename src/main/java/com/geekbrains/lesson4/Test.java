package com.geekbrains.lesson4;

public class Test {
    public static void main(String[] args) {
        User user = User.builder()
                .withName("Ivan")
                .withSurname("Ivanov")
                .build();

        LombokUser user1 = LombokUser.builder()
                .name("Ivan")
                .surname("Ivanov")
                .build();

        System.out.println(user1);
    }
}
