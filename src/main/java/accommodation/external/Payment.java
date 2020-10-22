package accommodation.external;

public class Payment {
    private int ReserveNo; // 받아야 되고
    private int ReservePrice; // 받아야되고
    private String ReservationStatus; //받아야되고

    public int getReserveNo() {
        return ReserveNo;
    }

    public void setReserveNo(int reserveNo) {
        ReserveNo = reserveNo;
    }

    public int getReservePrice() {
        return ReservePrice;
    }

    public void setReservePrice(int ReservePrice) {
        ReservePrice = ReservePrice;
    }

    public String getReservationStatus() {
        return ReservationStatus;
    }

    public void setReservationStatus(String reservationStatus) {
        ReservationStatus = reservationStatus;
    }
}

