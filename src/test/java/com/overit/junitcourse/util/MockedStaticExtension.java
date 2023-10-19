package com.overit.junitcourse.util;

import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.mockito.MockSettings;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import org.mockito.stubbing.OngoingStubbing;
import org.mockito.verification.VerificationMode;

import static org.mockito.Mockito.withSettings;

/**
 * {@code MockedStaticExtension} makes the Mockito static mock of a specific class available inside the test without the needs to
 * explicitly open/close a mockStatic instance.
 *
 * <p>It requires the test has been run using Mockito's runner.</p>
 *
 * <pre>
 * public class MockedStaticTest {
 *  &#064;RegisterExtension
 *  public MockedStaticExtension&lt;Integer&gt; mockedInteger = new MockedStaticExtension&lt;&gt;(Integer.class);
 *
 *  &#064;Test
 *  public void testA() {
 *      mockedInteger.when(() -> Integer.valueOf(1)).thenReturn(11);
 *  }
 * }
 * </pre>
 */
public class MockedStaticExtension<T> implements BeforeEachCallback, AfterEachCallback, MockedStatic<T> {

    private final Class<T> classToMock;
    private final MockSettings mockSettings;
    private MockedStatic<?> mock;

    /**
     * Create a new {@link MockedStaticExtension} instance.
     *
     * @param classToMock class or interface of which static mocks should be mocked.
     */
    @SuppressWarnings("unused")
    public MockedStaticExtension(Class<T> classToMock) {
        this.classToMock = classToMock;
        this.mockSettings = withSettings();
    }

    /**
     * Create a new {@link MockedStaticExtension} instance.
     *
     * @param classToMock  class or interface of which static mocks should be mocked.
     * @param mockSettings the settings to use where only name and default answer are considered.
     */
    @SuppressWarnings("unused")
    public MockedStaticExtension(Class<T> classToMock, MockSettings mockSettings) {
        this.classToMock = classToMock;
        this.mockSettings = mockSettings;
    }

    /**
     * Create a new {@link MockedStaticExtension} instance.
     *
     * @param classToMock   class or interface of which static mocks should be mocked.
     * @param defaultAnswer default answer to be used by mock when not stubbed
     */
    @SuppressWarnings("unused")
    public MockedStaticExtension(Class<T> classToMock, Answer<?> defaultAnswer) {
        this.classToMock = classToMock;
        this.mockSettings = withSettings().defaultAnswer(defaultAnswer);
    }

    @Override
    public void beforeEach(ExtensionContext context) {
        this.mock = Mockito.mockStatic(classToMock, mockSettings);
    }

    @Override
    public void afterEach(ExtensionContext context) {
        this.mock.close();
    }

    @Override
    public <S> OngoingStubbing<S> when(Verification verification) {
        return mock.when(verification);
    }

    @Override
    public void verify(Verification verification) {
        mock.verify(verification);
    }

    @Override
    public void verify(Verification verification, VerificationMode mode) {
        mock.verify(verification, mode);
    }

    @Override
    public void reset() {
        mock.reset();
    }

    @Override
    public void clearInvocations() {
        mock.clearInvocations();
    }

    @Override
    public void verifyNoMoreInteractions() {
        mock.verifyNoMoreInteractions();
    }

    @Override
    public void verifyNoInteractions() {
        mock.verifyNoInteractions();
    }

    @Override
    public boolean isClosed() {
        return mock.isClosed();
    }

    @Override
    public void close() {
        mock.close();
    }

    @Override
    public void closeOnDemand() {
        mock.closeOnDemand();
    }
}
