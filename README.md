# corporate-crm
CRM Project based on Microservices Architecture

Чтобы запустить любой микросервис:
1) Запускаем discovery service (localhost:8010)
2) Run zuul service (localhost:8011)
3) Запускаем микросервис и открываем его по пути localhost:8011/{microservice_name}
Список активных микросервисов можно поснотреть по localhost:8010
Первые два пункта обязательны!
