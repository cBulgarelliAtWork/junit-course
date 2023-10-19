package com.overit.junitcourse.example4;

import com.overit.junitcourse.example3.*;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.overit.junitcourse.example4.NewsletterSendingServiceImpl.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NewsletterSendingServiceImplTest {

    @Spy
    @InjectMocks
    private NewsletterSendingServiceImpl spiedSut;
    @Mock
    private UserService userService;
    @Mock
    private EmailSendingService emailSendingService;
    @Mock
    private EmailValidator emailValidator;
    private EasyRandom easyRandom;

    @BeforeEach
    public void setUp() {
        easyRandom = new EasyRandom();
    }

    @Test
    void sendNewsletterToMen_UserServiceThrowsEmptyUserException_ANewsletterSendingExceptionIsThrown() {
        // given
        List<User> recipients = easyRandom.objects(User.class, 3).toList();
        String subject = easyRandom.nextObject(String.class);
        String body = easyRandom.nextObject(String.class);
        EmptyUsersException expectedException = easyRandom.nextObject(EmptyUsersException.class);
        when(userService.filterByGender(Gender.MALE, recipients)).thenThrow(expectedException);
        // when
        assertThatThrownBy(() -> spiedSut.sendNewsletterToMen(recipients, subject, body))
                .isInstanceOf(NewsletterSendingException.class)
                .hasMessage(EMPTY_USERS_LIST_HAS_BEEN_PROVIDED)
                .hasRootCause(expectedException);
        // then
        verify(userService).filterByGender(Gender.MALE, recipients);
        verify(spiedSut, never()).validateEmail(anyString());
        verify(emailSendingService, never()).sendEmail(eq(SENDER_EMAIL), anyString(), anyString(), anyString());
    }

    @Test
    void sendNewsletterToMen_UserServiceThrowsNoUsersFoundException_ANewsletterSendingExceptionIsThrown() {
        // given
        List<User> recipients = easyRandom.objects(User.class, 3).toList();
        String subject = easyRandom.nextObject(String.class);
        String body = easyRandom.nextObject(String.class);
        NoUsersFoundException expectedException = easyRandom.nextObject(NoUsersFoundException.class);
        when(userService.filterByGender(Gender.MALE, recipients)).thenThrow(expectedException);
        // when
        assertThatThrownBy(() -> spiedSut.sendNewsletterToMen(recipients, subject, body))
                .isInstanceOf(NewsletterSendingException.class)
                .hasMessage(NO_USERS_HAVE_BEEN_FOUND)
                .hasRootCause(expectedException);
        // then
        verify(userService).filterByGender(Gender.MALE, recipients);
        verify(spiedSut, never()).validateEmail(anyString());
        verify(emailSendingService, never()).sendEmail(eq(SENDER_EMAIL), anyString(), anyString(), anyString());
    }

    @Test
    void sendNewsletterToMen_ValidateEmailThrowsInvalidEmailException_ANewsletterSendingExceptionIsThrown() {
        // given
        List<User> recipients = easyRandom.objects(User.class, 3).toList();
        List<User> menRecipients = easyRandom.objects(User.class, 3).toList();
        String subject = easyRandom.nextObject(String.class);
        String body = easyRandom.nextObject(String.class);
        InvalidEmailException expectedException = easyRandom.nextObject(InvalidEmailException.class);
        when(userService.filterByGender(Gender.MALE, recipients)).thenReturn(menRecipients);
        doThrow(expectedException).when(spiedSut).validateEmail(menRecipients.get(0).getEmail());
        // when
        assertThatThrownBy(() -> spiedSut.sendNewsletterToMen(recipients, subject, body))
                .isInstanceOf(NewsletterSendingException.class)
                .hasMessage(INVALID_E_MAIL_FOUND)
                .hasRootCause(expectedException);
        // then
        verify(userService).filterByGender(Gender.MALE, recipients);
        verify(spiedSut).validateEmail(menRecipients.get(0).getEmail());
        verify(emailSendingService, never()).sendEmail(eq(SENDER_EMAIL), anyString(), anyString(), anyString());
    }

    @Test
    void sendNewsletterToMen_SendEmailReturnsOneFalse_FalseIsReturned() {
        // given
        List<User> recipients = easyRandom.objects(User.class, 3).toList();
        List<User> menRecipients = easyRandom.objects(User.class, 3).toList();
        String subject = easyRandom.nextObject(String.class);
        String body = easyRandom.nextObject(String.class);
        when(userService.filterByGender(Gender.MALE, recipients)).thenReturn(menRecipients);
        doNothing().when(spiedSut).validateEmail(anyString());
        when(emailSendingService.sendEmail(eq(SENDER_EMAIL), anyString(), anyString(), anyString())).thenReturn(true);
        when(emailSendingService.sendEmail(SENDER_EMAIL, menRecipients.get(1).getEmail(), subject, body)).thenReturn(false);
        // when
        boolean actual = spiedSut.sendNewsletterToMen(recipients, subject, body);
        // then
        assertThat(actual).isFalse();
        verify(userService).filterByGender(Gender.MALE, recipients);
        verify(spiedSut).validateEmail(menRecipients.get(0).getEmail());
        verify(spiedSut).validateEmail(menRecipients.get(1).getEmail());
        verify(spiedSut).validateEmail(menRecipients.get(2).getEmail());
        verify(emailSendingService).sendEmail(SENDER_EMAIL, menRecipients.get(0).getEmail(), subject, body);
        verify(emailSendingService).sendEmail(SENDER_EMAIL, menRecipients.get(1).getEmail(), subject, body);
        verify(emailSendingService).sendEmail(SENDER_EMAIL, menRecipients.get(2).getEmail(), subject, body);
    }

    @Test
    void sendNewsletterToMen_SendEmailAlwaysReturnsTrue_TrueIsReturned() {
        // given
        List<User> recipients = easyRandom.objects(User.class, 3).toList();
        List<User> menRecipients = easyRandom.objects(User.class, 3).toList();
        String subject = easyRandom.nextObject(String.class);
        String body = easyRandom.nextObject(String.class);
        when(userService.filterByGender(Gender.MALE, recipients)).thenReturn(menRecipients);
        doNothing().when(spiedSut).validateEmail(anyString());
        when(emailSendingService.sendEmail(eq(SENDER_EMAIL), anyString(), anyString(), anyString())).thenReturn(true);
        // when
        boolean actual = spiedSut.sendNewsletterToMen(recipients, subject, body);
        // then
        assertThat(actual).isTrue();
        verify(userService).filterByGender(Gender.MALE, recipients);
        verify(spiedSut).validateEmail(menRecipients.get(0).getEmail());
        verify(spiedSut).validateEmail(menRecipients.get(1).getEmail());
        verify(spiedSut).validateEmail(menRecipients.get(2).getEmail());
        verify(emailSendingService).sendEmail(SENDER_EMAIL, menRecipients.get(0).getEmail(), subject, body);
        verify(emailSendingService).sendEmail(SENDER_EMAIL, menRecipients.get(1).getEmail(), subject, body);
        verify(emailSendingService).sendEmail(SENDER_EMAIL, menRecipients.get(2).getEmail(), subject, body);
    }

    @Test
    void validateEmail_ValidateEmailThrowsInvalidMailException_InvalidEmailExceptionIsThrown() {
        // given
        String email = easyRandom.nextObject(String.class);
        InvalidEmailException expectedException = easyRandom.nextObject(InvalidEmailException.class);
        doThrow(expectedException).when(emailValidator).validateEmail(email);
        // when
        assertThatThrownBy(() -> spiedSut.validateEmail(email))
                .isInstanceOf(InvalidEmailException.class)
                .isSameAs(expectedException);
        // then
        verify(emailValidator).validateEmail(email);
    }

    @Test
    void validateEmail_ValidateEmailDoesNotThrowInvalidMailException_NoExceptionsAreThrown() {
        // given
        String email = easyRandom.nextObject(String.class);
        doNothing().when(emailValidator).validateEmail(email);
        // when
        assertDoesNotThrow(() -> spiedSut.validateEmail(email));
        // then
        verify(emailValidator).validateEmail(email);
    }
}
