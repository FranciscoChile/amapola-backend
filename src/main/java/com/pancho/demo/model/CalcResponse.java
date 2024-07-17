package com.pancho.demo.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CalcResponse {

    private String result;
    private String operation;

}
