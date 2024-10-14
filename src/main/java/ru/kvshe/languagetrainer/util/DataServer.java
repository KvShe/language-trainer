package ru.kvshe.languagetrainer.util;

import lombok.SneakyThrows;

import java.net.InetAddress;

public interface DataServer {
    /**
     *
     * @return текущий ip сервера, на котором запущено приложение
     */
    @SneakyThrows
    static String getIp() {
        return InetAddress.getLocalHost().getHostAddress();
    }
}
