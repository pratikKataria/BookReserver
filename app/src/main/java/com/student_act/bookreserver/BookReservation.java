package com.student_act.bookreserver;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.bookreserver.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.io.FileOutputStream;
import java.io.IOException;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.WHITE;

/**
 * A simple {@link Fragment} subclass.
 */
public class BookReservation extends Fragment {

    SharedPreferences pref;
    SharedPreferences.Editor editor;

    public BookReservation() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_book_reservation, container, false);
        pref = getActivity().getSharedPreferences("reserved_books", Context.MODE_PRIVATE);//    Editor editor = sharedPreferences.edit();
        editor = pref.edit();

        TextView textView = view.findViewById(R.id.TextLogo);
        textView.setText("Request\nBook");

        EditText book1 = view.findViewById(R.id.book_entry1);
        EditText book2 = view.findViewById(R.id.book_entry2);
        EditText book3 = view.findViewById(R.id.book_entry3);
        EditText auth1 = view.findViewById(R.id.author1);
        EditText auth2 = view.findViewById(R.id.author2);
        EditText auth3 = view.findViewById(R.id.author3);

        Button button = view.findViewById(R.id.get_book_btn);
        button.setOnClickListener(v -> {

            if (isFieldEmpty(book1)) return;
            if (isFieldEmpty(auth1)) return;
            if (isFieldEmpty(book2)) return;
            if (isFieldEmpty(auth2)) return;
            if (isFieldEmpty(book3)) return;
            if (isFieldEmpty(auth3)) return;

            String registeredBook = book1.getText().toString()+ "," + auth1.getText().toString() + ","
                    + book2.getText().toString()+ "," + auth2.getText().toString() + ","
                    + book3.getText().toString()+ "," + auth3.getText().toString() + ",";

            editor.putString("bookData", registeredBook);
            editor.putString("image_data", "/data/data/com.example.bookreserver/shared_prefs/bookQrCode.png");
            editor.apply();

            createQRCode(registeredBook);

            book1.getText().clear();
            book2.getText().clear();
            book3.getText().clear();
            auth1.getText().clear();
            auth2.getText().clear();
            auth3.getText().clear();
        });

        View view3 = view.findViewById(R.id.animation_view);
        view3.setBackgroundColor(getResources().getColor(R.color.skyBlue));

        changeStatusBarColor();

        return view;
    }

    private boolean isFieldEmpty(TextView textView) {
        if (textView.getText().toString().trim().isEmpty()) {
            textView.setError("field should not be empty");
            textView.requestFocus();
            textView.setText("");
            return true;
        }
        return false;
    }

    private void createQRCode(String data) {
        BitMatrix result = null;
        try {
            result = new MultiFormatWriter().encode(data,
                    BarcodeFormat.QR_CODE, 240, 240, null);
        } catch (IllegalArgumentException iae) {
            // Unsupported format
            return;
        } catch (WriterException e) {
            e.printStackTrace();
        }
        if (result != null) {
            int w = result.getWidth();
            int h = result.getHeight();
            int[] pixels = new int[w * h];
            for (int y = 0; y < h; y++) {
                int offset = y * w;
                for (int x = 0; x < w; x++) {
                    pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
                }
            }
            Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, 240, 0, 0, w, h);

        try (FileOutputStream out = new FileOutputStream("/data/data/com.example.bookreserver/shared_prefs/bookQrCode.png")) {
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, out); // bmp is your Bitmap instance
                // PNG is a lossless format, the compression factor (100) is ignored
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void changeStatusBarColor() {
        Window window = getActivity().getWindow();
        window.setStatusBarColor(ContextCompat.getColor(getActivity(), R.color.skyBlue));
    }

}
