import javafx.scene.control.Button;

/**
 * Created by Andrew on 1/26/2017.
 */
public class custButton extends Button {
    private String id;

    public custButton(String text, String id){
        super(text);
        this.id = id;
    }

    public String getCustId(){
        return id;
    }
}
