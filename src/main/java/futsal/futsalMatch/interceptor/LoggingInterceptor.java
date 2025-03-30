package futsal.futsalMatch.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

@Component
@Slf4j
public class LoggingInterceptor implements HandlerInterceptor {

    private AtomicInteger count =  new AtomicInteger(0);
    private AtomicLong sumOfTime = new AtomicLong(0);

    private synchronized long getAverage() {
        return sumOfTime.get() / count.get();
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        request.setAttribute("startTime", System.currentTimeMillis());
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        Long startTime = (Long) request.getAttribute("startTime");
        long endTime = System.currentTimeMillis();
        count.getAndIncrement();
        sumOfTime.getAndAdd(endTime - startTime);
        String fullUrl = request.getRequestURI() + "?" + request.getQueryString();
        log.info("[{}] {} 처리 시간: {}ms", request.getMethod(), fullUrl, (endTime - startTime));
        log.info("평균 처리시간 : {}ms", getAverage());
    }

    public void reset() {
        count.set(0);
        sumOfTime.set(0);
    }
}
