import javax.swing.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DevelopmentCardDeck {

    private List<DevelopmentCard> deck = new ArrayList<>();

    public DevelopmentCardDeck(){
        for (int i = 0; i < 9; i++){
            for (int j = 0; j < ((i <= 4) ? 1 : (i == 5) ? 14 : 3); j++){
                deck.add(new DevelopmentCard(i));
            }
        }
        Collections.shuffle(deck);
    }

    public DevelopmentCard drawCard(){
        return deck.remove(0);
    }




}
