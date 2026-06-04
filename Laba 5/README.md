# Лабораторная работа №5: Регулярные выражения

## Описание работы

В лабораторной работе изучаются регулярные выражения (Regular expressions) в Java — мощный инструмент для поиска, проверки и обработки текстовой информации. Регулярные выражения позволяют находить в тексте фрагменты, соответствующие определённому шаблону, проверять соответствие строки формату (email, телефон, IP-адрес), заменять текст по шаблону и разбивать строки на части.

В Java регулярные выражения реализованы в классе `java.util.regex.Pattern` для компиляции регулярного выражения и классе `java.util.regex.Matcher` для поиска совпадений в тексте.

## Синтаксис регулярных выражений

**Метасимволы:** `.` — любой символ; `\d` — любая цифра; `\D` — любой не-цифровой символ; `\w` — буква, цифра или подчёркивание; `\W` — любой не-буквенно-цифровой символ; `\s` — пробельный символ; `\S` — непробельный символ; `\b` — граница слова; `^` — начало строки; `$` — конец строки.

**Квантификаторы:** `*` — 0 или более раз; `+` — 1 или более раз; `?` — 0 или 1 раз; `{n}` — ровно n раз; `{n,}` — не менее n раз; `{n,m}` — от n до m раз.

**Символьные классы:** `[abc]` — один символ из набора; `[^abc]` — любой символ, кроме указанных; `[a-z]` — диапазон символов; `[A-Z]` — заглавные буквы; `[0-9]` — цифры.

**Группировка:** `(abc)` — группа символов; `(ab|cd)` — ИЛИ; `(?:...)` — несохраняющая группа.

## Задание 1: Поиск всех чисел в тексте

Программа находит все числа (целые и дробные) в заданном тексте с использованием регулярного выражения и выводит их на экран. Обрабатываются возможные ошибки.

```java
import java.util.regex.*;

public class NumberFinder {
    public static void main(String[] args) {
        String text = "Цены: 19.99, 25, 100.5, 0.99, 42. Скидка 15%";
        Pattern pattern = Pattern.compile("\\d+\\.?\\d*");
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            System.out.println(matcher.group());
        }
    }
}


Задание 2: Проверка корректности пароля
Программа проверяет пароль на соответствие требованиям: только латинские буквы и цифры, длина от 8 до 16 символов, наличие хотя бы одной заглавной буквы и хотя бы одной цифры.

java
import java.util.regex.*;

public class PasswordValidator {
    public static boolean isValidPassword(String password) {
        String regex = "^(?=.*[A-Z])(?=.*\\d)[A-Za-z0-9]{8,16}$";
        return Pattern.compile(regex).matcher(password).matches();
    }
}
Задание 3: Поиск заглавной буквы после строчной
Программа находит все случаи в тексте, когда сразу после строчной буквы идёт заглавная (без пробелов), и выделяет их знаками «!» с двух сторон.

java
import java.util.regex.*;

public class CapitalFinder {
    public static void main(String[] args) {
        String text = "Пример: eXample, aBc, dEf";
        Pattern pattern = Pattern.compile("([a-z])([A-Z])");
        Matcher matcher = pattern.matcher(text);
        String result = matcher.replaceAll("!$1$2!");
        System.out.println(result);
    }
}
Задание 4: Проверка корректности IP-адреса
Программа проверяет IP-адрес (IPv4): 4 числа от 0 до 255, разделённые точками, без ведущих нулей.

java
import java.util.regex.*;

public class IPValidator {
    public static boolean isValidIPv4(String ip) {
        String regex = "^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
                       "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
                       "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
                       "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";
        return Pattern.compile(regex).matcher(ip).matches();
    }
}
Задание 5: Поиск слов, начинающихся с заданной буквы
Программа находит все слова в тексте, начинающиеся с заданной буквы (например, «к»), и выводит их на экран.

java
import java.util.regex.*;

public class WordFinder {
    public static void main(String[] args) {
        char letter = 'к';
        String text = "кот, кровать, молоко, карандаш, дерево, книга";
        Pattern pattern = Pattern.compile("\\b" + letter + "[а-яА-Яa-zA-Z]*\\b", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            System.out.println(matcher.group());
        }
    }
}
