package org.networklabs.icmppinger;

import java.io.IOException;

public class EntryICMP {
    public static void start() throws IOException {
        var icmpPinger = new ICMPPinger("www.upv.es", 4);
        icmpPinger.ping();
    }
}
