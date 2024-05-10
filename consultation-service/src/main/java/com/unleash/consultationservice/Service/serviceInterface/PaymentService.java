package com.unleash.consultationservice.Service.serviceInterface;

import com.unleash.consultationservice.DTO.CreateContactDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.util.List;

public interface PaymentService {


    ResponseEntity<?> createContact(CreateContactDto dto) throws IOException;



    ResponseEntity<?> getLastWeekPayments(int pageNo);

    ResponseEntity<?> procesPayment(int id) throws IOException;

    ResponseEntity<?> getMyTransactions(int userId);



    ResponseEntity<?> getAllTransactionsPage(int page);

    ResponseEntity<?> processPaymentOfSelected(List<Integer> selectedIds) throws IOException;
}
