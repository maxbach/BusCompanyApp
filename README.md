# BusCompanyApp
Веб-приложение для компании, которая занимается составлением расписания для автобусных маршрутов.

###Инструменты и технологии этого проекта:
Сервер: Jetty + Freemarker
База данных: Mysql + Hibernate
Сборка проекта: Maven

###Инструкция по запуску:
0) Установить Maven и MySql на ваш компьютер
1) В файле hibernate.cfg.xml настроить логин и пароль доступа к вашей базе данных и порт доступа к ней
2) В классе main.Main.java выбрать порт, на котором будет работать сервер
3) Запустить из консоли assembly.bat для Windows и assembly.sh для Linux и Mac (для сборки)
4) Запустить из консоли start.bat для Windows и start.sh для Linux и Mac (для запуска сервера)
5) Теперь по адресу localhost:<порт> будет работает стартовая страница приложения

###Важное примечание!!!
Для того, чтобы запустить приложение со стороны администратора, было заранее добавлены два супер-пользователя: логин admin - пароль admin и логин test - пароль test.

###Скриншоты:

#####Отчет администратора о пассажирах рейса
<img src="https://github.com/maxbach/BusCompanyApp/blob/master/Screenshots/AdminReportOfTrip.png" width='400'>
#####Страница администратора для добавления новых рейсов
<img src="https://github.com/maxbach/BusCompanyApp/blob/master/Screenshots/AdminTimetableMenu.png" width='400'>
#####Главное меню пассажира
<img src="https://github.com/maxbach/BusCompanyApp/blob/master/Screenshots/UserMainPage.png" width='400'>
