package com.zq.poem.view.fragment;

import android.graphics.Typeface;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import com.zq.poem.R;
import com.zq.poem.model.MainViewModel;
import com.zq.poem.model.ModelApi;
import com.zq.poem.view.PoemDisplayView;


import java.util.List;

/**
 * Created by SGXM on 2019/11/21.
 */

public class RhymeFragment extends LazyFragment {

    /** main view to show verses */
    private PoemDisplayView poemDisplayView = null;

    /** write */
    private TextView generate;

    /**
     * get you layout information
     *
     * @return Layout file id
     */
    @Override
    public int getResId() {
        return R.layout.fragment_nohint;
    }

    /**
     * Initialize method
     *
     * @param view
     */
    @Override
    public void onRealViewLoaded(View view) {
        generate = view.findViewById(R.id.tv_generate);
        generate.setTypeface(Typeface.createFromAsset(this.getContext().getAssets(), "FZCSJW.TTF"));
        poemDisplayView = view.findViewById(R.id.poemdp_main);
        generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainViewModel.getInstance().write("/poem", ModelApi.TYPE.RHYME, null, 0);
            }
        });

        MainViewModel.getInstance().getRhymeData().observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(@Nullable List<String> strings) {
                poemDisplayView.setVerses(strings);
            }
        });

    }
}
