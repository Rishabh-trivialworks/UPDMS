package com.example.updms.util;


import com.example.updms.eventbus.Events;
import com.example.updms.eventbus.GlobalBus;
import com.example.updms.rest.Response.AddressList;

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
