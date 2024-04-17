package com.unleash.consultationservice.Model.Util;

import com.unleash.consultationservice.Model.SessionBooking;

import java.time.LocalDateTime;
import java.util.Comparator;

public class SessionBookingComparator implements Comparator<SessionBooking> {
    @Override
    public int compare(SessionBooking o1, SessionBooking o2) {
        LocalDateTime slot1 = o1.getAvilability().getSlot();
        LocalDateTime slot2 = o2.getAvilability().getSlot();
        return slot1.compareTo(slot2);
    }
}
