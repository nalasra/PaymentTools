package org.hablo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import org.hablo.mastercard.iso.MCCISParser;
import org.hablo.mastercard.t112.T112Parser;
import org.hablo.mastercard.util.DE108Parser;
import org.hablo.mastercard.util.DE110Parser;
import org.hablo.mastercard.util.DE48IPMParser;
import org.hablo.mastercard.util.DE61Parser;
import org.hablo.mastercard.util.ParserSupport;
import org.jpos.ee.BLException;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOUtil;
import org.jpos.q2.Q2;
import org.jpos.security.Exportability;
import org.jpos.security.SMException;
import org.jpos.security.SecureKeyBlock;
import org.jpos.security.SecureKeyBlockBuilder;
import org.jpos.security.jceadapter.SSM;
import org.jpos.util.Logger;

public class Main {

    static Logger l = Logger.getLogger("Q2");
    static String USER_DIR = System.getProperty("user.dir");
    static String FILES_DIR = "/src/dist/files/";
    static String VISA_FILES_DIR = "visa/";
    static String MC_FILES_DIR = "mastercard/";
    static String MADA_FILES_DIR = "mada/";

    static String BASEII_FILES_IN = USER_DIR + FILES_DIR + VISA_FILES_DIR + "baseii/in/";
    static String BASEII_FILES_OUT = USER_DIR + FILES_DIR + VISA_FILES_DIR + "baseii/out/";

    static String RAW_DATA_FILES_IN = USER_DIR + FILES_DIR + VISA_FILES_DIR + "raw/in/";
    static String RAW_DATA_FILES_OUT = USER_DIR + FILES_DIR + VISA_FILES_DIR + "raw/out/";

    static String T112_FILES_IN = USER_DIR + FILES_DIR + MC_FILES_DIR + "t112/in/";
    static String T112_FILES_OUT = USER_DIR + FILES_DIR + MC_FILES_DIR + "t112/out/";

    static String T057_FILES_IN = USER_DIR + FILES_DIR + MC_FILES_DIR + "t057/in/";
    static String T057_FILES_OUT = USER_DIR + FILES_DIR + MC_FILES_DIR + "t057/out/";

    static String T067_FILES_IN = USER_DIR + FILES_DIR + MC_FILES_DIR + "t067/in/";
    static String T067_FILES_OUT = USER_DIR + FILES_DIR + MC_FILES_DIR + "t067/out/";

    static String MADA_TLF_FILES_IN = USER_DIR + FILES_DIR + MADA_FILES_DIR + "tlf/in/";
    static String MADA_TLF_FILES_OUT = USER_DIR + FILES_DIR + MADA_FILES_DIR + "tlf/out/";

    static String ID = "/" + System.currentTimeMillis() + "/";

    public static void main(String[] args) {
        init();

        /* Mada (TLF) */
        //parseFile(TLFParser.class, MADA_TLF_FILES_IN, MADA_TLF_FILES_OUT, "");
        //parseFile(TLFParser.class, MADA_TLF_FILES_IN, MADA_TLF_FILES_OUT, "TLF04.20210321.SPAN.588850"); //TLF04.20210321.SPAN.588850

        /* Visa BASE II */
        //parseFile(BaseIIParser.class, BASEII_FILES_IN, BASEII_FILES_OUT,"");
        //parseFile(BaseIIParser.class, BASEII_FILES_IN, BASEII_FILES_OUT,"VISA_OUTCTF0322160157.CTF"); //VISAIN_BAE_410896_090921.txt
        //parseFile(BaseIIParser.class, BASEII_FILES_IN, BASEII_FILES_OUT,"VISA_OUTCTF0322160157.CTF"); //VISAIN_BAE_410896_090921.txt

        /* Mastercard IPM Clearing (T112)*/
        //parseFile(T112Parser.class, T112_FILES_IN, T112_FILES_OUT, "");
        //parseFile(T112Parser.class, T112_FILES_IN, T112_FILES_OUT, "mexico");
        //parseFile(T112Parser.class, T112_FILES_IN, T112_FILES_OUT, "jeeves/MCI.AR.T112.M.E0030014.D230222.T185119.A001");

        /* Mastercard Currency Exchange Rates (T057) */
        //parseFile(T057Parser.class, T057_FILES_IN, T057_FILES_OUT, "");
        //parseFile(T057Parser.class, T057_FILES_IN, T057_FILES_OUT, "TT057T0.2020-10-25-00-00-27.001");

        /* Mastercard IPM MPE (T067) */
        //parseFile(T067Parser.class, T067_FILES_IN, T067_FILES_OUT, "");
        //parseFile(T067Parser.class, T067_FILES_IN, T067_FILES_OUT, "TT067T0.2022-05-26-00-04-08.001");

        //parseRawMessage(VisaBaseIParser.class,"01 00 76 7e 66 81 28 f0 ba 16 10 42 77 69 00 69 46 44 73 00 00 00 00 00 00 30 00 00 00 00 00 00 24 87 10 04 09 27 52 98 29 00 00 25 29 94 12 27 52 10 04 25 06 10 05 73 99 04 04 05 10 00 00 00 06 45 87 84 25 04 27 76 90 06 94 64 47 3d 25 06 22 00 18 47 64 10 00 02 f2 f2 f7 f7 f1 f3 f2 f8 f0 f5 f1 f0 f0 f0 f1 f8 f0 f9 f4 f0 f8 f2 f0 f9 f6 f1 f9 f1 f1 f3 40 40 40 40 40 d9 c1 c9 d5 d3 c5 e7 40 c9 d5 e5 c5 e2 e3 d4 c5 d5 e3 40 40 40 40 40 40 40 d5 c1 c9 d9 d6 c2 c9 40 40 40 40 40 40 d2 c5 05 40 40 40 40 f2 04 04 08 40 24 60 7d 83 ae 28 01 84 20 01 01 01 00 00 00 00 5f 01 00 5c 9f 34 03 42 03 00 9f 33 03 e0 f8 e8 95 05 00 80 04 80 00 9f 37 04 ea 36 cf b0 9f 10 07 06 01 12 03 a0 30 02 9f 26 08 d5 9d 73 df 43 35 fc e6 9f 36 02 00 03 82 02 38 00 9c 01 00 9f 1a 02 06 43 9a 03 22 10 04 9f 02 06 00 00 00 30 00 00 5f 2a 02 04 04 9f 03 06 00 00 00 00 00 00 04 05 00 00 10 10 40 00 00 00 00 00 00 00 03 82 27 73 40 72 84 81 05 80 00 00 00 02");
        //parseRawMessage(VisaBaseIParser.class,"0100fefd66912ae0e216000000000000000410476134000000001900000000000000400000000002500000000002500009191351156100000061000000000028175115091922120000597208400120000108c4f0f0f0f0f0f0f0f00b012345678901204761340000000019d221212312345129f0f2f6f3f1f3f0f0f0f0f2f8f9f6e3c5d9d4c9c4f0f1c3c1d9c440c1c3c3c5d7e3d6d94040c1c3d8e4c9d9c5d940d5c1d4c5404040404040404040404040c3c9e3e840d5c1d4c540404040e4e2084008400840690100669f3303204000950580000100009f37049badbcab9f100706011203a000009f2608696ab26dc89ca9b79f360200ff820200009c01019f1a0208409a032011179f02060000000123005f2a0208409f03060000000000009f6e04000000018407a000000003101001001a3030303031303030303030303030303030303030353330303030098100000002123456788e34363046303030303030303030303030303046314631463146314631463146314631303930303030303030303030303030303030303030303030303232323232323232323041303030303030303030303030303030303030303030303938373635343332313031323334353637383930313233343536373839303132333435363738393031323334353637383930");
        //parseRawMessage(VisaBaseIParser.class,"01007EFD669128E0FA1610443422702575617901000000000002200000000002500000000002500003081605206100000061000000000285200520030823010000601108400510000100C4F0F0F0F0F0F0F0F00B012345678901204434227025756179D230112312345129F1F0F6F7F1F6F0F0F0F2F8F5C1E3D4F0F1404040C3C1D9C440C1C3C3C5D7E3D6D94040C1C3D8E4C9D9C5D940D5C1D4C5404040404040404040404040C3C9E3E840D5C1D4C540404040E4E20840084008404DEAB7B53DF1885120010101000000006201005F9F3303204000950580000100009F37049BADBCAB9F100706011203A000009F260822446688224466889F360200FF820200009C01019F1A0208409A032011179F02060000000123005F2A0208409F03060000000000008407A00000000310100425000010098000000000000000E8068000200002F0");

        //parseRawMessage(MCCISParser.class, "F0F8F0F0C2200000800000020400000000040000F0F4F0F0F0F1F1F1F0F3F1F3F2F1F3F9F0F0F0F0F1F0F0F6F0F2F2F0F2F0F0F0F9D4C3C3F0F1F1F0F5D3F1F6F1F0F9F6F0F9F0F8F0C2F0F0F8F0D7F0E3C2F0F0E2F0F0F0F0C4C6C6F2F1F6C2F3C3C6F8C2C4F0F7C4F6F2C3F1C6C1F9F8F8C5F6C3F9F9F4F4C6F4F8F8F8F6C2F9F7C6C4F4F4F5F2F8F7F2C6F2C3F6C2F1C3F3F3F4C6F0C4F7F1F0F0F0F6F5F8C4F4C3F6");
        //parseRawMessage(MCCISParser.class, "F0F1F0F07EFF444128E1F80AF1F6F5F2F0F4F7F3F0F0F0F0F0F0F0F0F1F3F0F0F0F0F0F0F0F0F0F0F0F0F0F0F8F5F0F0F0F0F0F0F0F0F0F0F8F5F0F0F0F0F0F0F0F0F0F0F8F5F0F0F0F2F2F1F1F7F3F7F2F0F6F1F0F0F0F0F0F0F6F1F0F0F0F0F0F0F0F0F0F0F0F2F1F7F3F7F2F0F0F2F2F1F1F8F1F2F0F2F2F1F0F2F2F1F5F9F9F9F9F0F1F0F4F0F6F0F0F9F6F8F5F3F2F5F2F0F4F7F3F0F0F0F0F0F0F0F0F1F3C4F1F8F1F2F1F0F1F0F1F0F0F0F1F1F1F1F2F3F4F5F6F7F8F9F0F1F2D98587F0F0F0F0F4F6F8F7F5F5F5F5F3F7F8F7F7F4F6F4D48984A685A2A340C59497969989A49440404040404040C39693A494828981404040404040D4D640F0F0F7D9F8F0F0F2E3E5F8F4F0F8F4F0F8F4F03F8DF3A90A6C9D81F9F7F0F1F0F1F0F0F0F1F0F0F0F0F0F0F0F2F1F0F0F0F0F0F0F0F0F0F0F2F0F0F8F4F0F9F0F2F1F0F0F0F9D4C3E2F0F1F1F0F6F1");

        //parseDE(DE48Parser.class, "0100", 48, "R15100313114225");
        //parseDE(DE48Parser.class, "0100", 48, "Z37340111000000123450315A-00009876543218002TV"); //Additional Merchant Data
        //parseDE(DE48Parser.class, "0100", 48, "T420701032124328kBNprVWW0r2tzwggEy9l7TkhY+EW66450101202365019c1b5-a061-4ee0-a9a3-672a06305088");
        //parseDE(DE48Parser.class, "0100", 48, "T420701032114328hJJLtQa+Iws8AREAEbjsA1MAAAA=660501011"); //SPA1Attempt
        //parseDE(DE48Parser.class, "0100", 48, "T420701032124328jJJLtQa+Iws8AREAEbjsBkEAAAA=660501011"); //SPA1FullyAuthenticated
        //parseDE(DE48Parser.class, "0100", 48, "T420701032114328kLAfdvwQTySUPJTvhAEAABrOxpFx6645010120236F38E6948-5388-41A6-BCA4-B49723C19437"); //SPA2
        //parseDE(DE48Parser.class, "0100", 48, "T010120236F38E6948-5388-41A6-BCA4-B49723C19437420701032174328kOWg7cqnaqcoEgABlDyU78kgL6sa6315MCCA1B2C3  66456645010120236F38E6948-5388-41A6-BCA4-B49723C19437"); //SPA2Partial
        //parseDE(DE48Parser.class, "0800", 48, "1154PK0001A8B5474DBEF0FBC689707EE1C6329CE658D4            ");
        //parseDE(DE48IPMParser.class, "0100", 48, "0002003MSO0003003MSO0148008826282620158030MSI4826001S212061402     NNNNN0165001M0177001N0191001201590679570       0942027400                  1EU00000008N2104190121041901");
        //parseDE(DE61Parser.class, "0100", 61, "102510800600084063129-5210");
        //parseDE(DE108Parser.class, "0100", 108,"010640109FIRSTNAME0308LASTNAME0703USA1122123456789+123456789012180202020690109FIRSTNAME0308LASTNAME04121234 MAIN ST0703USA1111123456789101802050302301190877775555580121530");
        //parseDE(DE110Parser.class, "0100", 110,"09080B0080P0TB00S000082E9F773BCFF20772A6D292A45F5F4C97EAD3C519679D1E95AC6A9E509F7BEE21000658D4C6"); //key block DKE

        /* TCP Socket Channels
         * For VTS/MAS Simulator
         * */
        //startQ2();
        //startQ2CLI();

        //keyblock();
        //keyblockParser();
    }

    private static void init() {
        ID = "/" + System.currentTimeMillis() + "/";
        //l.addListener(new SimpleLogListener());
    }

    private static void startQ2() {
        Q2 q2 = new Q2("src/dist/deploy");
        q2.start();
    }

    private static void startQ2CLI() {
        Q2 q2 = new Q2(new String[]{
                "-i"
        });
        q2.start();
    }

    public static File[] getFiles(String desiredFile, String parentFolder) throws Exception {
        File[] fs;
        File fp;
        //process all files under parent
        if (desiredFile == null || desiredFile.isEmpty()) {

            List<Path> filePaths = Files
                    .find(Paths.get(parentFolder), Integer.MAX_VALUE, (filePath, fileAttr) -> fileAttr.isRegularFile())
                    .collect(Collectors.toList());

            File[] files = new File[filePaths.size()];
            int i = 0;
            for (Path p : filePaths) {
                files[i] = p.toFile();
                i++;
            }
            return files;
        } else {
            //process desired file
            fp = new File(parentFolder + desiredFile);
            if (!fp.exists()) {
                throw new FileNotFoundException();
            }
            //process desired folder
            if (fp.isDirectory()) {
                fs = fp.listFiles();
            } else {
                fs = new File[]{fp};
            }
        }
        return fs;
    }

    public static BufferedWriter initializeWriter(String path, String fileName) throws IOException {
        File theDir = new File(path + ID);
        if (!theDir.exists()) {
            theDir.mkdirs();
        }
        return new BufferedWriter(new FileWriter(path + ID + fileName + ".txt"));
    }

    public static <T> void parseFile(Class<T> clazz, String fileIn, String fileOut, String fileName) {
        try {
            File[] fs = getFiles(fileName, fileIn);
            if (fs != null) {
                int index = 1;
                System.out.println("Total files found " + fs.length);
                for (File f : fs) {
                    if (f.isFile()) {
                        System.out.println(
                                String.format("Processing file... %d/%d %s", index, fs.length, f.getAbsolutePath()));
                        BufferedWriter writer = initializeWriter(fileOut, f.getName());
                        T parser = clazz.newInstance();
                        if (parser instanceof FileParserSupport) {
                            FileParserSupport fps = (FileParserSupport) parser;
                            fps.setOutputParsedFile(true);
                            fps.setWriter(writer);
                            fps.parse(f);
                        }
                        writer.close();
                    }
                    index++;
                }
            }
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    public static <T> void parseRawMessage(Class<T> clazz, String hexString) {
        try {
            T o = clazz.newInstance();
            if (o instanceof ISOMsgParserSupport) {
                byte[] d = ISOUtil.hex2byte(hexString.replace(" ", ""));
                ISOMsg m = ((ISOMsgParserSupport) o).parse(d);
            } else {
                System.out.println("Unknown class type: " + clazz.getSimpleName());
            }
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> void parseDE(Class<T> clazz, String mti, int key, String data) {
        try {
            ISOMsg m = new ISOMsg();
            m.setMTI(mti);
            m.set(key, data);
            T o = clazz.newInstance();
            if (o instanceof ParserSupport) {
                ((ParserSupport) o).parse(m);
                ((ParserSupport) o).dump(System.out, "");
            } else {
                System.out.println("Unknown class type: " + clazz.getSimpleName());
            }
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (BLException | ISOException blException) {
            blException.printStackTrace();
        }
    }

    public static void keyblockParser() {
        try {
            String keyblock = "B0080P0TB00S0000AC12A687FD4F8637C77C8ABBDB667A2BF338B6B9264A7EBFA380D605C2232F12";
            SecureKeyBlock skb = SecureKeyBlockBuilder.newBuilder().build(keyblock);
            skb.dump(System.out, "");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void keyblock() {
        String kek = "13AED5DA1F32347523C708C11F2608FD"; //clear ZMK
        String pek = "0170F175468FB5E60213233243526273";
        String random = "F6DF85BC2043";

        SSM ssm;
        try {
            ssm = new SSM("src/dist/cfg/test.lmk");
        } catch (SMException e) {
            throw new RuntimeException(e);
        }
        String se11009 = ssm.encryptKeyTR31(pek, kek, random, Exportability.TRUSTED);
        String se11010;
        try {
            se11010 = ISOUtil.hexString(ssm.generateKeyCheckValue(SSM.getSecureKey(pek, 128, "ZPK", "")));
        } catch (SMException e) {
            throw new RuntimeException(e);
        }

        System.out.println("############ ENCRYPTION ############");
        System.out.println("KEK = [" + kek + "]");
        System.out.println("Clear PEK: [" + pek + "]");
        System.out.println("TR-31 Keyblock = [" + se11009 + "]");
        //System.out.println("DE110.TLV.09 = [" + se11009 + "]");
        //System.out.println("DE110.TLV.10 = [" + se11010 + "]");
        //System.out.println("DE-110 = [" + "090" + se11009.length() + se11009 + "1000" + se11010.length() + se11010 + "]");

        System.out.println("############ DECRYPTION ############");
        System.out.println("KEK = [" + kek + "]");
        System.out.println("TR-31 Keyblock = [" + se11009 + "]");
        ////String block = "B0080P0TB00S0000AC12A687FD4F8637C77C8ABBDB667A2BF338B6B9264A7EBFA380D605C2232F12";
        String pekd = ssm.decryptKeyTR31(se11009, kek);
        System.out.println("Clear PEK: [" + pekd + "]");
    }
}