package com.jam01.littlelight.application;

import com.jam01.littlelight.domain.DomainEvent;
import com.jam01.littlelight.domain.DomainEventPublisher;
import com.jam01.littlelight.domain.identityaccess.AccountId;
import com.jam01.littlelight.domain.identityaccess.User;
import com.jam01.littlelight.domain.legend.DestinyLegendService;
import com.jam01.littlelight.domain.legend.Legend;
import com.jam01.littlelight.domain.legend.LegendRepository;
import com.jam01.littlelight.domain.legend.LegendUpdated;

import io.reactivex.Observable;
import io.reactivex.functions.Predicate;


/**
 * Created by jam01 on 11/1/16.
 */
public class LegendService {
    private LegendRepository legendRepo;
    private DestinyLegendService destinyService;
    private User user;

    public LegendService(DestinyLegendService destinyService, LegendRepository legendRepository, User user) {
        this.legendRepo = legendRepository;
        this.destinyService = destinyService;
        this.user = user;
    }

    public Legend ofAccount(AccountId anAccountId) {
        if (!legendRepo.hasOfAccount(anAccountId)) {
            synchronizeLegendOf(anAccountId);
        }
        return legendRepo.ofId(anAccountId);
    }

    public void synchronizeLegendOf(AccountId anAccountId) {
        destinyService.synchronizeLegendFor(user.ofId(anAccountId), legendRepo);
    }

    public Observable<DomainEvent> subscribeToInventoryEvents(final AccountId subscriberAccountId) {
        return DomainEventPublisher.instanceOf().getEvents()
                .filter(new Predicate<DomainEvent>() {
                    @Override
                    public boolean test(DomainEvent domainEvent) throws Exception {
                        if (domainEvent instanceof LegendUpdated)
                            return ((LegendUpdated) domainEvent).getLegendUpdated().withId().equals(subscriberAccountId);
                        return false;
                    }
                });
    }
}
