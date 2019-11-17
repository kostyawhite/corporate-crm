# corporate-crm
CRM Project based on Microservices Architecture

Чтобы запустить любой микросервис:
1) Запускаем crm-discovery-ws (localhost:8010)
2) Запускаем crm-zuul-ws (localhost:8011)
3) Запускаем микросервис и открываем его по пути localhost:8011/{microservice_name}
Список активных микросервисов и их microservice_name можно посмотреть по localhost:8010 (Eureka Discovery Service)
Первые два пункта обязательны!
