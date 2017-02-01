package choongyul.android.com.runtimepermission;

import java.util.ArrayList;
            //Pure Old Java Object
/** 전화번호부 POJO class. (변수와 getter setter만으로 이루어진 클래스)
 * Created by myPC on 2017-02-01.
 */

public class User {
    private ArrayList<String> phoneNumber;
    private String name;
    private int no;

    public User() {
        phoneNumber = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public int getNo() {
        return no;
    }



    public void setName(String name) {
        this.name = name;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public ArrayList<String> getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(ArrayList<String> phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void addPhoneNumber(String phoneNumber) {
        this.phoneNumber.add(phoneNumber);
    }

    public void removePhoneNumber(String phoneNumber) {
        this.phoneNumber.remove(phoneNumber);
    }
}
