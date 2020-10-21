package hotelmanage;

import hotelmanage.config.kafka.KafkaProcessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class PolicyHandler{

    @Autowired
    ReservationManagementRepository reservationManagementrepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverPaymentCompleted_ChangeResvStatus(@Payload PaymentCompleted paymentcompleted){
        if(paymentcompleted.isMe()){
            System.out.println("====================================결제완료 1차====================================");
            if(reservationManagementrepository.findById(paymentcompleted.getReservationNumber()) != null){
                System.out.println("====================================결제완료====================================");
                ReservationManagement reservationManagement = reservationManagementrepository.findById(paymentcompleted.getReservationNumber()).get();
                reservationManagement.setReserveStatus("paymentComp");
                reservationManagementrepository.save(reservationManagement);
            }

        }

    }

}
