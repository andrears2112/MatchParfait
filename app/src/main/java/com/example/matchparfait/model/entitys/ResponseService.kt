package com.example.matchparfait.model.entitys

data class ResponseService(
    var fieldCount : Int = 0,
    var affectedRows : Int = 0,
    var insertId : Int = 0,
    var serverStatus : Int = 0,
    var warningCount : Int = 0,
    var message : String = "",
    var protocol41 : Boolean = true,
    var changedRows : Int = 0
)
