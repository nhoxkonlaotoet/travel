package com.example.administrator.travel.models;

import android.text.TextUtils;
import android.widget.Toast;

/**
 * Created by Henry on 12/10/2018.
 */

public class SignUpInteractorImpl implements SignUpInteractor {
    @Override
    public int CheckRegister(String Email, String Password, String CheckPass) {

        if (TextUtils.isEmpty (Email)) {
//            Toast.makeText (this, "Tên không hợp lệ!", Toast.LENGTH_SHORT).show ();
            return 1;
        }

        if (Password.length () < 6) {
//            Toast.makeText (this, "Mật khẩu phải ít nhất 6 kí tự!", Toast.LENGTH_SHORT).show ();
            return 2;
        }
        if (Password.equals (CheckPass) == false) {
//            Toast.makeText (this, "Mật khẩu không trùng!", Toast.LENGTH_SHORT).show ();
            return 3;
        }
        return 0;

    }
}
