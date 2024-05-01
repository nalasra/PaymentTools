package org.hablo;

import java.io.BufferedWriter;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.jpos.iso.ISOMsg;

public abstract class FileParserSupport {

    protected BufferedWriter writer;
    protected BufferedWriter summaryWriter;
    protected boolean outputParsedFile;
    protected List<ISOMsg> messages;
    protected String outputDir;
    protected String sessionId;

    public abstract void parse(File file);


    public void setWriter(BufferedWriter writer) {
        this.writer = writer;
    }

    public void setSummaryWriter(BufferedWriter summaryWriter) {
        this.summaryWriter = summaryWriter;
    }

    public void addISOMessage(ISOMsg t) {
        getISOMessages().add(t);
    }

    public List<ISOMsg> getISOMessages() {
        if (messages == null) {
            messages = new ArrayList<>();
        }
        return messages;
    }

    public void setOutputParsedFile(boolean outputParsedFile) {
        this.outputParsedFile = outputParsedFile;
    }

    public void setOutputDir(String outputDir) {
        this.outputDir = outputDir;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
