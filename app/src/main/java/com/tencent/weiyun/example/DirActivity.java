package com.tencent.weiyun.example;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.test.R;
import com.tencent.weiyun.DeleteFileCallback;
import com.tencent.weiyun.DownloadFileCallback;
import com.tencent.weiyun.DownloadOfflineFileCallback;
import com.tencent.weiyun.FetchFileCountCallback;
import com.tencent.weiyun.FetchDirFileListCallback;
import com.tencent.weiyun.FetchFileListCallback;
import com.tencent.weiyun.FetchOfflineTaskListCallback;
import com.tencent.weiyun.FetchUserInfoCallback;
import com.tencent.weiyun.QueryPasswordCallback;
import com.tencent.weiyun.UploadFileCallback;
import com.tencent.weiyun.WeiyunDir;
import com.tencent.weiyun.WeiyunFile;
import com.tencent.weiyun.WeiyunOfflineTask;
import com.tencent.weiyun.WeiyunSDK;
import com.tencent.weiyun.WeiyunUser;

import java.util.ArrayList;
import java.util.Map;

public class DirActivity extends Activity {
    WeiyunUser user;
    ArrayList<Object> listData;
    ListView listView;
    ListAdapter listAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dir);

        user = new WeiyunUser();
        listData = new ArrayList<Object>();
        listAdapter = new ListAdapter(this, listData);
        listView = (ListView)findViewById(R.id.listView);
        listView.setAdapter(listAdapter);

        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if (listData.get(position) instanceof WeiyunDir) {
                    return;
                }
                final WeiyunFile file = (WeiyunFile)listData.get(position);

                PopupMenu popup = new PopupMenu(DirActivity.this, view);
                popup.getMenuInflater().inflate(R.menu.menu_pop, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.download:
                                WeiyunSDK.getInstance().downloadFile(file, new DownloadFileCallback() {
                                    @Override
                                    public void callback(final String url, final String cookieName, final String cookieValue, final int errorcode) {
                                        DirActivity.this.runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                if (errorcode == 0) {
                                                    String text = String.format("WeiyunSDKExample:downloadFile. url = %s, cookieName = %s, cookieValue = %s", url, cookieName, cookieValue);
                                                    Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
                                                } else {
                                                    String text = String.format("WeiyunSDKExample:downloadFile failed. errorcode = %d", errorcode);
                                                    Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                    }
                                });
                                break;
                            case R.id.delete:
                                ArrayList<WeiyunFile> fileList = new ArrayList<WeiyunFile>();
                                fileList.add(file);
                                WeiyunSDK.getInstance().deleteFile(fileList, new DeleteFileCallback() {
                                     @Override
                                     public void callback(final int errorcode) {
                                         DirActivity.this.runOnUiThread(new Runnable() {
                                             @Override
                                             public void run() {
                                                 if (errorcode == 0) {
                                                     String text = String.format("WeiyunSDKExample:deleteFile successed.");
                                                     Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
                                                 } else {
                                                     String text = String.format("WeiyunSDKExample:downloadFile failed. errorcode = %d", errorcode);
                                                     Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
                                                 }
                                             }
                                         });
                                     }
                                });
                                break;
                        }
                        return true;
                    }
                });
                popup.show();
            }
        });

        WeiyunSDK.getInstance().enableDebugMode(true);

        WeiyunSDK.getInstance().registerContext(Contexts.getInstance());


        WeiyunSDK.getInstance().fetchUserInfo(new FetchUserInfoCallback() {
            @Override
            public void callback(final WeiyunUser user, final int errorcode) {
                DirActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (errorcode == 0) {
                            String text = String.format("WeiyunSDKExample:fetchUserInfo. mainKey = %s", user.mainkey.toString());
                            Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();

                            DirActivity.this.user = user;

                            WeiyunSDK.getInstance().fetchDirFileList(user.mainkey, true, new FetchDirFileListCallback() {
                                @Override
                                public void callback(final ArrayList<WeiyunDir> dirList, final ArrayList<WeiyunFile> fileList, boolean hasmore, boolean completion, final int errorcode) {
                                    DirActivity.this.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (errorcode == 0) {
                                                String text = String.format("WeiyunSDKExample:fetchDirFileList. dirList.size = %d, fileList.size = %d", dirList.size(), fileList.size());
                                                Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();

                                                listData.clear();
                                                for (WeiyunDir dir : dirList) {
                                                    listData.add(dir);
                                                }
                                                for (WeiyunFile file : fileList) {
                                                    listData.add(file);
                                                }
                                                listAdapter.notifyDataSetChanged();
                                            } else {
                                                String text = String.format("WeiyunSDKExample:fetchDirFileList failed. errorcode = %d", errorcode);
                                                Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }
                            });
                        } else {
                            String text = String.format("WeiyunSDKExample:fetchUserInfo failed. errorcode = %d", errorcode);
                            Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.reset:
                WeiyunSDK.getInstance().reset();
                Toast.makeText(getApplicationContext(), "重置缓存", Toast.LENGTH_SHORT).show();
                break;

            case R.id.count:
                WeiyunSDK.getInstance().fetchFileCount(new FetchFileCountCallback() {
                    @Override
                    public void callback(final Map<Integer, Integer> countMap, final int errorcode) {
                        DirActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (errorcode == 0) {
                                    String text = "WeiyunSDKExample:fetchFileCount. \n";
                                    for (Map.Entry<Integer, Integer> entry : countMap.entrySet()) {
                                        String text2 = String.format("Type = %d, Count = %d \n", entry.getKey().intValue(), entry.getValue().intValue());
                                        text = text.concat(text2);
                                    }
                                    Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
                                } else {
                                    String text = String.format("WeiyunSDKExample:fetchFileCount failed. errorcode = %d", errorcode);
                                    Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });
                break;

            case R.id.fetch:
                WeiyunSDK.getInstance().fetchDirFileList(user.mainkey, false, new FetchDirFileListCallback() {
                    @Override
                    public void callback(final ArrayList<WeiyunDir> dirList, final ArrayList<WeiyunFile> fileList, boolean hasmore, boolean completion, final int errorcode) {
                        DirActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (errorcode == 0) {
                                    String text = String.format("WeiyunSDKExample:fetchDirFileList. dirList.size = %d, fileList.size = %d", dirList.size(), fileList.size());
                                    Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();

                                    listData.clear();
                                    for (WeiyunDir dir : dirList) {
                                        listData.add(dir);
                                    }
                                    for (WeiyunFile file : fileList) {
                                        listData.add(file);
                                    }
                                    listAdapter.notifyDataSetChanged();
                                } else {
                                    String text = String.format("WeiyunSDKExample:fetchDirFileList failed. errorcode = %d", errorcode);
                                    Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });
                break;

            case R.id.uploadPhoto: {
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, 1);
            }
                break;

            case R.id.uploadFile: {
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.setType("*/*");
                i.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(i, 2);
            }
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        String filePath = null;

        if (requestCode == 1 && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            filePath = cursor.getString(columnIndex);
            cursor.close();
        } else if (requestCode == 2 && resultCode == RESULT_OK && null != data) {
            Uri uri = data.getData();
            if ("content".equalsIgnoreCase(uri.getScheme())) {
                String[] projection = { "_data" };
                try {
                    Cursor cursor = getContentResolver().query(uri, projection,null, null, null);
                    int column_index = cursor.getColumnIndexOrThrow("_data");
                    if (cursor.moveToFirst()) {
                        filePath = cursor.getString(column_index);
                    }
                } catch (Exception e) {

                }
            }
            else if ("file".equalsIgnoreCase(uri.getScheme())) {
                filePath = uri.getPath();
            }
        }

        if (filePath != null) {
            WeiyunSDK.getInstance().uploadFile(filePath, this.user.mainkey, WeiyunSDK.WeiyunUploadOptionRename, new UploadFileCallback() {
                @Override
                public void uploadFileCheckStarted(String filePath) {
                    DirActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "UploadFileCheckStarted", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                @Override
                public void uploadFileCheckExisted(String filePath, final String fileID) {
                    DirActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "UploadFileCheckExisted", Toast.LENGTH_SHORT).show();

                            WeiyunSDK.getInstance().fetchDirFileList(user.mainkey, true, new FetchDirFileListCallback() {
                                @Override
                                public void callback(final ArrayList<WeiyunDir> dirList, final ArrayList<WeiyunFile> fileList, boolean hasmore, boolean completion, final int errorcode) {
                                    DirActivity.this.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (errorcode == 0) {
                                                String text = String.format("WeiyunSDKExample:fetchDirFileList. dirList.size = %d, fileList.size = %d", dirList.size(), fileList.size());
                                                Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();

                                                listData.clear();
                                                for (WeiyunFile file : fileList) {
                                                    listData.add(file);
                                                }
                                                listAdapter.notifyDataSetChanged();
                                            } else {
                                                String text = String.format("WeiyunSDKExample:fetchDirFileList failed. errorcode = %d", errorcode);
                                                Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }
                            });
                        }
                    });
                }

                @Override
                public void uploadFileCheckFailed(String filePath, final int errorcode) {
                    DirActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            String text = String.format("UploadFileCheckFailed. errorcode = %d", errorcode);
                            Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                @Override
                public void uploadFileStarted(final String taskID, final String fileID) {
                    DirActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "UploadFileStarted", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                @Override
                public void uploadFileFinished(String taskID, final int errCode, final String errMsg) {
                    DirActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            String text = String.format("UploadFileFinished. errorcode = %d", errCode);
                            Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();

                            WeiyunSDK.getInstance().fetchDirFileList(user.mainkey, true, new FetchDirFileListCallback() {
                                @Override
                                public void callback(final ArrayList<WeiyunDir> dirList, final ArrayList<WeiyunFile> fileList, boolean hasmore, boolean completion, final int errorcode) {
                                    DirActivity.this.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (errorcode == 0) {
                                                String text = String.format("WeiyunSDKExample:fetchDirFileList. dirList.size = %d, fileList.size = %d", dirList.size(), fileList.size());
                                                Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();

                                                listData.clear();
                                                for (WeiyunFile file : fileList) {
                                                    listData.add(file);
                                                }
                                                listAdapter.notifyDataSetChanged();
                                            } else {
                                                String text = String.format("WeiyunSDKExample:fetchDirFileList failed. errorcode = %d", errorcode);
                                                Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }
                            });
                        }
                    });
                }

                @Override
                public void uploadFileCancelled(String taskID) {
                    DirActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "UploadFileCancelled", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                @Override
                public void uploadFileProgress(final String taskID, long uploadSize, long progressSize, long speed) {
                    DirActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                        }
                    });
                }
            });
        }
    }
}

