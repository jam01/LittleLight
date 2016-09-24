package com.jam01.littlelight.adapter.android.service.inventory;

/**
 * Created by jam01 on 7/27/16.
 */
public class LocalItemDefinitionDbProvider {
//    private final String TAG = getClass().getSimpleName();
//    private Context mContext;
//    private String availableManifestDb;
//    private SQLiteDatabase availableDb;
    //    private boolean updating = false;
//    private Future<Void> updating;

//    public LocalItemDefinitionDbProvider(Context context) {
//        this.mContext = context;
//    }

//    public void updateToLatest(String pathToLatest, Call<Response> test) {
//        if (checkIfExistsLocally(pathToLatest.substring(34))) {
//            return;
//        } else {
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//
//                }
//            }).run();
//        }
//    }
//
//    public SQLiteDatabase getLocalDb() {
//        if (availableDb != null) {
//            return availableDb;
//        } else if (updating) {
//
//        }
//        return null;
//    }

//    public void downloadLatestDefinitions() {
//        String latestManifestLocation = destinyApi.latestManifestUrl();
//        if (checkIfExistsLocally(latestManifestLocation.substring(34))) {
//            return;
//        }
//        downloadDb(latestManifestLocation);
//        availableManifestDb = latestManifestLocation.substring(34);
//    }

//    public String getDbPath() {
//        return mContext.getDatabasePath(availableManifestDb).getPath();
//    }

//    public boolean hasDb(String databaseName) {
//        //See http://stackoverflow.com/questions/3386667/query-if-android-database-exists
//        SQLiteDatabase checkDB;
//
//        try {
//            checkDB = SQLiteDatabase.openDatabase(mContext.getDatabasePath(databaseName).getPath(), null, SQLiteDatabase.OPEN_READONLY);
//            checkDB.close();
//        } catch (SQLiteException e) {
//            //Could not open DB so it does not exist
//            return false;
//        }
//        //DB exists
//        return true;
//    }

//    private boolean downloadDb(String dbUrl) {
//        try {
//            //Setting up the location for the file
//            String location = mContext.getDatabasePath("manifest").getParent().concat("/");
//
//            //Getting the file
//            InputStream zippedFile = destinyApi.manifestDb(dbUrl).byteStream();
//
//            //File writing setup
//            int size;
//            byte[] buffer = new byte[1024];
//
//            //Making sure the location exists and is a directory
//            File unzippedFile = new File(location);
//            if (!unzippedFile.isDirectory()) {
//                unzippedFile.mkdirs();
//            }
//
//            //Cleaning it, as to get rid of older DBs
//            //FileUtils.cleanDirectory(unzippedFile);
//            File[] flist;
//            flist = unzippedFile.listFiles();
//            if (flist != null && flist.length > 0) {
//                for (File f : flist) {
//                    f.delete();
//                }
//            }
//
//            //Unzip magic
//            ZipInputStream zin = new ZipInputStream(new BufferedInputStream(zippedFile));
//            try {
//                ZipEntry ze;
//                while ((ze = zin.getNextEntry()) != null) {
//                    String path = location + ze.getName();
//                    File unzipFile = new File(path);
//
//                    if (ze.isDirectory()) {
//                        if (!unzipFile.isDirectory()) {
//                            unzipFile.mkdirs();
//                        }
//                    } else {
//                        //Check for and create parent directories if they don't exist
//                        File parentDir = unzipFile.getParentFile();
//                        if (null != parentDir) {
//                            if (!parentDir.isDirectory()) {
//                                parentDir.mkdirs();
//                            }
//                        }
//
//                        //Unzip the file
//                        FileOutputStream out = new FileOutputStream(unzipFile, false);
//                        BufferedOutputStream fout = new BufferedOutputStream(out, 1024);
//                        try {
//                            while ((size = zin.read(buffer, 0, 1024)) != -1) {
//                                fout.write(buffer, 0, size);
//                            }
//
//                            zin.closeEntry();
//                        } finally {
//                            fout.flush();
//                            fout.close();
//                        }
//                    }
//                }
//            } finally {
//                zin.close();
//                Log.d(TAG, "Saved manifest DB to file at: " + location);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//            return false;
//        }
//
//        return true;
//    }

//    public void addDbFrom(final RetrofitDestinyApiFacade.RetrofitDestinyApi retrofitDestinyApi) {
//        Executors.newSingleThreadExecutor().submit(new Runnable() {
//            @Override
//            public void run() {
//                String latestDbPath = retrofitDestinyApi.latestManifestUrl();
//                if (!hasDb(latestDbPath.substring(34))) {
//                    addDbFrom(destinyApi.downloadManifestWithDynamicUrl(latestDbPath));
//                }
//            }
//        });
//    }

//    public void addDbFrom(Call<ResponseBody> responseBodyCall) {
//        try {
//            //Setting up the location for the file
//            String location = mContext.getDatabasePath("manifest").getParent().concat("/");
//
//            //Getting the file
//            InputStream zippedFile = responseBodyCall.execute().body().byteStream();
//
//            //File writing setup
//            int size;
//            byte[] buffer = new byte[1024];
//
//            //Making sure the location exists and is a directory
//            File unzippedFile = new File(location);
//            if (!unzippedFile.isDirectory()) {
//                unzippedFile.mkdirs();
//            }
//
//            //Cleaning it, as to get rid of older DBs
//            //FileUtils.cleanDirectory(unzippedFile);
//            File[] flist;
//            flist = unzippedFile.listFiles();
//            if (flist != null && flist.length > 0) {
//                for (File f : flist) {
//                    f.delete();
//                }
//            }
//
//            //Unzip magic
//            ZipInputStream zin = new ZipInputStream(new BufferedInputStream(zippedFile));
//            try {
//                ZipEntry ze;
//                while ((ze = zin.getNextEntry()) != null) {
//                    String path = location + ze.getName();
//                    File unzipFile = new File(path);
//
//                    if (ze.isDirectory()) {
//                        if (!unzipFile.isDirectory()) {
//                            unzipFile.mkdirs();
//                        }
//                    } else {
//                        //Check for and create parent directories if they don't exist
//                        File parentDir = unzipFile.getParentFile();
//                        if (null != parentDir) {
//                            if (!parentDir.isDirectory()) {
//                                parentDir.mkdirs();
//                            }
//                        }
//
//                        //Unzip the file
//                        FileOutputStream out = new FileOutputStream(unzipFile, false);
//                        BufferedOutputStream fout = new BufferedOutputStream(out, 1024);
//                        try {
//                            while ((size = zin.read(buffer, 0, 1024)) != -1) {
//                                fout.write(buffer, 0, size);
//                            }
//
//                            zin.closeEntry();
//                        } finally {
//                            fout.flush();
//                            fout.close();
//                        }
//                    }
//                }
//            } finally {
//                zin.close();
//                Log.d(TAG, "Saved manifest DB to file at: " + location);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//            return;
//        }
//    }
//
//    public SQLiteDatabase get() {
//        return availableDb;
//    }
}
