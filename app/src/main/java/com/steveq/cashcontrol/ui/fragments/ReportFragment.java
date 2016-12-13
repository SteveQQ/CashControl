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
import com.steveq.cashcontrol.database.ReceiptsDataSource;
import com.steveq.cashcontrol.model.Receipt;
import com.steveq.cashcontrol.ui.activities.CatalogsActivity;
import com.steveq.cashcontrol.ui.activities.ReceiptsActivity;
import com.steveq.cashcontrol.utilities.FileUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
        table = fillHeader(table);

        int cols = ReceiptsDataSource.getInstance().countReceipts(CatalogsActivity.currentCatalog.getId());
        ArrayList<Receipt> receipts = ReceiptsDataSource.getInstance().readReceipts(CatalogsActivity.currentCatalog.getId());

        table = fillTable(table, cols, receipts);

        return table;
    }

    private PdfPTable fillTable(PdfPTable table, int cols, ArrayList<Receipt> receipts) {
        for(int i = 0; i < cols; i++){
            Receipt rowReceipt = receipts.get(i);

            PdfPCell nameRec = new PdfPCell(new Phrase(rowReceipt.getName()));
            nameRec.setHorizontalAlignment(Element.ALIGN_CENTER);

            PdfPCell categoryRec = new PdfPCell(new Phrase(rowReceipt.getCategory()));
            categoryRec.setHorizontalAlignment(Element.ALIGN_CENTER);

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy");
            PdfPCell dateDec = new PdfPCell(new Phrase(dateFormat.format(new Date(rowReceipt.getDate()))));
            dateDec.setHorizontalAlignment(Element.ALIGN_CENTER);

            PdfPCell cashRec = new PdfPCell(new Phrase(String.format("%.2f %s",rowReceipt.getPrice(), CatalogsActivity.currentCatalog.getCurrency())));
            cashRec.setHorizontalAlignment(Element.ALIGN_CENTER);

            table.addCell(nameRec);
            table.addCell(categoryRec);
            table.addCell(dateDec);
            table.addCell(cashRec);
        }
        PdfPCell fillerCell = new PdfPCell(new Phrase(""));
        fillerCell.setColspan(2);
        table.addCell(fillerCell);

        PdfPCell totalCell = new PdfPCell(new Phrase("Total: "));
        totalCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(totalCell);

        PdfPCell cashCell = new PdfPCell(new Phrase(String.format("%.2f %s",
                                                    ReceiptsDataSource.getInstance().priceSum(),
                                                    CatalogsActivity.currentCatalog.getCurrency()
                                                    )));
        cashCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(cashCell);

        return table;
    }

    private PdfPTable fillHeader(PdfPTable table) {
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
        return table;
    }

    private Paragraph createHeaderParagraph(){
        Paragraph headerParagraph = new Paragraph();
        Phrase headerPhrase = new Phrase();

        headerPhrase.add(Chunk.NEWLINE);
        headerPhrase.add(Chunk.NEWLINE);
        headerPhrase.add(new Chunk("Receipts Report", mTitleFont));
        headerPhrase.add(Chunk.NEWLINE);
        headerPhrase.add(Chunk.NEWLINE);
        headerPhrase.add(new Chunk("Catalog: " + CatalogsActivity.currentCatalog.getName(), mSubTitleFont));

        headerParagraph.add(headerPhrase);

        return headerParagraph;
    }

}
