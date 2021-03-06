package com.chikeandroid.debtmanager.features.iowe;

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.chikeandroid.debtmanager.DebtManagerApplication;
import com.chikeandroid.debtmanager.R;
import com.chikeandroid.debtmanager.data.Debt;
import com.chikeandroid.debtmanager.features.home.MainActivity;
import com.chikeandroid.debtmanager.util.RecyclerViewItemCountAssertion;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.longClick;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.chikeandroid.debtmanager.util.AndroidTestUtil.createDebt;
import static com.chikeandroid.debtmanager.util.AndroidTestUtil.verifyDebtDetailUiViews;
import static com.chikeandroid.debtmanager.util.TestUtil.AMOUNT1;
import static com.chikeandroid.debtmanager.util.TestUtil.AMOUNT2;
import static com.chikeandroid.debtmanager.util.TestUtil.AMOUNT3;
import static com.chikeandroid.debtmanager.util.TestUtil.NAME1;
import static com.chikeandroid.debtmanager.util.TestUtil.NAME2;
import static com.chikeandroid.debtmanager.util.TestUtil.NAME3;
import static com.chikeandroid.debtmanager.util.TestUtil.NOTE1;
import static com.chikeandroid.debtmanager.util.TestUtil.NOTE2;
import static com.chikeandroid.debtmanager.util.TestUtil.NOTE3;
import static com.chikeandroid.debtmanager.util.TestUtil.PHONE_NUMBER1;
import static com.chikeandroid.debtmanager.util.TestUtil.PHONE_NUMBER2;
import static com.chikeandroid.debtmanager.util.TestUtil.PHONE_NUMBER3;

@RunWith(AndroidJUnit4.class)
public class IOweScreenTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule =
            new ActivityTestRule<MainActivity>(MainActivity.class) {

               
                @Override
                protected void beforeActivityLaunched() {
                    super.beforeActivityLaunched();

                    ((DebtManagerApplication) InstrumentationRegistry.getTargetContext()
                            .getApplicationContext()).getComponent()
                            .getDebtsRepository().deleteAllPersonDebtsByType(Debt.DEBT_TYPE_IOWE);
                }};

    @Before
    public void registerIdlingResource() {
        Espresso.registerIdlingResources(mActivityTestRule.getActivity().getCountingIdlingResource());
    }

    @Test
    public void shouldOpenAddDebtUiWhenAddDebtFabButtonIsClicked() {

        onView(withId(R.id.fab_main)).perform(click());

        onView(withId(R.id.et_comment)).check(matches(isDisplayed()));
    }

    @Test
    public void shouldBeAbleToAddANewDebtToIOweList() {

        createDebt(NAME1, PHONE_NUMBER1, AMOUNT1, NOTE1, Debt.DEBT_TYPE_IOWE);

        // onView(ViewMatchers.withId(R.id.rv_oweme)).perform(RecyclerViewActions.scrollToHolder(withTitle("Chike Mgbemena")));

        onView(withText(NAME1)).check(matches(isDisplayed()));

        // Click on the RecyclerView item at position 2
        // onView(withId(R.id.rv_oweme)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

    }

    @Test
    public void shouldOpenDebtDetailUiWhenAListIsClicked() {

        createDebt(NAME1, PHONE_NUMBER1, AMOUNT1, NOTE1, Debt.DEBT_TYPE_IOWE);

        onView(withText(NAME1)).perform(click());

        onView(withText(NAME1)).check(matches(isDisplayed()));

        verifyDebtDetailUiViews();
    }

    @Test
    public void shouldBeAbleToSelectAndDeleteMultipleDebtsListItemOnLongClick() {

        createDebt(NAME1, PHONE_NUMBER1, AMOUNT1, NOTE1, Debt.DEBT_TYPE_IOWE);

        createDebt(NAME2, PHONE_NUMBER2, AMOUNT2, NOTE2, Debt.DEBT_TYPE_IOWE);

        createDebt(NAME3, PHONE_NUMBER3, AMOUNT3, NOTE3, Debt.DEBT_TYPE_IOWE);

        onView(withText(NAME1)).perform(longClick());

        onView(withText(NAME2)).perform(click());

        onView(withText(NAME3)).perform(click());

        // action mode delete
        onView(withId(R.id.action_delete)).perform(click());

        // confirm dialog
        onView(withId(android.R.id.message)).check(matches(isDisplayed()));

        onView(withId(android.R.id.button1)).perform(click());

        onView(withText(NAME1)).check(doesNotExist());
        onView(withText(NAME2)).check(doesNotExist());
        onView(withText(NAME3)).check(doesNotExist());
    }

    @Test
    public void shouldNotShowActionModeWhenViewPagerIsSwiped() {

        createDebt(NAME1, PHONE_NUMBER1, AMOUNT1, NOTE1, Debt.DEBT_TYPE_IOWE);

        onView(withText(NAME1)).perform(longClick());

        onView(withId(R.id.action_delete)).check(matches(isDisplayed()));

        onView(withId(R.id.view_pager_main)).perform(swipeLeft());

        //onView(withText("Selected")).check(matches(not(isDisplayed())));
        onView(withId(R.id.action_delete)).check(doesNotExist());
    }

    @Test
    public void shouldBeAbleToDeleteDebtOnDetailScreenAndThenNotShowInList() {

        createDebt(NAME1, PHONE_NUMBER1, AMOUNT1, NOTE1, Debt.DEBT_TYPE_IOWE);

        createDebt(NAME2, PHONE_NUMBER2, AMOUNT2, NOTE2, Debt.DEBT_TYPE_IOWE);

        createDebt(NAME3, PHONE_NUMBER2, AMOUNT3, NOTE3, Debt.DEBT_TYPE_IOWE);

        onView(withText(NAME1)).perform(click());

        onView(withId(R.id.action_delete)).perform(click());

        // confirm dialog
        onView(withId(android.R.id.message)).check(matches(isDisplayed()));

        onView(withId(android.R.id.button1)).perform(click());

        onView(withText(NAME1)).check(doesNotExist());

        onView(withId(R.id.rv_iowe)).check(new RecyclerViewItemCountAssertion(2));
    }

    @After
    public void unregisterIdlingResource() {
        Espresso.unregisterIdlingResources(mActivityTestRule.getActivity().getCountingIdlingResource());
    }
}
