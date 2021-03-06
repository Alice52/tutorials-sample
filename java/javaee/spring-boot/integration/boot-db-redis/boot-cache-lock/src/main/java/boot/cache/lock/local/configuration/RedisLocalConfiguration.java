package boot.cache.lock.local.configuration;

import cn.edu.ntu.common.api.redis.serializer.DateTimeJsonDeserializer;
import cn.edu.ntu.common.api.redis.serializer.DateTimeJsonSerializer;
import cn.hutool.core.date.DateTime;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.PROPERTY;
import static com.fasterxml.jackson.databind.ObjectMapper.DefaultTyping.NON_FINAL;

/**
 * @author zack <br>
 * @create 2021-01-04 21:38 <br>
 * @project springboot <br>
 */
@Component
public class RedisLocalConfiguration {
    @Bean("springSessionDefaultRedisSerializer")
    public RedisSerializer setSerializer() {
        return new GenericJackson2JsonRedisSerializer();
    }

    /**
     * This serializer will contains Class info.
     *
     * @param redisConnectionFactory
     * @return
     */
    @Bean
    @Deprecated
    public RedisTemplate redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        // handle key serialization
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);

        // handle value serialization
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer =
                new Jackson2JsonRedisSerializer(Object.class);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.registerModule(new JavaTimeModule());
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(DateTime.class, new DateTimeJsonSerializer());
        simpleModule.addDeserializer(DateTime.class, new DateTimeJsonDeserializer());

        objectMapper.activateDefaultTyping(
                LaissezFaireSubTypeValidator.instance, NON_FINAL, PROPERTY);
        objectMapper.registerModule(simpleModule);

        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);

        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);

        return redisTemplate;
    }
}
