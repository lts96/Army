package com.example.strongfriends.Application.Services

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object Option { //옵션 컨피규레이션....?
    var user_id:String=""
    var enable : Int = 1  //설정을 적용하는지?
    var camera : Int = 0//카메라가 차단인지?
    var screen:  Int = 1 //스크린이 차단인지?
    var timer:String="2019052812510020190622155100" //현재 제한시간을 받아와서 여기다가 넣고!
    var formattedTimer= mutableListOf<LocalDateTime>() //이건 timer의 제한시간값을 시작시간, 끝시간 두개로 나눈것이다.
    var lastUpdate: String="20190101000000" //마지막으로 업데이트한 시간.




    lateinit var timerList:MutableList<String> //  0900에서~21시까지면 09002100 , 두개있으면 추가 해서 시간 제한의 리스트 //일단쓸지 안쓸지 모르겠다.
    //시간정보

    fun getFormattedTime():MutableList<LocalDateTime>{
        var start= LocalDateTime.parse(timer.slice(IntRange(0,13)), DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))
        var end= LocalDateTime.parse(timer.slice(IntRange(14,27)), DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))
        formattedTimer.add(start)
        formattedTimer.add(end)
        return formattedTimer
    }

}
