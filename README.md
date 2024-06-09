# 요구사항 문서

- [x] 결제 기능 추가
    - 사용자가 결제를 해야 예약이 가능하도록 수정
    - 결제 실패시 사용자가 실패 이유를 알 수 있어야 한다.

- [x] 내 예약 페이지에 결제 정보 추가
    - [x] paymentKey
    - [x] 결제 금액

# API 명세

## 예약 조회 API

### Request

> GET /reservations HTTP/1.1

### Response

> HTTP/1.1 200
>
> Content-Type: application/json

``` JSON 
[
    {
        "id": 1,
        "name": "브라운",
        "date": "2023-08-05",
        "time": {
            "id": 1,
            "startAt": "10:00"
        }
        "theme" : {
            "id": 1,
            "name": "이름",
            "description": "설명",
            "thumbnail": "썸네일"
        }
    }
]
```

## 예약 추가 API

### Request

> POST /reservations HTTP/1.1
> content-type: application/json
> cookie:
>
token=eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwibmFtZSI6ImFkbWluIiwicm9sZSI6IkFETUlOIn0.cwnHsltFeEtOzMHs2Q5-ItawgvBZ140OyWecppNlLoI
> host: localhost:8080

```json
{
  "date": "2024-03-01",
  "themeId": 1,
  "timeId": 1
}
```

### Response

> HTTP/1.1 201
>
> Content-Type: application/json
> Location: /reservations/{id}

```JSON
{
  "id": 1,
  "name": "브라운",
  "date": "2023-08-05",
  "time": {
    "id": 1,
    "startAt": "10:00"
  },
  "theme": {
    "id": 1,
    "name": "이름",
    "description": "설명",
    "thumbnail": "썸네일"
  }
}
```

## 관리자 예약 추가 API

### Request

> POST /admin/reservations HTTP/1.1
> content-type: application/json
> cookie:
>
token=eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwibmFtZSI6ImFkbWluIiwicm9sZSI6IkFETUlOIn0.cwnHsltFeEtOzMHs2Q5-ItawgvBZ140OyWecppNlLoI
> host: localhost:8080

```json
{
  "date": "2024-03-01",
  "themeId": 1,
  "timeId": 1,
  "memberId": 1
}
```

### Response

```JSON
{
  "id": 1,
  "name": "브라운",
  "date": "2023-08-05",
  "time": {
    "id": 1,
    "startAt": "10:00"
  },
  "theme": {
    "id": 1,
    "name": "이름",
    "description": "설명",
    "thumbnail": "썸네일"
  }
}
```

## 예약 취소 API

### Request

> DELETE /reservations/{reservationId} HTTP/1.1

### Response

> HTTP/1.1 204

## 시간 추가 API

### request

> POST /admin/times HTTP/1.1
> content-type: application/json

```JSON
{
  "startAt": "10:00"
}
```

### response

> HTTP/1.1 201
> Content-Type: application/json
> Location: /times/{id}

```JSON
{
  "id": 1,
  "startAt": "10:00"
}
```

## 시간 조회 API

### request

> GET /times HTTP/1.1

### response

> HTTP/1.1 200
> Content-Type: application/json

```JSON
[
  {
    "id": 1,
    "startAt": "10:00"
  }
]
```

## 시간 삭제 API

### request

> DELETE /admin/times/1 HTTP/1.1

### response

> HTTP/1.1 204

## 테마 조회 API

### request

> GET /themes HTTP/1.1

### response

> HTTP/1.1 200
> Content-Type: application/json

```json
[
  {
    "id": 1,
    "name": "레벨2 탈출",
    "description": "우테코 레벨2를 탈출하는 내용입니다.",
    "thumbnail": "https://i.pinimg.com/236x/6e/bc/46/6ebc461a94a49f9ea3b8bbe2204145d4.jpg"
  }
]
```

## 테마 추가 API

### request

> POST /admin/themes HTTP/1.1
> content-type: application/json

```json
{
  "name": "레벨2 탈출",
  "description": "우테코 레벨2를 탈출하는 내용입니다.",
  "thumbnail": "https://i.pinimg.com/236x/6e/bc/46/6ebc461a94a49f9ea3b8bbe2204145d4.jpg"
}
```

### response

> HTTP/1.1 201
> Location: /themes/1
> Content-Type: application/json

```json
{
  "id": 1,
  "name": "레벨2 탈출",
  "description": "우테코 레벨2를 탈출하는 내용입니다.",
  "thumbnail": "https://i.pinimg.com/236x/6e/bc/46/6ebc461a94a49f9ea3b8bbe2204145d4.jpg"
}
```

## 테마 삭제 API

### request

> DELETE /admin/themes/{id} HTTP/1.1

### response

> HTTP/1.1 204

## 예약 가능 시간 조회 API

### Request

> GET /times?date=${date}&themeId=${themeId}

### response

> HTTP/1.1 200
> Content-Type: application/json

```json
[
  {
    "id": 0,
    "startAt": "02:53",
    "isBooked": false
  }
]
```

## 로그인 API

### Request

> POST /login HTTP/1.1
> content-type: application/json
> host: localhost:8080

```json
{
  "password": "password",
  "email": "admin@email.com"
}
```

### Response

> HTTP/1.1 200 OK
> Content-Type: application/json
> Keep-Alive: timeout=60
> Set-Cookie:
>
token=eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwibmFtZSI6ImFkbWluIiwicm9sZSI6IkFETUlOIn0.cwnHsltFeEtOzMHs2Q5-ItawgvBZ140OyWecppNlLoI;
> Path=/; HttpOnly

## 인증 정보 조회 API

### Request

> GET /login/check HTTP/1.1
> cookie: _ga=GA1.1.48222725.1666268105; _ga_QD3BVX7MKT=GS1.1.1687746261.15.1.1687747186.0.0.0;
> Idea-25a74f9c=3cbc3411-daca-48c1-8201-51bdcdd93164;
>
token=eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwibmFtZSI6IuyWtOuTnOuvvCIsInJvbGUiOiJBRE1JTiJ9.vcK93ONRQYPFCxT5KleSM6b7cl1FE-neSLKaFyslsZM
> host: localhost:8080

### Response

> HTTP/1.1 200 OK
> Connection: keep-alive
> Content-Type: application/json
> Date: Sun, 03 Mar 2024 19:16:56 GMT
> Keep-Alive: timeout=60
> Transfer-Encoding: chunked

```json
{
  "name": "어드민"
}
```

## 내 예약 조회 API

### Request

> GET /reservations/mine HTTP/1.1
> cookie:
>
token=eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwibmFtZSI6IuyWtOuTnOuvvCIsInJvbGUiOiJBRE1JTiJ9.vcK93ONRQYPFCxT5KleSM6b7cl1FE-neSLKaFyslsZM
> host: localhost:8080

### Response

> HTTP/1.1 200
> Content-Type: application/json

``` JSON 
[
    {
        "reservationId": 1,
        "theme": "테마1",
        "date": "2024-03-01",
        "time": "10:00",
        "status": "예약"
    },
    {
        "reservationId": 2,
        "theme": "테마2",
        "date": "2024-03-01",
        "time": "12:00",
        "status": "1번째 예약 대기"
    },
    {
        "reservationId": 3,
        "theme": "테마3",
        "date": "2024-03-01",
        "time": "14:00",
        "status": "예약"
    }
]
```

## 예약 대기 추가 API

### Request

> POST /reservations/waiting HTTP/1.1
> cookie:
>
token=eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwibmFtZSI6IuyWtOuTnOuvvCIsInJvbGUiOiJBRE1JTiJ9.vcK93ONRQYPFCxT5KleSM6b7cl1FE-neSLKaFyslsZM
> host: localhost:8080

```json
{
  "date": "2024-03-01",
  "themeId": 1,
  "timeId": 1
}
```

### Response

> HTTP/1.1 204
> Content-Type: application/json

``` JSON 
{
  "id": 1,
  "name": "브라운",
  "date": "2024-05-26",
  "time": {
    "id": 1,
    "startAt": "02:23"
  },
  "theme": {
    "id": 1,
    "name": "테마1",
    "description": "description_cd6209599833",
    "thumbnail": "thumbnail_38689040a3b6"
  },
  "priority": 1
}
```

## 예약 대기 취소 API

### Request

> DELETE /reservations/waiting/{id} HTTP/1.1

### Response

> HTTP/1.1 204

## 관리자 예약 대기 목록 조회

### Request

> GET /admin/reservations/waiting HTTP/1.1
> cookie:
>
token=eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwibmFtZSI6IuyWtOuTnOuvvCIsInJvbGUiOiJBRE1JTiJ9.vcK93ONRQYPFCxT5KleSM6b7cl1FE-neSLKaFyslsZM
> host: localhost:8080

### Response

``` JSON 
[
  {
    "id": 1,
    "name": "브라운",
    "date": "2024-05-26",
    "time": {
      "id": 1,
      "startAt": "02:23"
    },
    "theme": {
      "id": 1,
      "name": "테마1",
      "description": "description_cd6209599833",
      "thumbnail": "thumbnail_38689040a3b6"
    },
    "priority": 1
  }
]
```
