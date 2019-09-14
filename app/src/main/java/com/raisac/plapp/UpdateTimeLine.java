package com.raisac.plapp;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.raisac.plapp.compareSongs.comparesongsModule;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class UpdateTimeLine extends AppCompatActivity implements View.OnClickListener {

    protected static final int TIMER_RUNTIME = 180000; // in ms --> 10s
    static final int SONG_PICKER = 1;
    static final String TAG = "UpdateTimeLine";
    ImageView comparesongs, voteSong1, voteSong2, recordVoice, uploadvideo, importAudio;
    RecyclerView social_recyclerView;
    DividerItemDecoration dividerItemDecoration;
    LinearLayoutManager linearLayoutManager;
    TextView mComparesong1, mComparesong2, mArtstCompare, mArtistCompare2,
            mCountcompare1, mCountcompare2, mPostTimesetting;

    TextView txtTimerCount;
    int count = 0;
    EditText createCaption;
    FloatingActionButton fabCompareConfirm, fabCompareCancel, fabUpdateTimeLne;
    Context context;
    Dialog dialog;
    PopupWindow popup;
    soclaSongsAdapter soclasongsadapter;
    boolean mbActive;
    ImageView download;
    Handler handler = new Handler();
    RelativeLayout downlodRel;
    ImageView downloadSong, playsong;
    private String selectedAudiopath;
    private ArrayList<comparesongsModule> compSongs = new ArrayList<comparesongsModule>();
    private ArrayList<socilaplayListmodule> socialplaylis;
    private ArrayList<socialTabModel> socialARRay;
    private ProgressBar progressTimer;
    SocialTabbAdapter soclaadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_time_line);


        socialARRay = new ArrayList<>();
        socialplaylis = new ArrayList<>();
        soclasongsadapter = new soclaSongsAdapter(UpdateTimeLine.this, socialplaylis);
        comparesongs = findViewById(R.id.compareSongsImgView);
        createCaption = findViewById(R.id.newplaylistEDT);

        social_recyclerView = findViewById(R.id.updatsocialRecycler);
        linearLayoutManager = new LinearLayoutManager(UpdateTimeLine.this);
        social_recyclerView.setLayoutManager(linearLayoutManager);
        popup = new PopupWindow(this);

        dividerItemDecoration = new DividerItemDecoration(social_recyclerView.getContext(), linearLayoutManager.getOrientation());
        social_recyclerView.addItemDecoration(dividerItemDecoration);
        social_recyclerView.setAdapter(soclasongsadapter);

        fabUpdateTimeLne = findViewById(R.id.updateFab);
        fabUpdateTimeLne.setOnClickListener(this);

        comparesongs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new Dialog(UpdateTimeLine.this);
                dialog.setTitle("Compare Songs");
                dialog.setContentView(R.layout.activity_compare_songs_module);

                //recyclerView = dialog.findViewById(R.id.comparesongsRecyclerv);
                //recyclerView.setLayoutManager(linearLayoutManager);
                //compareSongsAdapter = new CompareSongsAdapter(UpdateTimeLine.this, compSongs);


                /*linearLayoutManager = new LinearLayoutManager(UpdateTimeLine.this);
                dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), linearLayoutManager.getOrientation());

                recyclerView.addItemDecoration(dividerItemDecoration);
                recyclerView.setAdapter(compareSongsAdapter);
*/

                WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
                layoutParams.copyFrom(dialog.getWindow().getAttributes());
                layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;

                dialog.show();


                voteSong1 = dialog.findViewById(R.id.compareSongSelcetion);
                voteSong2 = dialog.findViewById(R.id.compareSongSelcetion2);
                mCountcompare2 = dialog.findViewById(R.id.compareVoteCount2);
                mArtstCompare = dialog.findViewById(R.id.artistname);
                mArtistCompare2 = dialog.findViewById(R.id.artistname);
                mCountcompare1 = dialog.findViewById(R.id.compareVoteCount);
                mComparesong1 = dialog.findViewById(R.id.comparesong_name);
                mComparesong2 = dialog.findViewById(R.id.comparesong_name2);
                fabCompareCancel = dialog.findViewById(R.id.fabaddsong_toCompare);
                mPostTimesetting = dialog.findViewById(R.id.postTiming);
                mPostTimesetting.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Calendar mCurrentTime = Calendar.getInstance();
                        int day = mCurrentTime.get(Calendar.DAY_OF_MONTH);
                        int hour = mCurrentTime.get(Calendar.HOUR_OF_DAY);
                        int min = mCurrentTime.get(Calendar.MINUTE);
                        TimePickerDialog timePickerDialog = new TimePickerDialog(UpdateTimeLine.this, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                                hourOfDay = (hourOfDay / 24);
                                mPostTimesetting.setText(hourOfDay + "days" + ":" + minute);
                            }
                        }, hour, min, false

                        );
                        timePickerDialog.setTitle("Select Time");
                        timePickerDialog.show();
                    }
                });


                voteSong1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        count++;
                        mCountcompare1.setText(String.valueOf(count));
                        voteSong1.setActivated(false);
                        voteSong1.setEnabled(false);

                    }
                });


            }
        });

        importAudio = findViewById(R.id.importsongs);
        uploadvideo = findViewById(R.id.uploadvideos);
        recordVoice = findViewById(R.id.recordSong);

        importAudio.setOnClickListener(this);
        uploadvideo.setOnClickListener(this);
        recordVoice.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.importsongs:
                Intent audiointent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                audiointent.setType("audio/*");
                startActivityForResult(audiointent, SONG_PICKER);


                break;

            case R.id.uploadvideos:
                Intent videoIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                videoIntent.addCategory(Intent.CATEGORY_OPENABLE);
                videoIntent.setType("video/*");
                startActivityForResult(videoIntent, SONG_PICKER);
                break;

            case R.id.download:

                downlodRel = findViewById(R.id.downloadRelaView);
                download = findViewById(R.id.playsocialsong);
                download.setImageResource(R.drawable.ic_file_download_black_24dp);
                downlodRel.setVisibility(View.VISIBLE);
                popup.dismiss();
                startTimer();

                break;
            case R.id.play:
                downlodRel = findViewById(R.id.downloadRelaView);
                download = findViewById(R.id.playsocialsong);
                downlodRel.setVisibility(View.INVISIBLE);
                download.setImageResource(R.drawable.ic_play_arrow_black_24dp);
                popup.dismiss();

                break;

            case R.id.updateFab:


                String caption = createCaption.getText().toString().trim();

                /*socialTabModel socialtabmodel = new socialTabModel(caption);
                socialARRay.add(socialtabmodel);
                Intent intent = new Intent(this, Tab2.class);
                intent.putExtra("TlinCaption", socialARRay);
                startActivityForResult(intent, 1);


                soclaadapter = new SocialTabbAdapter(this, socialARRay);
                TextView newCaption = findViewById(R.id.socialTabCaption);
                newCaption.setText(caption);
*/

                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SONG_PICKER && resultCode == RESULT_OK) {
            Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
            if (data != null) {
                uri = data.getData();
                Log.i(TAG, "Uri:" + uri.toString());
                getPath(uri);
            }
        }
    }

    private void getPath(Uri uri) {
        String selection = MediaStore.Audio.Media.IS_MUSIC + "!=0";
        Cursor cursor = getContentResolver()
                .query(uri, null, selection, null, null);
        try {
            if (cursor != null && cursor.moveToFirst()) {

                String name = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));
                //String artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
                //String url = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));

                socialplaylis.add(new socilaplayListmodule(name));
                soclasongsadapter = new soclaSongsAdapter(UpdateTimeLine.this, socialplaylis);
                social_recyclerView.setAdapter(soclasongsadapter);

                linearLayoutManager = new LinearLayoutManager(UpdateTimeLine.this);
                social_recyclerView.setLayoutManager(linearLayoutManager);
                dividerItemDecoration = new DividerItemDecoration(social_recyclerView.getContext(), linearLayoutManager.getOrientation());
                social_recyclerView.addItemDecoration(dividerItemDecoration);


            }
        } finally {

            cursor.close();
        }
    }

    public void startTimer() {
        final Thread timerThread = new Thread() {
            @Override
            public void run() {
                mbActive = true;
                try {
                    int waited = 0;
                    while (mbActive && (waited < TIMER_RUNTIME)) {
                        sleep(1000);
                        if (mbActive) {
                            waited += 1000;

                            updateProgress(waited);
                        }
                    }
                } catch (InterruptedException e) {
                    // do nothing
                } finally {
                    //onContinue();
                }
            }

        };
        timerThread.start();

    }

    /*private void onContinue() {
        handler.post(new Runnable() {
            @Override
            public void run() {

                txtCallContactUsNumber
                        .setText(CommonVariable.CallForSupporMessage
                                + contactInfo.MobileNo);

            }
        });*/


    public void updateProgress(final int timePassed) {
        if (null != progressTimer) {
            // Ignore rounding error here
            final int progress = progressTimer.getMax() * timePassed
                    / TIMER_RUNTIME;
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Log.i("timePassed", "timePassed=" + timePassed);

                    DateFormat formatter = new SimpleDateFormat("mm:ss");
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(timePassed);

                    downlodRel = findViewById(R.id.downloadRelaView);

                    txtTimerCount.findViewById(R.id.downloadProgress);
                    String timer = formatter.format(calendar.getTime());
                    formatter.setTimeZone(java.util.TimeZone.getTimeZone("GMT"));
                    txtTimerCount.setText(formatter.format(timePassed));

                }
            });

            progressTimer.setProgress(progress);

        }
    }

    public void showPopup(View v) {
        PopupWindow popup = new PopupWindow(this);

        View layout = getLayoutInflater().inflate(R.layout.song_menu, null);
        popup.setContentView(layout);
        popup.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        popup.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        popup.setOutsideTouchable(true);
        popup.setFocusable(true);


        try {
            Field[] fields = popup.getClass().getDeclaredFields();
            for (Field field : fields) {
                if ("mPopup".equals(field.getName())) {
                    field.setAccessible(true);
                    Object menuPopupHelper = field.get(popup);
                    Class<?> classPopupHelper = Class.forName(menuPopupHelper
                            .getClass().getName());
                    Method setForceIcons = classPopupHelper.getMethod(
                            "setForceShowIcon", boolean.class);
                    setForceIcons.invoke(menuPopupHelper, true);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        download = layout.findViewById(R.id.playsocialsong);
        downloadSong = layout.findViewById(R.id.download);
        playsong = layout.findViewById(R.id.play);
        downloadSong.setOnClickListener(this);
        playsong.setOnClickListener(this);

        popup.showAsDropDown(v);
    }


}


