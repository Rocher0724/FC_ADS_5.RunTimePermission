package choongyul.android.com.runtimepermission;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView mRecyclerView ;
    ArrayList<User> datas;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        // Data를 통해 리스트에 넣을 데이터 set을 생성
//        DataLoader data = new DataLoader();
//        datas = data.getDatas();

//        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
//
//        CardAdapter adapter = new CardAdapter(datas, this);
//
//        mRecyclerView.setAdapter(adapter);
//
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));



        // 버전체크해서 마시멜로우보다 낮으면 런타임 권한 체크를 하지 않는다.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkPermission();
        } else {
            loadData();
        }
    }

    private final int REQ_CODE = 100;


    // 1. 권한체크
    @TargetApi(Build.VERSION_CODES.M)
    private void checkPermission() {
        // 1.1 런타임 권한체크 (권한을 추가할때 1.2 목록작성과 2.1 권한체크에도 추가해야한다.)
        if ( checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED
                || checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {

            // 1.2 요청할 권한 목록 작성
            String permArr[] = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CALL_PHONE, Manifest.permission.READ_CONTACTS};

            // 1.3 시스템에 권한요청
            requestPermissions(permArr, REQ_CODE);

        } else {
            loadData();
        }
    }

    // 2. 권한체크 후 콜백 - 사용자가 확인 후 시스템이 호출하는 함수
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if( requestCode == REQ_CODE) {
            // 2.1 배열에 넘긴 런타임 권한을 체크해서 승인이 됐으면
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED
                    && grantResults[2] == PackageManager.PERMISSION_GRANTED) {
                // 2.2 프로그램 실행
                loadData();
            } else {
                Toast.makeText(this, "권한을 허용하지 않으시면 프로그램을 실행할 수 없습니다.", Toast.LENGTH_SHORT).show();
                // 선택 : 1 종료, 2 권한체크 다시물어보기 할수도 있다. 일단은 끝내기
                finish();
            }
        }
    }

    // 3. 데이터 읽어오기(시스템실행)
    private  void loadData() {
        Toast.makeText(this, "프로그램을 실행합니다.", Toast.LENGTH_SHORT).show();
        // 3.1 Data를 불러온다.
        DataLoader data = new DataLoader(this);
        datas = data.getDatas();

        // 3.2 리사이클러뷰 세팅
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        CardAdapter adapter = new CardAdapter(datas, this);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

}
