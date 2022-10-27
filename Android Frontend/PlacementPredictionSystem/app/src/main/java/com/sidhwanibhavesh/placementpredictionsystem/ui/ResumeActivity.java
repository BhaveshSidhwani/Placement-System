package com.sidhwanibhavesh.placementpredictionsystem.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;
import com.sidhwanibhavesh.placementpredictionsystem.HttpRequestExecutor;
import com.sidhwanibhavesh.placementpredictionsystem.R;

import java.io.InputStream;

// Activity to calculate similarity score of resume
public class ResumeActivity extends AppCompatActivity {

    private static final String LOG_TAG = ResultActivity.class.getSimpleName();

    private final int PICK_RESUME_REQUEST = 1;
    private final int PICK_JD_REQUEST = 2;

    private Uri resumeFilePath;
    private Uri jobDescriptionFilePath;

    private String resumeContent = "";
    private String jDContent = "";

    AppCompatButton btnUploadResume;
    AppCompatButton btnUploadJobDescription;
    AppCompatButton btnCheckResume;
    TextView resumeTextView;
    TextView jobDescriptionTextView;

    ActivityResultLauncher<Intent> resumeResultLauncher;
    ActivityResultLauncher<Intent> jDResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resume);

//      Find UI Components using id
        btnUploadResume = findViewById(R.id.upload_resume_btn);
        btnUploadJobDescription = findViewById(R.id.upload_job_description_btn);
        btnCheckResume = findViewById(R.id.check_resume_btn);
        resumeTextView = findViewById(R.id.resume_filename_tv);
        jobDescriptionTextView = findViewById(R.id.jd_filename_tv);


        resumeResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    // Initialize result data
                    Intent data = result.getData();
                    if (data != null) {
                        // When data is not equal to empty
                        // Get PDF uri
                        resumeFilePath = data.getData();
                        Log.d(LOG_TAG, "resumeFilePath: " + resumeFilePath);
                        Log.d(LOG_TAG, "data.getData: " + data.getData());

                        // Get PDF content
                        resumeContent = extractPDF(resumeFilePath);
                        Log.d(LOG_TAG, "resumeContent: " + resumeContent);

                        try (Cursor cursor = getContentResolver().query(resumeFilePath, null, null, null, null)) {
                            if (cursor != null && cursor.moveToFirst()) {
                                String resumeFileName = cursor.getString(cursor.getColumnIndexOrThrow(OpenableColumns.DISPLAY_NAME));
                                resumeTextView.setText(resumeFileName);
                                Log.d(LOG_TAG, "resumeResultLauncher: resumeFileName: " + resumeFileName);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

        jDResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    // Initialize result data
                    Intent data = result.getData();
                    // check condition
                    if (data != null) {
                        // When data is not equal to empty
                        // Get PDf uri
                        jobDescriptionFilePath = data.getData();
                        Log.d(LOG_TAG, "jDFilePath: " + jobDescriptionFilePath);
                        Log.d(LOG_TAG, "data.getData: " + data.getData());

                        // Get PDF path
                        jDContent = extractPDF(jobDescriptionFilePath);
                        Log.d(LOG_TAG, "jDContent: " + jDContent);

                        try (Cursor cursor = getContentResolver().query(jobDescriptionFilePath, null, null, null, null)) {
                            if (cursor != null && cursor.moveToFirst()) {
                                String jobDescriptionFileName = cursor.getString(cursor.getColumnIndexOrThrow(OpenableColumns.DISPLAY_NAME));
                                jobDescriptionTextView.setText(jobDescriptionFileName);
                                Log.d(LOG_TAG, "resumeResultLauncher: jobDescriptionFileName: " + jobDescriptionFileName);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

        btnUploadResume.setOnClickListener(v -> {
            // check condition
            if (ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                // When permission is not granted
                // Result permission
                ActivityCompat.requestPermissions(this,
                        new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},
                        PICK_RESUME_REQUEST);
            }
            else {
                // When permission is granted
                // Create method
                selectPDF(PICK_RESUME_REQUEST);
            }
        });

        btnUploadJobDescription.setOnClickListener(v -> {
            // check condition
            if (ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                // When permission is not granted
                // Result permission
                ActivityCompat.requestPermissions(this,
                        new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},
                        PICK_JD_REQUEST);
            }
            else {
                // When permission is granted
                // Create method
                selectPDF(PICK_JD_REQUEST);
            }
        });

        btnCheckResume.setOnClickListener(view -> {
            if (resumeContent.equals("") && jDContent.equals("")) {
                Toast.makeText(this, "Please upload both files", Toast.LENGTH_SHORT).show();
            } else {
                HttpRequestExecutor.checkResume(this, resumeContent, jDContent);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            @NonNull String[] permissions,
            @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // check condition
        if ((requestCode == PICK_RESUME_REQUEST || requestCode == PICK_JD_REQUEST)
                && grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // When permission is granted
            // Call method
            selectPDF(requestCode);
        }
        else {
            // When permission is denied
            // Display toast
            Toast.makeText(getApplicationContext(),"Permission Denied", Toast.LENGTH_SHORT).show();
        }
    }

    private void selectPDF(int pickRequest) {
        // Initialize intent
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        // set type
        intent.setType("application/pdf");
        // Launch intent
        if (pickRequest == PICK_RESUME_REQUEST) {
            resumeResultLauncher.launch(intent);
        }
        else if (pickRequest == PICK_JD_REQUEST) {
            jDResultLauncher.launch(intent);
        }
    }

    private String extractPDF(Uri pdfUri) {
        try {
            // creating a string for
            // storing our extracted text.
            StringBuilder extractedText = new StringBuilder();

            // creating a variable for pdf reader
            // and passing our PDF file in it.
            Log.d(LOG_TAG, "extractPdf: pdfUri: " + pdfUri);
            InputStream inputStream = getContentResolver().openInputStream(pdfUri);
            PdfDocument reader = new PdfDocument(new PdfReader(inputStream));

            // below line is for getting number
            // of pages of PDF file.
            int n = reader.getNumberOfPages();

            // running a for loop to get the data from PDF
            // we are storing that data inside our string.
            for (int i = 0; i < n; i++) {
                Log.d(LOG_TAG, "extractPDF: i+1: " + i);
                Log.d(LOG_TAG, "extractPDF: getTextFromPage: " + PdfTextExtractor.getTextFromPage(reader.getPage(i+1)));
                extractedText.append(PdfTextExtractor.getTextFromPage(reader.getPage(i+1))).append("\n");
                // to extract the PDF content from the different pages
            }

            // below line is used for closing reader.
            reader.close();

            // after extracting all the data we are
            // setting that string value to our text view.
            return extractedText.toString();

        } catch (Exception e) {
            // for handling error while extracting the text file.
            e.printStackTrace();
        }
        return null;
    }
}