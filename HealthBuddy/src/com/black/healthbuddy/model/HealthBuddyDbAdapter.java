/*
 * Health Buddy database adapter
 */

package com.black.healthbuddy.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class HealthBuddyDbAdapter {

	private static final String TAG = "HealthBuddyDbAdapter";
	private DatabaseHelper mDbHelper;
	private SQLiteDatabase mDb;

	/**
	 * Database creation sql statement
	 */

	// TABLES GROUP 1
	// the table represents the user using the phone
	private static final String USERPROFILE_CREATE = "create table UserProfileTable(_id integer primary key autoincrement, "
			+ "userName text not null, userAge integer not null, userSex text not null, userHeight real not null, "
			+ "userWeight real not null, userCalorieBank integer not null, userExerciseLog text not null);";

	// this table is used to record all the user food data from the user for the
	// last say 24 hours
	// for food that is not in the the database the user can enter his data into
	// the Nutrition table and it can be referenced from the table below
	// this table relates to the NutritionData object
	private static final String USERPROFILE_NUT_DATA_CREATE = "create table userProfileNutData(_id integer primary key autoincrement, NutritionId_FK integer not null, startTime integer not null, finishTime text not null);";

	// this table is used to record all activity for say last 24 hours
	// relates to Exercise Data object
	private static final String USERPROFILE_EX_DATA_CREATE = "create table userProfileExData(_id integer primary key autoincrement, exerciseId_FK integer not null, startTime integer not null, finishTime text not null);";

	// TABLES GROUP 2
	// this is the table that will store the mr perfect or mrs perfect details
	// based on Franks calulations he was on about today!
	private static final String USER_IDEAL_TABLE_CREATE = "create table user_ideal_table(_id integer primary key autoincrement, "
			+ "age integer not null, sex text not null, recommendedCalaroieBank integer);";

	// TABLES GROUP 3
	// stores all the data relating to typical exercises, I will only use on the
	// firstt column from the http://www.mayoclinic.com/health/exercise/SM00109
	// site at 1 minute duration
	private static final String EXERCISETABLE_CREATE = "create table ExerciseTable(_id integer primary key autoincrement, "
			+ "exerciseName text not null, recommendedDailyDuration integer not null, "
			+ "minimumDuration integer not null, caloriesPerMinDuration real not null)";

	// nutritional data from
	// http://www.caloriecounting.co.uk/resources/intro.htm#calories with
	// NutritionRecommendationTable_FK relating to http://www.choosemyplate.gov/
	private static final String NUTRITIONTABLE_CREATE = "create table NutritionTable(_id integer primary key autoincrement, "
			+ "NutritionRecommendationTable_FK integer not null, ScanID integer not null, foodType text not null,"
			+ "FoodOrNutrientName text not null, foodMinPortion integer not null, "
			+ "caloriesPerMinPortion integer not null, "
			+ "numberOfContainers integer not null, "
			+ "containerType text not null , measurement real not null, measurementUnit text not null)";

	// info base on http://www.choosemyplate.gov/
	private static final String NUTRITIONRECOMMENDATIONTABLE_CREATE = "create table NutritionRecommendationTable(foodGroupFromMyPlate text not null primary key, "
			+ "recommendedDailyAmount integer not null , unitOfMeasurment text not null)";

	private static final String DATABASE_NAME = "data";

	private static final int DATABASE_VERSION = 2;

	private final Context mCtx;

	private static class DatabaseHelper extends SQLiteOpenHelper {

		DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {

			db.execSQL(USERPROFILE_CREATE);
			db.execSQL(USERPROFILE_NUT_DATA_CREATE);
			db.execSQL(USERPROFILE_EX_DATA_CREATE);

			db.execSQL(USER_IDEAL_TABLE_CREATE);

			db.execSQL(EXERCISETABLE_CREATE);
			db.execSQL(NUTRITIONTABLE_CREATE);
			db.execSQL(NUTRITIONRECOMMENDATIONTABLE_CREATE);
			//

		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
					+ newVersion + ", which will destroy all old data");
			db.execSQL("DROP TABLE IF EXISTS " + "UserProfileTable");
			db.execSQL("DROP TABLE IF EXISTS " + "userProfileNutData");
			db.execSQL("DROP TABLE IF EXISTS " + " userProfileExData");
			db.execSQL("DROP TABLE IF EXISTS " + "user_ideal_table");
			db.execSQL("DROP TABLE IF EXISTS " + "ExerciseTable");
			db.execSQL("DROP TABLE IF EXISTS " + "NutritionTable");
			db.execSQL("DROP TABLE IF EXISTS " + "NutritionRecommendationTable");
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
	 * Open the mDbHelper database. If it cannot be opened, try to create a new
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

	// ///////////CREATEING RECORDS
	// create a record for the phone user
	// GROUP 1
	public long createUserProfile(String userName, int userAge, String userSex,
			double userHeight, double userWeight, int userCalorieBank,
			String userExerciseLog) {
		ContentValues initialValues = new ContentValues();
		initialValues.put("userName", userName);
		initialValues.put("userAge", userAge);
		initialValues.put("userSex", userSex);
		initialValues.put("userHeight", userHeight);
		initialValues.put("userWeight", userWeight);
		initialValues.put("userCalorieBank", userCalorieBank);
		initialValues.put("userExerciseLog", userExerciseLog);

		return mDb.insert("UserProfileTable", null, initialValues);
	}

	// create a user profile nutritional record
	public long createUserProfileNutData(int NutritionId_FK, long startTime,
			long finishTime) {
		ContentValues initialValues = new ContentValues();
		initialValues.put("NutritionId_FK", NutritionId_FK);
		initialValues.put("startTime", startTime);
		initialValues.put("finishTime", finishTime);
		return mDb.insert("userProfileNutData", null, initialValues);//
	}

	// create a user profile exercise record
	public long createuserProfileExData(int exerciseId_FK, long startTime,
			long finishTime) {
		ContentValues initialValues = new ContentValues();
		initialValues.put("exerciseId_FK", exerciseId_FK);
		initialValues.put("startTime", startTime);
		initialValues.put("finishTime", finishTime);
		return mDb.insert("userProfileExData", null, initialValues);//
	}

	// Group 2
	// create an ideal user record
	public long createanIdealUser(int age, String sex,
			int recommendedCalaroieBank) {
		ContentValues initialValues = new ContentValues();
		initialValues.put("age", age);
		initialValues.put("sex", sex);
		initialValues.put("recommendedCalaroieBank", recommendedCalaroieBank);
		return mDb.insert("user_ideal_table", null, initialValues);//
	}

	// GROUP 3
	// create an exercise record
	public long createExercise(String exerciseName,
			int recommendedDailyDuration, int minimumDuration,
			double caloriesPerMinDuration) {
		ContentValues initialValues = new ContentValues();
		initialValues.put("exerciseName", exerciseName);
		initialValues.put("recommendedDailyDuration", recommendedDailyDuration);
		initialValues.put("minimumDuration", minimumDuration);
		initialValues.put("caloriesPerMinDuration", caloriesPerMinDuration);

		return mDb.insert("ExerciseTable", null, initialValues);//
	}

	// create a nutrient record
	public long createNutrient(String NutritionRecommendationTable_FK,
			int ScanID, String foodType, String FoodOrNutrientName,
			int foodMinPortion, int caloriesPerMinPortion,
			int numberOfContainers, String containerType, double measurement,
			String measurementUnit) {
		ContentValues initialValues = new ContentValues();
		initialValues.put("NutritionRecommendationTable_FK",
				NutritionRecommendationTable_FK);
		initialValues.put("ScanID", ScanID);
		initialValues.put("foodType", foodType);
		initialValues.put("FoodOrNutrientName", FoodOrNutrientName);
		initialValues.put("foodMinPortion", foodMinPortion);
		initialValues.put("caloriesPerMinPortion", caloriesPerMinPortion);
		initialValues.put("numberOfContainers", numberOfContainers);
		initialValues.put("containerType", containerType);
		initialValues.put("measurement", measurement);
		initialValues.put("measurementUnit", measurementUnit);

		return mDb.insert("NutritionTable", null, initialValues);//
	}

	// create a Nutritional reccomendation record
	public long createNutritionalRecommendation(String foodGroupFromMyPlate,
			int recommendedDailyAmount, String unitOfMeasurment) {
		ContentValues initialValues = new ContentValues();
		initialValues.put("foodGroupFromMyPlate", foodGroupFromMyPlate);
		initialValues.put("recommendedDailyAmount", recommendedDailyAmount);
		initialValues.put("unitOfMeasurment", unitOfMeasurment);

		return mDb.insert("NutritionRecommendationTable", null, initialValues);//
	}

	// ///////////UPDATING RECORDS
	// Group 1
	// update user profile
	public boolean updateUserProfile(long _id, String userName, int userAge,
			String userSex, double userHeight, double userWeight,
			int userCalorieBank, String userExerciseLog) {
		ContentValues args = new ContentValues();
		args.put("userName", userName);
		args.put("userAge", userAge);
		args.put("userSex", userSex);
		args.put("userHeight", userHeight);
		args.put("userWeight", userWeight);
		args.put("userCalorieBank", userCalorieBank);
		args.put("userExerciseLog", userExerciseLog);

		return mDb.update("UserProfileTable", args, "_id" + "=" + _id, null) > 0;
	}

	// update phone users nutritional records
	public boolean updateUserProfileNutData(long _id, int NutritionId_FK,
			long startTime, long finishTime) {
		ContentValues args = new ContentValues();
		args.put("NutritionId_FK", NutritionId_FK);
		args.put("startTime", startTime);
		args.put("finishTime", finishTime);
		return mDb.update("userProfileNutData", args, "_id" + "=" + _id, null) > 0;//
	}

	// update phone user exercise records
	public boolean updateuserProfileExData(long _id, int exerciseId_FK,
			long startTime, long finishTime) {
		ContentValues args = new ContentValues();
		args.put("exerciseId_FK", exerciseId_FK);
		args.put("startTime", startTime);
		args.put("finishTime", finishTime);
		return mDb.update("userProfileExData", args, "_id" + "=" + _id, null) > 0;
	}

	// Group 2
	// update ideal user record
	public boolean updateIdealUser(long _id, int age, String sex,
			int recommendedCalaroieBank) {
		ContentValues args = new ContentValues();
		args.put("age", age);
		args.put("sex", sex);
		args.put("recommendedCalaroieBank", recommendedCalaroieBank);
		return mDb.update("user_ideal_table", args, "_id" + "=" + _id, null) > 0;//
	}

	// GROUP 3
	// update an exercise
	public boolean updateExercise(long _id, String excerciseName,
			int recommendedDailyDuration, int minimumDuration,
			int caloriesPerMinDuration) {
		ContentValues args = new ContentValues();
		args.put("ExerciseTable", excerciseName);
		args.put("recommendedDailyDuration", recommendedDailyDuration);
		args.put("minimumDuration", minimumDuration);
		args.put("caloriesPerMinDuration", caloriesPerMinDuration);

		return mDb.update("exerciseName", args, "_id" + "=" + _id, null) > 0;
	}

	// update a nutrient record
	public boolean updateNutrient(long _id,
			String NutritionRecommendationTable_FK, int ScanID,
			String foodType, String FoodOrNutrientName, int foodMinPortion,
			int caloriesPerMinPortion, int numberOfContainers,
			String containerType, double measurement, String measurementUnit) {
		ContentValues args = new ContentValues();
		args.put("NutritionRecommendationTable_FK",
				NutritionRecommendationTable_FK);
		args.put("ScanID", ScanID);
		args.put("foodType", foodType);
		args.put("FoodOrNutrientName", FoodOrNutrientName);
		args.put("foodMinPortion", foodMinPortion);
		args.put("caloriesPerMinPortion", caloriesPerMinPortion);
		args.put("numberOfContainers", numberOfContainers);
		args.put("containerType", containerType);
		args.put("measurement", measurement);
		args.put("measurementUnit", measurementUnit);

		return mDb.update("NutritionTable", args, "_id" + "=" + _id, null) > 0;
	}

	// update a Nutritional reccomendation record
	public boolean updateNutritionalRecommendation(long _id,
			String foodGroupFromMyPlate, int recommendedDailyAmount,
			String unitOfMeasurment) {
		ContentValues args = new ContentValues();
		args.put("foodGroupFromMyPlate", foodGroupFromMyPlate);
		args.put("recommendedDailyAmount", recommendedDailyAmount);
		args.put("unitOfMeasurment", unitOfMeasurment);

		return mDb.update("NutritionRecommendationTable", args, "_id" + "="
				+ _id, null) > 0;
	}

	// ////////////DELETING RECORDS
	// delete a row in a table when based on its id in a specidied table
	public boolean deleteRecordInTable(long rowId, String Table) {

		return mDb.delete(Table, "_id" + "=" + rowId, null) > 0;
	}

	// //////////////QUERYING ANY TABLE OR QUERYING ANY JOINED TABLES
	// Cursor query(String table, String[] columns, String selection, String[]
	// selectionArgs, String groupBy, String having, String orderBy)
	// Query the given table, returning a Cursor over the result set.
	public Cursor queryTable(String table, String[] columns, String selection,
			String[] selectionArgs, String groupBy, String having,
			String orderBy) {

		return mDb.query(table, columns, selection, selectionArgs, groupBy,
				having, orderBy);
	}

	// /////////rawQuery
	public Cursor rawQueryDatabase(String sql, String[] selectionArgs) {
		return mDb.rawQuery(sql, selectionArgs);
	}

	public void dropAndRecreateTables() {

		mDb.execSQL("DROP TABLE IF EXISTS " + "UserProfileTable");
		mDb.execSQL(USERPROFILE_CREATE);

		mDb.execSQL("DROP TABLE IF EXISTS " + "userProfileNutData");
		mDb.execSQL(USERPROFILE_NUT_DATA_CREATE);

		mDb.execSQL("DROP TABLE IF EXISTS " + " userProfileExData");
		mDb.execSQL(USERPROFILE_EX_DATA_CREATE);

		mDb.execSQL("DROP TABLE IF EXISTS " + "user_ideal_table");
		mDb.execSQL(USER_IDEAL_TABLE_CREATE);

		mDb.execSQL("DROP TABLE IF EXISTS " + "ExerciseTable");
		mDb.execSQL(EXERCISETABLE_CREATE);

		mDb.execSQL("DROP TABLE IF EXISTS " + "NutritionTable");
		mDb.execSQL(NUTRITIONTABLE_CREATE);

		mDb.execSQL("DROP TABLE IF EXISTS " + "NutritionRecommendationTable");
		mDb.execSQL(NUTRITIONRECOMMENDATIONTABLE_CREATE);

	}

	// /////////////SQL QUERY in raw form can be used for inserts like
	// "INSERT INTO ExerciseTable(exerciseName, recommendedDailyDuration, "
	// +
	// "minimumDuration, caloriesPerMinDuration) VALUES ('walking', 30, 1,300)"
	public void perform_query(String string) {
		mDb.execSQL(string);

	}

	public void sampletestQuerys() {
		//sample query Cursor c = mDbHelper.queryTable("NutritionTable ", null,"foodtype = 'Drinks'", null , null, null, null);
		// see http://developer.android.com/reference/android/database/sqlite/SQLiteDatabase.html  for
		// a detailled look at how the query method works which the queryTable method uses.
		
		//if you wanted to find the All the Vegetables names and there recommended daily amount and then put them in a string you would do the following 
		//queryTable will return a Cursor that will join the NutritionTable and the NutrionaRecommened and select items that are vegetables
		Cursor c = 
				this.queryTable(
						"NutritionTable JOIN NutritionRecommendationTable ON (NutritionTable.NutritionRecommendationTable_FK = NutritionRecommendationTable.foodGroupFromMyPlate)",
						null,
						"NutritionRecommendationTable.foodGroupFromMyPlate = 'Vegetables'",
						null, null, null, null);
		String result = "";

		int iRow = c.getColumnIndexOrThrow("_id");
		int iFood_Name = c.getColumnIndexOrThrow("FoodOrNutrientName");
		int irecommeded = c.getColumnIndexOrThrow("recommendedDailyAmount");
		//
		//we then iterate through the curser taking out the results
		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			result = result + "Record No:" + c.getString(iRow) + " "
					+ c.getString(iFood_Name) + " Recommended Daily Amount: "
					+ c.getString(irecommeded) + "\t";
		}

		c.close(); // closing curser
		int x = 0;

		x = 10 * 10;

		////////different updates to show whats happening
		this.updateUserProfile(1, "Jack Sparrow", 40, "Male", 1.65, 77,
				2300, "userExerciseLog");
		this.updateUserProfileNutData(1, 50, 4234, 234234);
		this.updateuserProfileExData(1, 3, 43543, 45555555);


		createUserProfile("Jill Dunne", 24, "female", 1.65, 60, 2100,
				"user Exercise log");

		createuserProfileExData(2, 11212345, 23452312);
		createUserProfileNutData(30, 34523423, 34534534);

	}

	public void insertDataIntoTables() {

		createExercise("Walking 4mph", 45, 15, 6);
		createExercise("Walking 2mph", 60, 30, 3);
		createExercise("Running 8mph", 30, 15, 16.43);
		createExercise("Sleeping", 30, 15, 1);
		createExercise("Sitting/Resting", 30, 15, 1.25);

		createNutritionalRecommendation("Grain", 85, "g");
		createNutritionalRecommendation("Vegetables", 177, "g");
		createNutritionalRecommendation("Fruit", 106, "g");
		createNutritionalRecommendation("Dairy", 400, "ml");
		createNutritionalRecommendation("Protein", 141, "g");

		createNutrient("Dairy", 111111, "Chocolate", "Dairy Milk Cadburys", 1,
				255, 1, "Bar", 49, "g");
		createNutrient("Dairy", 111111, "Chocolate", "Dream Cadburys", 1, 250,
				1, "Bar", 45, "g");
		createNutrient("Dairy", 111111, "Chocolate", "Rolo Nestle", 1, 247, 1,
				"Pack", 52.5, "g");
		createNutrient("Dairy", 111111, "Chocolate",
				"Dairy Milk with Caramel Cadburys", 1, 240, 1, "Bar", 50, "g");
		createNutrient("Dairy", 111111, "Chocolate",
				"Dairy Milk with Fruit  Nut Cadburys", 1, 240, 1, "Bar", 49,
				"g");
		createNutrient("Dairy", 111111, "Chocolate", "Toffee Crisp Nestle", 1,
				227, 1, "Bar", 44, "g");
		createNutrient("Dairy", 111111, "Chocolate", "Crunchie Cadburys", 1,
				200, 1, "Bar", 42, "g");
		createNutrient("Dairy", 111111, "Chocolate", "Snickers Mars", 1, 190,
				1, "Size", 38, "g");
		createNutrient("Dairy", 111111, "Chocolate", "Maltesers Mars", 1, 187,
				1, "Packet", 37, "g");
		createNutrient("Dairy", 111111, "Chocolate", "Turkish Delight Frys", 1,
				186, 1, "Bar", 51, "g");
		createNutrient("Dairy", 111111, "Chocolate", "Mars Bar Mars", 1, 175,
				1, "Size", 32, "g");
		createNutrient("Dairy", 111111, "Chocolate", "Flake Cadburys", 1, 170,
				1, "Bar", 32, "g");
		createNutrient("Dairy", 111111, "Chocolate", "Crme Egg Cadburys", 1,
				170, 1, "Egg", 39, "g");
		createNutrient("Dairy", 111111, "Chocolate",
				"Buttons Dairy Milk Cadburys", 1, 170, 1, "Pack", 32.5, "g");
		createNutrient("Dairy", 111111, "Chocolate", "Aero Nestle", 1, 164, 1,
				"Bar", 31, "g");
		createNutrient("Dairy", 111111, "Chocolate", "Twix Mars", 1, 143, 1,
				"Bar", 29, "g");
		createNutrient("Dairy", 111111, "Chocolate", "Bounty Mars", 1, 135, 1,
				"Bar", 28, "g");
		createNutrient("Dairy", 111111, "Chocolate", "Milky Way Mars", 1, 118,
				1, "Bar", 26, "g");
		createNutrient("Dairy", 111111, "Chocolate", "Twirl Cadburys", 1, 116,
				1, "Finger", 22, "g");
		createNutrient("Dairy", 111111, "Chocolate", "Fudge Cadburys", 1, 110,
				1, "Bar", 25, "g");
		createNutrient("Dairy", 111111, "Chocolate", "Kit Kat Nestle", 1, 106,
				2, "Fingers", 21, "g");

		createNutrient("Coffee", 111111, "Coffee",
				"Coffee Mocha White Chocolate Skimmed Whip Tall Starbucks", 1,
				344, 1, "tall", 354, "ml");
		createNutrient("Coffee", 111111, "Coffee",
				"Frappuccino Mocha With Whip  Syrup Tall Starbucks", 1, 331, 1,
				"tall", 354, "ml");
		createNutrient(
				"Coffee",
				111111,
				"Coffee",
				"Coffee Mocha With Whipped Cream Semi Skimmed Milk Regular Caffe Nero",
				1, 326, 1, "cup", 180, "ml");
		createNutrient("Coffee", 111111, "Coffee",
				"Coffee Latte Chai Semi Skimmed Milk Caffe Nero", 1, 283, 1,
				"Latte", 423, "ml");
		createNutrient("Coffee", 111111, "Coffee",
				"Coffee Mocha Peppermint Skimmed Tall Starbucks", 1, 237, 1,
				"tall", 354, "ml");
		createNutrient("Coffee", 111111, "Coffee",
				"Coffee Mocha Pret a Manger", 1, 232, 1, "serving", 340, "ml");
		createNutrient("Coffee", 111111, "Coffee",
				"Coffee Frappe Latte Caffe Nero", 1, 210, 1, "regular", 369.4,
				"g");
		createNutrient("Coffee", 111111, "Coffee",
				"Coffee Caffe Latte Whole Milk Tall Starbucks", 1, 200, 1,
				"tall", 354, "ml");
		createNutrient("Coffee", 111111, "Coffee",
				"Coffee Latte Pret a Manger", 1, 194, 1, "serving", 340, "ml");
		createNutrient("Coffee", 111111, "Coffee",
				"Frappuccino Coffee Tall Starbucks", 1, 190, 1, "tall", 354,
				"ml");
		createNutrient("Coffee", 111111, "Coffee",
				"Coffee Caramel Macchiato Skimmed Milk Tall Starbucks", 1, 173,
				1, "tall", 354, "ml");
		createNutrient("Coffee", 111111, "Coffee", "Coffee Cappuccino EAT", 1,
				168, 1, "tall", 355, "ml");
		createNutrient("Coffee", 111111, "Coffee",
				"Frappuccino Espresso Tall Starbucks", 1, 165, 1, "tall", 354,
				"ml");
		createNutrient("Coffee", 111111, "Coffee",
				"Coffee Iced latte Whole Milk With Syrup Tall Starbucks", 1,
				160, 1, "tall", 354, "ml");
		createNutrient("Coffee", 111111, "Coffee",
				"Coffee Latte Skimmed Milk Tall EAT", 1, 142, 1, "tall", 355,
				"ml");
		createNutrient("Coffee", 111111, "Coffee",
				"Coffee Iced Latte Skimmed Milk  Syrup Tall Starbucks", 1, 122,
				1, "tall", 354, "ml");
		createNutrient("Coffee", 111111, "Coffee",
				"Frappuccino Coffee Light Tall Starbucks", 1, 119, 1, "tall",
				354, "ml");
		createNutrient("Coffee", 111111, "Coffee",
				"Coffee Cappuccino Weight Watchers", 1, 115, 1, "beaker", 230,
				"ml");
		createNutrient("Coffee", 111111, "Coffee",
				"Coffee Caffe Misto Whole Milk Tall Starbucks", 1, 106, 1,
				"tall", 354, "ml");
		createNutrient("Coffee", 111111, "Coffee",
				"Coffee Cappucino FullFat Milk No Chocolate Pret a Manger", 1,
				102, 1, "serving", 355, "ml");
		createNutrient("Coffee", 111111, "Coffee", "Coffee Mocha Nescafe", 1,
				92, 1, "sachet", 22, "g");
		createNutrient("Coffee", 111111, "Coffee",
				"Coffee Cappuccino Instant Kenco", 1, 8, 1, "sachet", 20, "g");
		createNutrient("Coffee", 111111, "Coffee",
				"Coffee Cappuccino Skimmed Milk Tall Starbucks", 1, 76, 1,
				"tall", 354, "ml");
		createNutrient("Coffee", 111111, "Coffee",
				"Coffee Iced Latte Skimmed Milk Tall Starbucks", 1, 73, 1,
				"tall", 354, "ml");
		createNutrient("Coffee", 111111, "Coffee",
				"Coffee Caffe Misto Skimmed Milk Tall Starbucks", 1, 64, 1,
				"tall", 354, "ml");
		createNutrient("Coffee", 111111, "Coffee", "Coffee White made With", 1,
				51, 1, "cup", 190, "ml");
		createNutrient("Coffee", 111111, "Coffee",
				"Coffee Instant Made With Water  SemiSkimmed Milk", 1, 24, 1,
				"serving", 350, "ml");
		createNutrient("Coffee", 111111, "Coffee",
				"Coffee White Klix Vending Machine", 1, 23, 1, "cup", 190, "ml");
		createNutrient("Coffee", 111111, "Coffee",
				"Coffee Caffe Americano Tall Starbucks", 1, 11, 1, "tall", 354,
				"ml");
		createNutrient("Coffee", 111111, "Coffee",
				"Coffee Brewed Tall Starbucks", 1, 7, 1, "tall", 354, "ml");
		createNutrient("Coffee", 111111, "Coffee",
				"Coffee Espresso Solo Starbucks", 1, 6, 1, "solo", 30, "ml");
		createNutrient("Coffee", 111111, "Coffee",
				"Coffee Decaffeinated Gold Blend Nescafe", 1, 5, 1, "Tsp", 5,
				"g");
		createNutrient("Coffee", 111111, "Coffee", "Coffee Black", 1, 5, 1,
				"mug", 270, "ml");
		createNutrient("Coffee", 111111, "Coffee",
				"Coffee Iced Tall Starbucks", 1, 4, 1, "tall", 354, "ml");

		createNutrient("Drinks", 111111, "Drinks",
				"Gin  Tonic Premixed Canned Gordons", 1, 213, 1, "Can", 250,
				"ml");
		createNutrient("Drinks", 111111, "Drinks", "Beer Bitter Draught", 1,
				182, 1, "Pint", 568, "ml");
		createNutrient("Drinks", 111111, "Drinks", "Larger Average", 1, 165, 1,
				"Pint", 568, "ml");
		createNutrient("Drinks", 111111, "Drinks",
				"Smoothie Strawberries  Bananas Innocent", 1, 143, 1, "Bottle",
				250, "ml");
		createNutrient("Drinks", 111111, "Drinks", "Cola Coke CocaCola", 1,
				142, 1, "Can", 330, "ml");
		createNutrient("Drinks", 111111, "Drinks",
				"Juice Orange with Bits Tropicana", 1, 141, 1, "Bottle", 330,
				"ml");
		createNutrient("Drinks", 111111, "Drinks", "Juice Drink J", 1, 132, 1,
				"Bottle", 275, "ml");
		createNutrient("Drinks", 111111, "Drinks", "Red Bull Regular", 1, 113,
				1, "Can", 250, "ml");
		createNutrient("Drinks", 111111, "Drinks", "Champagne Average", 1, 89,
				1, "Glass", 120, "ml");
		createNutrient("Drinks", 111111, "Drinks", "Wine White Medium", 1, 87,
				1, "Glass", 120, "ml");
		createNutrient("Drinks", 111111, "Drinks", "Wine Red", 1, 80, 1,
				"Glass", 120, "ml");
		createNutrient("Drinks", 111111, "Drinks", "Barcardi  Diet Coke", 1,
				52, 1, "Serving", 275, "ml");
		createNutrient("Drinks", 111111, "Drinks",
				"High Lights Chocolate Drink Dry Cadbury", 1, 37, 1, "Sachet",
				10, "g");
		createNutrient("Drinks", 111111, "Drinks",
				"Tea Made With Water With Semi Skimmed Milk", 1, 14, 1, "Cup",
				200, "ml");
		createNutrient("Drinks", 111111, "Drinks",
				"Drinks Infusion Average With Semi Skimmed Milk", 1, 14, 1,
				"Cup", 220, "ml");
		createNutrient("Drinks", 111111, "Drinks",
				"Ribena Blackcurrant Really Light No Added Sugar", 1, 8, 1,
				"Carton", 250, "ml");
		createNutrient("Drinks", 111111, "Drinks",
				"Tea Earl Gray Infusion With Water", 1, 3, 1, "Mug", 250, "ml");
		createNutrient("Drinks", 111111, "Drinks", "Water Mineral or Tap", 1,
				0, 1, "Glass", 250, "ml");

		createNutrient("Vegetables", 111111, "Fruit and Veg",
				"Red Kidney Beans In Water Tesco", 1, 391, 1, "Can", 420, "g");
		createNutrient("Vegetables", 111111, "Fruit and Veg",
				"Potatoes Baked Flesh  Skin", 1, 245, 1, "Med", 180, "g");
		createNutrient("Fruit", 111111, "Fruit and Veg",
				"Banana Fresh Weighed Without Skin", 1, 143, 1, "Med", 150, "g");
		createNutrient("Vegetables", 111111, "Fruit and Veg",
				"Sweet Corn Green Giant", 1, 140, 1, "Can", 200, "g");
		createNutrient("Fruit", 111111, "Fruit and Veg",
				"Apricots Dried Sundora", 1, 83, 1, "Serving", 50, "g");
		createNutrient("Fruit", 111111, "Fruit and Veg", "Pear Average Raw", 1,
				68, 1, "Med", 170, "g");
		createNutrient("Fruit", 111111, "Fruit and Veg", "Orange", 1, 59, 1,
				"Med", 160, "g");
		createNutrient("Vegetables", 111111, "Fruit and Veg",
				"Garden Peas Birds Eye", 1, 53, 1, "Serving", 85, "g");
		createNutrient("Vegetables", 111111, "Fruit and Veg",
				"Potatoes New Boiled in Salted Water", 1, 53, 1, "Bunch", 100,
				"g");
		createNutrient("Fruit", 111111, "Fruit and Veg", "Cherries Black Raw",
				1, 51, 1, "Bunch", 100, "g");
		createNutrient("Fruit", 111111, "Fruit and Veg", "Apples Eating Raw",
				1, 53, 1, "Med", 112, "g");
		createNutrient("Fruit", 111111, "Fruit and Veg", "Mango Raw", 1, 60, 1,
				"Bunch", 225, "g");
		createNutrient("Fruit", 111111, "Fruit and Veg", "Blueberries Raw", 1,
				60, 1, "Bunch", 100, "g");
		createNutrient("VegeFruit", 111111, "Fruit and Veg", "Kiwi Fruit", 1,
				49, 1, "Bunch", 100, "g");
		createNutrient("Vegetables", 111111, "Fruit and Veg", "Onions Raw", 1,
				36, 1, "Bunch", 100, "g");
		createNutrient("Fruit", 111111, "Fruit and Veg", "Peach Raw", 1, 36, 1,
				"Med", 110, "g");
		createNutrient("Fruit", 111111, "Fruit and Veg", "Plums", 1, 36, 1,
				"Bunch", 100, "g");
		createNutrient("Fruit", 111111, "Fruit and Veg", "Satsumas", 1, 36, 1,
				"Bunch", 100, "g");
		createNutrient("Vegetables", 111111, "Fruit and Veg",
				"Cauliflower Raw", 1, 34, 1, "Bunch", 100, "g");
		createNutrient("Vegetables", 111111, "Fruit and Veg",
				"Broccoli Green Raw", 1, 33, 1, "Bunch", 100, "g");
		createNutrient("Vegetables", 111111, "Fruit and Veg",
				"Peppers Capsicum Red Raw", 1, 32, 1, "Bunch", 100, "g");
		createNutrient("Vegetables", 111111, "Fruit and Veg",
				"Carrots Young Raw", 1, 30, 1, "Bunch", 100, "g");
		createNutrient("Vegetables", 111111, "Fruit and Veg", "Broccoli Raw",
				1, 30, 1, "Bunch", 100, "g");
		createNutrient("Fruit", 111111, "Fruit and Veg", "Strawberries Raw", 1,
				27, 1, "Bunch", 100, "g");
		createNutrient("Vegetables", 111111, "Fruit and Veg", "Melon Average",
				1, 24, 1, "Bunch", 100, "g");
		createNutrient("Vegetables", 111111, "Fruit and Veg",
				"Green Beans French Beans Boiled in Unsalted Water", 1, 22, 1,
				"Bunch", 100, "g");
		createNutrient("Vegetables", 111111, "Fruit and Veg", "Courgette Raw",
				1, 18, 1, "Bunch", 100, "g");
		createNutrient("Fruit", 111111, "Fruit and Veg", "Grapes Average", 1,
				17, 1, "Bunch", 100, "g");
		createNutrient("Fruit", 111111, "Fruit and Veg", "Tomato Raw", 1, 14,
				1, "Med", 85, "g");
		createNutrient("Vegetables", 111111, "Fruit and Veg",
				"Lettuce Average Raw", 1, 14, 1, "Med", 100, "g");
		createNutrient("Vegetables", 111111, "Fruit and Veg",
				"Mushrooms Common Raw", 1, 13, 1, "Bunch", 100, "g");
		createNutrient("Vegetables", 111111, "Fruit and Veg", "Cucumber Raw",
				1, 10, 1, "Bunch", 100, "g");

		createNutrient("Protein", 111111, "Main Meals",
				"Lasagne Pizza Express", 1, 514, 1, "Serving", 350, "g");
		createNutrient("Protein", 111111, "Main Meals",
				"Toad in the Hole Aunt Bessies", 1, 452, 1, "Serving", 190, "g");
		createNutrient("Protein", 111111, "Main Meals",
				"Tikka Masala Chicken  Pilau Rice GFY Asda", 1, 440, 1, "Pack",
				400, "g");
		createNutrient("Protein", 111111, "Main Meals",
				"Chilli Con Carne With Rice Perfectly Balanced Waitrose", 1,
				404, 1, "Pack", 400, "g");
		createNutrient("Protein", 111111, "Main Meals",
				"Beef Dinner Roast Birds Eye", 1, 386, 1, "Meal", 340, "g");
		createNutrient("Protein", 111111, "Main Meals",
				"Bake Vegetable Marks  Spencer", 1, 383, 1, "Pack", 450, "g");
		createNutrient("Protein", 111111, "Main Meals",
				"Bolognese Spaghetti Marks  Spencer", 1, 380, 1, "Pack", 400,
				"g");
		createNutrient("Protein", 111111, "Main Meals",
				"Chicken Kiev Bernard Matthews", 1, 373, 1, "Kiev", 125, "g");
		createNutrient("Protein", 111111, "Main Meals",
				"Sausage  Mash Healthy Living Tesco", 1, 369, 1, "Pack", 450,
				"g");
		createNutrient("Protein", 111111, "Main Meals",
				"Tagliatelle Mushroom  Bacon BGTY Sainsburys", 1, 368, 1,
				"Pack", 400, "g");
		createNutrient("Protein", 111111, "Main Meals",
				"Bake Tuna  Pasta Healthy Living Tesco", 1, 360, 1, "Pack",
				400, "g");
		createNutrient("Protein", 111111, "Main Meals",
				"Pizza Mushroom  Ham COU Marks  Spencer", 1, 355, 1, "Pizza",
				245, "g");
		createNutrient("Protein", 111111, "Main Meals",
				"Cauliflower Cheese Finest Tesco", 1, 318, 1, "Serving", 250,
				"g");
		createNutrient("Protein", 111111, "Main Meals",
				"Burgers Beef Aberdeen Angus Marks  Spencer", 1, 298, 1,
				"Burger", 142, "g");
		createNutrient("Protein", 111111, "Main Meals",
				"Chilli Non Carne Linda McCartney", 1, 275, 1, "Pack", 340, "g");
		createNutrient("Protein", 111111, "Main Meals",
				"Canelloni Spinach  Ricotta BGTY Sainsburys", 1, 246, 1,
				"Pack", 300, "g");
		createNutrient("Protein", 111111, "Main Meals",
				"Cod Battered Chop Shop Youngs", 1, 237, 1, "Portion", 113, "g");
		createNutrient("Protein", 111111, "Main Meals",
				"Pie Cottage Weight Watchers", 1, 230, 1, "Pack", 320, "g");
		createNutrient("Protein", 111111, "Main Meals",
				"Hot Pot Beef Weight Watchers", 1, 210, 1, "Pack", 320, "g");
		createNutrient("Protein", 111111, "Main Meals",
				"Salmon Fillet Raw Average", 1, 149, 1, "Fillet", 79, "g");
		createNutrient("Protein", 111111, "Main Meals",
				"Fish Fingers Birds Eye", 1, 140, 3, "Fingers", 75, "g");
		createNutrient("Protein", 111111, "Main Meals", "Burgers Beef", 1, 120,
				1, "Burger", 41, "g");
		createNutrient("Protein", 111111, "Main Meals", "Sausages Quorn", 1,
				96, 2, "Sausages", 85, "g");
		createNutrient("Protein", 111111, "Main Meals", "Burger Quorn", 1, 73,
				1, "Burger", 50, "g");

		createNutrient("Snacks", 111111, "Snacks", "Nuts Brazilian", 1, 275,
				12, "Whole", 40, "g");
		createNutrient("Snacks", 111111, "Snacks",
				"Crisps Chargrilled Steak MAX Walkers", 1, 263, 1, "Bag", 50,
				"g");
		createNutrient("Snacks", 111111, "Snacks",
				"Popcorn Toffee Blockbuster", 1, 220, 1, "Bag", 50, "g");
		createNutrient("Snacks", 111111, "Snacks",
				"Doritos Extreme Chilli Heatwave Doritos", 1, 200, 1, "Bag",
				40, "g");
		createNutrient("Snacks", 111111, "Snacks",
				"Crisps Lime  Thai Spices Sensations Walkers", 1, 200, 1,
				"Bag", 40, "g");
		createNutrient("Snacks", 111111, "Snacks",
				"Popcorn Salted Blockbuster", 1, 198, 1, "Bag", 50, "g");
		createNutrient("Snacks", 111111, "Snacks",
				"Crisps Vintage Cheddar  Red Onion Chutney Sensations Walkers",
				1, 162, 1, "Bag", 40, "g");
		createNutrient("Snacks", 111111, "Snacks",
				"Crisps Salt  Shake Walkers", 1, 161, 1, "Bag", 30, "g");
		createNutrient("Snacks", 111111, "Snacks",
				"Nuts Salted Roasted Luxury KP", 1, 152, 1, "Bag", 25, "g");
		createNutrient("Snacks", 111111, "Snacks", "Nuts Pistachio", 1, 136, 1,
				"Bag", 25, "g");
		createNutrient("Snacks", 111111, "Snacks", "Nuts  Raisins Mixed KP", 1,
				133, 1, "Bag", 25, "g");
		createNutrient("Snacks", 111111, "Snacks",
				"Crisps Ready Salted Walkers", 1, 128, 1, "Bag", 25, "g");
		createNutrient("Snacks", 111111, "Snacks",
				"Crisps Cheese  Onion Cheese Heads Walkers", 1, 121, 1, "Bag",
				27, "g");
		createNutrient("Snacks", 111111, "Snacks", "Frazzles Bacon Walkers", 1,
				114, 1, "Bag", 25, "g");
		createNutrient("Snacks", 111111, "Snacks",
				"Crisps Sour Cream  Chive Lights Walkers", 1, 107, 1, "Bag",
				24, "g");
		createNutrient("Snacks", 111111, "Snacks",
				"Monster Munch Pickled Onion Walkers", 1, 106, 1, "Bag", 22,
				"g");
		createNutrient("Snacks", 111111, "Snacks",
				"Crisps Roast Chicken Potato Heads Walkers", 1, 101, 1, "Bag",
				23, "g");
		createNutrient("Snacks", 111111, "Snacks",
				"Crisps Naked Potato Heads Walkers", 1, 98, 1, "Bag", 23, "g");
		createNutrient("Snacks", 111111, "Snacks", "Quavers Cheese Walkers", 1,
				95, 1, "Bag", 20, "g");
		createNutrient("Snacks", 111111, "Snacks",
				"Crisps Salt  Vinegar Baked Walkers", 1, 94, 1, "Bag", 25, "g");
		createNutrient("Snacks", 111111, "Snacks", "Wotsits Cheesy Walkers", 1,
				93, 1, "Bag", 19, "g");
		createNutrient("Snacks", 111111, "Snacks",
				"French Fries Worcester Sauce Walkers", 1, 92, 1, "Bag", 22,
				"g");
		createNutrient("Snacks", 111111, "Snacks",
				"Wotsits Prawn Cocktail Walkers", 1, 90, 1, "Bag", 19, "g");
		createNutrient("Snacks", 111111, "Snacks",
				"Squares Salt  Vinegar Walkers", 1, 90, 1, "Bag", 25, "g");

		createNutrient("Protein", 111111, "Takeaway",
				"Chicken Sandwich Burger King", 1, 659, 1, "Sandwich", 224, "g");
		createNutrient("Protein", 111111, "Takeaway",
				"Quarter Pounder With Cheese McDonalds", 1, 515, 1, "Burger",
				206, "g");
		createNutrient("Protein", 111111, "Takeaway", "Big Mac McDonalds", 1,
				492, 1, "Mac", 215, "g");
		createNutrient("Protein", 111111, "Takeaway",
				"McChicken Sandwich McDonalds", 1, 376, 1, "Sandwich", 167, "g");
		createNutrient("Protein", 111111, "Takeaway", "Cheeseburger McDonalds",
				1, 300, 1, "Burger", 122, "g");
		createNutrient("Protein", 111111, "Takeaway", "Hamburger McDonalds", 1,
				254, 1, "Burger", 108, "g");
		createNutrient("Protein", 111111, "Takeaway", "Fries McDonalds", 1,
				224, 1, "Portion", 78, "g");
		createNutrient("Protein", 111111, "Takeaway",
				"Extra Crispy Chicken Drumstick Kentucky Fried Chicken", 1,
				195, 1, "Drumstick", 67, "g");
		createNutrient("Protein", 111111, "Takeaway", "Hash Brown McDonalds",
				1, 127, 1, "Portion", 56, "g");
		createNutrient("Protein", 111111, "Takeaway",
				"Cheese  Tomato Pizza Dominos Pizza", 1, 125, 1, "Slice", 52,
				"g");
		createNutrient("Protein", 111111, "Takeaway", "Garlic Bread Pizza Hut",
				1, 101, 1, "Slice", 24, "g");

		createNutrient("Protein", 111111, "Fish", "Scampi Breaded Average", 1,
				565, 1, "Serving", 255, "g");
		createNutrient("Protein", 111111, "Fish",
				"Mackerel Fillets Smoked Average", 1, 200, 1, "Serving", 60,
				"g");
		createNutrient("Protein", 111111, "Fish", "Salmon Fillet Aldi", 1, 198,
				1, "Fillet", 100, "g");
		createNutrient("Protein", 111111, "Fish",
				"Cod Fillet Battered Average", 1, 158, 1, "Fillet", 90, "g");
		createNutrient("Protein", 111111, "Fish", "Sardines Grilled", 1, 146,
				3, "Sardines", 75, "g");
		createNutrient("Protein", 111111, "Fish", "Fishcakes Frozen Average",
				1, 112, 1, "Cake", 85, "g");
		createNutrient("Protein", 111111, "Fish",
				"Tuna Chunks in Brine Sainsburys", 1, 105, 1, "Tin", 45, "g");
		createNutrient("Protein", 111111, "Fish", "Shark Raw", 1, 102, 1,
				"Serving", 100, "g");
		createNutrient("Protein", 111111, "Fish",
				"Haddock Fillet Smoked Youngs", 1, 98, 1, "Fillet", 100, "g");
		createNutrient("Protein", 111111, "Fish", "Langoustine Raw Average", 1,
				90, 1, "Serving", 100, "g");
		createNutrient("Protein", 111111, "Fish", "Lobster Boiled", 1, 88, 2,
				"TbspMeat", 85, "g");
		createNutrient("Protein", 111111, "Fish", "Shrimp Boiled Average", 1,
				70, 1, "Serving", 60, "g");
		createNutrient("Protein", 111111, "Fish", "Prawns Boiled", 1, 59, 20,
				"Prawns", 60, "g");
		createNutrient("Protein", 111111, "Fish", "Crab Sticks Tesco", 1, 45,
				3, "Sticks", 45, "g");
		createNutrient("Protein", 111111, "Fish", "Mussels Boiled Average", 1,
				36, 5, "Mussels", 35, "g");

		createNutrient("Protein", 111111, "Sushi", "Tuna  Salmon Sushi Itsu",
				1, 315, 1, "Box", 282, "g");
		createNutrient("Protein", 111111, "Sushi", "Sushi Selection Ichiban",
				1, 253, 1, "Pack", 161, "g");
		createNutrient("Protein", 111111, "Sushi",
				"Sushi Rice Dry Weight Nishiki", 1, 249, 1, "Serving", 70, "g");
		createNutrient("Protein", 111111, "Sushi",
				"Fine Beans Shredded Fresh Ginger Wakame Sushi Itsu", 1, 222,
				1, "Pack", 250, "g");
		createNutrient("Protein", 111111, "Sushi",
				"Salmon Sushi Roll I Love Sushi", 1, 195, 1, "Roll", 125, "g");
		createNutrient("Protein", 111111, "Sushi",
				"Prawn Nigiri Sushi Sushi San", 1, 190, 1, "Pack", 138, "g");
		createNutrient("Protein", 111111, "Sushi",
				"California Sushi Rolls Sushi San", 1, 169, 1, "Pack", 123, "g");
		createNutrient("Protein", 111111, "Sushi", "Prawn Sushi Itsu", 1, 151,
				1, "Portion", 98, "g");
		createNutrient("Protein", 111111, "Sushi",
				"Vegetarian Sushi Roll I Love Sushi", 1, 141, 1, "Roll", 125,
				"g");
		createNutrient("Protein", 111111, "Sushi", "Salmon Sushi Itsu", 1, 129,
				1, "Portion", 64, "g");
		createNutrient("Protein", 111111, "Sushi", "Sushi Seasoning Mitsukan",
				1, 50, 2, "Tbsp", 30, "ml");
		createNutrient("Protein", 111111, "Sushi",
				"Sushi Akami Tuna Nigiri Wasabi", 1, 43, 1, "Piece", 45, "g");
		createNutrient("Protein", 111111, "Sushi", "Sushi Ginger Mitoku", 1, 4,
				1, "Serving", 18, "g");

		createNutrient("Protein", 111111, "Subway", "Meatball Marinara Sub", 1,
				520, 6, "InchSub", 382, "");
		createNutrient("Protein", 111111, "Subway", "Chicken  Bacon Ranch Sub",
				1, 489, 6, "InchSub", 309, "g");
		createNutrient("Protein", 111111, "Subway", "Spicy Italian Sub", 1,
				461, 6, "InchSub", 229, "g");
		createNutrient("Protein", 111111, "Subway", "Italian BMT Sub", 1, 429,
				6, "InchSub", 245, "g");
		createNutrient("Protein", 111111, "Subway", "Tuna Sub", 1, 402, 6,
				"InchSub", 253, "g");
		createNutrient("Protein", 111111, "Subway",
				"Sweet Onion Chicken Teriyaki Sub", 1, 352, 6, "InchSub", 283,
				"");
		createNutrient("Protein", 111111, "Subway", "Cheese Steak Sub", 1, 336,
				6, "InchSub", 261, "g");
		createNutrient("Protein", 111111, "Subway", "Club Sub", 1, 299, 6,
				"InchSub", 268, "g");
		createNutrient("Protein", 111111, "Subway", "Turkey Breast Sub", 1,
				256, 6, "InchSub", 226, "g");
		createNutrient("Protein", 111111, "Subway", "Veggie Delite Sub", 1,
				203, 6, "InchSub", 169, "g");

		createNutrient("Protein", 111111, "Eggs", "Egg Goose Whole Raw", 1,
				232, 1, "Egg", 144, "g");
		createNutrient("Protein", 111111, "Eggs", "Egg Dried Whole Average", 1,
				159, 1, "Serving", 28, "g");
		createNutrient("Protein", 111111, "Eggs", "Egg Duck Whole Raw", 1, 122,
				1, "Egg", 75, "g");
		createNutrient("Protein", 111111, "Eggs", "Egg Whole Raw", 1, 108, 1,
				"Egg", 66, "g");
		createNutrient("Protein", 111111, "Eggs", "Egg Fried", 1, 105, 1,
				"Egg", 60, "g");
		createNutrient("Protein", 111111, "Eggs", "Egg Scrambled Average", 1,
				100, 1, "Egg", 68, "g");
		createNutrient("Protein", 111111, "Eggs", "Egg Boiled", 1, 74, 1,
				"Egg", 50, "g");
		createNutrient("Protein", 111111, "Eggs", "Egg Poached", 1, 74, 1,
				"Egg", 50, "g");
		createNutrient("Protein", 111111, "Eggs", "Egg Yolk Raw", 1, 48, 1,
				"Yolk", 14, "g");
		createNutrient("Protein", 111111, "Eggs", "Egg White Dried Average", 1,
				41, 1, "Tbsp", 14, "g");
		createNutrient("Protein", 111111, "Eggs", "Egg White Only Raw", 1, 21,
				1, "Egg", 40, "g");
		createNutrient("Protein", 111111, "Eggs", "Egg Quail Whole Raw", 1, 20,
				1, "Egg", 13, "g");

	}
}
