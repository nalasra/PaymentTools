package org.hablo;

import java.io.BufferedWriter;
import java.io.File;

public abstract class ParserSupport {
    protected BufferedWriter writer;
    protected boolean outputParsedFile;
    protected abstract void parse(File file);
    protected void setWriter(BufferedWriter writer) {
        this.writer = writer;
    }
    protected void setOutputParsedFile(boolean outputParsedFile){
        this.outputParsedFile = outputParsedFile;
    }
}
