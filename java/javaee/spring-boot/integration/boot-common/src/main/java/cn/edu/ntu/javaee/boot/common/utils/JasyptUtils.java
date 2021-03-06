package cn.edu.ntu.javaee.boot.common.utils;

import com.ulisesbocchio.jasyptspringboot.properties.JasyptEncryptorConfigurationProperties;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;

import java.util.Optional;

/**
 * @author zack <br>
 * @create 2021-05-16<br>
 * @project integration <br>
 */
public final class JasyptUtils {

    static String KEY = "JASYPT_ENCRYPTOR_PASSWORD";

    static PooledPBEStringEncryptor ENCRYPTOR = getInstance();

    public static String decrypt(String message) {

        return ENCRYPTOR.decrypt(message);
    }

    public static String encrypt(String message) {

        return ENCRYPTOR.encrypt(message);
    }
    /**
     * 默认的解码器的配置
     *
     * @see JasyptEncryptorConfigurationProperties
     * @return
     */
    private static PooledPBEStringEncryptor getInstance() {
        PooledPBEStringEncryptor pooledPBEStringEncryptor = new PooledPBEStringEncryptor();

        // get from env: -D is used properties
        String secret = Optional.ofNullable(System.getenv(KEY)).orElse(System.getProperty(KEY));

        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPassword(secret);
        config.setAlgorithm("PBEWITHHMACSHA512ANDAES_256");
        config.setKeyObtentionIterations(1000);
        config.setPoolSize(1);
        config.setSaltGenerator(new org.jasypt.salt.RandomSaltGenerator());
        config.setIvGenerator(new org.jasypt.iv.RandomIvGenerator());
        config.setStringOutputType("base64");

        pooledPBEStringEncryptor.setConfig(config);

        return pooledPBEStringEncryptor;
    }
}
