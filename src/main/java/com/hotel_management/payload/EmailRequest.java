package com.hotel_management.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailRequest {

    private String to;
    private String subject;
    private String text;
}
