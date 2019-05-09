package com.gblau.controller.base;

import com.gb.common.util.Log;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.net.*;
import java.util.Enumeration;
import java.util.List;

/**
 * @author gblau
 * @date 2017-05-14
 */
public class BaseController {
    protected static Logger log = Log.get(BaseController.class);

    /**
     * 通过抛出异常的方式校验
     * @param datas
     * @param <T>
     * @throws MissingServletRequestParameterException
     */
    protected <T> void validateRequestParameter(T... datas) throws MissingServletRequestParameterException {
        for (T data : datas)

            if (data == null)
                throw new MissingServletRequestParameterException("param", "Object");

            else if (data instanceof String && StringUtils.isEmpty((String) data))
                throw new MissingServletRequestParameterException(data.getClass().getName(), "String");

            else if (data instanceof MultipartFile && isTooLarge((MultipartFile) data))
                throw new MissingServletRequestParameterException("image", "file larger than 300k so ");

            else if (data instanceof Integer && (Integer) data == 0)
                throw new MissingServletRequestParameterException(data.getClass().getName(), "Integer");

            else if (data instanceof List && ((List) data).size() == 0)
                throw new MissingServletRequestParameterException(data.getClass().getName(), "List");
    }

    protected boolean isTooLarge(MultipartFile... images) {
        for (MultipartFile image : images)
            if (image.getSize() > 3096_00) //300k
                return true;
        return false;
    }

    public static String getClientIp(final HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        Log.info(log, "X-Forwarded-For: " + ip);
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
            Log.info(log, "Proxy-Client-IP: " + ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
            Log.info(log, "WL-Proxy-Client-IP: " + ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
            Log.info(log, "HTTP_CLIENT_IP: " + ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            Log.info(log, "HTTP_X_FORWARDED_FOR :" + ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            Log.info(log, "remote: " + ip);
        }
        return ip;
    }

    /**
     * linux下返回的是/etc/hosts中配置的localhost的ip地址，而不是网卡的绑定地址。在网卡处理未激活状态下，直接获取配置地址会返回空或127.0.0.1
     * 从java里获取本机ip，要考虑以下两种情况：
     * 1. 在网卡处理激活状态下，直接使用InetAddress.getHostAddress();方法无法获取ip。
     * 2. 若不论网卡是否处理激活状态，想获取到本机所有的网卡的所有的IP（可能不只有一个网卡），则可用JDK6.0开始的
     *    NetworkInterface.getNetworkInterfaces();就可获取到本机 所有的网卡的所有的IP（可能不只有一个网卡）了。
     * Created by gblau on 2017-02-10.
     * @param preferIpv4
     * @param preferIPv6
     * @return
     */
    public static String getFirstNonLoopbackIP(boolean preferIpv4, boolean preferIPv6) {
        try {
            Enumeration<?> enumeration = NetworkInterface.getNetworkInterfaces();
            while (enumeration.hasMoreElements()) {
                NetworkInterface i = (NetworkInterface) enumeration.nextElement();
                for (Enumeration<?> inetAddressEnumeration = i.getInetAddresses(); inetAddressEnumeration.hasMoreElements(); ) {
                    InetAddress inetAddress = (InetAddress) inetAddressEnumeration.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        if (inetAddress instanceof Inet4Address) {
                            if (preferIPv6)
                                continue;
                            return inetAddress.getHostAddress();
                        }
                        if (inetAddress instanceof Inet6Address) {
                            if (preferIpv4)
                                continue;
                            return inetAddress.getHostAddress();
                        }
                    }
                }
            }
        } catch (SocketException e) {
            Log.error(log, "获取ip地址发生错误", e);
        }
        return "";
    }
}
