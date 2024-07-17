package com.pancho.demo.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CalcRequest {

    private List<Double> values;
    private int cost;
    private String operation;
    private Long userId;
}
