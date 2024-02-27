package com.example.groupproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import entities.UserProfile;


public class DBHelper extends SQLiteOpenHelper{

    public static final String DBNAME = "BookSharing.db";
    final static int DATABASE_VERSION = 3;
    final static String TABLE1_NAME = "Users_Information";
    final static String TABLE2_NAME = "Book_Information";
    final static String TABLE3_NAME = "Message_Information";
    final static String TABLE4_NAME = "Tracker_Information";
    final static String T1COL1 = "Id";
    final static String T1COL2 = "Username";
    final static String T1COL3 = "Email";
    final static String T1COL4 = "Address";
    final static String T1COL5 = "Age";
    final static String T1COL6 = "Interest";
    final static String T1COL7 = "Password";
    final static String T2COL1 = "BookID";
    final static String T2COL2 = "BookTitle";
    final static String T2COL3 = "AuthorName";
    final static String T2COL4 = "PublisherName";
    final static String T2COL5 = "PublicationYear";
    final static String T2COL6 = "BorrowActivity";





    public DBHelper( Context context)  {

        super(context, DBNAME, null, DATABASE_VERSION);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase MyDatabase) {
        String query1 = "CREATE TABLE " + TABLE1_NAME + "(" + T1COL1 + " Integer PRIMARY KEY,"+
                T1COL2 + " Text, " + T1COL3 + " Text, " + T1COL4 + " Text, " +
                T1COL5 + " Text, " + T1COL6 + " Text, " + T1COL7 + " Text);";

        MyDatabase.execSQL(query1);

        MyDatabase.execSQL("create Table Book_Information(bookID Integer primary key, bookTitle TEXT, authorName TEXT, " +
                "publisherName TEXT, publicationYear TEXT, borrowActivityShare TEXT,borrowActivityRent TEXT,borrowActivityGiveAway TEXT," +
                "bookImageName TEXT, bookStatus TEXT, rentPrice REAL, Id Integer, dateAdded TEXT," +
                " foreign key(Id) REFERENCES Users_Information(Id))");

        MyDatabase.execSQL("CREATE TABLE Message_Information(messgeID Integer PRIMARY KEY, userName Text, messContent Text," +
                " Id Integer," +
                " FOREIGN KEY(Id) REFERENCES Users_Information(Id))");

        MyDatabase.execSQL("CREATE TABLE Tracker_Information(tracerID Integer PRIMARY KEY, bookName Text, " +
                "pageNum Integer, dateTracked Text," +
                " Id Integer," +
                " FOREIGN KEY(Id) REFERENCES Users_Information(Id))");

    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDatabase, int oldVersion, int newVersion) {
        MyDatabase.execSQL("DROP TABLE " + TABLE1_NAME);
        MyDatabase.execSQL("DROP TABLE " + TABLE2_NAME);
        MyDatabase.execSQL("DROP TABLE " + TABLE3_NAME);
        MyDatabase.execSQL("DROP TABLE " + TABLE4_NAME);
        onCreate(MyDatabase);
    }
    public Boolean insertTrackData(String bookName, int pageNum, String dateTracked, int id){
        SQLiteDatabase MyDatabase = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("bookName",bookName);
        values.put("pageNum",pageNum);
        values.put("dateTracked",dateTracked);
        values.put("Id",id);


        long result = MyDatabase.insert(TABLE4_NAME,null, values);

        if(result==-1){
            return false;
        }
        else{

            return true;
        }

    }
    Cursor readTrackerData(int id){
        String query = "SELECT * FROM " + TABLE4_NAME + " WHERE id = ?";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, new String[]{String.valueOf(id)});
        }
        return cursor;
    }
    public Boolean insertData(UserProfile userProfile) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(T1COL2,userProfile.getUsername());
        contentValues.put(T1COL3,userProfile.getEmail());
        contentValues.put(T1COL4,userProfile.getAddress());
        contentValues.put(T1COL5,userProfile.getAge());
        contentValues.put(T1COL6,userProfile.getInterest());
        contentValues.put(T1COL7,userProfile.getPassword());
        long result = MyDatabase.insert(TABLE1_NAME,null,contentValues);

        if(result==-1){
            return false;
        }
        else{
            return true;
        }

    }

    public Boolean insertBookData(String bookTitle, String authorName, String publisherName, String publicationYear,
                                  String borrowActivityShare,String borrowActivityRent,String borrowActivityGiveaway,
                                  String imgURL,String bookStatus,Float rentPrice, int userId, String dateAdded){
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("bookTitle",bookTitle);
        contentValues.put("authorName",authorName);
        contentValues.put("publisherName",publisherName);
        contentValues.put("publicationYear",publicationYear);
        contentValues.put("borrowActivityShare",borrowActivityShare);
        contentValues.put("borrowActivityRent",borrowActivityRent);
        contentValues.put("borrowActivityGiveaway",borrowActivityGiveaway);
        contentValues.put("bookImageName",imgURL);
        contentValues.put("bookStatus",bookStatus);
        contentValues.put("rentPrice",rentPrice);
        contentValues.put("Id",userId);
        contentValues.put("dateAdded", dateAdded);


        long result = MyDatabase.insert("Book_Information",null,contentValues);

        if(result==-1){
            return false;
        }
        else{

            return true;
        }

    }
    public Boolean insertMessData(String username, String msg, int id){
        SQLiteDatabase MyDatabase = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("userName",username);
        values.put("messContent",msg);
        values.put("Id",id);


        long result = MyDatabase.insert(TABLE3_NAME,null, values);

        if(result==-1){
            return false;
        }
        else{

            return true;
        }

    }


    public  Cursor getBookInfo(int id){
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        String query = "SELECT * FROM Book_Information INNER JOIN Users_Information" +
                " ON Book_Information.Id= Users_Information.Id " +
                "WHERE Book_Information.Id = " + id;
        Cursor c = MyDatabase.rawQuery(query,null);
        return c;
    }

    public  Cursor getSingleBookInfo(int bookID){
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        String query = "SELECT * FROM Book_Information WHERE bookID=" + bookID;
        Cursor c = MyDatabase.rawQuery(query,null);
        return c;
    }

    public Boolean updateBookData(int bookID,String bookTitle, String authorName, String publisherName, String publicationYear,
                                  String borrowActivityShare,String borrowActivityRent,String borrowActivityGiveaway,
                                  String imgURL,String bookStatus,Float rentPrice){

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("bookTitle",bookTitle);
        contentValues.put("authorName",authorName);
        contentValues.put("publisherName",publisherName);
        contentValues.put("publicationYear",publicationYear);
        contentValues.put("borrowActivityShare",borrowActivityShare);
        contentValues.put("borrowActivityRent",borrowActivityRent);
        contentValues.put("borrowActivityGiveaway",borrowActivityGiveaway);
        contentValues.put("bookImageName",imgURL);
        contentValues.put("bookStatus",bookStatus);
        contentValues.put("rentPrice",rentPrice);

        int u = sqLiteDatabase.update("Book_Information",contentValues,"bookID=?",
                new String[]{Integer.toString(bookID)});
        if(u>0)
            return true;
        else
            return false;

    }

    public  Cursor getSender(){
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        String query = "SELECT * FROM Users_Information INNER JOIN Message_Information" +
                " ON Users_Information.Id = Message_Information.Id";
        Cursor c = MyDatabase.rawQuery(query,null);
        return c;
    }

    public Cursor viewNearByAvailableBooks(String postalCode, String borrowActivity){
        String borrowFlag= "Yes";
        String borrowActivityQuery="";
        //public Cursor viewBookOwnerTest(){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

       /* borrowActivityQuery = "SELECT BookTitle from Book_Information inner join " +
                " Users_Information on Book_Information.Id = Users_Information.Id where Address=? AND borrowActivityGiveAway=?";*/

        if(borrowActivity.equalsIgnoreCase("Share") ){
            borrowActivityQuery = "SELECT BookTitle from Book_Information inner join " +
                    " Users_Information on Book_Information.Id = Users_Information.Id where Address=? AND borrowActivityShare=?";
        }else if(borrowActivity.equalsIgnoreCase("Rent") ){
            borrowActivityQuery = "SELECT BookTitle from Book_Information inner join " +
                    " Users_Information on Book_Information.Id = Users_Information.Id where Address=? AND borrowActivityRent=?";
        }else if (borrowActivity.equalsIgnoreCase("Give Away") ){
            borrowActivityQuery = "SELECT BookTitle from Book_Information inner join " +
                    " Users_Information on Book_Information.Id = Users_Information.Id where Address=? AND borrowActivityGiveAway=?";
        }
        //Cursor c = sqLiteDatabase.rawQuery(queryTest,null);
        //Cursor c = sqLiteDatabase.rawQuery(borrowActivityQuery,new String[] {postalCode,borrowFlag});
        Cursor c = sqLiteDatabase.rawQuery(borrowActivityQuery,new String[] {postalCode,borrowFlag});
        //"SELECT * FROM " + DatabaseHelper.TABLE_NAME +" WHERE " + DatabaseHelper.COL_2 + "=? AND " + DatabaseHelper.COL_4 + "=? "
        //String query = "SELECT * FROM " + DatabaseHelper.TABLE_NAME + " WHERE " + DatabaseHelper.COL_2 + "=? AND " + DatabaseHelper.COL_4 + "=? ";

        return c;
    }

    /*public Cursor viewNearbyBooks(String postalCode){
        //public Cursor viewBookOwnerTest(){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        //String queryTest = "SELECT Email from Users_Information where Username= ?";
        //String queryTest = "SELECT * from Users_Information where Username="+ postalCode;
        //String queryTest = "SELECT BookTitle, Email from Book_Information inner join " +
        //" Users_Information on Book_Information.Id = Users_Information.Id";
        String query = "SELECT BookTitle from Book_Information inner join " +
                " Users_Information on Book_Information.Id = Users_Information.Id where Address= ?";
        //Cursor c = sqLiteDatabase.rawQuery(queryTest,null);
        Cursor c = sqLiteDatabase.rawQuery(query,new String[] {postalCode});

        return c;
    }*/

    public Cursor viewBookOwner(String booktitle){
        //public Cursor viewBookOwnerTest(){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        //String queryTest = "SELECT Id from Users_Information";
        //String queryTest = "SELECT BookTitle, Email from Book_Information inner join " +
        //" Users_Information on Book_Information.Id = Users_Information.Id";
        String query = "SELECT Username from Book_Information inner join " +
                " Users_Information on Book_Information.Id = Users_Information.Id where BookTitle=?";
        Cursor c = sqLiteDatabase.rawQuery(query,new String[] {booktitle});
        return c;
    }



    public Cursor getUserID(String username)
    {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        String query = "SELECT Users_Information.Id FROM Users_Information" +
                " WHERE Username =" + username ;
        Cursor c = MyDatabase.rawQuery(query,null);
        return c;
    }

    public UserProfile login (String username, String password){
        UserProfile userProfile = null;
        try{
            SQLiteDatabase db = this.getWritableDatabase();
            String query = "SELECT * FROM " + TABLE1_NAME + " WHERE username = ? AND password = ?";
            Cursor cursor = db.rawQuery(query, new String[]{username, password});
            if(cursor.moveToFirst()){
                userProfile = new UserProfile();
                userProfile.setUserId(cursor.getInt(0));
                userProfile.setUsername(cursor.getString(1));
                userProfile.setEmail(cursor.getString(2));
                userProfile.setAddress(cursor.getString(3));
                userProfile.setAge(cursor.getString(4));
                userProfile.setInterest(cursor.getString(5));
                userProfile.setPassword(cursor.getString(6));
            }
        }
        catch (Exception e){
            userProfile = null;
        }
        return userProfile;
    }

    public UserProfile checkuser (String username){
        UserProfile userProfile = null;
        try{
            SQLiteDatabase db = this.getWritableDatabase();
            String query = "SELECT * FROM " + TABLE1_NAME + " WHERE username = ?";
            Cursor cursor = db.rawQuery(query, new String[]{username});
            if(cursor.moveToFirst()){
                userProfile = new UserProfile();
                userProfile.setUserId(cursor.getInt(0));
                userProfile.setUsername(cursor.getString(1));
                userProfile.setEmail(cursor.getString(2));
                userProfile.setAddress(cursor.getString(3));
                userProfile.setAge(cursor.getString(4));
                userProfile.setInterest(cursor.getString(5));
                userProfile.setPassword(cursor.getString(6));
            }
        }
        catch (Exception e){
            userProfile = null;
        }
        return userProfile;
    }

    public UserProfile find (int id){
        UserProfile userProfile = null;
        try{
            SQLiteDatabase db = this.getWritableDatabase();
            String query = "SELECT * FROM " + TABLE1_NAME + " WHERE id = ?";
            Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(id)});
            if(cursor.moveToFirst()){
                userProfile = new UserProfile();
                userProfile.setUserId(cursor.getInt(0));
                userProfile.setUsername(cursor.getString(1));
                userProfile.setEmail(cursor.getString(2));
                userProfile.setAddress(cursor.getString(3));
                userProfile.setAge(cursor.getString(4));
                userProfile.setInterest(cursor.getString(5));
                userProfile.setPassword(cursor.getString(6));
            }
        }
        catch (Exception e){
            userProfile = null;
        }
        return userProfile;
    }
    Cursor readOwnerData(int id){
        String query = "SELECT * FROM " + TABLE2_NAME + " WHERE id = ?";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, new String[]{String.valueOf(id)});
        }
        return cursor;
    }

    public Boolean checkusername(String username){
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE1_NAME + " WHERE username = ?" ;
        Cursor cursor = MyDatabase.rawQuery(query, new String[] {username});
        if(cursor.getCount()>0){
            return true;
        }
        else{
            return false;
        }
    }

    public Boolean checkusernamepassword(String username, String password){
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE1_NAME + " WHERE username = ? AND password = ?";
        Cursor cursor = MyDatabase.rawQuery(query, new String[] {username,password});
        if(cursor.getCount()>0){
            return true;
        }
        else{
            return false;
        }
    }

    public Boolean updateData(UserProfile userProfile) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(T1COL2,userProfile.getUsername());
        contentValues.put(T1COL3,userProfile.getEmail());
        contentValues.put(T1COL4,userProfile.getAddress());
        contentValues.put(T1COL5,userProfile.getAge());
        contentValues.put(T1COL6,userProfile.getInterest());
        contentValues.put(T1COL7,userProfile.getPassword());
        long result = MyDatabase.update(TABLE1_NAME, contentValues, T1COL1 + " = ?",
                new String[] {String.valueOf(userProfile.getUserId())});

        if(result < 0){
            return false;
        }
        else{
            return true;
        }

    }

    Cursor getMess(String user){
        String query = "SELECT * FROM " + TABLE3_NAME+ " WHERE username = ?";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, new String[]{String.valueOf(user)});
        }
        return cursor;
    }




}