package choongyul.android.com.runtimepermission;

import java.util.ArrayList;

/**
 * Created by myPC on 2017-02-01.
 */

public class DataLoader {
    ArrayList<User> datas = new ArrayList<>();

    public DataLoader() {
        load();
    }

    public void load() {
        for(int i=0 ; i<100 ; i++) {
            User user = new User();
            user.setNo(i+1);
            user.setName("홍길동"+user.getNo());
            String temp = String.format("%02d",i);
            user.addPhoneNumber("010-1234-56"+temp);

            datas.add(user);
        }
    }

    public ArrayList<User> getDatas() {
        return datas;
    }
}
