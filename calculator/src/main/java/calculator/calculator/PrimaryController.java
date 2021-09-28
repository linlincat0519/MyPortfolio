package calculator.calculator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;


import java.math.BigDecimal;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class PrimaryController {

	@FXML
		private TextField result;//顯示畫面
		//https://www.runoob.com/java/java-linkedlist.html ArrayList and LinkedList差別
		private LinkedList<String> numbers = new LinkedList<String>();//第一個值
		private LinkedList<String> store = new LinkedList<String>();//第二個值
		private BigDecimal bigDecimal; //四則運算
			Pattern pattern = Pattern.compile("\\d+|\\d.?\\d");
			
		public void initialize(URL location,ResourceBundle resources) {
			}
		//數字鍵
		public void PressNumberButton(@SuppressWarnings("exports")ActionEvent event) {
			String number = getButtonValue(event);//輸入數目
			String textResult = result.getText();
			//
			if(".".equals(number.trim())) {
				boolean contains = textResult.contains(".");
				if(contains || isBlank (textResult)) {
					return;
				}else {
					result.setText(textResult+".");
					return;
				}	
			}
			result.setText(result.getText()+number);
		}
		//建立功能鍵、+、-、*、/
		 public void pressYunsuanfu(@SuppressWarnings("exports")ActionEvent event) {
			String text =  result.getText();
	        if (!pattern.matcher(text).matches()) {
	            return;
	        }
	        if (isBlank(text)){
	            return;
	        }
	       
			numbers.add(text);
			String buttonValue = getButtonValue(event);
			store.add(buttonValue);//功能鍵的顯示
			result.setText(""); //當清理螢幕時
		}
		    @SuppressWarnings("deprecation")
		    //執行的計算
			public void pressDengyu(@SuppressWarnings("exports") ActionEvent event) {
		        boolean numbersEmpty = numbers.isEmpty();
		        boolean yunsuanfuEmpty = store.isEmpty();
		        if(numbersEmpty||yunsuanfuEmpty){
		            result.setText(result.getText());
		            return;
		        }
		 
		        if(numbers.size()>0){
		            numbers.add(result.getText());
		            bigDecimal=new BigDecimal(numbers.getFirst());//輸入的值進行四則運算
		            for (int i=1;i<numbers.size();i++){
		                switch (store.get(i-1)){//進行四則運算的選擇
		                    case "+"://加號處理
		                        bigDecimal=bigDecimal.add(new BigDecimal(numbers.get(i)));
		                        break;
		                    case "-"://減號處理
		                        bigDecimal=bigDecimal.subtract(new BigDecimal(numbers.get(i)));
		                        break;
		                    case "/"://除數整理
		                        BigDecimal decimal = new BigDecimal(numbers.get(i));
		                        int compareTo = decimal.compareTo(new BigDecimal("0"));
		                        if(compareTo==0){//如果當分母為0時需的處理
		                            result.setText("分母不能為0");
		                            numbers.clear(); 
		                            store.clear();
		                            return;
		                        }
		                        this.bigDecimal = bigDecimal.divide(new BigDecimal(numbers.get(i)), 20, BigDecimal.ROUND_UP);
		                        break;
		                    case "*"://乘號處理
		                        this.bigDecimal = this.bigDecimal.multiply(new BigDecimal(numbers.get(i)));
		                        break;
		 
		                }
		            }
		            //當清理螢幕時
		            result.setText(bigDecimal.stripTrailingZeros().toString());
		            numbers.clear();
		            store.clear();
		        }
		    }
		    //當執行C鍵時
		public void pressClear(@SuppressWarnings("exports")ActionEvent event) {
			result.setText("");
			store.clear();
			numbers.clear();
		}
		//當執行"<-"鍵注意事項
		public void pressDeleteOne(@SuppressWarnings("exports")ActionEvent event) {
			String text = result.getText();
			if(text != null && text != ""&& text.length()>0) {
				text = text.substring(0,text.length()-1);
				result.setText(text);
				
			}
			
		}
		
		private String getButtonValue(ActionEvent event) {//設定案件數字
			Button button = (Button)event.getSource();
			return button.getText();
		}
		private boolean isBlank(String text) {
			if(text!=null&&text!=""&&text.length()>0){
	            return false;
	        }
	        return true;
		}

	}
