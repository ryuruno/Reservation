
package hotelmanage.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * Created by uengine on 2018. 11. 21..
 */

@org.springframework.stereotype.Service
@FeignClient(name="reservationNumber", url="${api.url.payment}")
public interface PaymentManagementService {

    @RequestMapping(method= RequestMethod.POST, path="/payments", consumes = "application/json")
    public void CompletePayment(@RequestBody Payment payment);

}