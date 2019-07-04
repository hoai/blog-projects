package com.lampart.actuator.config;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class TeeHttpServletResponse extends HttpServletResponseWrapper {

    TeeServletOutputStream teeServletOutputStream;
    PrintWriter teeWriter;

    public TeeHttpServletResponse(HttpServletResponse httpServletResponse) {
        super(httpServletResponse);
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        if (teeServletOutputStream == null) {
            teeServletOutputStream = new TeeServletOutputStream(this.getResponse());
        }
        return teeServletOutputStream;
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        if (teeWriter == null) {
            teeWriter = new PrintWriter(
                    new OutputStreamWriter(getOutputStream(), this.getResponse().getCharacterEncoding()), true);
        }
        return teeWriter;
    }

    @Override
    public void flushBuffer() {
        if (teeWriter != null) {
            teeWriter.flush();
        }
    }

    byte[] getOutputBuffer() {
        // teeServletOutputStream can be null if the getOutputStream method is never
        // called.
        if (teeServletOutputStream != null) {
            return teeServletOutputStream.getOutputStreamAsByteArray();
        } else {
            return null;
        }
    }

    void finish() throws IOException {
        if (teeWriter != null) {
            teeWriter.close();
        }
        if (teeServletOutputStream != null) {
            teeServletOutputStream.close();
        }
    }
}