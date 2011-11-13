/*
 * Health Buddy database adapter
 */

package com.black.healthbuddy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class HealthBuddyDbAdapter {

	//public static final String KEY_TITLE = "title";
	//public static final String KEY_BODY = "body";
	//public static final String KEY_ROWID = "_id";

	private static final String TAG = "HealthBuddyDbAdapter";
	private DatabaseHelper mDbHelper;
	private SQLiteDatabase mDb;

	/**
	 * Database creation sql statement
	 */
	private static final String DATABASE_CREATE = "create table notes (_id integer primary key autoincrement, "
			+ "title text not null, body text not null);";

	private static final String USERTABLE_CREATE = "create table UserTable(_id integer primary key autoincrement, "
			+ "age integer not null, sex text not null, recommendedCalaroieBank integer);";

	public static final String EXERCISENAME = "exerciseName";
	private static final String EXERCISETABLE_CREATE = "create table ExerciseTable(_id integer primary key autoincrement, "
			+ "exerciseName text not null, recommendedDailyDuration integer not null, "
			+ "minimumDuration integer not null, caloriesPerMinDuration integer not null)";

	private static final String NUTRITIONTABLE_CREATE = "create table NutritionTable(_id integer primary key autoincrement, "
			+ "NutritionRecommendationTable_FK integer not null, ScanID integer not null, "
			+ "FoodOrNutrientName text not null, foodMinPortion integer not null, "
			+ "caloriesPerMinPortion integer not null)";

	private static final String NUTRITIONRECOMMENDATIONTABLE_CREATE = "create table NutritionRecommendationTable(foodGroup text not null, "
			+ "recommendedDailyAmount integer not null)";

	private static final String DATABASE_NAME = "data";
	private static final String DATABASE_TABLE = "notes";

	private static final String USERTABLE_NAME = "UserTable";
	private static final String EXERCISETABLE_NAME = "ExerciseTable";
	private static final String NUTRITIONTABLE_NAME = "NutritionTable";
	private static final String NUTRITIONRECOMMENDATION_TABLE = "NutritionRecommendationTable";

	private static final int DATABASE_VERSION = 2;

	private final Context mCtx;

	private static class DatabaseHelper extends SQLiteOpenHelper {

		DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {

			//db.execSQL(DATABASE_CREATE);
			db.execSQL(USERTABLE_CREATE);
			db.execSQL(EXERCISETABLE_CREATE);
			db.execSQL(NUTRITIONTABLE_CREATE);
			db.execSQL(NUTRITIONRECOMMENDATIONTABLE_CREATE);

		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
					+ newVersion + ", which will destroy all old data");
			db.execSQL("DROP TABLE IF EXISTS notes");
			onCreate(db);
		}
	}

	/**
	 * Constructor - takes the context to allow the database to be
	 * opened/created
	 * 
	 * @param ctx
	 *            the Context within which to work
	 */
	public HealthBuddyDbAdapter(Context ctx) {
		this.mCtx = ctx;
	}

	/**
	 * Open the notes database. If it cannot be opened, try to create a new
	 * instance of the database. If it cannot be created, throw an exception to
	 * signal the failure
	 * 
	 * @return this (self reference, allowing this to be chained in an
	 *         initialization call)
	 * @throws SQLException
	 *             if the database could be neither opened or created
	 */

	public HealthBuddyDbAdapter open() throws SQLException {
		mDbHelper = new DatabaseHelper(mCtx);
		mDb = mDbHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		mDbHelper.close();
	}

	/**
	 * Create a new exercise using the exercise details. If the Exercise is
	 * successfully created return the new rowId for that note, otherwise return
	 * a -1 to indicate failure.
	 * 
	 * @param exercise details
	 * 
	 * @return rowId or -1 if failed
	 */

//	public long createNote(String title, String body) {
//		ContentValues initialValues = new ContentValues();
//		initialValues.put(KEY_TITLE, title);
//		initialValues.put(KEY_BODY, body);
//
//		return mDb.insert(DATABASE_TABLE, null, initialValues);
//	}

	public long createExercise(String exerciseName,
			int recommendedDailyDuration, int minimumDuration,
			int caloriesPerMinDuration) {
		ContentValues initialValues = new ContentValues();
		initialValues.put("exerciseName", exerciseName);
		initialValues.put("recommendedDailyDuration", recommendedDailyDuration);
		initialValues.put("minimumDuration", minimumDuration);
		initialValues.put("caloriesPerMinDuration", caloriesPerMinDuration);

		return mDb.insert(EXERCISETABLE_NAME, null, initialValues);//
	}

	/**
	 * Delete the exercise with the given rowId
	 * 
	 * @param rowId
	 *            id of note to delete
	 * @return true if deleted, false otherwise
	 */
//	public boolean deleteNote(long rowId) {
//
//		return mDb.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
//	}

	public boolean deleteExercise(long rowId) {

		return mDb.delete(EXERCISETABLE_NAME, "_id" + "=" + rowId, null) > 0;
	}

	/**
	 * Return a Cursor over the exercises of all exercises in the exercise table
	 * 
	 * @return Cursor over all exercises
	 */
//	public Cursor fetchAllNotes() {
//
//		return mDb.query(DATABASE_TABLE, new String[] { KEY_ROWID, KEY_TITLE,
//				KEY_BODY }, null, null, null, null, null);
//	}

	public Cursor fetchAllExercises() {
		return mDb.query(EXERCISETABLE_NAME, new String[] { "_id",
				"exerciseName", "caloriesPerMinDuration" }, null, null, null,
				null, null);
	}

	/**
	 * Return a Cursor positioned at the exercise that matches the given rowId
	 * 
	 * @param rowId
	 *            id of exercise to retrieve
	 * @return Cursor positioned to matching exercise, if found
	 * @throws SQLException
	 *             if note could not be found/retrieved
	 */
	public Cursor fetchExercise(long rowId) throws SQLException {

		Cursor mCursor =

		mDb.query(true, EXERCISETABLE_NAME, new String[] { "_id", EXERCISENAME,
				"caloriesPerMinDuration" }, "_id" + "=" + rowId, null, null, null, null,
				null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;

	}

//	public Cursor fetchNote(long rowId) throws SQLException {
//
//		Cursor mCursor =
//
//		mDb.query(true, DATABASE_TABLE, new String[] { KEY_ROWID, KEY_TITLE,
//				KEY_BODY }, KEY_ROWID + "=" + rowId, null, null, null, null,
//				null);
//		if (mCursor != null) {
//			mCursor.moveToFirst();
//		}
//		return mCursor;
//
//	}

	/**
	 * Update the note using the details provided. The note to be updated is
	 * specified using the rowId, and it is altered to use the title and body
	 * values passed in
	 * 
	 * @param rowId
	 *            id of note to update
	 * @param execise details
	 * @return true if the note was successfully updated, false otherwise
	 */
//	public boolean updateNote(long rowId, String title, String body) {
//		ContentValues args = new ContentValues();
//		args.put(KEY_TITLE, title);
//		args.put(KEY_BODY, body);
//
//		return mDb.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
//	}
//			
	public boolean updateExercise(long rowId, String excerciseName, int recommendedDailyDuration,
			int minimumDuration, int caloriesPerMinDuration) {
		ContentValues args = new ContentValues();
		args.put(EXERCISENAME, excerciseName);
		args.put("recommendedDailyDuration", recommendedDailyDuration);
		args.put("minimumDuration", minimumDuration );
		args.put("caloriesPerMinDuration", caloriesPerMinDuration);
		

		return mDb.update(EXERCISETABLE_NAME, args, "_id" + "=" + rowId, null) > 0;
	}

	
	
	public void dropAndRecreateTables() {

		mDb.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
//		mDb.execSQL(DATABASE_CREATE);

		mDb.execSQL("DROP TABLE IF EXISTS " + USERTABLE_NAME);
		mDb.execSQL(USERTABLE_CREATE);

		mDb.execSQL("DROP TABLE IF EXISTS " + EXERCISETABLE_NAME);
		mDb.execSQL(EXERCISETABLE_CREATE);

		mDb.execSQL("DROP TABLE IF EXISTS " + NUTRITIONTABLE_NAME);
		mDb.execSQL(NUTRITIONTABLE_CREATE);

		mDb.execSQL("DROP TABLE IF EXISTS " + NUTRITIONRECOMMENDATION_TABLE);
		mDb.execSQL(NUTRITIONRECOMMENDATIONTABLE_CREATE);

	}

	public void perform_query(String string) {
		mDb.execSQL(string);

	}

	public void insertDataIntoTables() {
		mDb.execSQL("INSERT INTO ExerciseTable(exerciseName, recommendedDailyDuration, "
				+ "minimumDuration, caloriesPerMinDuration) VALUES ('walking', 30, 1,300)");

		mDb.execSQL("INSERT INTO ExerciseTable(exerciseName, recommendedDailyDuration, "
				+ "minimumDuration, caloriesPerMinDuration) VALUES ('jogging', 15, 1,400)");

		mDb.execSQL("INSERT INTO UserTable(age, sex, recommendedCalaroieBank)"
				+ " VALUES (28, 'female', 2079)");

		mDb.execSQL("INSERT INTO NutritionTable(NutritionRecommendationTable_FK,"
				+ "ScanID, FoodOrNutrientName , foodMinPortion , "
				+ "caloriesPerMinPortion) 	VALUES ('Dairy', 111111111, 'Milk', 15, 10)");
		
		mDb.execSQL("INSERT INTO NutritionTable(NutritionRecommendationTable_FK,"
				+ "ScanID, FoodOrNutrientName , foodMinPortion , "
				+ "caloriesPerMinPortion) 	VALUES ('Bakery Wares', 111111111, 'White Bread', 15, 10)");

		createExercise("Swimming", 15, 15, 400);
		
		//updateExercise(1, "stuff", 5, 6, 4);

		//deleteExercise(1);

	}
}
