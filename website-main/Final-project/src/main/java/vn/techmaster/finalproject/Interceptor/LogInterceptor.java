package vn.techmaster.finalproject.Interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Slf4j
public class LogInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        LocalTime localTime = LocalTime.now();
        String localTimeStr = localTime.format(DateTimeFormatter.ofPattern("hh:mm:ss"));

        log.info(localTimeStr+ "-" + request.getMethod()+"-"+request.getRequestURI());
        return true;
    }
}
