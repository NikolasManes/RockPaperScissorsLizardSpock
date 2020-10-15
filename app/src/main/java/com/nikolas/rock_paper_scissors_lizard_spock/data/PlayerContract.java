package com.nikolas.rock_paper_scissors_lizard_spock.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Nikolas Manes on 13/4/2017.
 */

public class PlayerContract {

    private static final String LOG_TAG = PlayerContract.class.getSimpleName();

    /***** Constant Values We Need To Create The Content URI *****/
    // Content Authority is the complete package name is unique on the device
    public static final String CONTENT_AUTHORITY = "com.nikolas.rock_paper_scissors_lizard_spock";
    // The URI the apps use to contact the content provider
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    // Possible path to append the URI
    public static final String PATH_PLAYER_TABLE = "players";

    // This class must not instantiate, so to prevent it we create an empty constructor
    private PlayerContract(){}

    /**
     *  Inner class that defines the constant values for players table
     *  Each entry in the table represents a single player
     */
    public static final class PlayerEntry implements BaseColumns {

        // Glue all URI parts together...
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_PLAYER_TABLE);

        // The Mime type of the {@link CONTENT_URI} for single player
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PLAYER_TABLE;

        // The Mime type of the {@link CONTENT_URI} for a list of players
        public static final String CONTENT_LIST_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PLAYER_TABLE;

        // Name of the database table
        public static final String TABLE_NAME = "players";

        // ID - INTEGER : Unique id number for players. Only use in the database.
        public static final String _ID = BaseColumns._ID;

        // name - TEXT : Player Name.
        public static final String COLUMN_PLAYER_NAME = "name";

        // last_game_score - INTEGER : Last game score.
        public static final String COLUMN_LAST_GAME_SCORE = "last_game_score";

        // last_game_level - INTEGER : Last game level.
        public static final String COLUMN_LAST_GAME_LEVEL = "last_game_level";

        // high_score - INTEGER : Player's highest score.
        public static final String COLUMN_HIGH_SCORE = "high_score";

        // top_level - INTEGER : Player's top level
        public static final String COLUMN_TOP_LEVEL = "top_level";

        /***** METHODS TO CHECK IF THE VALUES INSERTED IN THE DATABASE ARE VALID *****/
        // Check if the name is valid
        public static boolean nameIsValid(String name) {
            if (name != null && !name.isEmpty()) {
                return true;
            }
            return false;
        }
        // Check if the score is valid (same for last game and high score)
        public static boolean scoreIsValid(int score) {
            if (score >= 0) {
                return true;
            }
            return false;
        }
        // Check if the level is valid (same for last game and top level)
        public static boolean levelIsValid(int level) {
            if (level >= 0) {
                return true;
            }
            return false;
        }
    }
}
