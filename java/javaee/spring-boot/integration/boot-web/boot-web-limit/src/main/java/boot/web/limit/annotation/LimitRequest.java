package boot.web.limit.annotation;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;

/**
 * 限流: <br>
 *
 * <pre>
 *     1. 令牌桶: 可以应对突发流量
 *     2. 漏桶[以固定的速度流入]
 * </pre>
 *
 * @author zack <br>
 * @create 2021-04-12 14:29 <br>
 * @project integration <br>
 */
@Target({TYPE, METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface LimitRequest {
    /**
     * 可以访问的次数
     *
     * @return
     */
    int count() default 0;

    /**
     * 指定的一段时间, 单位是 {@link java.util.concurrent.TimeUnit.SECONDS}
     *
     * @return
     */
    long time() default 0;

    /**
     * 请求等待时间
     *
     * @return
     */
    long acquireTokenTimeout() default 0;

    /**
     * 请求等待时间的单位
     *
     * @return
     */
    TimeUnit acquireTokenTimeUnit() default TimeUnit.SECONDS;

    /**
     * 错误提示
     *
     * @return
     */
    String message() default "调用频繁";
}
