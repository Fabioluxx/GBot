package com.fabiolux.gbot.dao.controller;

import com.fabiolux.gbot.api.enums.Market;
import com.fabiolux.gbot.api.exchanges.Braziliex;
import com.fabiolux.gbot.dao.models.BraziliexOrderbookHistory;
import com.fabiolux.gbot.dao.models.BraziliexStatus;
import com.fabiolux.gbot.dao.models.BraziliexTradeHistory;

import javax.persistence.TypedQuery;
import java.util.List;

public class BraziliexDaoController extends DaoController<Braziliex> {

    public BraziliexTradeHistory getLatestByTime(Market market){
        TypedQuery<BraziliexTradeHistory> query = getEm().createQuery("SELECT bth FROM BraziliexTradeHistory bth WHERE bth.bthMarket = :market ORDER BY bth.bthTimestamp DESC", BraziliexTradeHistory.class);
        query.setParameter("market", market.getCode());
        query.setMaxResults(1);
        return singleOrNull(query);
    }

    public void persistBraziliexTradeHistory(List<BraziliexTradeHistory> history){
        for(BraziliexTradeHistory h:history)
            getEm().persist(h);
    }

    public BraziliexStatus getStatus(){
        TypedQuery<BraziliexStatus> query = em.createQuery("SELECT s FROM BraziliexStatus s", BraziliexStatus.class);
        return singleOrNull(query);
    }

    public List<BraziliexOrderbookHistory> getActiveOrderbooks(){
        TypedQuery<BraziliexOrderbookHistory> query = em.createQuery("SELECT c FROM BraziliexOrderbookHistory c WHERE c.bohActive = TRUE ", BraziliexOrderbookHistory.class);
        return query.getResultList();
    }

    public void updateStatus(BraziliexStatus status){
        em.merge(status);
    }
}
