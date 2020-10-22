package accommodation;

public class Reserved extends AbstractEvent {

    private Integer reserveNo;
    private String customerName;
    private Integer customerId;
    private String reserveStatus;
    private Integer roomNo;

    public Integer getReservePrice() {
        return ReservePrice;
    }

    public void setReservePrice(Integer ReservePrice) {
        ReservePrice = ReservePrice;
    }

    private Integer ReservePrice;

    public Reserved(){
        super();
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
}
