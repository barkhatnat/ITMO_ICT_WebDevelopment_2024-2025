### Задание:
Реализовать клиентскую и серверную часть приложения. Клиент отсылает серверу
сообщение «Hello, server». Сообщение должно отразиться на стороне сервера.
Сервер в ответ отсылает клиенту сообщение «Hello, client». Сообщение должно
отобразиться у клиента.
### Стек:
- ЯП: Java
- Пакет: net
- Протокол: UDP
### Инструкция по запуску
Выполните команды в папке `Lr1\task_1\src\main\java\ru\barkhatnat`

1. Запуск сервера
	- `cd server`
	- `javac ServerUdp.java`
	- `java ServerUdp.java <ip> <port>` (вы можете указать желаемый ip и порт, по умолчанию `localhost:1234`)
2.  Запуск клиента
	- `cd client`
	- `javac ClientUdp.java`
	- `java ClientUdp.java <ip> <port>` (вы можете указать желаемый ip и порт, по умолчанию `localhost:1234`)
	