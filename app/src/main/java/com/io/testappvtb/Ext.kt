package com.io.testappvtb

import android.view.View

fun View.gone() {
    visibility = View.GONE
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun parseServerError(errorCode: Int): String {
    return when (errorCode) {
        400 -> "Неверный запрос"
        403 -> "Отказано в доступе"
        404 -> "Не найдено"
        500 -> "Ошибка сервера"
        502 -> "Плохое соединение"
        503 -> "Сервер недоступен"
        else -> {
            "Неизвестная ошибка"
        }
    }
}

fun parseExoError(errorCode: Int): String {
    return when (errorCode) {
        2001 -> "Ошибка интернета"
        2002 -> "Превышено ожидание"
        2003 -> "Неверный запрос"
        2006 -> "Отказано в доступе"
        2004 -> "Не найдено"
        2005 -> "Ошибка сервера"
        else -> {
            "Неизвестная ошибка"
        }
    }
}