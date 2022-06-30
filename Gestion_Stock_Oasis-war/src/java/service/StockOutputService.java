/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entities.Lignelivraisonclient;
import entities.Livraisonclient;

/**
 *
 * @author kenne
 */
public class StockOutputService {

    public StockOutputService() {

    }

    public void compute_marge_benefit(Livraisonclient livraisonclient) {
        livraisonclient.setMarge(0);
        livraisonclient.setBenefice(0);
        if (!livraisonclient.getLignelivraisonclientList().isEmpty()) {
            livraisonclient.getLignelivraisonclientList().forEach(item -> {
                item.compute_benef_marge();
                livraisonclient.setMarge(livraisonclient.getMarge() + item.getMarge());
                livraisonclient.setBenefice(livraisonclient.getBenefice() + item.getBenefice());
            });
        }
    }

    public void computeTotalHt(Livraisonclient livraisonclient) {
        double value = livraisonclient.getLignelivraisonclientList().stream().mapToDouble(Lignelivraisonclient::getMontant).sum();
        livraisonclient.setMontant(value);
    }

    public void computeTotals(Livraisonclient livraisonclient) {
        try {

            compute_marge_benefit(livraisonclient);
            computeTotalHt(livraisonclient);

            livraisonclient.setMontantRemise((livraisonclient.getMontant() * livraisonclient.getTauxRemise()) / 100);

            livraisonclient.setMontantHt(livraisonclient.getMontant() - livraisonclient.getMontantRemise());

            livraisonclient.setMontantTva((livraisonclient.getMontantHt() * livraisonclient.getTauxTva()) / 100);
            livraisonclient.setMontantTtc(livraisonclient.getMontantTva() + livraisonclient.getMontantHt());
            //this.livraisonclient.setMarge(livraisonclient.getMarge() - ((livraisonclient.getMarge() * livraisonclient.getTauxRemise()) / 100));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
