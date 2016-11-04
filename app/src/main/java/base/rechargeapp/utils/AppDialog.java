package base.rechargeapp.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import base.rechargeapp.R;
import base.rechargeapp.appInterface.DialogInterface;

/**
 * Class is Used to show a dialog common for Application
 * You will get the yes , no callback in your calling class
 * <p>
 * Created by Vaibhav Jain  on 29/6/16.
 * Organization LPTPL
 */

public class AppDialog {

    /**
     * Method to show common dialog for complete Application
     *
     * @param context            -- Context
     * @param Id                 -- Unique Dialog Id
     * @param title              -- Dialog title
     * @param description        -- Dialog Description
     * @param yesbuttonTxt       -- Text to be printed on Yes Button
     * @param noButtonText       -- Text to be printed on No Button
     * @param displayTitle       -- Either display title or not
     * @param cancelTouchOutside -- Either cancel dialog or not on touching outside
     * @param dialogInterface    -- Instance of dialogInterface
     */
    public static void showDialog(Context context, final int Id, String title, String description, String yesbuttonTxt,
                                  String noButtonText, boolean displayTitle, boolean cancelTouchOutside, final DialogInterface dialogInterface) {

        TextView tvtitle, tvdescription, btn_yes, btn_no;

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_layout);

        tvtitle = (TextView) dialog.findViewById(R.id.txt_title);
        tvdescription = (TextView) dialog.findViewById(R.id.txt_description);
        btn_yes = (TextView) dialog.findViewById(R.id.txt_yes);
        btn_no = (TextView) dialog.findViewById(R.id.txt_no);

        tvtitle.setVisibility(displayTitle ? View.VISIBLE : View.GONE);

        tvtitle.setText(title);
        tvdescription.setText(description);
        btn_yes.setText(yesbuttonTxt);
        btn_no.setText(noButtonText);

        dialog.setCanceledOnTouchOutside(cancelTouchOutside);
        dialog.setTitle(title);
        dialog.show();

        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogInterface.clickResponse(Id, AppConstant.dialogYes);
                dialog.dismiss();
            }
        });

        btn_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogInterface.clickResponse(Id, AppConstant.dialogNo);
                dialog.dismiss();
            }
        });
    }

    public static void showDialog(final int Id, String title, String description, String yesbuttonTxt,
                                  String noButtonText, boolean displayTitle, boolean cancelTouchOutside, final DialogInterface dialogInterface) {
        {
            dialogInterface.clickResponse(Id, AppConstant.dialogYes);
        }
    }

}
