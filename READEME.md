카카오페이 경력 공채 과제(2019년 상반기)
====


### 과제명
* 주택금융 API개발


###개발환경
* Java 8
* maven
* Spring Boot
* JPA
###Build
~~~
mvn package
~~~
###Web Application Run
~~~
java -jar ${ProjectPath}/target/homework-0.0.1-SNAPSHOT.jar
~~~
###필수항목
* 데이터 파일에서 각 레코드를 데이터베이스에 저장하는 API 개발.
* 주택 금융 공급 금융기관(은행) 목록을 출력하는 API 를 개발.
* 연도별 각 금융기관의 지원금액 합계를 출력하는 API 를 개발.
* 각 연도별 각 기관의 전체 지원 금액중에서 가장 큰 금액의 기관명을 출력하는 API 개발.
* 전체 년도에서 외환은행의 지원금액 평균 중에서 가장 작은 금액과 큰 금액을 출력하는 API 개발.
###옵션항목
* 특정 은행의 특정 달에 대해서 2018년도 해당 달에 금융 지원 금액을 예측하는 API.
###문제 해결 전략
* 기간 내 Spring Boot 관련 기술 습득
  * controller, model, repository, service 구조
* 주어진 Data Set 분석
  * Entity 정의
    * HouseFund: 년,월,금융기관이름,예산
    * Agency: 금융기관이름, 금융기관코
  * 금융기관 리스트는 Application 실행시 자동으로 insert
  * 제공받은 file 가공
* 특정 은행의 특정 달에 대한 예측 API
  * 시계열 데이터 예측에 활용되는 ARIMA(Autoregressive integrated moving average)을 활용한 예측.
  * ARIMA Wiki Page: [Wiki](https://en.wikipedia.org/wiki/Autoregressive_integrated_moving_average)
###API 명세서
#####데이터 파일 데이터베이스에 저장 API
>POST
http:{IP}:8080/fund/upload
~~~
Header
Content-Type: application/json

Request
{
	"fileName": "/Users/silvizin/Downloads/test.txt"
}
~~~
#####주택 금융 공급 금융기관(은행) 목록을 출력하는 API
>POST
http:{IP}:8080/agency/listall

Request
~~~
Header
Content-Type: application/json

Request

~~~
Response
~~~
[
    {
        "name": "주택도시기금",
        "code": "bnk1"
    },
    {
        "name": "국민은행",
        "code": "bnk2"
    },
    ...
    {
        "name": "기타은행",
        "code": "bnk9"
    }
]
~~~

#####연도별 각 금융기관의 지원금액 합계를 출력하는 API
>POST
http:{IP}:8080/fund/annualfund

Request
~~~
Header
Content-Type: application/json
~~~

Response
~~~
[
    {
        "year": "2005",
        "total_amount": 48016,
        "detail_amount": {
            "주택도시기금": 22247,
            "국민은행": 13231,
            "우리은행": 2303,
            "신한은행": 1815,
            "한국시티은행": 704,
            "하나은행": 3122,
            "농협은행/수협은행": 1486,
            "외환은행": 1732,
            "기타은행": 1376
        }
    },
    ...
    {
        "year": "2017",
        "total_amount": 295126,
        "detail_amount": {
            "주택도시기금": 85409,
            "국민은행": 31480,
            "우리은행": 38846,
            "신한은행": 40729,
            "한국시티은행": 7,
            "하나은행": 35629,
            "농협은행/수협은행": 26969,
            "외환은행": 0,
            "기타은행": 36057
        }
    }
]
~~~
#####각 연도별 각 기관의 전체 지원 금액중에서 가장 큰 금액의 기관명을 출력하는 API개발
>POST
http:{IP}:8080/fund/annualfund

Request
~~~
Header
Content-Type: application/json
~~~

Response
~~~
{
    "year": "2014",
    "bank": "주택도시기금"
}
~~~
#####전체 년도에서 외환은행의 지원금액 평균 중에서 가장 작은 금액과 큰 금액을 출력하는 API
>POST
http:{IP}:8080/fund/min-max-keb

Request
~~~
Header
Content-Type: application/json
~~~

Response
~~~
{
    "bank": "외환은행",
    "support_amount": [
        {
            "year": "2017",
            "amout": "0"
        },
        {
            "year": "2015",
            "amout": "1701"
        }
    ]
}
~~~
#####특정 은행의 특정 달에 대해서 2018년도 해당 달에 금융 지원 금액을 예측하는 API.
>GET
http:{IP}:8080/fund/forcast?bank={은행이름}&month={목표 달}

Request
~~~
예제 url
http:localhost:8080/fund/forcast?bank=우리은행&month=7
~~~

Response
~~~
{

    "year": 2018,
    "month": 7,
    "bank": "bnk3",
    "amounts": 5070

}
~~~
