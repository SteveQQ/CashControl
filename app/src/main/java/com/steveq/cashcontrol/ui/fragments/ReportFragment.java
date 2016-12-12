package com.steveq.cashcontrol.ui.fragments;


import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.itextpdf.text.Anchor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.steveq.cashcontrol.R;
import com.steveq.cashcontrol.ui.activities.CatalogsActivity;
import com.steveq.cashcontrol.ui.activities.ReceiptsActivity;
import com.steveq.cashcontrol.utilities.FileUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReportFragment extends Fragment {

    private static final String FILE = "/data/reports_cc/";
    private static Font titleFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
    ImageView generateReport;


    public ReportFragment() {
        // Required empty public constructor
        Bundle bundle = new Bundle();
        bundle.putString(ReceiptsActivity.FRAGMENT_NAME, "Report");
        this.setArguments(bundle);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_report, container, false);

        generateReport = (ImageView) view.findViewById(R.id.generateFileImageView);
        generateReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(FileUtils.isExternalStorageAvailable() && FileUtils.isExternalWritable()){
                    Log.d("DEBUG", "External storage available and writable");
                    File path = new File(getActivity().getExternalCacheDir() + "/reports/test.pdf");
                    Log.d("DEBUG", getActivity().getExternalCacheDir() + "/reports");
                    if(!path.exists()) {
                        if (path.getParentFile().mkdirs()) {
                            Toast.makeText(getActivity(), "catalog created", Toast.LENGTH_LONG).show();
                            Log.d("DEBUG", "Catalog created");
                        }
                    }
                    try {

                        Document document = new Document();
                        PdfWriter.getInstance(document, new FileOutputStream(path));
                        document.open();
                        addContent(document);
                        document.close();

                    } catch (DocumentException e) {
                        e.printStackTrace();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException ioe){
                        ioe.printStackTrace();
                    }
                }

            }
        });

        return view;
    }

    private void addContent(Document document) throws DocumentException {
        document.add(new Paragraph("Hello World!"));
    }

}
