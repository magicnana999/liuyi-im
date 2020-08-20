package com.creolophus.im.netty.core;

import com.creolophus.im.netty.exception.NettyError;
import com.creolophus.im.netty.utils.SessionUtil;
import com.creolophus.im.protocol.Command;
import com.creolophus.liuyi.common.json.JSON;
import com.creolophus.liuyi.common.logger.TracerUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;

import javax.websocket.Session;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * @author magicnana
 * @date 2020/2/24 上午10:33
 */
public abstract class AbstractWebSocketServer {

    private static final Logger logger = LoggerFactory.getLogger(AbstractWebSocketServer.class);
    private static final ScheduledExecutorService timer = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors());
    protected static RequestProcessor requestProcessor;
    protected static SessionEventListener sessionEventListener;
    protected static TracerUtil tracerUtil;
    protected static ContextProcessor contextProcessor;
    private static ConfigurableApplicationContext applicationContext;
    private ScheduledFuture scheduledFuture;

    public void connect(Session session) {
        scheduledFuture = timer.schedule(new IdleSessionManager(session), 60 * 2, TimeUnit.SECONDS);
    }

    public Command decode(String message) {
        return JSON.parseObject(message, Command.class);
    }

    public void flush(Session session, String request, Command response) {
        logger.debug("{}", session.getId());
        logger.info("flush {}", JSON.toJSONString(response));
    }

    public abstract Session getSession();

    public Command process(Session session, Command cmd) {
        Command response = null;
        try {
            final Object requestReturn = requestProcessor.processRequest(cmd);
            response = Command.newAck(cmd.getHeader().getSeq(), cmd.getHeader().getType(), requestReturn);
        } catch (Throwable e) {
            logger.error(e.getMessage(), e);
            response = Command.newAck(cmd.getHeader().getSeq(), cmd.getHeader().getType(), NettyError.E_ERROR);
        } finally {
            return response(session, response);
        }
    }

    public Command response(Session session, Command response) {
        try {
            contextProcessor.setResponse(response);
            session.getBasicRemote().sendText(JSON.toJSONString(response));
            return response;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void setConfigurableApplicationContext(ConfigurableApplicationContext configurableApplicationContext) {
        applicationContext = configurableApplicationContext;
        requestProcessor = applicationContext.getBean(RequestProcessor.class);
        tracerUtil = applicationContext.getBean(TracerUtil.class);
        contextProcessor = applicationContext.getBean(ContextProcessor.class);
        sessionEventListener = applicationContext.getBeansOfType(SessionEventListener.class, false, false)
                .size() > 0 ? applicationContext.getBean(SessionEventListener.class) : null;
    }

    public void verify(Session session, Command command) {
        if(scheduledFuture != null) scheduledFuture.cancel(true);
        requestProcessor.verify(command);
    }

    class IdleSessionManager implements Runnable {

        private Session session;

        public IdleSessionManager(Session session) {
            this.session = session;
        }

        @Override
        public void run() {
            try {
                session.close();
                logger.info("{} has been closed", SessionUtil.getSessionId(session));
            } catch (Throwable e) {
                logger.info("{} close session error", SessionUtil.getSessionId(session), e);
            }
        }
    }
}
