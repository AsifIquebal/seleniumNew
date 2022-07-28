import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class BillPaymentData {
    String testId;
    String payeeName;
    String payeeAddress;
    String payeeCity;
    String payeeState;
    String payeeZip;
    String payeePhone;
    String payeeAccountNum;
    String payeeVerifyAccountNum;
    String amount;

}
