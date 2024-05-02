package com.unleash.consultationservice.Service;

import com.razorpay.*;
import com.unleash.consultationservice.DTO.CounselorPaymentProcess;
import com.unleash.consultationservice.DTO.CreateContactDto;
import com.unleash.consultationservice.DTO.UserDto;
import com.unleash.consultationservice.Interface.UserClient;
import com.unleash.consultationservice.Model.CounselorAvilability;
import com.unleash.consultationservice.Model.CounselorFundAccount;
import com.unleash.consultationservice.Repository.CounselorAvailabilityRepo;
import com.unleash.consultationservice.Repository.CounselorFundAccountRepo;
import com.unleash.consultationservice.Service.serviceInterface.PaymentService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentServiceImp implements PaymentService {

    @Value("${razorpay.key_id}")
    private String key;

    @Value("${razorpay.secret_id}")
    private String secret;

    @Autowired
    private CounselorFundAccountRepo fundAccountRepo;

    @Autowired
    private CounselorAvailabilityRepo counselorAvailabilityRepo;



    @Autowired
    private UserClient userClient;


    @Override
    public ResponseEntity<?> createContact(CreateContactDto dto) throws IOException {
        URL url = new URL("https://api.razorpay.com/v1/contacts");
        String responseBody = null;
        // Open a connection
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Authorization", "Basic " + Base64.getEncoder().encodeToString((key + ":" + secret).getBytes()));



        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", dto.getName());
        jsonObject.put("email", dto.getEmail());
        jsonObject.put("contact", dto.getContact());
        jsonObject.put("type", "employee");
        jsonObject.put("reference_id", dto.getReferenceId());

        JSONObject notes = new JSONObject();
        notes.put("notes_key_1", "counselor contact");
        notes.put("notes_key_2", "counselor contact");

        jsonObject.put("notes", notes);

        String jsonInputString = jsonObject.toString();

        conn.setDoOutput(true);
        try(OutputStream os = conn.getOutputStream()) {
            byte[] input = jsonInputString.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                response.append(line);
            }
            responseBody = response.toString();
            System.out.println("Response Body: " + responseBody);
        }
        if(responseBody!=null){
            JSONObject jsonResponse = new JSONObject(responseBody);
            String id = jsonResponse.getString("id");
            try{
                createFundAccount(dto,id);
            }catch (Exception e){
                e.printStackTrace();
                return ResponseEntity.badRequest().build();
            }

        }



        // Close the connection
        conn.disconnect();
        return ResponseEntity.ok().body("created");
    }



    private boolean createFundAccount(CreateContactDto dto , String contactId) throws RazorpayException {


        JSONObject jsonObject = new JSONObject();

        // Set values
        jsonObject.put("contact_id", contactId);
        jsonObject.put("account_type", "bank_account");
        JSONObject bankAccount = new JSONObject();
        bankAccount.put("name", dto.getName());
        bankAccount.put("ifsc", dto.getIfcCode());
        bankAccount.put("account_number", dto.getAccountNo());
        jsonObject.put("bank_account", bankAccount);

        RazorpayClient razorpayClient = new RazorpayClient(key,secret);
        FundAccount account=null;
        try{
             account=  razorpayClient.fundAccount.create(jsonObject);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

        CounselorFundAccount fundAccount = new CounselorFundAccount();
        fundAccount.setContactId(contactId);
        fundAccount.setUserId(dto.getReferenceId());
        fundAccount.setAccountNo(dto.getAccountNo());
        fundAccount.setIfcCode(dto.getIfcCode());
        fundAccount.setFundAccount(account.get("id"));
        fundAccountRepo.save(fundAccount);
        return true;
    }

    public ResponseEntity<?> processPayments() throws RazorpayException {
        RazorpayClient razorpayClient = new RazorpayClient(key,secret);


        return ResponseEntity.ok().build();
    }


    @Override
    public ResponseEntity<?> getAllPendingPayments(){
        List<UserDto> counselors = userClient.findAllCounselors();
        LocalDateTime currentDate = LocalDateTime.now();
        LocalDateTime endOfWeek = currentDate.getDayOfWeek().equals(DayOfWeek.SATURDAY)? currentDate : currentDate.minusDays(currentDate.getDayOfWeek().getValue() - 1);
        LocalDateTime startOfWeek = endOfWeek.minusDays(6);
        List<CounselorPaymentProcess> paymentProcessesList = new ArrayList<>();
        for(UserDto dto : counselors){
            int count = counselorAvailabilityRepo.countBookedSessionsByUserIdAndDate(dto.getId(),startOfWeek,endOfWeek);
            CounselorPaymentProcess process = new CounselorPaymentProcess();
            process.setCounselorid(dto.getId());
            process.setCounselorName(dto.getFullname());
            process.setSessionCount(count);
            process.setTotalAmount(count*500);
            paymentProcessesList.add(process);
        }

        return ResponseEntity.ok().body(paymentProcessesList);

    }

    @Override
    public ResponseEntity<?> procesPayment(int id) throws IOException {

        URL url = new URL("https://api.razorpay.com/v1/payouts");
        String responseBody = null;
        // Open a connection
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Authorization", "Basic " + Base64.getEncoder().encodeToString((key + ":" + secret).getBytes()));


        LocalDateTime currentDate = LocalDateTime.now();
        LocalDateTime endOfWeek = currentDate.getDayOfWeek().equals(DayOfWeek.SATURDAY)? currentDate : currentDate.minusDays(currentDate.getDayOfWeek().getValue() - 1);
        LocalDateTime startOfWeek = endOfWeek.minusDays(6);
        int count = counselorAvailabilityRepo.countBookedSessionsByUserIdAndDate(id,startOfWeek,endOfWeek);
        Optional<CounselorFundAccount> fundAccount = fundAccountRepo.findByUserId(id);

        if(fundAccount.isPresent()){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("account_number", "2323230025711664");
            jsonObject.put("fund_account_id", fundAccount.get().getFundAccount());
            jsonObject.put("amount", count*50000);
            jsonObject.put("currency", "INR");
            jsonObject.put("mode", "NEFT");
            jsonObject.put("purpose", "salary");
            jsonObject.put("queue_if_low_balance", true);
            jsonObject.put("reference_id", "no reference");
            jsonObject.put("narration", "WeeklyPayment");
            jsonObject.put("mode", "IMPS");

            JSONObject notes = new JSONObject();
            notes.put("notes_key_1", "counselorPayment");
            notes.put("notes_key_2", "counselorPayment");

            jsonObject.put("notes", notes);

            String jsonInputString = jsonObject.toString();

            conn.setDoOutput(true);
            try(OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = in.readLine()) != null) {
                    response.append(line);
                }
                responseBody = response.toString();
                System.out.println("Response Body: " + responseBody);
            }
            /*if(responseBody!=null){
                JSONObject jsonResponse = new JSONObject(responseBody);
                String id = jsonResponse.getString("id");
                try{
                    createFundAccount(dto,id);
                }catch (Exception e){
                    e.printStackTrace();
                    return ResponseEntity.badRequest().build();
                }*/

        }



            // Close the connection
            conn.disconnect();
            return ResponseEntity.ok().body("payment successfull");




    }


}
