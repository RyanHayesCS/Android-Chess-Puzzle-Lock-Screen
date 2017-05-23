package edu.hayes_rlynchburg.chesspuzzlelockscreen;

/**
 * Created by Ryan Hayes on 4/11/2017.
 */

public class Puzzle {

    private String initialLayout;
    private String finalLayout;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFinalLayout() {
        return finalLayout;
    }

    public void setFinalLayout(String finalLayout) {
        this.finalLayout = finalLayout;
    }

    public String getInitialLayout() {
        return initialLayout;
    }

    public void setInitialLayout(String initialLayout) {
        this.initialLayout = initialLayout;
    }

}
