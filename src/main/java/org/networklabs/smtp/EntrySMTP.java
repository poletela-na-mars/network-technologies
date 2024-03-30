package org.networklabs.smtp;

import java.io.IOException;

public class EntrySMTP {
    public static void start() throws IOException {
        try (var smtpClient = new SMTPClient()) {
            smtpClient.openConnection("smtp.gmail.com", 10000, 25);
            smtpClient.send("network@test.test", "network2@test.test", "Say hello");
        }
    }
}
