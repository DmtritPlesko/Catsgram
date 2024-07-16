package ru.yandex.practicum.catsgram.service;

public enum SortOrder {
    ASCENDING,
    DESCENDING;

    public static SortOrder from(String order) {
        switch (order.toLowerCase()) {
            case "ascending":
            case "asc":
                return ASCENDING;
            case "descending":
            case "desc":
                return DESCENDING;
            default:
                return null;
        }
    }
}
