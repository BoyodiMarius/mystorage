/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.resultview.web.utils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 *
 * @author Brendev
 */
public class IpAdressUtils {

    public static String getIpAdresse() throws UnknownHostException, SocketException {
        String monIp = "";
        String ip = InetAddress.getLocalHost().getHostAddress();
        if (!ip.isEmpty()) {
            monIp = ip;
        } else {
            monIp = "Pas connecté";
        }
        return monIp;
    }

    public static String getMacAddress() throws UnknownHostException, SocketException {
        String MonMac = "";
        try {
            InetAddress ipAddress = InetAddress.getLocalHost();
            if (!ipAddress.getHostAddress().isEmpty()) {
                NetworkInterface networkInterface = NetworkInterface
                        .getByInetAddress(ipAddress);
                byte[] macAddressBytes = networkInterface.getHardwareAddress();
                StringBuilder macAddressBuilder = new StringBuilder();

                for (int macAddressByteIndex = 0; macAddressByteIndex < macAddressBytes.length; macAddressByteIndex++) {
                    String macAddressHexByte = String.format("%02X",
                            macAddressBytes[macAddressByteIndex]);
                    macAddressBuilder.append(macAddressHexByte);

                    if (macAddressByteIndex != macAddressBytes.length - 1) {
                        macAddressBuilder.append(":");
                    }
                }
                MonMac = macAddressBuilder.toString();
            } else {
                MonMac = "Pas connecté";
            }

        } catch (Exception e) {

        }

        return MonMac;
    }
}
