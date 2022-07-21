package com.fpt.prm391groupproject.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.fpt.prm391groupproject.DAO.UserSQLiteDAO;
import com.fpt.prm391groupproject.R;
import com.fpt.prm391groupproject.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangePasswordFragment extends Fragment {
    private EditText etOldPass,etNewPass,etReNewPass;
    private Button btChange;
    private ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_change_password,container,false);

        //etOldPass = view.findViewById(R.id.etOldPass);
        etNewPass = view.findViewById(R.id.etNewPass);
        etReNewPass = view.findViewById(R.id.etReNewPass);
        btChange = view.findViewById(R.id.btChange);
        progressDialog = new ProgressDialog(getActivity());
        btChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePass();
            }
        });

        return view;
    }

    private void changePass() {
        //String oldPass = etOldPass.getText().toString().trim();
        String newPass = etNewPass.getText().toString().trim();
        String reNewPass = etReNewPass.getText().toString().trim();
        if (TextUtils.isEmpty(newPass)){
            etNewPass.setError("You must enter your new pass word.");
            return;
        }
        if(newPass.length()<6){
            etNewPass.setError("Password must be longer than 6 characters");
            return;
        }
        if(!reNewPass.equals(newPass)){
            etReNewPass.setError("RePassword didn't match");
            return;
        }
        progressDialog.show();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        user.updatePassword(newPass).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getActivity(),"Password changed",Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }
        });
    }
}
