package com.example.matchparfait.model.entitys

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date
data class User(
    var userId : String = "",
    var email : String = "",
    var plainPassword : String = "",
    var name : String = "",
    var lastName1 : String = "",
    var lastName2 : String = "",
    var phone_number : String = "",
    var password : String = "",
    var date_of_birth : String = "",
    var gender : String = "",
    var country : String = "",
    var state : String = "",
    var municipality : String = "",
    var postal_code: String = "",
    var suburb : String = "",
    var street : String = "",
    var ext_num : String = "",
    var int_num : String = "",
    var texture : Int = 0,
    var dermatitis : Int = 0,
    var shine : Int = 0,
    var rosacea : Int = 0,
    var tightness : Int = 0,
    var peeling : Int = 0,
    var hives : Int = 0,
    var acne : Int = 0,
    var enlarged_pores : Int = 0,
    var sun_spots : Int = 0,
    var cloth : Int = 0,
    var blackheads : Int = 0,
    var roughness : Int = 0,
    var tone : Int = 0,
    var classification : String = ""
)

data class AddressUser(
    var country : String = "",
    var state : String = "",
    var municipality : String = "",
    var postal_code: String = "",
    var suburb : String = "",
    var street : String = "",
    var ext_num : String = "",
    var int_num : String = ""
)

class UserFactory {
    companion object {
        fun userForLogin(mail : String, password : String) : User {
            var tempUser = User()
            tempUser.email = mail
            tempUser.password = password
            return tempUser
        }
    }
}
