package com.junseo.daepyeong.meal;

/**
 * Created by Junseo on 16. 3. 2..
 */

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.junseo.dphs.R;

import java.util.Calendar;

public class MealActivity extends AppCompatActivity {

    Toolbar toolbar;

    SwipeRefreshLayout mSwipeRefreshLayout;

    int DAY_OF_WEEK;
    int YEAR, MONTH, DAY;

    int position_select = 0;
    boolean if_select = false;
    /**
     * 리스트뷰와 어뎁터
     */
    ListView mListView;
    BapListAdapter mAdapter;

    /**
     * ProcessTask를 상속한 BapDownloadTask class
     */
    BapDownloadTask mProcessTask;

    /**
     * 진행 상황을 표시하기 위한 Dialog
     */
    ProgressDialog mDialog;

    /**
     * 오늘 날짜를 알아오기 위한 Calendar
     */
    Calendar mCalendar;

    /**
     * 2번이상 BapDownloadTask를 실행하지 않도록 도와주는 boolean
     */
    boolean isUpdating = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal);

        toolbar = (Toolbar) findViewById(R.id.toolbar); //툴바설정
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        setSupportActionBar(toolbar);//액션바와 같게 만들어줌

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //툴바 뒤로가기
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_24dp));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //What to do on back clicked
                finish();
            }
        });

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getBapList(true);
                if (mSwipeRefreshLayout.isRefreshing())
                    mSwipeRefreshLayout.setColorSchemeResources(R.color.color_1);
                    mSwipeRefreshLayout.setRefreshing(false);
            }
        });

        /**
         * 지금 날짜를 가져오기 위한 Calendar 생성
         */
        mCalendar = Calendar.getInstance();

        getCalendarInstance(true);

        /**
         * 리스트뷰를 findViewById하고 mAdapter를 생성합니다.
         */
        mListView = (ListView) findViewById(R.id.mListView);
        mAdapter = new BapListAdapter(getApplicationContext());
        mListView.setAdapter(mAdapter);

        /**
         * 급식 리스트를 얻습니다.
         * isUpdate=true로 설정하여 급식이 없을경우 BapDownloadTask를 실행합니다.
         */
        getBapList(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.share_lunch);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BapListData bld = mAdapter.getItem(position_select);

                // List Adapter 생성
                position_select = 0;
                if_select = true;
                final String items[] = {"월요일", "화요일", "수요일", "목요일", "금요일"};
                AlertDialog.Builder ab = new AlertDialog.Builder(MealActivity.this);

                ab.setTitle("요일 선택");
                ab.setCancelable(false);
                ab.setSingleChoiceItems(items, 0,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                // 각 리스트를 선택했을때
                                position_select = whichButton;
                                if_select = true;
                            }
                        });

                ab.setPositiveButton("확인",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                // OK 버튼 클릭시 , 여기서 선택한 요일을 클립보드로 복사한다.

                                if (if_select) {
                                    BapListData bld = mAdapter.getItem(position_select);
                                    //ㅇㅇ 이게 점심 가져오는 코드

                                    Intent msg = new Intent(Intent.ACTION_SEND);

                                    msg.addCategory(Intent.CATEGORY_DEFAULT);
                                    msg.putExtra(Intent.EXTRA_SUBJECT, "[대평고등학교 급식정보]");
                                    msg.putExtra(Intent.EXTRA_TEXT, "\n" + "<" + bld.mCalender + " " + bld.getmDayOfTheWeek() + " 중식>" + "\n\n" + bld.getmLunch() + "\n" + bld.mKcal_Lunch + " Kcal" + "\n\n#대평고등학교 앱");
                                    msg.putExtra(Intent.EXTRA_TITLE, "대평고등학교 급식");
                                    msg.setType("text/plain");
                                    startActivity(Intent.createChooser(msg, "급식 공유하기"));
                                    if_select = false;
                                } else {
                                    Toast.makeText(MealActivity.this, "리스트에서 요일을 선택해주십시오.", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
                ab.setNegativeButton("취소",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                // Cancel 버튼 클릭시
                                if_select = false;
                            }
                        });
                ab.show();
            }
        });

        FloatingActionButton fab2 = (FloatingActionButton) findViewById(R.id.share_dinner);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BapListData bld = mAdapter.getItem(position_select);

                // List Adapter 생성
                position_select = 0;
                if_select = true;
                final String items[] = {"월요일", "화요일", "수요일", "목요일", "금요일"};
                AlertDialog.Builder ab = new AlertDialog.Builder(MealActivity.this);

                ab.setTitle("요일 선택");
                ab.setCancelable(false);
                ab.setSingleChoiceItems(items, 0,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                // 각 리스트를 선택했을때
                                position_select = whichButton;
                                if_select = true;
                            }
                        });

                ab.setPositiveButton("확인",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                // OK 버튼 클릭시 , 여기서 선택한 요일을 클립보드로 복사한다.

                                if (if_select) {
                                    BapListData bld = mAdapter.getItem(position_select);
                                    //ㅇㅇ 이게 점심 가져오는 코드

                                    Intent msg = new Intent(Intent.ACTION_SEND);

                                    msg.addCategory(Intent.CATEGORY_DEFAULT);
                                    msg.putExtra(Intent.EXTRA_SUBJECT, "[대평고등학교 급식정보]");
                                    msg.putExtra(Intent.EXTRA_TEXT, "\n" + "<" + bld.mCalender + " " + bld.getmDayOfTheWeek() + " 석식>" + "\n\n" + bld.mDinner + "\n" + bld.mKcal_Dinner + " Kcal" + "\n\n#대평고등학교 앱");
                                    msg.putExtra(Intent.EXTRA_TITLE, "대평고등학교 급식");
                                    msg.setType("text/plain");
                                    startActivity(Intent.createChooser(msg, "급식 공유하기"));
                                    //Toast.makeText(MealActivity.this,bld.getmLunch(),Toast.LENGTH_SHORT).show();
                                    if_select = false;
                                } else {
                                    Toast.makeText(MealActivity.this, "리스트에서 요일을 선택해주십시오.", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
                ab.setNegativeButton("취소",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                // Cancel 버튼 클릭시
                                if_select = false;
                            }
                        });
                ab.show();
            }
        });

        FloatingActionButton fab3 = (FloatingActionButton) findViewById(R.id.share_all);
        fab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BapListData bld = mAdapter.getItem(position_select);

                // List Adapter 생성
                position_select = 0;
                if_select = true;
                final String items[] = {"월요일", "화요일", "수요일", "목요일", "금요일"};
                AlertDialog.Builder ab = new AlertDialog.Builder(MealActivity.this);

                ab.setTitle("요일 선택");
                ab.setCancelable(false);
                ab.setSingleChoiceItems(items, 0,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                // 각 리스트를 선택했을때
                                position_select = whichButton;
                                if_select = true;
                            }
                        });

                ab.setPositiveButton("확인",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                // OK 버튼 클릭시 , 여기서 선택한 요일을 클립보드로 복사한다.

                                if (if_select) {
                                    BapListData bld = mAdapter.getItem(position_select);
                                    //ㅇㅇ 이게 점심 가져오는 코드

                                    Intent msg = new Intent(Intent.ACTION_SEND);

                                    msg.addCategory(Intent.CATEGORY_DEFAULT);
                                    msg.putExtra(Intent.EXTRA_SUBJECT, "[대평고등학교 급식정보]");
                                    msg.putExtra(Intent.EXTRA_TEXT, "\n" + "<" + bld.mCalender + " " + bld.getmDayOfTheWeek() + ">" + "\n\n" + "<중식>" + "\n" + bld.mLunch + "\n" + bld.mKcal_Lunch + " Kcal" + "\n\n\n" + "<석식>" + "\n" + bld.mDinner + "\n" + bld.mKcal_Dinner + " Kcal" + "\n\n#대평고등학교 앱");
                                    msg.putExtra(Intent.EXTRA_TITLE, "대평고등학교 급식");
                                    msg.setType("text/plain");
                                    startActivity(Intent.createChooser(msg, "급식 공유하기"));
                                    //Toast.makeText(MealActivity.this,bld.getmLunch(),Toast.LENGTH_SHORT).show();
                                    if_select = false;
                                } else {
                                    Toast.makeText(MealActivity.this, "리스트에서 요일을 선택해주십시오.", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
                ab.setNegativeButton("취소",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                // Cancel 버튼 클릭시
                                if_select = false;
                            }
                        });
                ab.show();
            }
        });

        Calendar calendar = Calendar.getInstance();              //현재 날짜 불러옴
        YEAR = calendar.get(Calendar.YEAR);
        MONTH = calendar.get(Calendar.MONTH);
        DAY = calendar.get(Calendar.DAY_OF_MONTH);

    }

    private void getBapList(boolean isUpdate) {

        mAdapter.clearData();
        mAdapter.notifyDataSetChanged();

        getCalendarInstance(false);

        final Calendar mToday = Calendar.getInstance();
        final int TodayYear = mToday.get(Calendar.YEAR);
        final int TodayMonth = mToday.get(Calendar.MONTH);
        final int TodayDay = mToday.get(Calendar.DAY_OF_MONTH);
        /**
         * 기존 데이터를 초기화 합니다.
         */
        mAdapter.clearData();
        mAdapter.notifyDataSetChanged();

        /**
         * mCalendar가 null이면 새로 생성합니다.
         */
        if (mCalendar == null)
            mCalendar = Calendar.getInstance();

        /**
         * 월요일 부터 급식을 표시하므로
         * 이번주 월요일 날짜로 설정합니다.
         */
        mCalendar.add(Calendar.DATE, 2 - mCalendar.get(Calendar.DAY_OF_WEEK));

        /**
         * for 반복문을 5번 돌면서 월요일부터 금요일까지의 급식 데이터를 가져옵니다.
         */
        for (int i = 0; i < 5; i++) {
            int year = mCalendar.get(Calendar.YEAR);
            int month = mCalendar.get(Calendar.MONTH);
            int day = mCalendar.get(Calendar.DAY_OF_MONTH);

            /**
             * BapTool을 이용해서 저장된 급식 데이터를 가져옵니다.
             */
            BapTool.restoreBapDateClass mData =
                    BapTool.restoreBapData(getApplicationContext(), year, month, day);

            /**
             * isBlankDay가 true이면 급식 데이터가 저장되지 않은 날입니다.
             */
            if (mData.isBlankDay) {
                if (Tools.isNetwork(getApplicationContext())) {
                    /**
                     * 네트워크가 켜져있으면
                     */
                    if (!isUpdating && isUpdate) {
                        /**
                         * mProcessTask가 실행중이지 않고 : !isUpdating
                         * 업데이트를 실행하라고 하면 : isUpdate
                         *
                         * TODO 작업중 표시를 커스텀하려면 이곳을 수정하세요
                         */
                        mDialog = new ProgressDialog(this);
                        mDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                        mDialog.setMax(100);
                        mDialog.setTitle(R.string.loading_title);
                        mDialog.setCancelable(false);
                        mDialog.show();

                        /**
                         * 급식을 업데이트 합니다.
                         */
                        mProcessTask = new BapDownloadTask(getApplicationContext());
                        mProcessTask.execute(year, month, day);
                    }
                } else {
                    /**
                     * 네트워크가 꺼져있으면 오류 메세지를 표시합니다.
                     * TODO 원하는 오류 처리방식으로 수정하세요
                     */
                    Toast.makeText(getApplicationContext(), R.string.no_network_msg, Toast.LENGTH_SHORT).show();
                }

                return;
            }
            /**
             * 급식 데이터가 저장되어 있으면 : mData.isBlankDay가 false이면
             * mAdapter에 급식을 추가합니다.
             */
            mAdapter.addItem(mData.Calender, mData.DayOfTheWeek, mData.Lunch, mData.Dinner, mData.Kcal_Lunch, mData.Kcal_Dinner);
            mCalendar.add(Calendar.DATE, 1);
        }

        /**
         * TODO for문이 실행되고 나면 mCalendar의 날짜가 이번주 금요일을 설정되므로 mCalendar의 날짜를 다시 설정해주어야 합니다.
         */

        mCalendar.set(YEAR, MONTH, DAY);
        mAdapter.notifyDataSetChanged();
        setCurrentItem();
    }


    /**
     * 오늘 날짜에 맞는 급식을 바로 보여주기 위한 메소드
     * 월~금까지는 각자의 요일이 바로 위에 뜨며 토, 일은 월요일이 맨앞에 뜨게 됩니다.
     */
    private void setCurrentItem() {
        final Calendar mToday = Calendar.getInstance();
        final int DAY = mToday.get(Calendar.DAY_OF_WEEK);
        mListView.setSelection(DAY - 2);
    }

    /**
     * ProcessTask를 상속받아 만든 BapDownloadTask
     */
    public class BapDownloadTask extends ProcessTask {
        public BapDownloadTask(Context mContext) {
            super(mContext);
        }

        @Override
        public void onPreDownload() {
            isUpdating = true;
        }

        @Override
        public void onUpdate(int progress) {
            /**
             * TODO 작업 현황을 표시하는 방법을 커스텀 하세요
             */
            mDialog.setProgress(progress);
        }

        @Override
        public void onFinish(long result) {
            if (mDialog != null)
                mDialog.dismiss();

            isUpdating = false;

            if (result == -1) {
                /**
                 * TODO 에러가 발생하면 어떻게 처리할건지 이곳에 작성하세요
                 */
                return;
            }

            /**
             * 무한 반복 업데이트를 막기 위해 isUpdate=false로 getBapList()을 호출합니다.
             */
            getBapList(false);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        /**
         * 앱을 일시중지 할경우 Dialog를 닫습니다.
         */
        if (mDialog != null)
            mDialog.dismiss();

        mCalendar = null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_meal, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {

            AlertDialog.Builder alert = new AlertDialog.Builder(MealActivity.this);
            alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();  //닫기
                }
            });
            alert.setTitle("알레르기 정보");
            alert.setMessage(" ① 난류\n ② 우유\n ③ 메밀\n ④ 땅콩\n ⑤ 대두\n ⑥ 밀\n ⑦ 고등어\n ⑧ 게\n ⑨ 새우\n ⑩ 돼지고기\n ⑪ 복숭아\n ⑫ 토마토\n ⑬ 아황산염\n ⑭ 호두\n ⑮ 닭고기\n ⑯ 소고기\n ⑰ 오징어\n ⑱ 전복\n ⑲ 홍합");
            alert.show();

        }

        if (id == R.id.action_calendar) {
            setCalenderBap();
        }

        return super.onOptionsItemSelected(item);
    }

    public void setCalenderBap() {
        getCalendarInstance(false);

        int year = mCalendar.get(Calendar.YEAR);
        int month = mCalendar.get(Calendar.MONTH);
        int day = mCalendar.get(Calendar.DAY_OF_MONTH);

        com.fourmob.datetimepicker.date.DatePickerDialog datePickerDialog = com.fourmob.datetimepicker.date.DatePickerDialog.newInstance(new com.fourmob.datetimepicker.date.DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(com.fourmob.datetimepicker.date.DatePickerDialog datePickerDialog, int year, int month, int day) {
                mCalendar.set(year, month, day);
                getCalendarInstance(false);

                getBapList(true);
            }
        }, year, month, day, false);
        datePickerDialog.setYearRange(2006, 2030);
        datePickerDialog.setCloseOnSingleTapDay(false);
        datePickerDialog.show(getSupportFragmentManager(), "Tag");
    }

    private void getCalendarInstance(boolean getInstance) {
        if (getInstance || (mCalendar == null))
            mCalendar = Calendar.getInstance();
        YEAR = mCalendar.get(Calendar.YEAR);
        MONTH = mCalendar.get(Calendar.MONTH);
        DAY = mCalendar.get(Calendar.DAY_OF_MONTH);
        DAY_OF_WEEK = mCalendar.get(Calendar.DAY_OF_WEEK);
    }
}