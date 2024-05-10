package com.unleash.consultationservice.Service;

import com.razorpay.*;
import com.unleash.consultationservice.DTO.CreateContactDto;
import com.unleash.consultationservice.DTO.UserDto;
import com.unleash.consultationservice.Interface.UserClient;
import com.unleash.consultationservice.Model.*;

import com.unleash.consultationservice.Repository.*;
import com.unleash.consultationservice.Service.serviceInterface.PaymentService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.*;

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
    private WeekRepository weekRepository;

    @Autowired
    private WeeklySessionRepositroy weeklySessionRepositroy;

    @Autowired
    private UserClient userClient;

    @Autowired
    private CounselorTransactionRepository counselorTransactionRepository;


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
        jsonObject.put("reference_id", String.valueOf(dto.getReferenceId()));

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






    @Override
    public ResponseEntity<?> getLastWeekPayments(int pageNo){
        WeekData week = weekRepository.findFirstByOrderByIdDesc();
        Pageable pageable = PageRequest.of(pageNo,10);
        Page<WeeklySession> weeklySessionList =  weeklySessionRepositroy.findByWeek(week,pageable);
        return ResponseEntity.ok().body(weeklySessionList);
    }

    @Override
    public ResponseEntity<?> procesPayment(int weeklySessionId) throws IOException {

        URL url = new URL("https://api.razorpay.com/v1/payouts");
        String responseBody = null;
        // Open a connection
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Authorization", "Basic " + Base64.getEncoder().encodeToString((key + ":" + secret).getBytes()));




        WeeklySession weeklySession = weeklySessionRepositroy.findById(weeklySessionId).orElse(null);
        Optional<CounselorFundAccount> fundAccount = fundAccountRepo.findByUserId(weeklySession.getUserId());
        if(weeklySession!=null && fundAccount.isPresent()){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("account_number", "2323230025711664");
            jsonObject.put("fund_account_id", fundAccount.get().getFundAccount());
            jsonObject.put("amount", weeklySession.getAmount()*100);
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
                if(responseBody!=null){
                    JSONObject jsonResponse = new JSONObject(responseBody);
                    CounselorTransactions transactions = new CounselorTransactions();
                    transactions.setAmount(weeklySession.getAmount());
                    transactions.setPayedOn(LocalDateTime.now());
                    transactions.setFundAccountid(fundAccount.get().getFundAccount());
                    transactions.setPayoutId(jsonResponse.getString("id"));
                    transactions.setUserId(weeklySession.getUserId());
                    counselorTransactionRepository.save(transactions);
                    weeklySession.setTransactions(transactions);
                    weeklySession.setPayed(true);
                    weeklySessionRepositroy.save(weeklySession);
                }
            }
        }



            // Close the connection
            conn.disconnect();
            return ResponseEntity.ok().body("payment successfull");

    }

    @Override
    public ResponseEntity<?> getMyTransactions(int userId) {
       List<CounselorTransactions>transactions=  counselorTransactionRepository.findByUserIdOrderByIdDesc(userId).orElse(null);
       if(transactions!=null){
           return ResponseEntity.ok().body(transactions);
       }else {
           return ResponseEntity.notFound().build();
       }
    }



    @Override
    public ResponseEntity<?> getAllTransactionsPage(int page){
        Page<CounselorTransactions> transactionsPage =  counselorTransactionRepository.findAll(PageRequest.of(page, 10).withSort(Sort.by("id").descending()));
        return ResponseEntity.ok().body(transactionsPage);
    }

    @Override
    public ResponseEntity<?> processPaymentOfSelected(List<Integer> selectedIds) throws IOException {
        for(int i : selectedIds){
            procesPayment(i);
        }
        return ResponseEntity.ok().body("payment processed successfully ");
    }


    @Scheduled(cron = "0 0 1 * * SUN")
    public void runWeeklyPaymentCalculation(){
        List<UserDto> counselors = userClient.findAllCounselors();
        LocalDateTime currentDate = LocalDateTime.now().withHour(0).withMinute(0);
        LocalDateTime endOfWeek = currentDate.getDayOfWeek().equals(DayOfWeek.SATURDAY)? currentDate : currentDate.minusDays(currentDate.getDayOfWeek().getValue() - 1);
        LocalDateTime startOfWeek = endOfWeek.minusDays(6);

        WeekData week = new WeekData();
        week.setStartDate(startOfWeek);
        week.setEndDate(endOfWeek);
        weekRepository.save(week);


        for(UserDto dto : counselors){
            int count = counselorAvailabilityRepo.countBookedSessionsByUserIdAndDate(dto.getId(),startOfWeek,endOfWeek);
            if(count>0){
                WeeklySession weeklySession = new WeeklySession();
                weeklySession.setAmount(count*500);
                weeklySession.setCount(count);
                weeklySession.setUserId(dto.getId());
                weeklySession.setPayed(false);
                weeklySession.setUserName(dto.getFullname());
                weeklySession.setWeek(week);
                weeklySessionRepositroy.save(weeklySession);
            }

        }
    }





}
