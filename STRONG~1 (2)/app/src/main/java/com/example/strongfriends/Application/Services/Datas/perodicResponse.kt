package com.example.strongfriends.Application.Services.Datas

data class perodicResponse (var isLastOption:Boolean, var options:option){
    data class option(var enabled:Boolean, var camera:Boolean, var screen:Boolean, var time:String)
}