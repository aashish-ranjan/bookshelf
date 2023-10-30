package com.aashish.bookshelf.utils

import com.aashish.bookshelf.R
import com.aashish.bookshelf.ui.ResourceProvider
import com.aashish.bookshelf.utils.Constants.EMAIL_REGEX

object ValidationUtils {

    fun emailValidation(email: String, resourceProvider: ResourceProvider): Resource<String> {
        return if (EMAIL_REGEX.toRegex().matches(email)) {
            Resource.Success(data = resourceProvider.getString(R.string.valid_email))
        } else {
            Resource.Error(resourceProvider.getString(R.string.invalid_email_format))
        }
    }

    fun passwordValidation(password: String, resourceProvider: ResourceProvider): Resource<String> {
        return when {
            password.length < 8 -> Resource.Error(resourceProvider.getString(R.string.password_length_error))
            !Regex(".*[0-9].*").matches(password) -> Resource.Error(resourceProvider.getString(R.string.password_number_error))
            !Regex(".*[!@#$%&()].*").matches(password) -> Resource.Error(
                resourceProvider.getString(
                    R.string.password_special_char_error
                )
            )

            !Regex(".*[a-z].*").matches(password) -> Resource.Error(resourceProvider.getString(R.string.password_lowercase_error))
            !Regex(".*[A-Z].*").matches(password) -> Resource.Error(resourceProvider.getString(R.string.password_uppercase_error))
            else -> Resource.Success(resourceProvider.getString(R.string.valid_password))
        }
    }

    fun nameValidation(name: String, resourceProvider: ResourceProvider): Resource<String> {
        return when {
            name.isBlank() -> Resource.Error(resourceProvider.getString(R.string.name_blank_error))
            !name.all { it.isLetter() || it.isWhitespace() } -> Resource.Error(
                resourceProvider.getString(
                    R.string.name_invalid_chars_error
                )
            )

            name.length > 50 -> Resource.Error(resourceProvider.getString(R.string.name_too_long_error))
            name.length < 2 -> Resource.Error(resourceProvider.getString(R.string.name_too_short_error))
            else -> Resource.Success(resourceProvider.getString(R.string.valid_name))
        }
    }
}