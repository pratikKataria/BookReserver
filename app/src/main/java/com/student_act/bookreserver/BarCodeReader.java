package com.student_act.bookreserver;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import com.example.bookreserver.R;
import com.google.android.gms.vision.barcode.Barcode;
import com.notbytes.barcode_reader.BarcodeReaderActivity;
import com.notbytes.barcode_reader.BarcodeReaderFragment;

import java.util.Calendar;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class BarCodeReader extends Fragment implements BarcodeReaderFragment.BarcodeReaderListener {

    private static final int BARCODE_READER_ACTIVITY_REQUEST = 1208;

    private BarcodeReaderFragment barcodeReader;
    private BarCodeFragmentListner barCodeFragmentListner;

    public BarCodeReader() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bar_code_reader, container, false);
        barcodeReader = (BarcodeReaderFragment) getChildFragmentManager().findFragmentById(R.id.barcode_fragment);
        barcodeReader.setListener(this);
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != Activity.RESULT_OK) {
            Toast.makeText(getActivity(), "error in  scanning", Toast.LENGTH_SHORT).show();
            return;
        }

        if (requestCode == BARCODE_READER_ACTIVITY_REQUEST && data != null) {
            Barcode barcode = data.getParcelableExtra(BarcodeReaderActivity.KEY_CAPTURED_BARCODE);
            Toast.makeText(getActivity(), Calendar.getInstance().getTime().toString(), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onScanned(Barcode barcode) {
        Toast.makeText(getActivity(), barcode.rawValue, Toast.LENGTH_SHORT).show();
        barCodeFragmentListner.onScanCode(Calendar.getInstance().getTime().toString(), barcode.rawValue);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof BarCodeFragmentListner)
            barCodeFragmentListner = (BarCodeFragmentListner) context;
        else
            throw new RuntimeException(context.toString() + "must implement BarCodeFragmentListner interface");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        barCodeFragmentListner = null;
    }

    @Override
    public void onScannedMultiple(List<Barcode> barcodes) {

    }

    @Override
    public void onBitmapScanned(SparseArray<Barcode> sparseArray) {

    }

    @Override
    public void onScanError(String errorMessage) {

    }

    @Override
    public void onCameraPermissionDenied() {
        Toast.makeText(getActivity(), "Camera permission denied!", Toast.LENGTH_LONG).show();
    }

    public interface BarCodeFragmentListner {
        void onScanCode(String date, String scannedData);
    }
}
