package com.zq.poem.view.fragment;

import android.graphics.Typeface;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import com.zq.poem.R;
import com.zq.poem.model.MainViewModel;
import com.zq.poem.model.ModelApi;
import com.zq.poem.util.CharacterUtil;
import com.zq.poem.view.PoemDisplayView;

import java.util.List;

/**
 * Created by SGXM on 2019/11/21.
 */

/**
 * @Description: the forth window
 * @Author: SGXM
 * @Date: 2019/11/19
 */
public class AcrosticFragment extends LazyFragment {
    private boolean flag = false;
    private String hint;
    /**
     * main view to show verses
     */
    private PoemDisplayView poemDisplayView = null;

    /**
     * write and pls input seed
     */
    private TextView generate, title;

    /**
     * input text
     */
    private EditText editText;

    /**
     * get you layout information
     *
     * @return Layout file id
     */
    @Override
    public int getResId() {
        return R.layout.fragment_hint;
    }

    /**
     * Initialize method
     *
     * @param view
     */
    @Override
    public void onRealViewLoaded(View view) {
        generate = view.findViewById(R.id.tv_generate);
        title = view.findViewById(R.id.tv_title);
        editText = view.findViewById(R.id.et_hint);

        generate.setTypeface(Typeface.createFromAsset(this.getContext().getAssets(), "FZCSJW.TTF"));
        title.setTypeface(Typeface.createFromAsset(this.getContext().getAssets(), "FZCSJW.TTF"));
        editText.setTypeface(Typeface.createFromAsset(this.getContext().getAssets(), "FZCSJW.TTF"));

        poemDisplayView = view.findViewById(R.id.poemdp_main);

        if (flag) {
            flag = !flag;
            editText.setText(hint);
            {
                if (!CharacterUtil.checkCharacter(editText.getText().toString()))
                    Toast.makeText(AcrosticFragment.this.getContext(), "请输入中文", Toast.LENGTH_SHORT).show();
                else if (editText.toString().length() < 4)
                    Toast.makeText(AcrosticFragment.this.getContext(), "种子不能少于四个字", Toast.LENGTH_SHORT).show();
                else
                    MainViewModel.getInstance().write("/poem", ModelApi.TYPE.ACROSTIC, editText.getText().toString(), 0);
            }
        }
        generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!CharacterUtil.checkCharacter(editText.getText().toString()))
                    Toast.makeText(AcrosticFragment.this.getContext(), "请输入中文", Toast.LENGTH_SHORT).show();
                else if (editText.getText().length() < 4)
                    Toast.makeText(AcrosticFragment.this.getContext(), "种子不能少于四个字", Toast.LENGTH_SHORT).show();
                else
                    MainViewModel.getInstance().write("/poem", ModelApi.TYPE.ACROSTIC, editText.getText().toString(), 0);
            }
        });

        MainViewModel.getInstance().getArcrosticData().observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(@Nullable List<String> strings) {
                poemDisplayView.setVerses(strings);
            }
        });

    }

    public void setHint(String hint) {
        Log.e("woggle", hint);
        if (editText != null) {
            editText.setText(hint);
            {
                if (!CharacterUtil.checkCharacter(editText.getText().toString()))
                    Toast.makeText(AcrosticFragment.this.getContext(), "请输入中文", Toast.LENGTH_SHORT).show();
                else if (editText.getText().length() < 4)
                    Toast.makeText(AcrosticFragment.this.getContext(), "种子不能少于四个字", Toast.LENGTH_SHORT).show();
                else
                    MainViewModel.getInstance().write("/poem", ModelApi.TYPE.ACROSTIC, editText.getText().toString(), 0);
            }
        } else {
            flag = true;
            this.hint = hint;
        }
    }
}
