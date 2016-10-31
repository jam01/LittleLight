//package com.jam01.littlelight.application;
//
//import com.jam01.littlelight.domain.identityaccess.AccountId;
//import com.jam01.littlelight.domain.legend.DestinyLegendService;
//import com.jam01.littlelight.domain.legend.LegendRepository;
//
///**
// * Created by jam01 on 7/25/16.
// */
//public class DestinyLegendImportService {
//    private LegendRepository accountRepo;
//    private DestinyLegendService destinyService;
//
//    public DestinyLegendImportService(LegendRepository accountRepo, DestinyLegendService destinyService) {
//        this.accountRepo = accountRepo;
//        this.destinyService = destinyService;
//    }
//
//    public void loadAccount(AccountId anAccountId) {
//        accountRepo.save(destinyService.ofId(anAccountId));
//    }
//}