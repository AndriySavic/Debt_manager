package com.chikeandroid.debtmanager;

import android.content.Context;

import com.chikeandroid.debtmanager.data.source.PersonDebtsRepository;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;

/**
 * Created by Chike on 12/12/2019.
 */

@Singleton
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent {

  
    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder context(Context context);
        ApplicationComponent build();
    }

    PersonDebtsRepository getDebtsRepository();

    Context getContext();

}
