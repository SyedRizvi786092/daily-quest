package com.project.dailyquest.utils

fun checkEmailValidity(email: String): Boolean {
    if (email.trim().contains(' ')) return false
    val emailParts = email.split('@')
    return if (emailParts.size != 2) false
    else emailParts[0].isNotBlank() && emailParts[1].isNotBlank() &&
            emailParts[1].contains('.') &&
            emailParts[1].substringBefore('.').isNotBlank() &&
            emailParts[1].substringAfter('.').isNotBlank()
}

fun checkPasswordValidity(password: String): Boolean = if (password.contains(' ')) false
    else password.length >= 6
