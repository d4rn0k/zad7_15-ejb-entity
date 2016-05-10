import pl.jrj.game.IGameRemote;

import javax.ejb.Stateless;

@Stateless
public class GameManager implements IGameRemote {

    @Override
    public boolean register(int hwork, String album) {
        return hwork == 6;
    }
}
