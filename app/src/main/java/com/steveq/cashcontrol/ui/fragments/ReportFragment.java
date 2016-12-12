package com.steveq.cashcontrol.ui.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
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
    private static Font mTitleFont = new Font(Font.FontFamily.HELVETICA, 28, Font.BOLD);
    private static Font mSubTitleFont = new Font(Font.FontFamily.HELVETICA, 20, Font.BOLDITALIC);
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
                    File path = new File(
                            getActivity().getExternalCacheDir() +
                            "/reports/" +
                            CatalogsActivity.currentCatalog.getName() + ".pdf"
                    );
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

                        document.add(createHeaderParagraph());
                        document.add(Chunk.NEWLINE);
                        document.add(Chunk.NEWLINE);
                        document.add(Chunk.NEWLINE);
                        document.add(createTable());

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

    private PdfPTable createTable() {
        PdfPTable table = new PdfPTable(4);

        PdfPCell nameC = new PdfPCell(new Phrase("Name"));
        nameC.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(nameC);

        PdfPCell categoryC = new PdfPCell(new Phrase("Category"));
        categoryC.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(categoryC);

        PdfPCell dateC = new PdfPCell(new Phrase("Date"));
        dateC.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(dateC);

        PdfPCell cashC = new PdfPCell(new Phrase("Cash"));
        cashC.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cashC);

        for(int aw = 0; aw < 16; aw++){
            table.addCell("hi");
        }

        return table;
    }

    private Paragraph createHeaderParagraph(){
        Paragraph headerParagraph = new Paragraph();
        Phrase headerPhrase = new Phrase();

        headerPhrase.add(new Chunk("Receipts Report", mTitleFont));
        headerPhrase.add(Chunk.NEWLINE);
        headerPhrase.add(Chunk.NEWLINE);
        headerPhrase.add(new Chunk("Catalog: " + CatalogsActivity.currentCatalog.getName(), mSubTitleFont));

        headerParagraph.add(headerPhrase);

        return headerParagraph;
    }

}
