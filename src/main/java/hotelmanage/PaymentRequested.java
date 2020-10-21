package hotelmanage;

public class PaymentRequested extends AbstractEvent {

    private Integer reservationNumber;
    private Integer PaymentPrice;
    private String reserveStatus;


    public PaymentRequested(){
        super();
    }

    public Integer getReservationNumber() {
        return reservationNumber;
    }

    public void setReservationNumber(Integer reservationNumber) {
        this.reservationNumber = reservationNumber;
    }



    public String getReserveStatus() {
        return reserveStatus;
    }

    public void setReserveStatus(String reserveStatus) {
        this.reserveStatus = reserveStatus;
    }


    public Integer getPaymentPrice() {
        return PaymentPrice;
    }

    public void setPaymentPrice(Integer paymentPrice) {
        PaymentPrice = paymentPrice;
    }

}
