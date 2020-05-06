package com.student_act.bookreserver;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.bookreserver.R;

import java.io.File;
import java.io.FileInputStream;
import java.util.StringTokenizer;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewBookCode extends Fragment {

    private String data;
    private TextView book1;
    private TextView book2;
    private TextView book3;
    private TextView author1;
    private TextView author2;
    private TextView author3;
    private ImageView imageView;

    private SharedPreferences bookData;

    public ViewBookCode() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_book_code, container, false);

        bookData = getActivity().getSharedPreferences("reserved_books", 0);
        TextView textView = view.findViewById(R.id.TextLogo);
        textView.setText("Book\nCode");

        View view1 = view.findViewById(R.id.animation_view);
        view1.setBackgroundColor(getResources().getColor(R.color.babyPink));

        book1 = view.findViewById(R.id.book_entry1);
        book2 = view.findViewById(R.id.book_entry2);
        book3 = view.findViewById(R.id.book_entry3);
        author1 = view.findViewById(R.id.author1);
        author2 = view.findViewById(R.id.author2);
        author3 = view.findViewById(R.id.author3);
        imageView = view.findViewById(R.id.image_qr_code);

        TextView [] textViewArray = {book1, author1, book2, author2, book3, author3};
        StringTokenizer st = new StringTokenizer(bookData.getString("bookData", ""), ",");
        while (st.hasMoreTokens()) {
            for (TextView x : textViewArray)
                x.setText(st.nextToken());
        }
        getBitmap(bookData.getString("image_data", " "));
        changeStatusBarColor();
        return view;
    }

    public void getBitmap(String path) {
        Bitmap bitmap=null;
        try {
            File f= new File(path);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;

            bitmap = BitmapFactory.decodeStream(new FileInputStream(f), null, options);
            imageView.setImageBitmap(Bitmap.createScaledBitmap(bitmap, 250, 250, false));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void changeStatusBarColor() {
        Window window = getActivity().getWindow();
        window.setStatusBarColor(ContextCompat.getColor(getActivity(), R.color.babyPink));
    }

}
