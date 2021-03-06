package com.chikeandroid.debtmanager.features.people;

import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;

import com.chikeandroid.debtmanager.data.Person;

import java.util.List;

/**
 * Created by Chike on 12/12/2019.
 * DiffCallback for People RecyclerView
 */
public class PeopleDiffCallback extends DiffUtil.Callback {

    private final List<Person> mOldPersonList;
    private final List<Person> mNewPersonList;

    public PeopleDiffCallback(List<Person> oldPersonList, List<Person> newPersonList) {
        mOldPersonList = oldPersonList;
        mNewPersonList = newPersonList;
    }

    @Override
    public int getOldListSize() {
        return mOldPersonList.size();
    }

    @Override
    public int getNewListSize() {
        return mNewPersonList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return mOldPersonList.get(oldItemPosition).getPhoneNumber().equals(
                mNewPersonList.get(newItemPosition).getPhoneNumber());
    }


    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        final Person person = mOldPersonList.get(oldItemPosition);
        final Person person1 = mNewPersonList.get(newItemPosition);

        return person.equals(person1);
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}
