package com.chikeandroid.debtmanager.features.addeditdebt;

import com.chikeandroid.debtmanager.data.Debt;
import com.chikeandroid.debtmanager.data.Person;
import com.chikeandroid.debtmanager.data.PersonDebt;
import com.chikeandroid.debtmanager.data.source.PersonDebtsRepository;
import com.chikeandroid.debtmanager.util.TestUtil;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Chike on 12/12/2019.
 * Unit tests for the implementation of {@link AddEditDebtPresenter}.
 */

public class AddEditDebtPresenterTest {

    @Mock
    private PersonDebtsRepository mDebtsRepository;

    @Mock
    private AddEditDebtContract.View mAddDebtView;

    private AddEditDebtPresenter mAddEditDebtPresenter;

    @Before
    public void setUp() {

        MockitoAnnotations.initMocks(this);

        // The presenter wont't update the view unless it's active.
        when(mAddDebtView.isActive()).thenReturn(true);

        mAddEditDebtPresenter = new AddEditDebtPresenter(mDebtsRepository, mAddDebtView, false);
    }

    @Test
    public void shouldSaveDebtToRepository() {

        Person person1 = TestUtil.createPerson("Chike Mgbemena", "07038111534");
        Debt debt1 = TestUtil.createDebt(person1.getPhoneNumber(), 60000, Debt.DEBT_TYPE_IOWE,
                Debt.DEBT_STATUS_ACTIVE, "note 4345");

        mAddEditDebtPresenter.saveDebt(person1, debt1);

        verify(mDebtsRepository).savePersonDebt(any(Debt.class), any(Person.class));
        verify(mAddDebtView).showDebts();
    }

    @Test
    public void shouldShowEmptyDebtErrorUiWhenDebtSaved() {

        Person person1 = TestUtil.createPerson("", "");
        Debt debt1 = TestUtil.createDebt("", 0, Debt.DEBT_TYPE_OWED,
                Debt.DEBT_STATUS_ACTIVE, "");

        mAddEditDebtPresenter.saveDebt(person1, debt1);

        verify(mAddDebtView).showEmptyDebtError();
    }

    @Test
    public void shouldUpdateDebtInRepository() {

        mAddEditDebtPresenter = new AddEditDebtPresenter(mDebtsRepository, mAddDebtView, true);

        Person person1 = TestUtil.createPerson("Mary Jane", "08023021782");
        Debt debt1 = TestUtil.createDebt(person1.getPhoneNumber(), 60000, Debt.DEBT_TYPE_IOWE,
                Debt.DEBT_STATUS_ACTIVE, "note 4345");

        mAddEditDebtPresenter.saveDebt(person1, debt1);

        verify(mDebtsRepository).updatePersonDebt(any(PersonDebt.class));
        verify(mAddDebtView).showDebts();
    }
}
