package com.company.stores;

import java.util.UUID;

public class Voucher {
    private final UUID id = UUID.randomUUID();
    private String voucherType;
    private String mesaje;
    private double value;

    public Voucher(String voucherType, String mesaje, double value) {
        this.voucherType = voucherType;
        this.mesaje = mesaje;
        this.value = value;
    }

    public UUID getId() {
        return id;
    }

    public String getVoucherType() {
        return voucherType;
    }

    public Voucher setVoucherType(String voucherType) {
        this.voucherType = voucherType;
        return this;
    }

    public String getMesaje() {
        return mesaje;
    }

    public Voucher setMesaje(String mesaje) {
        this.mesaje = mesaje;
        return this;
    }

    public double getValue() {
        return value;
    }

    public Voucher setValue(double value) {
        this.value = value;
        return this;
    }
}
