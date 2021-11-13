package com.geekbrains.lesson4;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LombokUser {

    private final String name;
    private final String surname;
    private final String address;
    private final String email;
    private final String phone;
    private final String vkLink;
    private final String facebookLink;

}
