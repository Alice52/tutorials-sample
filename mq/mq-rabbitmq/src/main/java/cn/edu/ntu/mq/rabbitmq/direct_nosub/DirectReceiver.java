package cn.edu.ntu.mq.rabbitmq.direct_nosub;

import cn.edu.ntu.mq.constants.Constants;
import cn.edu.ntu.mq.rabbitmq.Utils.ConnectionUtils;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DeliverCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author zack
 * @create 2019-07-28 14:50
 * @function
 */
public class DirectReceiver {

    private static final Logger LOG = LoggerFactory.getLogger(DirectReceiver.class);
    private static Connection connection = ConnectionUtils.getConnection();

    public static void main(String[] args) {
        receiveMsg();
    }

    public static void receiveMsg() {

        Channel channel;

        try {
            channel = connection.createChannel();
            channel.exchangeDeclare(Constants.EXCHANGE_DIRECT_NAME, BuiltinExchangeType.DIRECT);

            DeliverCallback deliverCallback =
                    (consumerTag, delivery) -> {
                        String message = new String(delivery.getBody(), "UTF-8");
                        LOG.info(
                                " [x] Received '"
                                        + delivery.getEnvelope().getRoutingKey()
                                        + "':'"
                                        + message
                                        + "'");
                    };

            channel.basicConsume(
                    Constants.QUEUE_DIRECT_NAME, true, deliverCallback, consumerTag -> {});
        } catch (IOException e) {
            LOG.error(
                    "RabbitMQ: receiveMsg IO exception. exception cause: {}; exception message: {}",
                    e.getCause(),
                    e.getMessage());
            throw new RuntimeException();
        } finally {
            // close resources
        }
    }
}
