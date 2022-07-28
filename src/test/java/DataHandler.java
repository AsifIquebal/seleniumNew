import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class DataHandler {
    private String[][] inputArr;


    @Test(dataProvider = "billPaymentData")
    public void test(String input1, String input2, String input3, String input4, String input5, String input6, String input7, String input8, String input9, String input10) {
        System.out.println(input1 + " " + input2 + " " + input3 + "" + input4 + " "
                + input5 + " " + input6 + input7 + " " + input8 + " " + input9 + " " + input10);
    }

    @DataProvider(name = "billPaymentData")
    public Object[][] feedDP() {
        String[][] data = readDB();
        System.out.println("2D array length: " + data.length);
        /*for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                System.out.println("in for :: " + data[i][j]);
            }
        }*/
        return data;
    }


    private String[][] readDB() {
        String url = "jdbc:mysql://localhost:3306/classicmodels";
        String username = "root";
        String password = "root";
        String query = "select * from billpaymentdata";
        try (Connection con = DriverManager.getConnection(url, username, password);
             Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
             ResultSet rs = st.executeQuery(query)) {

            ResultSetMetaData rsmd = rs.getMetaData();

            rs.last();
            int numberOfRows = rs.getRow();
            int numberOfColumns = rsmd.getColumnCount();
            inputArr = new String[numberOfRows][numberOfColumns];
            int k = 0;
            rs.beforeFirst();
            while (rs.next()) {
                for (int i = 1; i <= numberOfColumns; i++) {
                    inputArr[k][i - 1] = rs.getString(i);
                    //System.out.print(rs.getString(i) + " ");
                }
                k++;
                //System.out.println();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return inputArr;
    }

    // TODO
    //references:https://stackoverflow.com/questions/50290521/how-to-store-the-testng-dataprovider-values-in-a-pojo-class
    @DataProvider
    public static Iterator<Object[]> getDataForUser() throws Exception {
        Object[][] testObjArray = {{ "Monika", "Jain", "30", "Female"},{ "Krishna", "Verma", "28" , "Male"}}; //You can pick this data from an excel here
        return getBillPaymentData(testObjArray);
    }

    private static Iterator<Object[]> getBillPaymentData(final Object[][] objArr) {
        List<BillPaymentData> testList = new ArrayList<>();
        for (Object[] arr : objArr) {
            BillPaymentData billPaymentData = new BillPaymentData();
            billPaymentData.setTestId(arr[0].toString())
                    .setPayeeName(arr[1].toString())
                    .setPayeeAddress(arr[2].toString())
                    .setPayeeCity(arr[3].toString())
                    .setPayeeState(arr[4].toString())
                    .setPayeeZip(arr[5].toString())
                    .setPayeePhone(arr[6].toString())
                    .setPayeeAccountNum(arr[7].toString())
                    .setPayeeVerifyAccountNum(arr[8].toString())
                    .setAmount(arr[9].toString());
            testList.add(billPaymentData);
        }
        Collection<Object[]> dp = new ArrayList<>();
        for (BillPaymentData billPaymentData : testList) {
            dp.add(new Object[]{billPaymentData});
        }
        return dp.iterator();
    }
}
