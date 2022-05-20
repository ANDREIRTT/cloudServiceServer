# cloudServiceServer
Облачное хранилище, backend.

## Api

### Авторизация

#### login (POST) /account/login
авторизация пользователя на сервере

Post-параметры

| KEY | VALUE |
| ------ | ------ |
| username | логин пользователя |
| password | пароль пользователя |

Ответ от сервера
| JSON param | JSON value |
| ------ | ------ |
| token | токен авторизации пользователя |
| username | логин пользователя |
| userRole | роль пользователя |


#### register (POST) /account/register
регистрация пользователя на сервере

Post-параметры
| KEY | VALUE |
| ------ | ------ |
| username | логин пользователя |
| password | пароль пользователя |
| userRole | роль пользователя |

Ответ от сервера
| JSON param | JSON value |
| ------ | ------ |
| token | токен авторизации пользователя |
| username | логин пользователя |
| userRole | роль пользователя |

### Действия с файлами пользователя

#### Сохранение файла (POST) /uploads/upload

Post-параметры
| KEY | VALUE |
| ------ | ------ |
| file | файл пользователя |

Ответ от сервера
| JSON param | JSON value |
| ------ | ------ |
| fileHash | хэш токен файла |
| fileType | тип файла |
| originFileName | имя оригинального файла |

fileType
| Type | тип файла |
| ------ | ------ |
| IMAGE | тип изображение(jpeg, jpg, png, [еще](https://ru.wikipedia.org/wiki/%D0%A1%D0%BF%D0%B8%D1%81%D0%BE%D0%BA_MIME-%D1%82%D0%B8%D0%BF%D0%BE%D0%B2#image)) |
| VIDEO | тип изображение(AVI, MP4, [еще](https://ru.wikipedia.org/wiki/%D0%A1%D0%BF%D0%B8%D1%81%D0%BE%D0%BA_MIME-%D1%82%D0%B8%D0%BF%D0%BE%D0%B2#video)) |
| TEXT | тип изображение(cmd, css, html, [еще](https://ru.wikipedia.org/wiki/%D0%A1%D0%BF%D0%B8%D1%81%D0%BE%D0%BA_MIME-%D1%82%D0%B8%D0%BF%D0%BE%D0%B2#text)) |
| AUDIO | тип изображение(mp4, mpeg, webm, [еще](https://ru.wikipedia.org/wiki/%D0%A1%D0%BF%D0%B8%D1%81%D0%BE%D0%BA_MIME-%D1%82%D0%B8%D0%BF%D0%BE%D0%B2#audio)) |
| UNIDENTIFIED| тип не определен |


#### Получение файла (GET) /uploads/files/{fileHash}

Path-параметры

| fileHash | хэш файла |

Ответ от сервера
Возвращается сам файл

#### Удаление файла
В разработке






