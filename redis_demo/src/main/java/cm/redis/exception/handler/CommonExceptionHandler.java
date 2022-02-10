package cm.redis.exception.handler;

import io.lettuce.core.RedisConnectionException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class CommonExceptionHandler {

    @ExceptionHandler(RedisConnectionException.class)
    @ResponseStatus(HttpStatus.OK)
    public Object handlerRedisConnectFail(WebRequest request, RedisConnectionException exception) {
        System.out.println(request);
        System.out.println(exception.getMessage());
        return "connect fail";
    }

}
