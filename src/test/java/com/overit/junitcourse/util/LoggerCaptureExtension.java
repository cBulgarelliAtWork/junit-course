package com.overit.junitcourse.util;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.Logger;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.mockito.ArgumentCaptor;
import org.mockito.verification.VerificationMode;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.Mockito.*;

/**
 * {@code LoggerCaptureExtension} allows to intercept all log events that has been logged during the execution and provides
 * an easy interface to verify the level and the content of each one.
 *
 * <p>It requires the test has been run using Mockito's runner.</p>
 *
 * <pre>
 * public class LoggerCaptureTest {
 *     &#064;RegisterExtension
 *     public LoggerCaptureExtension logger = new LoggerCaptureExtension(SUTClass.class, Level.DEBUG);
 *
 *     &#064;Test
 *     public void testA() {
 *         List&lt;LoggerCaptureExtension.Log&gt; logs = logger.getLogs(times(1));
 *         assertThat(logs.get(0).match(Level.DEBUG, "log message")).isTrue();
 *     }
 * }
 * </pre>
 */
public class LoggerCaptureExtension implements BeforeEachCallback, AfterEachCallback {

    private Appender appender;
    private Logger logger;
    private ArgumentCaptor<LogEvent> eventCaptor;
    private final Level level;
    private final Class<?> clazz;

    /**
     * Create a new {@link LoggerCaptureExtension} instance by setting the provided logging {@link Level}.
     *
     * @param clazz The {@link Class} whose name should be used as the {@link Logger} name.
     * @param level {@link Level} about the logging level to set.
     */
    public LoggerCaptureExtension(Class<?> clazz, Level level) {
        this.clazz = clazz;
        this.level = level;
    }

    /**
     * Create a new {@link LoggerCaptureExtension} instance by setting the logging level to {@link Level#TRACE}.
     *
     * @param clazz The {@link Class} whose name should be used as the {@link Logger} name.
     */
    @SuppressWarnings("unused")
    public LoggerCaptureExtension(Class<?> clazz) {
        this(clazz, Level.TRACE);
    }

    @Override
    public void beforeEach(ExtensionContext context) {
        // mocking log4j
        this.appender = mock(Appender.class);
        when(appender.getName()).thenReturn("appender");
        lenient().when(appender.isStarted()).thenReturn(true);
        this.logger = (Logger) LogManager.getLogger(clazz);
        this.logger.addAppender(appender);
        this.logger.setLevel(level);
        this.eventCaptor = ArgumentCaptor.forClass(LogEvent.class);
    }

    @Override
    public void afterEach(ExtensionContext context) {
        logger.removeAppender(appender);
    }

    /**
     * Returns a {@link List}&lt;{@link Log}&gt; about the captured log events.
     *
     * @param verificationMode {@link VerificationMode} about the expected captured logged events.
     * @return all logs that has been collected during the test execution.
     */
    public List<Log> getLogs(VerificationMode verificationMode) {
        verify(appender, verificationMode).append(eventCaptor.capture());
        return eventCaptor.getAllValues()
                .stream()
                .map(Log::new)
                .collect(Collectors.toList());
    }

    /**
     * Returns a {@link List}&lt;{@link Log}&gt; about the captured log events.
     *
     * @return all logs that has been collected during the test execution.
     */
    @SuppressWarnings("unused")
    public List<Log> getLogs() {
        return getLogs(atLeastOnce());
    }

    public static class Log {
        private final LogEvent logEvent;

        public Log(LogEvent logEvent) {
            this.logEvent = logEvent;
        }

        public boolean match(Level level, String message) {
            return logEvent.getLevel().equals(level) && logEvent.getMessage().getFormattedMessage().equals(message);
        }

        @SuppressWarnings("unused")
        public boolean match(Level level, String format, Object... parameters) {
            return logEvent.getLevel().equals(level)
                    && logEvent.getMessage().getFormat().equals(format)
                    && Arrays.equals(logEvent.getMessage().getParameters(), parameters);
        }

        @SuppressWarnings("unused")
        public boolean match(Level level, Throwable throwable, String format) {
            return logEvent.getLevel().equals(level)
                    && logEvent.getMessage().getFormat().equals(format)
                    && logEvent.getThrown() != null && logEvent.getThrown().equals(throwable);
        }

        @SuppressWarnings("unused")
        public boolean match(Level level, Throwable throwable, String format, Object... parameters) {
            return logEvent.getLevel().equals(level)
                    && logEvent.getMessage().getFormat().equals(format)
                    && logEvent.getThrown() != null && logEvent.getThrown().equals(throwable)
                    && Arrays.equals(logEvent.getMessage().getParameters(), parameters);
        }

        @SuppressWarnings("unused")
        public boolean match(Level level, Throwable throwable) {
            return logEvent.getLevel().equals(level)
                    && logEvent.getThrown() != null && logEvent.getThrown().equals(throwable);
        }
    }
}

