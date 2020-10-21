package hotelmanage;

import javax.persistence.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hotelmanage.config.kafka.KafkaProcessor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.util.MimeTypeUtils;

import java.util.List;

@Entity
@Table(name="ReservationManagement_table")
public class ReservationManagement {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer reserveNo;
    private Integer roomNo;
    private String reserveStatus;
    private Integer customerId;
    private String customerName;
    private String customerPhone;
    private Integer ReservePrice;
    private Integer ReserveDate;

    @PrePersist
    public void onPrePersist(){
        setReserveStatus("reserve");
        Reserved reserved = new Reserved();
        reserved.setReserveNo(this.getReserveNo());
        reserved.setReserveStatus(this.getReserveStatus());
        reserved.setCustomerName(this.getCustomerName());
        reserved.setCustomerId(this.getCustomerId());
        reserved.setRoomNo(this.getRoomNo());
        reserved.setReservePrice(this.getReservePrice());

        reserved.publishAfterCommit();
    }

    @PreUpdate
    public void onPreUpdate(){
        if("reserve".equals(this.getReserveStatus())){
            Reserved reserved = new Reserved();
            reserved.setReserveNo(this.getReserveNo());
            reserved.setReserveStatus(this.getReserveStatus());
            reserved.setCustomerName(this.getCustomerName());
            reserved.setCustomerId(this.getCustomerId());
            reserved.setRoomNo(this.getRoomNo());
            reserved.setReservePrice(this.getReservePrice());
            ObjectMapper objectMapper = new ObjectMapper();
            String json = null;

            try {
                json = objectMapper.writeValueAsString(reserved);
            } catch (JsonProcessingException e) {
                throw new RuntimeException("JSON format exception", e);
            }
            System.out.println("Reserve Json : " + json);

            KafkaProcessor processor = Application.applicationContext.getBean(KafkaProcessor.class);
            MessageChannel outputChannel = processor.outboundTopic();

            outputChannel.send(MessageBuilder
                    .withPayload(json)
                    .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON)
                    .build());

            //Following code causes dependency to external APIs
            // it is NOT A GOOD PRACTICE. instead, Event-Policy mapping is recommended.

            //fooddelivery.external.결제이력 결제이력 = new fooddelivery.external.결제이력();

            hotelmanage.external.Payment payment = new hotelmanage.external.Payment();
            payment.setReservePrice(getReservePrice());
            payment.setReserveNo(getReserveNo());
            payment.setReservationStatus(getReserveStatus());
            //paymentManagement.CompletePayment(getreserveNo(), getReservePrice(), getReserveStatus());

            Application.applicationContext.getBean(hotelmanage.external.PaymentManagementService.class).CompletePayment(payment);
            //setReserveStatus("paymentComp");
            System.out.println("PaymentCompleted reserveNo= " + getReserveNo());
        }
        else if("checkOut".equals(this.getReserveStatus())){
            CheckedOut checkedOut = new CheckedOut();
            checkedOut.setReserveNo(this.getReserveNo());
            checkedOut.setReserveStatus(this.getReserveStatus());
            checkedOut.setCustomerName(this.getCustomerName());
            checkedOut.setCustomerId(this.getCustomerId());
            checkedOut.setRoomNo(this.getRoomNo());
            ObjectMapper objectMapper = new ObjectMapper();
            String json = null;

            try {
                json = objectMapper.writeValueAsString(checkedOut);
            } catch (JsonProcessingException e) {
                throw new RuntimeException("JSON format exception", e);
            }
//        System.out.println(json);

            KafkaProcessor processor = Application.applicationContext.getBean(KafkaProcessor.class);
            MessageChannel outputChannel = processor.outboundTopic();

            outputChannel.send(MessageBuilder
                    .withPayload(json)
                    .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON)
                    .build());
            System.out.println(checkedOut.toJson());
            checkedOut.publishAfterCommit();
            setReserveStatus("End");
        }
    }

    public Integer getReserveNo() {
        return reserveNo;
    }
    public void setReserveNo(Integer reserveNo) {
        this.reserveNo = reserveNo;
    }
    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }
    public String getReserveStatus() {
        return reserveStatus;
    }

    public void setReserveStatus(String reserveStatus) {
        this.reserveStatus = reserveStatus;
    }
    public Integer getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(Integer roomNo) {
        this.roomNo = roomNo;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public Integer getReserveDate() {
        return ReserveDate;
    }

    public void setReserveDate(Integer paymentDate) {
        this.ReserveDate = paymentDate;
    }

    public Integer getReservePrice() {
        return ReservePrice;
    }

    public void setReservePrice(Integer ReservePrice) {
        this.ReservePrice = ReservePrice;
    }
}
