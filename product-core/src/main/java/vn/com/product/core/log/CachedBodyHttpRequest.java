package vn.com.product.core.log;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import lombok.Getter;
import org.apache.commons.io.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;

@Getter
public class CachedBodyHttpRequest extends HttpServletRequestWrapper {
    private byte[] cache;

    public CachedBodyHttpRequest(HttpServletRequest request) {
        super(request);

        try {
            ServletInputStream inputStream = super.getInputStream();
            cache = IOUtils.toByteArray(inputStream);
        } catch (IOException e) {
            cache = new byte[0];
        }
    }

    @Override
    public ServletInputStream getInputStream() {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(cache);

        return new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return inputStream.available() == 0;
            }

            @Override
            public boolean isReady() {
                return true;
            }

            @Override
            public int read() {
                return inputStream.read();
            }

            @Override
            public void setReadListener(ReadListener readListener) {
                throw new UnsupportedOperationException();
            }
        };
    }
}
