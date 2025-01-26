package helper;

public class WaitHelper {


    public void waitSomeTime(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }



}
