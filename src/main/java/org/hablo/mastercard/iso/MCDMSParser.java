package org.hablo.mastercard.iso;

import java.util.ArrayList;
import java.util.List;

import org.hablo.ISOMsgParserSupport;
import org.hablo.mastercard.util.DE110Parser;
import org.hablo.mastercard.util.DE48Parser;
import org.hablo.mastercard.util.DE61Parser;
import org.hablo.mastercard.util.DEParserSupport;
import org.jpos.ee.BLException;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.packager.GenericPackager;

public class MCDMSParser implements ISOMsgParserSupport {

    static GenericPackager packager;
    static List<Class> mcDEParsers = new ArrayList<>();

    static {
        mcDEParsers.add(DE61Parser.class);
        mcDEParsers.add(DE48Parser.class);
        mcDEParsers.add(DE110Parser.class);
    }

    public ISOMsg parse(byte[] d) {
        ISOMsg m = null;
        try {
            packager = new GenericPackager("jar:packager/mc_dms.xml");
            m = packager.createISOMsg();
            m.setPackager(packager);
            m.unpack(d);
            m.dump(System.out, "");
            if (mcDEParsers != null) {
                System.out.println("----- Parsers ------");
                expandDE(m, mcDEParsers);
            }
        } catch (ISOException | BLException e) {
            System.out.println("Exception occurred...");
            e.printStackTrace();
        }
        return m;
    }


    public <T> void expandDE(ISOMsg m, List<Class> dEParsers) throws BLException, ISOException {
        for (Class<T> c : dEParsers) {
            try {
                T o = c.getDeclaredConstructor(new Class[]{ISOMsg.class}).newInstance(m);
                if (o instanceof DEParserSupport) {
                    ((DEParserSupport) o).dump(System.out, "");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
