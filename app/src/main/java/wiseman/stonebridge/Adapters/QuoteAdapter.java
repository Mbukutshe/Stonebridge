package wiseman.stonebridge.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

import wiseman.stonebridge.Holders.QuoteHolder;
import wiseman.stonebridge.Objects.QuoteObject;
import wiseman.stonebridge.R;

/**
 * Created by Wiseman on 2018-02-26.
 */

public class QuoteAdapter extends RecyclerView.Adapter<QuoteHolder> implements View.OnClickListener {
    List<QuoteObject> list;
    EditText description, quantity, price;
    LinearLayout add, send;
    Context context;
    TextView total;

    public QuoteAdapter(Context context, List<QuoteObject> list, EditText description, EditText quantity, EditText price, LinearLayout add, LinearLayout send, TextView total) {
        this.context = context;
        this.list = list;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
        this.add = add;
        this.send = send;
        this.total = total;
        add.setOnClickListener(this);
        send.setOnClickListener(this);
    }

    @Override
    public QuoteHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.quote_item, parent, false);
        QuoteHolder holder = new QuoteHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(QuoteHolder holder, int position) {

        if (position % 2 == 0) {
            holder.itemView.setBackgroundColor(Color.parseColor("#EDEDED"));
        }
        if (!list.get(position).message.equalsIgnoreCase("")) {
            holder.description.setText(list.get(position).time.substring(1));
            holder.quantity.setText(list.get(position).message);
            holder.price.setText("R " + list.get(position).who);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_quote:
                Toast.makeText(context, description.getText().toString(), Toast.LENGTH_SHORT).show();
                list.add(new QuoteObject(description.getText().toString(), quantity.getText().toString(), price.getText().toString()));
                notifyDataSetChanged();
                double prices;
                double qty;
                double sum = 0.0;
                for (int i = 1; i < list.size(); i++) {
                    prices = Double.parseDouble(list.get(i).who.toString());
                    qty = Double.parseDouble(list.get(i).message.toString());
                    sum += (prices * qty);
                }
                total.setText(": R" + sum);
                break;
            case R.id.send_button:
                createPDF();
                String filename="quotation.pdf";
                File filelocation = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), filename);
                Uri path = Uri.fromFile(filelocation);
                String[] TO = {"kmbukutshe@gmail.com.com"};
                String[] CC = {"xyz@gmail.com"};
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setData(Uri.parse("mailto:"));
                emailIntent.setType("text/html");
                emailIntent .putExtra(Intent.EXTRA_STREAM, path);
                emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
                emailIntent.putExtra(Intent.EXTRA_CC, CC);
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Quotation");
                emailIntent.putExtra(Intent.EXTRA_TEXT, Html.fromHtml("" +
                        "<html><img src='http://mydm.co.za/ContainerConsumables/scripts/images/motorlogo.png' with=100 height=100/><font color='#0000ff'>Email text Go's here</font></html>"));
                try {
                    context.startActivity(Intent.createChooser(emailIntent, "Send mail..."));
                    Log.i("Finished...", "");
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(context, "There is no email client installed.", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    public void createPDF() {
        //create document object
        final Document doc = new Document();
        String outpath = Environment.getExternalStorageDirectory() + "/quotation.pdf";
        try {
            //create pdf writer instance
            PdfWriter.getInstance(doc, new FileOutputStream(outpath));
            //open the document for writing
            doc.open();

            //adding image
            Drawable d = context.getResources().getDrawable(R.drawable.motorlogo);

            BitmapDrawable bitDw = ((BitmapDrawable) d);

            Bitmap bmp = bitDw.getBitmap();

            ByteArrayOutputStream stream = new ByteArrayOutputStream();

            bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);

            Image image = null;
            try
            {
                image = Image.getInstance(stream.toByteArray());
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            doc.add(image);
            doc.add(new Paragraph("Description"+"            "+"Quantity"+"                 "+"Price"));

            for (int i=0; i< list.size(); i++) {
                doc.add(new Paragraph("\n"+list.get(i).time+"         "+list.get(i).message+"           R"+list.get(i).who));
                Toast.makeText(context,"Quotation Generated",Toast.LENGTH_LONG).show();
            }
            doc.add(new Paragraph("\n"+"         Total"+"     "+total.getText().toString()));
            doc.close();
        }
        catch (FileNotFoundException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (DocumentException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}