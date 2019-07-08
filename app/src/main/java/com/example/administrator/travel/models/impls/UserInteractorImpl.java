package com.example.administrator.travel.models.impls;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.administrator.travel.models.bases.UserInteractor;
import com.example.administrator.travel.models.entities.UserInformation;
import com.example.administrator.travel.models.listeners.Listener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Admin on 4/3/2019.
 */

public class UserInteractorImpl implements UserInteractor {
    @Override
    public String getUserId() {
        if (FirebaseAuth.getInstance().getCurrentUser() != null)
            return FirebaseAuth.getInstance().getCurrentUser().getUid();
        return "none";
    }

    @Override
    public void getUserInfor(final String userId, final Listener.OnGetUserInforFinishedListener listener) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userRef = database.getReference("users");
        userRef.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserInformation user = dataSnapshot.getValue(UserInformation.class);
                user.id = dataSnapshot.getKey();
                listener.onGetUserInforSuccess(user);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void getUserAvatar(final String userId, final Listener.OnGetUserAvatarFinishedListener listener) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference avatarRef = storage.getReference();
        final long MEGABYTE = 1024 * 1024;


        avatarRef.child("default_image.png").getBytes(MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                listener.onGetUserAvatarSuccess(userId, bmp);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
            }
        });
    }

    @Override
    public void getUserAvatar(final String userId, String avatarUrl, final Listener.OnGetUserAvatarFinishedListener listener) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference avatarRef = storage.getReferenceFromUrl(avatarUrl);
        final long MEGABYTE = 1024 * 1024;


        avatarRef.getBytes(MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                listener.onGetUserAvatarSuccess(userId, bmp);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
            }
        });
    }

    @Override
    public void searchUsers(final String keyword, final Listener.OnSearchUsersFinishedListener listener) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference usersRef = database.getReference("users");
        final List<UserInformation> userList = new ArrayList<>();
        final String noAccentKeyword = removeAccent(keyword);
        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    UserInformation user = snapshot.getValue(UserInformation.class);
                    user.id = snapshot.getKey();
                    if (removeAccent(user.name).contains(noAccentKeyword))
                        userList.add(user);
                }
                listener.onSearchUsersSuccess(userList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onSearchUsersFail(databaseError.toException());
            }
        });
    }

    private String removeAccent(String s) {
        String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(temp).replaceAll("").replaceAll("Đ", "D").replace("đ", "").toLowerCase();
    }

    @Override
    public boolean isLogged() {
        if (FirebaseAuth.getInstance().getCurrentUser() != null)
            return true;
        return false;
    }

    @Override
    public void login(String email, String password, Context context, final Listener.OnLoginFinishedListener listener) {
        if (context == null)
            listener.onLoginFail(new Exception(""));
        if (email.isEmpty() || email.contains("/") || password.isEmpty() || password.contains("/")) {
            listener.onLoginFail(new Exception("Email hoặc mật khẩu không hợp lệ"));
        } else {
            final FirebaseAuth mAuth = FirebaseAuth.getInstance();
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                listener.onLoginSuccess(mAuth.getCurrentUser().getUid());
                            } else {
                                listener.onLoginFail(new Exception("Email hoặc mật khẩu không chính xác"));
                            }
                        }
                    });
        }
    }

    @Override
    public void updateAvatar(final String userId, Bitmap avatar, final Listener.OnUpdateAvatarFinishedListener listener) {
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference mountainsRef = storageRef.child("avatar").child(userId).child("avatar" +calendar.getTimeInMillis() + ".png");

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        avatar.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = mountainsRef.putBytes(data);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                final Uri downloadUrl = taskSnapshot.getDownloadUrl();
                DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users");
                usersRef.child(userId).child("urlAvatar").setValue(downloadUrl.toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        listener.onUpdateAvatarSuccess(downloadUrl.toString());
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        listener.onUpdateAvatarFail(e);
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                listener.onUpdateAvatarFail(exception);
            }
        });
    }
    @Override
    public void updateUserInfor(UserInformation user, final Listener.OnUpdateUserInforFinishedListener listener){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference usersRef=database.getReference("users");
        String id=user.id;
        user.id=null;
        usersRef.child(id).setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                listener.onUpdateUserInforSuccess();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                listener.onUpdateUserInforFail(e);
            }
        });
    }
    @Override
    public void signUp(String email, String password, Listener.OnSignUpFinishedListener listener) {

    }

    @Override
    public void logout() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.signOut();
    }
}
