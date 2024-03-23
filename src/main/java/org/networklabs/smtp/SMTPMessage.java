package org.networklabs.smtp;

class SMTPMessage {
    final String fromAddress;
    final String toAddress;
    final String data;

    SMTPMessage(String fromAddress, String toAddress, String data) {
        this.fromAddress = fromAddress;
        this.toAddress = toAddress;
        this.data = data;
    }
}
