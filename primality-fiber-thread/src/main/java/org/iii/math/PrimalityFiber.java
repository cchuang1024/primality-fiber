package org.iii.math;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import org.iii.concurrent.BaseWorker;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

@Component
@Scope(SCOPE_PROTOTYPE)
public class PrimalityFiber extends BaseWorker implements Callable<List<Long>> {

    @Autowired
    private MathService service;

    private long n;

    public PrimalityFiber(long n) {
        this.n = n;
    }

    @Override
    public List<Long> call() {
        List<Long> result = new ArrayList<>();

        for (long i = 1; i <= n; i++) {
            long fib = waitAndTake(service.calFib(i));
            boolean prime = waitAndTake(service.calPrime(fib));
            if (prime) { result.add(i); }
        }

        return result;
    }
}
