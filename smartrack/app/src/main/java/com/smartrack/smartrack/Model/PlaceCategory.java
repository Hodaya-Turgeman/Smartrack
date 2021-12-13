package com.smartrack.smartrack.Model;

public enum PlaceCategory {
        c1("amusement_park"),
        c2("aquarium"),
        c3("art_gallery"),
        c4("bar"),
        c5("casino"),
        c6("museum"),
        c7("night_club"),
        c8("park"),
        c9("shopping_mall"),
        c10("spa"),
        c11("tourist_attraction"),
        c12("zoo"),
        c13("bowling_alley"),
        c14("cafe"),
        c15("church"),
        c16("city_hall"),
        c17("library"),
        c18("mosque"),
        c19("synagogue")
        ;
        String category;

        PlaceCategory(String c) {
            category=c;
        }
        String showCategory(){
            return category;
        }
}

