package com.example.andreromano.coolweather.extensions

import java.text.Normalizer;
import java.util.regex.Pattern;

fun String.withoutAccents(): String {
    val nfdNormalizedString = Normalizer.normalize(this, Normalizer.Form.NFD)
    val pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+")
    return pattern.matcher(nfdNormalizedString).replaceAll("")
}