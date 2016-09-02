package lessons.services;

import lessons.services.interfaces.Calculator;
import org.springframework.stereotype.Component;

/**
 * Created by max on 02.09.16.
 */

@Component("calculator")

public class CalculatorImpl implements Calculator {

    @Override
    public int add(int a, int b) {
        return a + b;
    }
}
