package com.example.cities.game;

import java.util.ArrayList;
import java.util.List;

public class CityRepository {

    private final List<String> cities = new ArrayList<>(List.of(
            "Луцьк",
            "Київ",
            "Львів",
            "Одеса",
            "Харків",
            "Полтава",
            "Чернігів",
            "Черкаси",
            "Житомир",
            "Тернопіль",
            "Ужгород",
            "Рівне",
            "Хмельницький",
            "Вінниця",
            "Миколаїв",
            "Херсон",
            "Суми",
            "Дніпро",
            "Запоріжжя",
            "Івано-Франківськ",
            "Кропивницький",
            "Бровари",
            "Буча",
            "Ірпінь",
            "Дрогобич",
            "Стрий",
            "Мукачево",
            "Ковель",
            "Коломия",
            "Кременчук"
    ));

    public List<String> getCities() {
        return new ArrayList<>(cities);
    }
}
