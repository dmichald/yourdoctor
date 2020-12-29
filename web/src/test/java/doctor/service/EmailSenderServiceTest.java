package doctor.service;

import com.icegreen.greenmail.store.FolderException;
import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;
import org.junit.jupiter.api.*;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("EmailSenderService should")
class EmailSenderServiceTest {

    private static JavaMailSenderImpl sender = new JavaMailSenderImpl();
    private static EmailSenderService emailService;


    private static GreenMail greenMail;

    @BeforeAll
    void setupSMTP() {
        sender.setPort(2525);
        sender.setProtocol("smtp");
        emailService = new EmailSenderService(sender);
        greenMail = new GreenMail(new ServerSetup(2525, "127.0.0.1", "smtp"));
        greenMail.start();
    }

    @AfterAll
    void tearDownSMTP() {
        greenMail.stop();
    }

    @AfterEach
    void cleanup() throws FolderException {
        greenMail.purgeEmailFromAllMailboxes();
    }

    @Test
    @DisplayName("send email")
    void testSendEmail() throws MessagingException {
        String SUBJECT = "Test";
        final SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo("test@localhost.pl");
        mailMessage.setSubject(SUBJECT);
        mailMessage.setFrom("<MAIL>");
        mailMessage.setText("Content");
        emailService.sendEmail(mailMessage);

        boolean ok = greenMail.waitForIncomingEmail(1);

        if (ok) {
            MimeMessage testMessage = greenMail.getReceivedMessages()[0];
            assertEquals(testMessage.getSubject(), SUBJECT);

        } else {
            Assertions.fail("email not sent");
        }
    }
}
