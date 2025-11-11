/************************************************************************
 * @file: Game.java
 * @description: Defines Game object, which stores data about games from the steam_games database
 * @author: Will S
 * @date: September 24, 2025 (small edit 11/11/25)
 ************************************************************************/

public class Game implements Comparable<Game> {
    int steam_appid; //internal id used by Steam
    String name; //name of the videogame/software
    int n_achievements; //number of achievements present in the game
    String release_date; //the initial release date of the game
    int total_reviews; //total number of reviews from users on Steam
    int total_positive; //total number of positive reviews from users on Steam
    int total_negative; //total number of negative reviews from users on Steam
    int review_score; //not really sure what this is, probably
    int metacritic; //score given by professional video game reviewers, accuracy of reviews is debatable but it's a good metric for our purposes
    double price_initial; //I think it's either the price of the game on launch day, or the price before any discounts

    //empty constructor
    public Game() {
        steam_appid = -1;
        name = "{Untitled}";
        n_achievements = -1;
        release_date = "(Undated)";
        total_reviews = -1;
        total_positive = -1;
        total_negative = -1;
        metacritic = -1;
        price_initial = -1;
    }

    //full constructor
    public Game(int steam_appid, String name, int n_achievements, String release_date, int total_reviews, int total_positive, int total_negative, int review_score, int metacritic, double price_initial) {
        this.steam_appid = steam_appid;
        this.name = name;
        this.n_achievements = n_achievements;
        this.release_date = release_date;
        this.total_reviews = total_reviews;
        this.total_positive = total_positive;
        this.total_negative = total_negative;
        this.review_score = review_score;
        this.metacritic = metacritic;
        this.price_initial = price_initial;
    }

    //************************
    //Getter and setter methods
    //******************************
    public int getSteam_appid() {
        return steam_appid;
    }

    public void setSteam_appid(int steam_appid) {
        this.steam_appid = steam_appid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getN_achievements() {
        return n_achievements;
    }

    public void setN_achievements(int n_achievements) {
        this.n_achievements = n_achievements;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public int getTotal_reviews() {
        return total_reviews;
    }

    public void setTotal_reviews(int total_reviews) {
        this.total_reviews = total_reviews;
    }

    public int getTotal_positive() {
        return total_positive;
    }

    public void setTotal_positive(int total_positive) {
        this.total_positive = total_positive;
    }

    public int getTotal_negative() {
        return total_negative;
    }

    public void setTotal_negative(int total_negative) {
        this.total_negative = total_negative;
    }

    public int getReview_score() {
        return review_score;
    }

    public void setReview_score(int review_score) {
        this.review_score = review_score;
    }

    public int getMetacritic() {
        return metacritic;
    }

    public void setMetacritic(int metacritic) {
        this.metacritic = metacritic;
    }

    public double getPrice_initial() {
        return price_initial;
    }

    public void setPrice_initial(double price_initial) {
        this.price_initial = price_initial;
    }

    //*********
    //OVERRIDES
    //*********

    //returns a readable display of all data stored in the Game object
    @Override
    public String toString() {
        String priceString = "$" + price_initial;
        if (priceString.charAt(priceString.length()-2) == '.') {
            priceString += "0";
        }
        return steam_appid + " " + name + ", " + priceString + " | Metacritic: " + metacritic + " | Released: " + release_date +
                " | " + n_achievements + " achievements | Reviews: " + total_reviews + " (" + total_positive + " +, " + total_negative + " -), Score: " + review_score;
    }

    //first compares metacritic score, then uses price as tiebreaker, then uses name (alphabetically/lexicographically) as tiebreaker
    //this puts more relevant games on the right side of the BST and less relevant games on the left side
    /*if this were used in a larger program, it might be a good idea to swap the greater than and less than signs for metacritic
    because search goes from left to right, and people are more likely to search for games with high metacritic scores*/

    //NOTE: This section was edited on 11/11/25 to prioritize price over metacritic score
    @Override
    public int compareTo(Game o) {
        if (price_initial < o.getPrice_initial()) {
            return -1;
        }
        else if (price_initial > o.getPrice_initial()) {
            return 1;
        }
        else {
            if (metacritic < o.getMetacritic()) {
                return -1;
            }
            else if (metacritic > o.getMetacritic()) {
                return 1;
            }
            return name.toLowerCase().compareTo(o.getName().toLowerCase());
        }
    }

    //returns true if and only if all data values are equal
    @Override
    public boolean equals(Object o) {
        Game g = (Game) o;
        return (this.steam_appid == g.getSteam_appid()) && (this.name.equals(g.getName())) &&
                (this.n_achievements == g.getN_achievements()) && (this.release_date.equals(g.getRelease_date())) &&
                (this.total_reviews == g.getTotal_reviews()) && (this.total_positive == g.getTotal_positive()) &&
                (this.total_negative == g.getTotal_negative()) && (this.metacritic == g.getMetacritic()) &&
                (this.review_score == g.getReview_score()) && (this.price_initial == g.getPrice_initial());
    }
}
