package com.fpt.prm391groupproject.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.fpt.prm391groupproject.DAO.UserSQLiteDAO;
import com.fpt.prm391groupproject.R;
import com.fpt.prm391groupproject.model.User;

public class ProfileFragment extends Fragment {
    TextView textView;
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
        textView = view.findViewById(R.id.test_text);
        textView.setText(user.getEmail());
        return view;
    }
}
