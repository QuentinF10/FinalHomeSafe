package homesafe.digital;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;

public class Code {
    private AccessSafe safe;
    private String pwd;
    private String pin;
    private String tempPin;
    private String pinDisplay;
    private String pinLabel;
    private boolean pinEntry;
    private int accessCount;
    private ArrayList<Boolean> reset;
    private ArrayList<Boolean> override;
    private MailService mailService = new MailService();

    public Code(String pwd, AccessSafe safe) throws IOException {
        this.pin ="";
        this.tempPin ="";
        this.pinDisplay="";
        this.pinLabel ="";
        this.pinEntry = true;
        this.accessCount = 0;
        this.reset = new ArrayList<>(3);
        this.reset.add(false);
        this.reset.add(false);
        this.reset.add(false);

        this.override = new ArrayList<>(2);
        this.override.add(false);
        this.override.add(false);

        this.pwd = pwd;
        this.safe = safe;
    }

    public boolean test(String pin){
        return pin.equals(this.pwd);
    }

    public void regularEntry(char k){
        this.pinEntry = true;
        this.pin += k;
        this.pinDisplay += " " + '*';
        this.pinLabel += " " + k;
        this.safe.getDisplay().clear();
        this.safe.getDisplay().appendText(pinDisplay);
        this.safe.getPin().setText(pinLabel);

        if(pin.length() == 4){
            System.out.println("Pin = " + pin);
            this.safe.getTimer().getTimeline().stop();
            this.safe.getDisplay().clear();
            if(this.safe.getCode().test(pin)) {
                this.safe.getDisplay().accessGranted();
            }
            else{
                this.safe.getDisplay().accessDenied();
            }
            this.pin ="";
            this.pinDisplay = "";
            this.pinLabel ="";
            this.tempPin = "";
        }
    }

    public void reset(char k){
        this.pin += k;
        this.pinDisplay += " " + '*';
        this.pinLabel += " " + k;
        safe.getDisplay().clear();
        safe.getDisplay().appendText(pinDisplay);
        safe.getPin().setText(pinLabel);

        if(pin.length() == 4) {
            this.safe.getTimer().getTimeline().stop();
            if (reset.get(0) && reset.get(1) && reset.get(2)) {
                safe.getDisplay().clear();
                if(safe.getCode().test(pin)) {
                    reset.set(0,false);
                    reset.set(1,false);
                    reset.set(2,false);
                    this.safe.getCode().setPwd(pin);
                    this.safe.getDisplay().resetComplete(pinLabel);
                }
                else{
                    reset.set(2,false);
                    safe.getCode().setPwd(tempPin);
                    safe.getDisplay().newPassword();
                }
                pin ="";
                pinDisplay = "";
                pinLabel = "";
            } else if (reset.get(0) && reset.get(1)) {
                safe.getDisplay().clear();
                safe.getCode().setPwd(pin);
                if(safe.getCode().test(pin)) {
                    reset.set(2,true);
                    safe.getDisplay().confirmPassword();
                }
                else{
                    reset.set(1,false);
                    safe.getCode().setPwd(tempPin);
                    safe.getDisplay().newPassword();
                }
                pin ="";
                pinDisplay = "";
                pinLabel = "";

            } else if (reset.get(0)) {
                safe.getDisplay().clear();
                if(safe.getCode().test(pin)) {
                    reset.set(1,true);
                    safe.getDisplay().newPassword();
                }
                else{
                    safe.getDisplay().tryAgain();
                }
                pin ="";
                pinDisplay = "";
                pinLabel ="";
            }
        }
    }

    public void override(char k) {
        this.pin += k;
        this.pinDisplay += " " + '*';
        this.pinLabel += " " + k;
        safe.getDisplay().clear();
        safe.getDisplay().appendText(pinDisplay);

        if (pin.length() == 4) {
            this.safe.getTimer().getTimeline().stop();
            if (override.get(0) && override.get(1)) {
                safe.getDisplay().clear();
                if (safe.getCode().test(pin)) {
                    override.set(0, false);
                    override.set(1, false);
                    safe.getCode().setPwd(pin);
                    safe.getDisplay().overrideComplete(pinLabel);
                } else {
                    override.set(1, false);
                    safe.getDisplay().newPassword();
                }
                pin = "";
                pinDisplay = "";
                pinLabel = "";
            } else if (override.get(0)) {
                safe.getDisplay().clear();

                safe.getCode().setPwd(pin);

                override.set(1, true);
                safe.getDisplay().confirmPassword();

                pin = "";
                pinDisplay = "";
                pinLabel = "";
            }
        }
    }

    public String getPwd(){
        return this.pwd;
    }

    public void setPwd(String pwd){
        this.pwd = pwd;
    }

    public String getPin() {
        return this.pin;
    }

    public ArrayList<Boolean> getReset(){
        return this.reset;
    }

    public String getTempPin(){
        return this.tempPin;
    }

    public Boolean isPinEntry(){
        return this.pinEntry;
    }

    public ArrayList<Boolean> getOverride(){
        return this.override;
    }

    public void resetVariables(){
        this.pin ="";
        this.pinDisplay ="";
        this.pinLabel="";
        this.tempPin="";
    }

    public void incrementAccessCount(){
        this.accessCount++;
        if(accessCount > 3){
            this.safe.setLockout(true);
            this.safe.getDisplay().lockOut();
            this.safe.getTimer().lockout();
            this.accessCount = 0;
            mailService.sendEmail("qfaye@unm.edu", "Security Alert from HomeSafe", "An incorrect PIN was entered 4 times on your safe. Please check for any suspicious activity.");

            this.resetVariables();
        }
    }
}
