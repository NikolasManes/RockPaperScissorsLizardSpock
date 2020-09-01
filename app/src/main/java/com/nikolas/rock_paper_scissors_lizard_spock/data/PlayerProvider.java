package com.nikolas.rock_paper_scissors_lizard_spock.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.nikolas.rock_paper_scissors_lizard_spock.data.PlayerContract.PlayerEntry;

/**
 * Created by Nikolas on 13/4/2017.
 */

public class PlayerProvider extends ContentProvider {

    public static final String LOG_TAG = PlayerProvider.class.getSimpleName();
    // The code that is returned whether we need to access the whole table or a single row
    public static final int PLAYERS = 100;
    public static final int PLAYER_ID = 101;
    // PlayerDbHelper Object
    private PlayerDbHelper mPlayerDbHelper;

    // URI matcher
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        // Add all the URIs that should be recognizable by the content provider
        // UriMatcher code for the whole table
        sUriMatcher.addURI(PlayerContract.CONTENT_AUTHORITY, PlayerContract.PATH_PLAYER_TABLE, PLAYERS);
        // UriMatcher code for a single row
        sUriMatcher.addURI(PlayerContract.CONTENT_AUTHORITY, PlayerContract.PATH_PLAYER_TABLE + "/#", PLAYER_ID);
    }


    @Override
    public boolean onCreate() {
        // Initialize the mPlayerDbHelper
        mPlayerDbHelper = new PlayerDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        // Create a database object
        SQLiteDatabase database = mPlayerDbHelper.getReadableDatabase();
        // Then a cursor object
        Cursor cursor;

        int match = sUriMatcher.match(uri);
        switch (match){
            case PLAYERS:
                cursor = database.query(
                        PlayerEntry.TABLE_NAME,         // The name of the table
                        projection,                     // The columns to return
                        null,                           // The columns for the WHERE clause
                        null,                           // The values for the WHERE clause
                        null,                           // Don't group the rows
                        null,                           // Don't filter by row groups
                        sortOrder);                     // The sort order
                break;
            case PLAYER_ID:
                selection = PlayerEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = database.query(
                        PlayerEntry.TABLE_NAME,         // The name of the table
                        projection,                     // The columns to return
                        selection,                      // The columns for the WHERE clause
                        selectionArgs,                  // The values for the WHERE clause
                        null,                           // Don't group the rows
                        null,                           // Don't filter by row groups
                        null);                          // The sort order
                break;
            default:
                throw new IllegalArgumentException("Cannot query... Unknown URI: " + uri);
        }
        // Set notification URI on the Cursor, so we know what content URI the Cursor was created for.
        // If the data of that URI changes then the Cursor must be updated.
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch(match){
            case PLAYER_ID:
                return PlayerEntry.CONTENT_ITEM_TYPE;
            case PLAYERS:
                return PlayerEntry.CONTENT_LIST_TYPE;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri + " with match " + match);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final int match = sUriMatcher.match(uri);
        switch(match) {
            case PLAYERS:
                return insertPlayer(uri, values);
            default:
                throw new IllegalArgumentException("Error: cannot save the high score");
        }
    }

    private Uri insertPlayer(Uri uri, ContentValues values) {
        // VALUES
        String playerName = values.getAsString(PlayerEntry.COLUMN_PLAYER_NAME);
        int lastScore = values.getAsInteger(PlayerEntry.COLUMN_LAST_GAME_SCORE);
        int lastLevel = values.getAsInteger(PlayerEntry.COLUMN_LAST_GAME_LEVEL);
        int highScore = values.getAsInteger(PlayerEntry.COLUMN_HIGH_SCORE);
        int topLevel = values.getAsInteger(PlayerEntry.COLUMN_TOP_LEVEL);

        /****** SANITY CHECK ******/
        // Check if the values that would be entered in the database are valid
        if (!PlayerEntry.nameIsValid(playerName)){
            throw new IllegalArgumentException("You must enter a name.");
        }
        if (!PlayerEntry.scoreIsValid(lastScore)) {
            throw new IllegalArgumentException("Last game score not valid");
        }
        if (!PlayerEntry.scoreIsValid(highScore)) {
            throw new IllegalArgumentException("HighScore not valid");
        }
        if (!PlayerEntry.levelIsValid(lastLevel)) {
            throw new IllegalArgumentException("Last game level not valid");
        }
        if (!PlayerEntry.levelIsValid(topLevel)) {
            throw new IllegalArgumentException("TopLevel not valid");
        }

        // Get writable database
        SQLiteDatabase database = mPlayerDbHelper.getWritableDatabase();

        // Insert a new player on the highScores database with the given content values
        long newRowID = database.insert(PlayerEntry.TABLE_NAME, null, values);

        // If the ID is -1 the insertion of the new highScore has failed
        if (newRowID == -1) {
            Log.e(LOG_TAG, "Failed to insert highScore.");
            return null;
        }

        // Notify all listeners that the Uri has changed
        getContext().getContentResolver().notifyChange(uri, null);

        // Once the new highScore successfully inserted to the database
        // Return the new URI with the ID appended at the end of if
        return ContentUris.withAppendedId(uri, newRowID);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}