package com.project.dailyquest.utils

fun checkEmailValidity(email: String): Boolean {
    val emailParts = email.split('@')
    return if (emailParts.size != 2) false
    else emailParts[0].isNotBlank() && emailParts[1].isNotBlank() &&
            emailParts[1].contains('.') &&
            emailParts[1].substringBefore('.').isNotBlank() &&
            emailParts[1].substringAfter('.').isNotBlank()
}

fun checkPasswordValidity(password: String): Boolean = if (password.trim().contains(' ')) false
    else password.trim().length >= 6