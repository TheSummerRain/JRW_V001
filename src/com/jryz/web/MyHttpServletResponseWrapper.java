package com.jryz.web;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class MyHttpServletResponseWrapper extends HttpServletResponseWrapper {

    private List<Byte> byteList = new ArrayList<>();

    public MyHttpServletResponseWrapper(HttpServletResponse response) {
        super(response);
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        ServletOutputStream response = super.getOutputStream();
        ServletOutputStream stream = new ServletOutputStream() {
            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setWriteListener(WriteListener listener) {

            }

            @Override
            public void write(int b) throws IOException {
                byteList.add((byte) b);
                response.write(b);
            }
        };
        return stream;
    }

    @Override
    public String toString() {
        byte[] bytes = new byte[byteList.size()];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = byteList.get(i);
        }
        return new String(bytes, 0, bytes.length, Charset.forName("UTF-8"));
    }
}