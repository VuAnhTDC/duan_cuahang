package com.example.duancuahang.Class;

public class Voucher {
   private String idVoucher;
   private int maxCountUser;
   private String codeVoucher;
   private String idItemPercentDiscount;
   private String idProduct;

    public String getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(String idProduct) {
        this.idProduct = idProduct;
    }

    public Voucher() {
    }

    public Voucher(String idVoucher, int maxCountUser, String codeVoucher, String idItemPercentDiscount, String idProduct) {
        this.idVoucher = idVoucher;
        this.maxCountUser = maxCountUser;
        this.codeVoucher = codeVoucher;
        this.idItemPercentDiscount = idItemPercentDiscount;
        this.idProduct = idProduct;

    }

    public String getIdItemPercentDiscount() {
        return idItemPercentDiscount;
    }

    public void setIdItemPercentDiscount(String idItemPercentDiscount) {
        this.idItemPercentDiscount = idItemPercentDiscount;
    }

    public int getMaxCountUser() {
        return maxCountUser;
    }

    public void setMaxCountUser(int maxCountUser) {
        this.maxCountUser = maxCountUser;
    }

    public String getCodeVoucher() {
        return codeVoucher;
    }

    public void setCodeVoucher(String codeVoucher) {
        this.codeVoucher = codeVoucher;
    }


    public String getIdVoucher() {
        return idVoucher;
    }

    public void setIdVoucher(String idVoucher) {
        this.idVoucher = idVoucher;
    }

}