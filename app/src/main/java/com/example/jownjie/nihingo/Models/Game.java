package com.example.jownjie.nihingo.Models;

import java.util.List;

/**
 * Created by hebi5 on 12/2/2015.
 * edited by User on 12/8/2015 : reconstructed class.
 */
abstract class Game {
    private List<GamePool> questionsPool;

    public List<GamePool> getQuestionsPool() {
        return questionsPool;
    }

    public void setQuestionsPool(List<GamePool> questionsPool) {
        this.questionsPool = questionsPool;
    }
}
