package com.govt.updms.util;


import com.govt.updms.eventbus.Events;
import com.govt.updms.eventbus.GlobalBus;
import com.govt.updms.rest.Response.AddressList;

public class EventBroadcastHelper {



    public static void sendAddressUpdate(AddressList addressList) {
        GlobalBus.getBus().post(new Events.AddressUpdate(addressList));
    }
    public static void sendPaymentUpdate(int bookingid, String status) {
        GlobalBus.getBus().post(new Events.paymentUpdated(bookingid,status));
    }

    public static  void  sendPaymentStatus(){
        GlobalBus.getBus().post(new Events.paymentUpdateRsa());
    }
}
