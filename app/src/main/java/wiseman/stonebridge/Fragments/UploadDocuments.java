package wiseman.stonebridge.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import java.util.Date;

import wiseman.stonebridge.Globals.Globals;
import wiseman.stonebridge.R;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Wiseman on 2018-01-31.
 */

public class UploadDocuments extends Fragment implements View.OnClickListener{
    View view;
    LinearLayout choose;
    EditText description,subject;
    LinearLayout upload;
    TextView icon;
    String desc,subj;
    Uri filePath;
    public static final int PICK_PDF_REQUEST =1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.send_document, container, false);
        choose = (LinearLayout)view.findViewById(R.id.file_choose);
        description = (EditText)view.findViewById(R.id.text_description);
        subject= (EditText)view.findViewById(R.id.text_subject);
        icon = (TextView)view.findViewById(R.id.choose_icon);
        upload = (LinearLayout)view.findViewById(R.id.upload_button);
        upload.setOnClickListener(this);
        choose.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch(view.getId())
        {
            case R.id.upload_button:
                desc = description.getText().toString();
                subj = subject.getText().toString();
                upload(filePath.getPath().toString(),desc,subj);
            break;
            case R.id.file_choose:
                fileChoose();
            break;
        }
    }
    public void fileChoose()
    {
        Intent intent = new Intent();
        intent.setType("application/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(Intent.createChooser(intent, "Choose a document"), PICK_PDF_REQUEST);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode == PICK_PDF_REQUEST && resultCode == RESULT_OK && data!=null && data.getData()!=null) {
           filePath = data.getData();
        }
        icon.setBackgroundResource(R.drawable.successfile);
    }
    public void upload(String file, String desc, String subject) {
        Date dt = new Date();
        int hours = dt.getHours();
        int minutes = dt.getMinutes();
        String time = hours + ":" + minutes;
        try {

                new MultipartUploadRequest(view.getContext(), Globals.INSERT_POST_URL)
                        .addFileToUpload(file, "uploaded_file")
                        .addParameter("subject", subject)
                        .addParameter("message", desc)
                        .addParameter("attachment", "yes")
                        .addParameter("type", "document")
                        .addParameter("urgent", "no")
                        .addParameter("username", "Wiseman")
                        .addParameter("time", time)
                        .setMethod("POST")
                        .setNotificationConfig(new UploadNotificationConfig())
                        .setMaxRetries(1)
                        .startUpload();
        } catch (Exception ex) {
            Toast.makeText(view.getContext(), ex.getMessage().toString(), Toast.LENGTH_LONG).show();
        }
    }
}
