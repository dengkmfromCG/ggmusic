package com.gdut.dkmfromcg.ggmusic.ui.fragment.localmusic;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gdut.dkmfromcg.ggmusic.R;
import com.gdut.dkmfromcg.ggmusic.adapter.ListMultipleAdapter;
import com.gdut.dkmfromcg.ggmusic.util.MusicLoader;
import com.gdut.dkmfromcg.ggmusic.widget.PinnedHeaderDecoration;
import com.gdut.dkmfromcg.ggmusic.widget.WaveSideBarView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListMultipleFragment extends Fragment {

    private XRecyclerView mRecyclerView;
    private WaveSideBarView waveSideBarView;
    private ListMultipleAdapter adapter;


    public ListMultipleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mView = inflater.inflate(R.layout.fragment_list_multiple, container, false);
        mRecyclerView= mView.findViewById(R.id.xrecv_local);
        waveSideBarView= mView.findViewById(R.id.side_view);
        initView();
        return mView;
    }

    private void initView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        final PinnedHeaderDecoration decoration = new PinnedHeaderDecoration();
        decoration.registerTypePinnedHeader(1, new PinnedHeaderDecoration.PinnedHeaderCreator() {
            @Override
            public boolean create(RecyclerView parent, int adapterPosition) {
                return true;
            }
        });
        mRecyclerView.addItemDecoration(decoration);
        adapter=new ListMultipleAdapter(getActivity(), MusicLoader.INSTANCE.getMusicList());
        mRecyclerView.setAdapter(adapter);
        waveSideBarView.setOnTouchLetterChangeListener(new WaveSideBarView.OnTouchLetterChangeListener() {
            @Override
            public void onLetterChange(String letter) {
                int pos = adapter.getLetterPosition(letter);

                if (pos != -1) {
                    mRecyclerView.scrollToPosition(pos);
                    LinearLayoutManager mLayoutManager =
                            (LinearLayoutManager) mRecyclerView.getLayoutManager();
                    mLayoutManager.scrollToPositionWithOffset(pos, 0);
                }
            }
        });



    }



}
