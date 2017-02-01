package choongyul.android.com.runtimepermission;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

import java.util.ArrayList;

/**
 * Created by myPC on 2017-02-01.
 */

public class DataLoader {
    private ArrayList<User> datas = new ArrayList<>();
    private Context context;

    public DataLoader(Context context) {
        this.context = context;
        load();
    }

    public void load() {
        // 1. 주소록에 접근하기 위해 ContentResolver를 불러온다.
        ContentResolver resolver = context.getContentResolver();

        // 2. 주소록에서 가져올 데이터 컬럼명을 정의한다.
        String projections[] = {
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID,                  // 데이터아이디
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,                // 이름
                ContactsContract.CommonDataKinds.Phone.NUMBER                       // 전화번호
        };
        // 3. Content Resolver 로 불러온(쿼리한) 데이터를 커서에 담는다.
        // 전화번호 URI : ContactsContract.CommonDataKinds.Phone.CONTENT_URI
        // 주소록 URI : ContactsContract.Contacts.CONTENT_URI
        //              HAS_PHONE_NUMBER : 전화번호가 있는지 확인하는 상수

        Cursor cursor = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI // 데이터 주소
                            , projections // 가져올 데이터 컬럼명 배열
                            , null        // 조건절에 들어가는 컬럼명들 지정
                            , null        // 지정된 컬럼명과 매핑되는 실제 조건 값
                            , null        // 정렬
        );

        if( cursor != null) {
            // 4. 커서에 넘어온 데이터가 있다면 반복문을 돌면서 datas에 담아준다.
            while ( cursor.moveToNext() ) {
                User user = new User();

                int idx = cursor.getColumnIndex(projections[0]);
                user.setNo(cursor.getInt(idx));
                idx = cursor.getColumnIndex(projections[1]);
                user.setName(cursor.getString(idx));

                idx = cursor.getColumnIndex(projections[2]);
                user.addPhoneNumber(cursor.getString(idx));

                datas.add(user);
            }
            // * 중요 : 사용 후 close를 호출하지 않으면 메모리 누수가 발생할 수 있다.
            cursor.close();
        }





    }

    public ArrayList<User> getDatas() {
        return datas;
    }
}
