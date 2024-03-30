package org.networklabs;

import org.networklabs.icmppinger.EntryICMP;
import org.networklabs.proxyserver.EntryProxyServer;
import org.networklabs.smtp.EntrySMTP;
import org.networklabs.updpinger.EntryUDP;
import org.networklabs.webserver.EntryWebServer;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        // 1. Web server
//        EntryWebServer.start();

        // 2. UPD-pinger
//        EntryUDP.start();

        // 3. SMTP
//        EntrySMTP.start();

        // 4. ProxyServer
//        EntryProxyServer.start();

        // 5. ICMPPinger
        EntryICMP.start();
    }
}
