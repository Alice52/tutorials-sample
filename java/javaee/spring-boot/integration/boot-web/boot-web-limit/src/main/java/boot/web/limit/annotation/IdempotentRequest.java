package boot.web.limit.annotation;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * @author zack <br>
 * @create 2021-04-12 17:46 <br>
 * @project integration <br>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
@Inherited
public @interface IdempotentRequest {
    /**
     * 限制时间: 指定时间段内同一用户调用相同API 多次则处理为幂等<br>
     * 单位: 毫秒
     *
     * @return
     */
    long time() default 6000;

    /**
     * 请求等待时间的单位
     *
     * @return
     */
    TimeUnit timeUnit() default TimeUnit.MICROSECONDS;
}
