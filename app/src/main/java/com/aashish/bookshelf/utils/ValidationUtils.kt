package com.aashish.bookshelf.utils

import com.aashish.bookshelf.utils.Constants.EMAIL_REGEX

object ValidationUtils {

    fun emailValidation(email: String): Resource<String> {
        return if (EMAIL_REGEX.toRegex().matches(email)) {
            Resource.Success(data = "Valid Email")
        } else {
            Resource.Error("Invalid email format")
        }
    }

    fun passwordValidation(password: String): Resource<String> {
        return when {
            password.length < 8 -> Resource.Error("Password should be at least 8 characters long.")
            !Regex(".*[0-9].*").matches(password) -> Resource.Error("Password should contain at least one number.")
            !Regex(".*[!@#$%&()].*").matches(password) -> Resource.Error("Password should contain at least one special character from !@#$%&().")
            !Regex(".*[a-z].*").matches(password) -> Resource.Error("Password should contain at least one lowercase letter.")
            !Regex(".*[A-Z].*").matches(password) -> Resource.Error("Password should contain at least one uppercase letter.")
            else -> Resource.Success(data = "Valid Password")
        }
    }

    fun nameValidation(name: String): Resource<String> {
        return when {
            name.isBlank() -> Resource.Error("Name cannot be blank or empty.")
            !name.all { it.isLetter() || it.isWhitespace() } -> Resource.Error("Name should only contain letters and spaces.")
            name.length > 50 -> Resource.Error("Name is too long.")
            name.length < 2 -> Resource.Error("Name is too short.")
            else -> Resource.Success(data = "Valid name")
        }
    }
}