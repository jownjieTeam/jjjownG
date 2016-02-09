package com.example.jownjie.nihingo;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.jownjie.nihingo.Models.Game;
import com.example.jownjie.nihingo.Models.Modes.BaseGame;

import butterknife.Bind;
import butterknife.ButterKnife;

public class StageActivity extends AppCompatActivity {
    @Bind(R.id.view)
    ViewPager mViewPager;

    private Game game;
    public static int gameMode;
    public static int questionsSize;
    public static String CURRENT_GAME_MODE;
    public static Game CURRENT_GAME;
    private int currentLevel;
    private int REQUEST_CODE = 0;
    private int RESULT_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stage);
        ButterKnife.bind(this);


        gameMode = getIntent().getExtras().getInt("GAME_MODE");
        game = (Game)getIntent().getExtras().getSerializable("GAME");
        CURRENT_GAME = game;

        switch (gameMode){
            case BaseGame.MODE_BEGINNER: CURRENT_GAME_MODE = "General Info";break;
            case BaseGame.MODE_ADVANCED: CURRENT_GAME_MODE = "Technology";break;
            case BaseGame.MODE_EXPERT: CURRENT_GAME_MODE = "Science";break;
        }

        switch(gameMode){
            case 0:
                questionsSize = game.getBeginnerGame().getQuestionsSize();
                break;
            case 1:
                questionsSize = game.getAdvancedGame().getQuestionsSize();
                break;
            case 2:
                questionsSize = game.getExpertGame().getQuestionsSize();
                break;
        }

        //Log.e("EEEEEE", questionsSize+"");

        getSupportActionBar().setTitle(CURRENT_GAME_MODE);

        mViewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager(), CURRENT_GAME_MODE));
        mViewPager.setPageTransformer(true, new ZoomOutPageTransformer());
    }

    public static String getGameMode() {
        return CURRENT_GAME_MODE;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            if(RESULT_CODE == resultCode) {
                int gamePoints = data.getExtras().getInt("TOP_PLAYER");
                game.getTopPlayer().setGamePoints(gamePoints);
                int totalTime = data.getExtras().getInt("TIMER");
                game.getTimer().setTotalTime(totalTime);
                gameMode = data.getExtras().getInt("GAME_MODE");
                BaseGame temp = (BaseGame)data.getExtras().getSerializable("GAME");
                int gameDifficulty = data.getExtras().getInt("GAME_DIFFICULTY");
                switch(gameMode) {
                    case BaseGame.MODE_BEGINNER :
                                switch(gameDifficulty) {
                                    case BaseGame.POOL_SHORT : game.getBeginnerGame().setGameQuestions_SHORT(temp.getGamePoolList());
                                        break;
                                    case BaseGame.POOL_MEDIUM : game.getBeginnerGame().setGameQuestions_MEDIUM(temp.getGamePoolList());
                                        break;
                                    case BaseGame.POOL_LONG : game.getBeginnerGame().setGameQuestions_LONG(temp.getGamePoolList());
                                        break;
                                    default: Log.e("ERROR", "BEGINNER -> DIFFICULTY -> LENGTH INVALID.");
                                        break;
                                }
                            currentLevel = game.getBeginnerGame().getAccomplished(BaseGame.POOL_SHORT)+game.getBeginnerGame().getAccomplished(BaseGame.POOL_MEDIUM)+game.getBeginnerGame().getAccomplished(BaseGame.POOL_LONG);
                        game.getBeginnerGame().setCurrentLevel(currentLevel);
                        break;
                    case BaseGame.MODE_ADVANCED :
                        switch(gameDifficulty) {
                            case BaseGame.POOL_SHORT : game.getAdvancedGame().setGameQuestions_SHORT(temp.getGamePoolList());
                                break;
                            case BaseGame.POOL_MEDIUM : game.getAdvancedGame().setGameQuestions_MEDIUM(temp.getGamePoolList());
                                break;
                            case BaseGame.POOL_LONG : game.getAdvancedGame().setGameQuestions_LONG(temp.getGamePoolList());
                                break;
                            default: Log.e("ERROR", "ADVANCED -> DIFFICULTY -> LENGTH INVALID.");
                                break;
                        }
                        currentLevel = game.getAdvancedGame().getAccomplished(BaseGame.POOL_SHORT)+game.getAdvancedGame().getAccomplished(BaseGame.POOL_MEDIUM)+game.getAdvancedGame().getAccomplished(BaseGame.POOL_LONG);
                        game.getAdvancedGame().setCurrentLevel(currentLevel);
                        Log.e("LEVEL ", currentLevel + "");
                        break;
                    case BaseGame.MODE_EXPERT :
                        switch(gameDifficulty) {
                            case BaseGame.POOL_SHORT : game.getExpertGame().setGameQuestions_SHORT(temp.getGamePoolList());
                                break;
                            case BaseGame.POOL_MEDIUM : game.getExpertGame().setGameQuestions_MEDIUM(temp.getGamePoolList());
                                break;
                            case BaseGame.POOL_LONG : game.getExpertGame().setGameQuestions_LONG(temp.getGamePoolList());
                                break;
                            default: Log.e("ERROR", "EXPERT -> DIFFICULTY -> LENGTH INVALID.");
                                break;
                        }
                        currentLevel = game.getExpertGame().getAccomplished(BaseGame.POOL_SHORT)+game.getExpertGame().getAccomplished(BaseGame.POOL_MEDIUM)+game.getExpertGame().getAccomplished(BaseGame.POOL_LONG);
                        game.getExpertGame().setCurrentLevel(currentLevel);
                        Log.e("LEVEL ", currentLevel +   "");
                        break;
                    case BaseGame.MODE_NULL : Log.e("ERROR", "UNIDENTIFIED GAME MODE!");
                        break;
                }
            }
    }

    @Override
    public void finish() {
        Intent intent = new Intent();
        intent.putExtra("GAME_MODE",gameMode);
        intent.putExtra("TOP_PLAYER", game.getTopPlayer().getGamePoints());
        switch(gameMode) {
            case BaseGame.MODE_BEGINNER :
                intent.putExtra("GAME", game.getBeginnerGame());
                break;
            case BaseGame.MODE_ADVANCED :
                intent.putExtra("GAME", game.getAdvancedGame());
                break;
            case BaseGame.MODE_EXPERT :
                intent.putExtra("GAME", game.getExpertGame());
                break;
            default: Log.e("ERROR", "GAME MODE ERROR");
                break;
        }
        intent.putExtra("TIMER", game.getTimer().getTotalTime());
        this.setResult(RESULT_CODE,intent);
        super.finish();
    }



    class MyPagerAdapter extends FragmentPagerAdapter{
        private int DEFAULT_PAGE_COUNT = 3;
        private int pageCount = DEFAULT_PAGE_COUNT;
        private String gameMode;

        public MyPagerAdapter(FragmentManager fm, int pageCount, String gameMode) {
            super(fm);
            this.pageCount = pageCount;
            this.gameMode = gameMode;
        }

        public MyPagerAdapter(FragmentManager fm, String gameMode) {
            super(fm);
            this.gameMode = gameMode;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0: return new FiveLetterWord().getNewInstance(gameMode);
                case 1: return new SevenLetterWord().getNewInstance(gameMode);
                case 2: return new NineLetterWord().getNewInstance(gameMode);
            }
            return null;
        }

        @Override
        public int getCount() {
            return pageCount;
        }
    }
}

class ZoomOutPageTransformer implements ViewPager.PageTransformer {
    private static final float MIN_SCALE = 0.85f;
    private static final float MIN_ALPHA = 0.5f;

    public void transformPage(View view, float position) {
        int pageWidth = view.getWidth();
        int pageHeight = view.getHeight();

        if (position < -1) { // [-Infinity,-1)
            // This page is way off-screen to the left.
            view.setAlpha(0);

        } else if (position <= 1) { // [-1,1]
            // Modify the default slide transition to shrink the page as well
            float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
            float vertMargin = pageHeight * (1 - scaleFactor) / 2;
            float horzMargin = pageWidth * (1 - scaleFactor) / 2;
            if (position < 0) {
                view.setTranslationX(horzMargin - vertMargin / 2);
            } else {
                view.setTranslationX(-horzMargin + vertMargin / 2);
            }

            // Scale the page down (between MIN_SCALE and 1)
            view.setScaleX(scaleFactor);
            view.setScaleY(scaleFactor);

            // Fade the page relative to its size.
            view.setAlpha(MIN_ALPHA +
                    (scaleFactor - MIN_SCALE) /
                            (1 - MIN_SCALE) * (1 - MIN_ALPHA));

        } else { // (1,+Infinity]
            // This page is way off-screen to the right.
            view.setAlpha(0);
        }
    }
}
