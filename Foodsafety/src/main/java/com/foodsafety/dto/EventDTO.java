package com.foodsafety.dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventDTO {
    private Long value;
    private String viewValue;
    private  String groupId;
}
