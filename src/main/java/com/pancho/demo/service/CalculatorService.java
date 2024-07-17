package com.pancho.demo.service;

import com.pancho.demo.exception.DataValidationException;
import com.pancho.demo.model.*;
import com.pancho.demo.persistence.OperationRepository;
import com.pancho.demo.persistence.RecordRepository;
import com.pancho.demo.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CalculatorService {

    @Autowired
    RecordRepository recordRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    OperationRepository operationRepository;

    private static final String urlRandom = "https://www.random.org/strings/?num=1&len=32&digits=on&upperalpha=on&loweralpha=on&unique=on&format=plain&rnd=new";

    @Transactional
    public CalcResponse addition(CalcRequest calcRequest) {
        Operation operation = operationRepository.findByOperationType(calcRequest.getOperation());
        calcRequest.setCost(operation.getCost());
        if (!creditBalanceAvailable(calcRequest)) {
            throw new DataValidationException("No credit available");
        }

        List<Double> values = calcRequest.getValues();
        double result = values.stream().mapToDouble(Double::doubleValue).sum();

        RecordOperation recordOperation = RecordOperation.builder()
                .operationId(1L)
                .userId(calcRequest.getUserId())
                .amount(result)
                .operationCost(operation.getCost())
                .operationResponse("")
                .date(new Date())
                .build();

        recordRepository.save(recordOperation);

        return CalcResponse.builder()
                .result(String.valueOf(result))
                .operation(EnumOperation.ADDITION.toString())
                .build();

    }

    public CalcResponse subtraction(CalcRequest calcRequest) {
        Operation operation = operationRepository.findByOperationType(calcRequest.getOperation());
        calcRequest.setCost(operation.getCost());
        if (!creditBalanceAvailable(calcRequest)) {
            throw new DataValidationException("No credit available");
        }

        List<Double> values = calcRequest.getValues();

        if (values.size() < 2) {
            throw new DataValidationException("No values present");
        }

        if (values.size() > 2) {
            throw new DataValidationException("Subtraction allows just two values");
        }

        double result = Math.abs(values.get(0) - values.get(1));

        return CalcResponse.builder()
                .result(String.valueOf(result))
                .operation(EnumOperation.SUBTRACTION.toString())
                .build();
    }

    public CalcResponse multiplication(CalcRequest calcRequest) {
        Operation operation = operationRepository.findByOperationType(calcRequest.getOperation());
        calcRequest.setCost(operation.getCost());
        if (!creditBalanceAvailable(calcRequest)) {
            throw new DataValidationException("No credit available");
        }

        List<Double> values = calcRequest.getValues();

        if (values.isEmpty()) {
            throw new DataValidationException("No values present");
        }

        double result = values.stream().reduce(0d, (a,b) -> (a * b));

        return CalcResponse.builder()
                .result(String.valueOf(result))
                .operation(EnumOperation.MULTIPLICATION.toString())
                .build();
    }

    public CalcResponse division(CalcRequest calcRequest) {
        Operation operation = operationRepository.findByOperationType(calcRequest.getOperation());
        calcRequest.setCost(operation.getCost());
        if (!creditBalanceAvailable(calcRequest)) {
            throw new DataValidationException("No credit available");
        }

        List<Double> values = calcRequest.getValues();

        if (values.size() < 2) {
            throw new DataValidationException("No values present");
        }

        if (values.size() > 2) {
            throw new DataValidationException("Division allows just two values");
        }

        double result = Math.abs(values.get(0) / values.get(1));

        return CalcResponse.builder()
                .result(String.valueOf(result))
                .operation(EnumOperation.MULTIPLICATION.toString())
                .build();
    }

    public CalcResponse squareRoot(CalcRequest calcRequest) {
        Operation operation = operationRepository.findByOperationType(calcRequest.getOperation());
        calcRequest.setCost(operation.getCost());
        if (!creditBalanceAvailable(calcRequest)) {
            throw new DataValidationException("No credit available");
        }

        List<Double> values = calcRequest.getValues();

        if (values.isEmpty()) {
            throw new DataValidationException("No values present");
        }

        if (values.size() > 1) {
            throw new DataValidationException("Square root allows just one value");
        }

        double result = Math.sqrt(values.get(0));

        return CalcResponse.builder()
                .result(String.valueOf(result))
                .operation(EnumOperation.MULTIPLICATION.toString())
                .build();
    }

    public CalcResponse randomString(CalcRequest calcRequest) {
        Operation operation = operationRepository.findByOperationType(calcRequest.getOperation());
        calcRequest.setCost(operation.getCost());
        if (!creditBalanceAvailable(calcRequest)) {
            throw new DataValidationException("No credit available");
        }

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> randomString = restTemplate.getForEntity(urlRandom, String.class );

        return CalcResponse.builder()
                .result(Objects.requireNonNull(randomString.getBody()).replace("\n", ""))
                .operation(EnumOperation.RANDOM_STRING.toString())
                .build();
    }

    public void loadData() {

        User user = User.builder()
                .username("demo@demo.com")
                .password("demo")
                .balance(10L)
                .status(User.Status.ACTIVE.toString())
                .build();

        userRepository.save(user);

        Operation op1 = Operation.builder().operationType(EnumOperation.ADDITION.toString()).cost(1).build();
        Operation op2 = Operation.builder().operationType(EnumOperation.SUBTRACTION.toString()).cost(1).build();
        Operation op3 = Operation.builder().operationType(EnumOperation.MULTIPLICATION.toString()).cost(1).build();
        Operation op4 = Operation.builder().operationType(EnumOperation.DIVISION.toString()).cost(1).build();
        Operation op5 = Operation.builder().operationType(EnumOperation.SQUARE_ROOT.toString()).cost(1).build();
        Operation op6 = Operation.builder().operationType(EnumOperation.RANDOM_STRING.toString()).cost(1).build();

        operationRepository.save(op1);
        operationRepository.save(op2);
        operationRepository.save(op3);
        operationRepository.save(op4);
        operationRepository.save(op5);
        operationRepository.save(op6);
    }

    private boolean creditBalanceAvailable(CalcRequest calcRequest) {

        User user;
        Optional<User> userOp = userRepository.findById(calcRequest.getUserId());

        if (userOp.isPresent()) {
            user = userOp.get();

            if (user.getBalance() == 0) return false;
            if (calcRequest.getCost() > user.getBalance()) return false;

            user.setBalance(user.getBalance() - calcRequest.getCost());
            userRepository.save(user);
        }
        else {
            return false;
        }

        return true;
    }
}
