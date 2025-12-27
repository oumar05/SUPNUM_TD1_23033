package com.example.middleservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateServerRequest {
    private String name;
    private String ipAddress;
    private String status; 
}
