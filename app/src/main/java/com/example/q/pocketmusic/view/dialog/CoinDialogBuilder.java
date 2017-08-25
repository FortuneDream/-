package com.example.q.pocketmusic.view.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;

/**
 * Created by 鹏君 on 2017/7/8.
 * （￣m￣）
 */

public class CoinDialogBuilder extends AlertDialog.Builder {
    public CoinDialogBuilder(@NonNull Context context, int coin) {
        super(context);
        setTitle("确认");
        setMessage("需要消耗 " + coin + " 枚硬币");
        setNeutralButton("查看帮助", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent("help_activity");
                getContext().startActivity(intent);
            }
        });
    }

    public CoinDialogBuilder setPositiveButton(DialogInterface.OnClickListener listener) {
        String text = "OK";
        return (CoinDialogBuilder) super.setPositiveButton(text, listener);
    }

    public CoinDialogBuilder setNegativeButton(DialogInterface.OnClickListener listener) {
        String text = "算了";
        return (CoinDialogBuilder) super.setNegativeButton(text, listener);
    }


}
