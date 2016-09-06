package batch.lessons.services;

import batch.lessons.services.interfaces.Calculator;
import batch.lessons.services.interfaces.GreetingService;

/**
 * Created by max on 02.09.16.
 */
public class BeanWithDependency {
    private GreetingService service;
    private Calculator calc;

    public BeanWithDependency(GreetingService service, Calculator calc) {
        this.service = service;
        this.calc = calc;
    }

    public Object printText() {
        return "Max, " + service.sayGreeting() + " 3*3=" + calc.mul(3, 3);
    }
}
