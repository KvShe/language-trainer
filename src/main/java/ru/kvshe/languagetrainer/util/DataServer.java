package ru.kvshe.languagetrainer.util;

import lombok.SneakyThrows;

import java.net.InetAddress;

/**
 * Интерфейс для получения информации о сервере данных.
 * Этот интерфейс предоставляет метод для получения текущего IP-адреса сервера,
 * на котором запущено приложение.
 */
public interface DataServer {
    /**
     * Получает текущий IP-адрес сервера, на котором запущено приложение.
     * Использует класс `InetAddress` для получения локального адреса.
     *
     * @return строка с IP-адресом текущего сервера
     * @throws java.net.UnknownHostException если не удается получить IP-адрес
     */
    @SneakyThrows
    static String getIp() {
        return InetAddress.getLocalHost().getHostAddress();
    }
}
