package com.jam01.littlelight.adapter.android.rxlegacy.domain.service;

import android.content.Context;

import com.bungie.netplatform.Character;
import com.jam01.littlelight.adapter.android.rxlegacy.data.repository.AccountRepository;
import com.jam01.littlelight.adapter.android.rxlegacy.data.repository.CredentialsRepository;
import com.jam01.littlelight.adapter.android.rxlegacy.domain.model.mapper.CharacterMapper;

import java.util.List;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by jam01 on 4/28/16.
 */
public class LegendService {
    private AccountRepository accountRepository;
    private CredentialsRepository credentialsRepository;
    private String TAG = getClass().getSimpleName();
    private static LegendService mInstance;

    private LegendService(Context applicationContext) {
        credentialsRepository = CredentialsRepository.getInstance(applicationContext);
        accountRepository = AccountRepository.getInstance();
    }

    //Singleton magic
    public static synchronized LegendService getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new LegendService(context);
        }
        return mInstance;
    }

    public Observable<List<com.jam01.littlelight.adapter.android.rxlegacy.domain.model.Character>> getCharacters() {
        return accountRepository.getAllCharacters(credentialsRepository.getMembershipType(), credentialsRepository.getMembershipId(), 3600000)
                .first()
                .map(new Func1<List<Character>, List<com.jam01.littlelight.adapter.android.rxlegacy.domain.model.Character>>() {
                    @Override
                    public List<com.jam01.littlelight.adapter.android.rxlegacy.domain.model.Character> call(List<Character> characterEntities) {
                        return CharacterMapper.transform(characterEntities);
                    }
                });
    }

    public Observable<com.jam01.littlelight.adapter.android.rxlegacy.domain.model.Character> getLegendById(final String charId) {
        return accountRepository.getCharacterById(credentialsRepository.getMembershipType(), credentialsRepository.getMembershipId(), charId, 5000)
                .map(new Func1<Character, com.jam01.littlelight.adapter.android.rxlegacy.domain.model.Character>() {
                    @Override
                    public com.jam01.littlelight.adapter.android.rxlegacy.domain.model.Character call(Character character) {
                        return CharacterMapper.transform(character);
                    }
                });
    }

    public void refreshData() {
        accountRepository.refreshData();
    }
}
