package com.fpt.prm391groupproject.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.fpt.prm391groupproject.DAO.UserSQLiteDAO;
import com.fpt.prm391groupproject.EditProfile;
import com.fpt.prm391groupproject.LoginActivity;
import com.fpt.prm391groupproject.R;
import com.fpt.prm391groupproject.model.User;

public class ProfileFragment extends Fragment {
    TextView name,email,phone,age,address;
    ImageView imageView;
    private Button btnEdit;
    private String id;

    private UserSQLiteDAO userSQLiteDAO;

    public ProfileFragment(String id) {
        this.id = id;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile,container,false);
        userSQLiteDAO = new UserSQLiteDAO(container.getContext());
        User user = userSQLiteDAO.getUserById(id);
        name = view.findViewById(R.id.txtName);
        email = view.findViewById(R.id.txtEmail);
        phone = view.findViewById(R.id.txtPhone);
        age = view.findViewById(R.id.txtAge);
        address = view.findViewById(R.id.txtAddress);
        name.setText(user.getName());
        email.setText(user.getEmail());
        phone.setText(user.getPhone());
        age.setText(user.getAge()+"");
        address.setText(user.getAddress());
        imageView=view.findViewById(R.id.imageProfile);
        btnEdit = view.findViewById(R.id.btnEdit);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(v.getContext(), EditProfile.class);
                intent.putExtra("name",name.getText());
                intent.putExtra("email",email.getText());
                intent.putExtra("phone",phone.getText().toString());
                intent.putExtra("age",age.getText().toString());
                intent.putExtra("address",address.getText());
                intent.putExtra("userId",user.getUserId().toString());
                startActivity(intent);
            }
        });
        return view;
    }
}
