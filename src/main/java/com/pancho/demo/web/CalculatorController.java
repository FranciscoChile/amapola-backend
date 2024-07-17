package com.pancho.demo.web;

import com.pancho.demo.model.APIResponse;
import com.pancho.demo.model.CalcRequest;
import com.pancho.demo.model.EnumOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/calculator")
public class CalculatorController {

    @Autowired
    Mediator mediator;

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<APIResponse> addition(@RequestBody CalcRequest calcRequest) {
        calcRequest.setOperation(EnumOperation.ADDITION.toString());
        return mediator.handler(calcRequest);
    }

    @PostMapping("/sub")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<APIResponse> subtraction(@RequestBody CalcRequest calcRequest) {
        calcRequest.setOperation(EnumOperation.SUBTRACTION.toString());
        return mediator.handler(calcRequest);
    }

    @PostMapping("/multi")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<APIResponse> multiplication(@RequestBody CalcRequest calcRequest) {
        calcRequest.setOperation(EnumOperation.MULTIPLICATION.toString());
        return mediator.handler(calcRequest);
    }

    @PostMapping("/div")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<APIResponse> division(@RequestBody CalcRequest calcRequest) {
        calcRequest.setOperation(EnumOperation.DIVISION.toString());
        return mediator.handler(calcRequest);
    }

    @PostMapping("/square")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<APIResponse> squareRoot(@RequestBody CalcRequest calcRequest) {
        calcRequest.setOperation(EnumOperation.SQUARE_ROOT.toString());
        return mediator.handler(calcRequest);
    }

    @PostMapping("/random")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<APIResponse> randomString(@RequestBody CalcRequest calcRequest) {
        calcRequest.setOperation(EnumOperation.RANDOM_STRING.toString());
        return mediator.handler(calcRequest);
    }

    @PostMapping("/load-data")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<APIResponse> loadData() {
        CalcRequest calcRequest = CalcRequest.builder().operation(EnumOperation.LOAD_DATA.toString()).build();
        return mediator.handler(calcRequest);
    }
}
