package com.sedooe.actuator.config;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.actuate.trace.TraceProperties;
import org.springframework.boot.actuate.trace.TraceRepository;
import org.springframework.boot.actuate.trace.WebRequestTraceFilter;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import org.springframework.web.util.WebUtils;

@Component
public class RequestTraceFilter extends WebRequestTraceFilter {

    private final String[] excludedEndpoints = new String[] { "/css/**", "/js/**", "/trace" };
    private static final String RESPONSE_BODY = "resBody";
    private static final String REQUEST_BODY = "reqBody";

    RequestTraceFilter(TraceRepository repository, TraceProperties properties) {
        super(repository, properties);
    }

    private String getRequestBody(ContentCachingRequestWrapper request) {
        ContentCachingRequestWrapper wrapper = WebUtils.getNativeRequest(request, ContentCachingRequestWrapper.class);
        String characterEncoding = wrapper.getCharacterEncoding();
        return getPayload(wrapper.getContentAsByteArray(), characterEncoding);
    }

    @Override
    protected Map<String, Object> getTrace(HttpServletRequest request) {
        Map<String, Object> trace = super.getTrace(request);
        Object requestBody = request.getAttribute(REQUEST_BODY);
        Object responseBody = request.getAttribute(RESPONSE_BODY);
        if (requestBody != null) {
            trace.put(REQUEST_BODY, requestBody);
        }
        if (responseBody != null) {
            trace.put(RESPONSE_BODY, responseBody);
        }
        return trace;
    }

    public String getPayload(byte[] buf, String characterEncoding) {
        String payload = null;
        if (buf.length > 0) {
            try {
                payload = new String(buf, 0, buf.length, characterEncoding);
            } catch (UnsupportedEncodingException ex) {
                payload = "[unknown]";
            }
        }
        return payload;
    }

    private String getResponseBody(ContentCachingResponseWrapper response) {
        ContentCachingResponseWrapper wrapper = WebUtils.getNativeResponse(response,
                ContentCachingResponseWrapper.class);
        return getPayload(wrapper.getContentAsByteArray(), wrapper.getCharacterEncoding());
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);
        filterChain.doFilter(requestWrapper, responseWrapper);
        responseWrapper.copyBodyToResponse();
        request.setAttribute(REQUEST_BODY, getRequestBody(requestWrapper));
        request.setAttribute(RESPONSE_BODY, getResponseBody(responseWrapper));
        super.doFilterInternal(requestWrapper, responseWrapper, filterChain);
    }

//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//            throws ServletException, IOException {
//        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
//        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);
//
//        TeeHttpServletResponse teeResponse = new TeeHttpServletResponse(responseWrapper);
//
//        filterChain.doFilter(responseWrapper, responseWrapper);
//
//        teeResponse.finish();
//
//        request.setAttribute("responseBody", teeResponse.getOutputBuffer());
//
//        super.doFilterInternal(request, teeResponse, filterChain);
//    }
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//            throws ServletException, IOException {
//        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
//        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);
//        filterChain.doFilter(requestWrapper, responseWrapper);
//        responseWrapper.copyBodyToResponse();
//        request.setAttribute(REQUEST_BODY, getRequestBody(requestWrapper));
//        request.setAttribute(RESPONSE_BODY, getResponseBody(responseWrapper));
//        super.doFilterInternal(requestWrapper, responseWrapper, filterChain);
//    }

//    @Override
//    protected Map<String, Object> getTrace(HttpServletRequest request) {
//        Map<String, Object> trace = super.getTrace(request);
//
//        byte[] outputBuffer = (byte[]) request.getAttribute("responseBody");
//
//        if (outputBuffer != null) {
//            trace.put("responseBody", new String(outputBuffer));
//        }
//
//        return trace;
//    }

    @Override
    protected boolean shouldNotFilter(final HttpServletRequest request) throws ServletException {
        return Arrays.stream(excludedEndpoints).anyMatch(e -> new AntPathMatcher().match(e, request.getServletPath()));
    }
}