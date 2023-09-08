package vn.techmaster.finalproject.service.serviceimplement;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.function.Predicate;

@Component
public class EmailValidServiceImpl implements Predicate<String> {
    @Override
    public boolean test(String s) {
        return true;
    }
}
