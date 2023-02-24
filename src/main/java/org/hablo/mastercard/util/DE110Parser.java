package org.hablo.mastercard.util;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

public class DE110Parser extends GenericTLVParser {
    private static final Map<String, String> allowedSF = new HashMap<>();

    static {
    }

    public DE110Parser() {
        super(110, 2, 3, "SE", 0, 10);
    }

    @Override
    public void dump(PrintStream p, String indent) {
        p.println(indent + getClass().getName() + " value='" + sourceTLVData + "'");
        super.dump(p, indent);
    }
}
